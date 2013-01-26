import java.util.List;

import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        // Check if the database is empty 
        // and we are running in development mode
        if (app.isDev() && User.count() == 0) {
            Logger.debug("Bootstrapping with default data");
            Ebean.save((List) Yaml.load("initial-data.yml"));
        }
    }
}