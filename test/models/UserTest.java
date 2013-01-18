package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static play.test.Helpers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.FakeApplication;

public class UserTest {

    private FakeApplication fakeApplication;

    @Before
    public void setUp() {
        fakeApplication = fakeApplication(inMemoryDatabase());
        start(fakeApplication);
    }
    
    @Test
    public void createAndRetrieveUser() {
        new User("tutorial@java-magazin.de", "Java", "Magazin", "secret").save();

        User user = User.authenticate("tutorial@java-magazin.de", "secret");

        assertNotNull(user);
        assertEquals("Java", user.firstName);
        assertEquals("Magazin", user.lastName);
    }

    @After
    public void tearDown() {
        stop(fakeApplication);
    }
}
