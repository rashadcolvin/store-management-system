import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Store {
    private String name;
    private String location;
    private String contactInfo;
    private String type;
    private String openingDate;
    private List<Item> inventory;

    public  static HashMap<String, Store> DB = new HashMap<>();

    public Store(String name, String location, String contactInfo, String type, String openingDate) {
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
        this.type = type;
        this.openingDate = openingDate;
        this.inventory = new ArrayList<>();

        if(Store.DB == null) Store.DB = new HashMap<>();
    }

    public void save() {
        Store.DB.put(this.name, this);
    }

    public static boolean exists(String name) {return Store.DB.containsKey(name);}
    public void delete() {
        Store.DB.remove(this.name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }



    public String toString(){

        return "\n{\n" +
                "\tStore Name: " + this.name + "\n" +
                "\tStore Type: " + this.type + "\n" +
                "\tStore Location: " + this.location+ "\n" +
                "\tContact: " + this.contactInfo + "\n" +
                "}\n";
    }

}
