package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(index.render(
                "Hier entsteht unsere TODO-Liste...",
                User.find.byId(request().username())
        ));
	}
}