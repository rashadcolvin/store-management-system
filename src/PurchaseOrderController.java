import java.lang.reflect.Array;
import java.util.ArrayList;

public class PurchaseOrderController extends Controller<PurchaseOrder> {
    public ControllerResponse<PurchaseOrder> create(User user) {
        if(!user.canGeneratePurchaseOrders()) {
            return this.respond(false, "403: Unauthorized to generate orders", this.data());
        }

        Main.log("Creating Purchase Orders");
        Main.log("=========================");
        Main.log("Enter store Name: ");
        String storeName = this.input();

        if(!Store.exists(storeName)) {
            return this.respond(false, "404: Store Not found", this.data());
        }

        String item = "0";
        String quantity = "1";
        ArrayList<String> items = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();

        Main.log("-------Items Selection-------");

        do{
            Main.log("Enter Item Name(0 to finish): ");
            item = this.input();

            if(!item.equals("0")) {

                if(!Item.whereName(item)) {
                    Main.log("Item does not exist. Please enter a correct name");
                    continue;
                }
                Main.log("Choose quantity for " + item + ": ");
                quantity = this.input();
                if(quantity.equals("0")) quantity = "1";
                items.add(item);
                quantities.add(quantity);
                Main.log("Added " + quantity + " pcs of " + item + " to order");
                Main.log("-----------------------");
            };

        }
        while (!item.equals("0"));

        ArrayList<Item> cart = new ArrayList<>();
        ArrayList<Integer> qts = new ArrayList<>();

        for(int i = 0; i < items.size(); i++) {
            cart.add(Item.DB.get(items.get(i)));
            qts.add(Integer.valueOf(quantities.get(i)));
        }

        Main.log("Enter Supplier details: ");
        String supplier = this.input();

        Main.log("Enter order status: ");
        String status = this.input();

        PurchaseOrder p = new PurchaseOrder(Store.DB.get(storeName), cart, qts, supplier, status);
        p.save();

        return this.respond(true, "Successfully Created Purchase order. ID: " + p.getNumber(), this.data(p));
    }

    public ControllerResponse<PurchaseOrder> view(User user, String number) {
        if(!PurchaseOrder.DB.containsKey(number)) return this.respond(false, "404: Order not found", this.data());
        Main.log("Order Information");

        Main.log("-----------------------");
        PurchaseOrder p = PurchaseOrder.DB.get(number);
        Main.log( p + "");
        Main.log("=========================");

        return this.respond(true, "Purchase Order Found", this.data(p));
    }

    public void viewAll(User user) {
        Main.log("Viewing all Orders");
        Main.log("===================");
        PurchaseOrder.DB.forEach((n, p) -> this.view(user, n));
    }
}
