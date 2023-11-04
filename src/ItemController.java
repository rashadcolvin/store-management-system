import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ItemController extends Controller<Item> {
    public ControllerResponse<Item> create(User user){
        if(!user.canManageItems()){
            return this.respond(false, "403: Unauthorized action", this.data());
        }

        Main.log("Creating Item");
        Main.log("===============================");

        Main.log("Enter Item Category: ");
        String category = this.input();

        if(!Category.exist(category)) {
            return this.respond(false, "404: Category Not Found", this.data());
        }

        Main.log("Enter Item name: ");
        String name = this.input();
        Main.log("Enter Item Description: ");
        String description = this.input();
        Main.log("Enter Item Initial Quantity: ");
        String quantity = this.input();
        Main.log("Enter Item Price: ");
        String price = this.input();

        Item item = new Item(name, Double.parseDouble(price), category);
        item.setStockLevel(Integer.parseInt(quantity));
        item.setDescription(description);

        item.save();
        Category.DB.get(category).addItem(item);

        return this.respond(true, "Added Item " + name + " to category " + category, this.data(item));
    }

    public ControllerResponse<Item> update(User user){
        if(!user.canManageItems()){
            return this.respond(false, "403: Unauthorized action", this.data());
        }

        Main.log("Updating Item");
        Main.log("===============================");

        Main.log("Enter Item ID: ");
        String id = this.input();

        if(!Item.exist(id)) {
            return this.respond(false, "404: Item Not Found", this.data());
        }
        Item item = Item.DB.get(id);

        Main.log("Found Item with current details: ");
        Main.log(item + "");
        Main.log("---------Updating Item: " + id + "----------");
        Main.log("* To skip updating a value, leave it blank");

        Main.log("Enter New Item Category: ");
        String category = this.input();

        if(!Category.exist(category)) {
            return this.respond(false, "404: Category Not Found", this.data());
        }

        if(!category.isEmpty() && !category.equals(Category.DB.get(category).getName())){
            Category.DB.get(category).removeItem(item.getId());
            item.setCategory(category);
        }

        Main.log("Enter New Item name: ");
        String name = this.input();

        if(!name.isEmpty()) item.setName(name);

        Main.log("Enter New Item Description: ");
        String description = this.input();

        if(!description.isEmpty()) item.setDescription(description);

        Main.log("Add/Subtract quantity (e.g 2, -5): ");
        String quantity = this.input();

        if(quantity.isEmpty()) item.setStockLevel(item.getStockLevel() + Integer.parseInt(quantity));

        Main.log("Enter New Item Price: ");
        String price = this.input();
        if(price.isEmpty()) item.setPrice(Double.parseDouble(price));

        item.save();
        Category.DB.get(category).addItem(item);

        return this.respond(true, "Updated Item: " + id + " in category: " + category, this.data(item));
    }

    public ControllerResponse<Item> delete(User user) {
        if(!user.canManageItems()){
            return this.respond(false, "Unauthorized action", this.data());
        }

        Main.log("Deleting item");
        Main.log("============================");
        Main.log("Enter item ID: ");

        String id = this.input();

        if(!Item.exist(id)) {
            return this.respond(false, "404: Item Not Found", this.data());
        }

        Item item = Item.DB.get(id);
        Category.DB.get(item.getCategory()).removeItem(id);
        item.delete();

        return this.respond(true, "Deleted item " + id + " from category: " + item.getCategory(), this.data());
    }

    public ControllerResponse<Item> view(User user, String name) {

        if(!Item.whereName(name)) {
            return this.respond(false, "404: Item not found", this.data());
        }


        Main.log("Viewing Item {" + name + "}");
        Main.log("=========================");
        ArrayList<Item> result = new ArrayList<>();

        for(Map.Entry<String, Item> item: Item.DB.entrySet()){
            if(item.getValue().getName().equals(name)) {
                Main.log( item.getValue() + "");
                result.add(item.getValue());
            }
        }

        Main.log("=========================");

        return this.respond(true, "Successfully found Item: " + name, result);
    }

    public void viewAll(User user) {
        Main.log("All Items");
        Main.log("========================================");
        HashSet<String> hasViewed = new HashSet<>();

        for(Map.Entry<String, Item> item: Item.DB.entrySet()){
            if(!hasViewed.contains(item.getValue().getName())) {
                this.view(user, item.getValue().getName())
                        .data
                        .forEach((n) -> hasViewed.add(n.getName()));
            }
        }
        Main.log("========================================");
    }

    public ControllerResponse<Item> search(User user) {
        Main.log("Search Items by: ");
        Main.log("1. name");
        Main.log("2. category");
        Main.log("3. price");

        Main.log("Your choice: ");
        String choice = this.input();

        ArrayList<Item> result = new ArrayList<>();

        switch (choice) {
            case "1":
                Main.log("Enter item name: ");
                String name = this.input();
                if(!Item.whereName(name)) {
                    Main.log("Not Item exist for your search");
                }
                else {
                    Main.log("-----------------Results-------------------");

                    for(Map.Entry<String, Item> itemEntry: Item.DB.entrySet()) {
                        if(name.equals(itemEntry.getValue().getName())) {
                            Main.log(itemEntry.getValue() + "");
                            result.add(itemEntry.getValue());
                        }
                    }
                    Main.log("---------------Results ends-----------------");
                }

                break;
            case "2":
                Main.log("Enter category name: ");
                String category = this.input();
                if(!Category.exist(category)) {
                    Main.log("Category does not exist");
                }
                else {
                    Category cat = Category.DB.get(category);
                    Main.log("-----------------Results (" + cat.getItems().size() + " items)--------------");

                    for(Map.Entry<String, Item> itemEntry: cat.getItems().entrySet()) {
                        Main.log(itemEntry.getValue() + "");
                        result.add(itemEntry.getValue());
                    }

                    Main.log("---------------Results ends-----------------");
                }
                break;
            case "3":
                Main.log("Enter Lower price");
                double lower = Double.parseDouble(this.input());
                Main.log("Enter upper price");
                double higher = Double.parseDouble(this.input());

                Main.log("-----------------Results-------------------");

                for(Map.Entry<String, Item> itemEntry: Item.DB.entrySet()) {
                    Item item = itemEntry.getValue();
                    if(item.getPrice() >= lower && item.getPrice() <= higher) {
                        Main.log(itemEntry.getValue() + "");
                        result.add(item);
                    }
                }

                Main.log("---------------Results ends-----------------");
                break;
            default:
                Main.log("Invalid choice");
        }

        return this.respond(true, "Results", result);
    }
}
