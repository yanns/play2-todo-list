package models;

import com.avaje.ebean.Ebean;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;

import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class UserTest {

    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
        Ebean.save((List<Object>) Yaml.load("test-data.yml"));
    }
    
    @Test
    public void createAndRetrieveUser() {
        new User("tutorial@java-magazin.de", "Java", "Magazin", "secret").save();

        User user = User.authenticate("tutorial@java-magazin.de", "secret");

        assertNotNull(user);
        assertEquals("Java", user.firstName);
        assertEquals("Magazin", user.lastName);
    }

    @Test
    public void canAuthenticateUserLoadedByYamlTest() {
        assertEquals(2, User.count());

        assertNotNull(User.authenticate("bob@example.com", "secret"));
        assertNull(User.authenticate("invalid@exemple.de", "secret"));
        assertNull(User.authenticate("bob@example.com", "wrong"));
    }
}
