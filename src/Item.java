import java.util.HashMap;
import java.util.Map;

public class Item {
    private String name;
    private String id;

    private String description;

    private int stockLevel = 1;


    public static HashMap<String, Item> DB = new HashMap<>();

    public static int ID = 0;

    private double price;
    private String category;

    public Item(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.id = "it-" + ++Item.ID;

        if(Item.DB == null) Item.DB = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public String toString() {
        return "\n{\n" +
                "\tname: " + this.name + "\n" +
                "\tcategory: " + this.category + "\n" +
                "\tprice: " + this.price + "\n" +
                "\tdescription: " + this.description + "\n" +
                "\tstock level: " + this.stockLevel + "\n" +
                "\tID: " + this.id + "\n" +
                "\n}\n";
    }

    public void save(){
        Item.DB.put(this.id, this);
    }

    public void delete(){
        Item.DB.remove(this.id);
    }

    public static boolean exist(String itemId) {
        return Item.DB.containsKey(itemId);
    }

    public static boolean whereName(String name) {

        for(Map.Entry<String, Item> item: Item.DB.entrySet()){
            if(item.getValue().name.equals(name)) return true;
        }
        return false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ){
        this.price = price;
    }

    public String getCategory () {
        return category;
    }

    public void setCategory (String category){
        this.category = category;
    }

}