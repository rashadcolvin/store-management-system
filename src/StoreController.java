import java.util.ArrayList;
import java.util.Map;

public class StoreController extends Controller<Store> {
    public ControllerResponse<Store> create(User user, String name, String location, String contactInfo, String type, String openingDate) {
        if(!user.canCreateStore()) {
            return  this.respond(false, "Unauthorized", this.data());
        }

        Store s = new Store(name, location, contactInfo, type, openingDate);
        s.save();

        return this.respond(true, "Successfully created store", this.data(s));
    }

    public ControllerResponse<Store> view(User user, String name) {
        if(!Store.DB.containsKey(name)) return this.respond(false, "404: Store not found", this.data());
        Main.log("Current Store Information");

        Main.log("=========================");
        Store store = Store.DB.get(name);
        Main.log( store + "");
        Main.log("=========================");

        return this.respond(true, "Store Found", this.data(Store.DB.get(name)));
    }

    public void viewAll(User user) {

        Main.log("All stores");
        Main.log("=========================");

        Store.DB.forEach((s, store) -> this.view(user, s));
    }

    public ControllerResponse<Store> update(User user, String oldName, String name) {
        if(!user.canUpdateStore()) {
            return  this.respond(false, "Unauthorized", this.data());
        }

        Store s = Store.DB.get(oldName);

        s.delete();
        s.setName(name);
        s.save();

        return this.respond(true, "Successfully updated store", this.data(s));
    }

    public ControllerResponse<Store> delete(User user, String name) {
        if(!user.canDeleteStore()) {
            return  this.respond(false, "Unauthorized to Delete " + name, this.data());
        }

        if(!Store.DB.containsKey(name)) return this.respond(false, "404: Store not found", this.data());



        Store s = Store.DB.get(name);
        s.delete();

        return this.respond(true, "Successfully deleted store { " + s.getName() + " }", this.data(s));
    }
    public ControllerResponse<Store> addItemToStore(User user) {

        if (!user.canAddItemsToInventory()) {
            return respond(false, "403: Unauthorized to add items to inventory", data());
        }

        Main.log("Adding Item to Store");
        Main.log("=====================");
        Main.log("Enter store name:");
        String storeName = input();

        if (!Store.DB.containsKey(storeName)) {
            return respond(false, "404: Store not found", data());
        }

        Main.log("Enter Item ID: ");
        String itemId = input();

        if (!Item.exist(storeName)) {
            return respond(false, "404: Item not found", data());
        }

        Item item = Item.DB.get(itemId);

        Main.log("Enter quantity: - Current has " + item.getStockLevel() + "pcs");
        int quantity = Integer.parseInt(input());

        item.setStockLevel(Math.max(quantity, item.getStockLevel()));

        Store store = Store.DB.get(storeName);
        store.addItemToInventory(item);

        return respond(true, "Successfully added item: " + itemId + " to store inventory", data(store));
    }

    public ControllerResponse<Store> search(User user) {
        Main.log("Search Store by: ");
        Main.log("1. name");
        Main.log("2. location");
        Main.log("3. type");
        Main.log("4. opening date");

        Main.log("Your choice: ");
        String choice = this.input();

        ArrayList<Store> result = new ArrayList<>();

        switch (choice) {
            case "1":
                Main.log("Enter store name: ");
                String name = this.input();
                if(!Store.exists(name)) {
                    Main.log("Not Store exist for your search");
                }
                else {
                    Main.log("-----------------Results-------------------");
                    Main.log(Store.DB.get(name) + "");
                    result.add(Store.DB.get(name));
                    Main.log("---------------Results ends-----------------");
                }

                break;
            case "2":
                Main.log("Enter location name: ");
                String location = this.input();
                Main.log("-----------------Results-------------------");

                for(Map.Entry<String, Store> storeEntry: Store.DB.entrySet()) {
                    Store store = storeEntry.getValue();
                    if(store.getLocation().equals(location)) {
                        Main.log(store + "");
                        result.add(store);
                    }
                }

                Main.log("---------------Results ends-----------------");
                break;
            case "3":
                Main.log("Enter store type: ");
                String type = this.input();
                Main.log("-----------------Results-------------------");

                for(Map.Entry<String, Store> storeEntry: Store.DB.entrySet()) {
                    Store store = storeEntry.getValue();
                    if(store.getType().equals(type)) {
                        Main.log(store + "");
                        result.add(store);
                    }
                }

                Main.log("---------------Results ends-----------------");
                break;
            case "4":
                Main.log("Enter opening date: ");
                String date = this.input();
                Main.log("-----------------Results-------------------");

                for(Map.Entry<String, Store> storeEntry: Store.DB.entrySet()) {
                    Store store = storeEntry.getValue();
                    if(store.getType().equals(date)) {
                        Main.log(store + "");
                        result.add(store);
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
