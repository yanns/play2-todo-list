package models;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.start;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ToDoTest {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
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
}
