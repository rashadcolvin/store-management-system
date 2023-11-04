import java.util.HashMap;
import java.util.List;

public class Category {
    private String name;
    public HashMap<String, Item> items;

    public static HashMap<String, Category> DB = new HashMap<>();

    public Category(String name) {
        this.name = name;
        this.items = new HashMap<>();
        if(Category.DB == null) Category.DB = new HashMap<>();
    }
    public static boolean exist(String name) {
        return Category.DB.containsKey(name);
    }
    public void save(){
        Category.DB.put(this.name, this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public void delete() {
        Category.DB.remove(this.name);
    }

    public List<Item> items(){
        return this.items.values().stream().toList();
    }

    public boolean hasItem(Item item){
        return this.items.containsKey(item.getId());
    }

    public void addItem(Item item) {
        this.items.put(item.getId(), item);
    }

    public void removeItem(String itemId) {
        this.items.remove(itemId);
    }

    public String toString(){
        return "\n{\n" +
                "\tname: " + this.name + "\n" +
                "\titemsCount: " + this.items.size() + "\n" +
                "}\n";
    }
}
