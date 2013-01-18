package models;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

    @After
    public void tearDown() {
        stop(fakeApplication);
    }

}
