public class CategoryController extends Controller<Category> {
    public ControllerResponse<Category> create(User user, String name) {
        if(!user.canManageCategories()) return this.respond(false, "You are not permitted to manage categories", this.data());

        Category c = new Category(name);
        c.save();

        return this.respond(true, "Successfully created category " + name, this.data(c));
    }

    public ControllerResponse<Category> update(User user, String currentName, String newName) {
        if(!user.canManageCategories()) return this.respond(false, "You are not permitted to manage categories", this.data());

        if(!Category.exist(currentName)) {
            return this.respond(false, "404: Category not found", this.data());
        }
        Category c = Category.DB.get(currentName);
        c.delete();

        c.setName(newName);
        c.save();

        return this.respond(true, "Successfully updated category " + newName, this.data(c));
    }

    public ControllerResponse<Category> delete(User user, String name) {
        if(!user.canManageCategories()) return this.respond(false, "You are not permitted to manage categories", this.data());

        if(!Category.exist(name)) {
            return this.respond(false, "404: Category not found", this.data());
        }

        Category.DB.remove(name);

        return this.respond(true, "Successfully deleted category " + name, this.data());
    }

    public ControllerResponse<Category> view(User user, String name) {

        if(!Category.exist(name)) {
            return this.respond(false, "404: Category not found", this.data());
        }

        Main.log("Viewing Category {" + name + "}");
        Main.log("=========================");
        Category c = Category.DB.get(name);
        Main.log( c + "");
        Main.log("=========================");

        return this.respond(true, "Successfully found category " + name, this.data(c));
    }

    public void viewAll(User user) {
        Main.log("All Categories");
        Main.log("=========================");

        Category.DB.forEach((c, category) -> this.view(user, c));
    }
}
