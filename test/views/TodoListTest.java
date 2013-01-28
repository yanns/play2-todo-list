package views;

import com.avaje.ebean.Ebean;
import controllers.routes;
import org.junit.Test;
import play.libs.F;
import play.libs.Yaml;
import play.test.TestBrowser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static play.test.Helpers.*;

public class TodoListTest {

    @Test
    public void anUserCanLoginSeeAddAndDeleteToDo() {
        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                Ebean.save((List) Yaml.load("test-data.yml"));

                // login page
                browser.goTo("http://localhost:3333" + routes.Credential.login().url());
                assertThat(browser.$("h1", withText("Sign in"))).hasSize(1);
                browser.fill("input").with("bob@example.com", "secret");
                browser.click("button");

                // Tasks page
                browser.await().atMost(3, SECONDS).until("h1").withText("TODOS").hasSize(1);
                assertThat(browser.find("li")).hasSize(2);

                // add a TODO
                browser.fill("input").with("new TODO");
                browser.click("button");
                assertThat(browser.$("li")).hasSize(3);

                // delete the first TODO
                browser.click(browser.findFirst(".delete-class"));
                browser.await().atMost(5, SECONDS).until(".delete-class").hasSize(2);
                assertThat(browser.$("li")).hasSize(2);
            }
        });
    }
}
