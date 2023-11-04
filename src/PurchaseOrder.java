import java.util.HashMap;
import java.util.List;

public class PurchaseOrder {
    private Store store;  // Name of the store associated with the purchase order.
    private List<Item> items;  // The item being ordered.
    private List<Integer> quantities;
    private String supplier;
    private String status;  // e.g., "pending," "approved," "delivered"

    private final String number;

    public static HashMap<String, PurchaseOrder> DB = new HashMap<>();
    public  static  int ID = 0;

    public String getNumber() {
        return number;
    }

    public PurchaseOrder(Store store, List<Item> items, List<Integer> quantities, String supplier, String status) {
        this.store = store;
        this.items = items;
        this.quantities = quantities;
        this.supplier = supplier;
        this.status = status;
        this.number = "po-" + ++PurchaseOrder.ID;

        if(PurchaseOrder.DB == null) PurchaseOrder.DB = new HashMap<>();
    }

    public void save() {
        PurchaseOrder.DB.put(this.number, this);
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItem(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void addItem(Item item, int quantity){
        this.items.add(item);
        this.quantities.add(quantity);
    }


    public void setQuantity(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        StringBuilder output = new StringBuilder("\n{\n");
        output.append("\tNumber: ").append(this.number).append("\n");
        output.append("\tStatus: ").append(this.status).append("\n");
        output.append("\tSupplier: ").append(this.supplier).append("\n");
        output.append("\tStore: ").append(this.store.getName()).append("\n");
        output.append("\tItems:\n");
        output.append("\t------\n");
        output.append("\t{\n");

        for(int i = 0; i < this.items.size(); i++){
            output.append("\t\tItem: ").append(this.items.get(i).getName()).append(" x ").append(this.quantities.get(i)).append("pcs \n");
        }
        output.append("\t}\n");
        output.append("}\n");

        return output.toString();
    }
}
