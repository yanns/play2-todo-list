package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static play.test.Helpers.*;

import java.util.List;

import com.avaje.ebean.Ebean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.test.FakeApplication;

public class ToDoTest {

    private FakeApplication fakeApplication;

    @Before
    public void setUp() {
        fakeApplication = fakeApplication(inMemoryDatabase());
        start(fakeApplication);
    }
    
    @Test
    public void findTodosByUserTest() {
    	User user = new User("tutorial@java-magazin.de", "Java", "Magazin", "secret");

    	user.save();

    	ToDo todo = new ToDo();
    	todo.description = "Java-Magazin kaufen";
    	todo.assignedUser = user;
    	todo.done = true;
    	todo.save();
    	
    	todo = new ToDo();
    	todo.description = "Play-Tutorial lesen";
    	todo.assignedUser = user;
    	todo.save();

        List<ToDo> resultList = ToDo.findTodosByUserEMail("tutorial@java-magazin.de");
        assertEquals(1, resultList.size());
        assertEquals("Play-Tutorial lesen", resultList.get(0).description);
    }

    @Test
    public void fullTest() {
        Ebean.save((List) Yaml.load("test-data.yml"));

        // Count things
        assertEquals(2, User.count());
        assertEquals(5, ToDo.count());

        // Try to authenticate as users
        assertNotNull(User.authenticate("bob@example.com", "secret"));
        assertNotNull(User.authenticate("jane@example.com", "secret"));
        assertNull(User.authenticate("jeff@example.com", "badpassword"));
        assertNull(User.authenticate("tom@example.com", "secret"));

        // Find all Bob's todo tasks
        List<ToDo> bobsTasks = ToDo.findTodosByUserEMail("bob@example.com");
        assertEquals(2, bobsTasks.size());
    }

    @After
    public void tearDown() {
        stop(fakeApplication);
    }

}
