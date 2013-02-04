package controllers;

import models.ToDo;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import static play.data.Form.form;

@Security.Authenticated(Secured.class)
public class ToDos extends Controller {

    public static Result add() {
        String description = form().bindFromRequest().get("description");
        if (StringUtils.isEmpty(description)) {
            flash("error", "Please enter a description");
        } else {
            ToDo toDo = new ToDo();
            toDo.description = description;
            toDo.assignedUser = User.find.byId(request().username());
            toDo.save();
        }
        return redirect(routes.Application.index());
    }

    public static Result delete(Long id) {
        String email = request().username();
        ToDo toDo = ToDo.findTodoByIdAndUserEmail(id, email);
        if (toDo != null) {
            toDo.delete();
        }
        return ok();
    }

}
