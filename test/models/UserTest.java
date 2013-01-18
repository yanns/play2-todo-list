package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }
    
    @Test
    public void createAndRetrieveUser() {
        new User("tutorial@java-magazin.de", "Java", "Magazin", "secret").save();

        User user = User.authenticate("tutorial@java-magazin.de", "secret");

        assertNotNull(user);
        assertEquals("Java", user.firstName);
        assertEquals("Magazin", user.lastName);
    }
}
