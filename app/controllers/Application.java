package controllers;

import models.ToDo;
import models.User;
import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;


public class Application extends Controller {

    @Security.Authenticated(Secured.class)
	public static Result index() {
        String email = request().username();
		return ok(index.render(
                ToDo.findTodosByUserEMail(email),
                User.find.byId(email)
        ));
	}

    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                controllers.routes.javascript.ToDos.delete()
            )
        );
    }
}