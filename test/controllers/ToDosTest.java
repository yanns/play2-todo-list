package controllers;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;
import models.ToDo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.mvc.Result;
import play.test.FakeApplication;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static play.test.Helpers.*;

public class ToDosTest {

    private FakeApplication fakeApplication;

    @Before
    public void setUp() {
        fakeApplication = fakeApplication(inMemoryDatabase());
        start(fakeApplication);
        Ebean.save((List) Yaml.load("test-data.yml"));
    }

    @Test
    public void toDoCanBeAdded() {
        int bobToDos = ToDo.findTodosByUserEMail("bob@example.com").size();
        Result result = callAction(
                routes.ref.ToDos.add(),
                fakeRequest().withSession("email", "bob@example.com")
                        .withFormUrlEncodedBody(ImmutableMap.of(
                                "description", "a new TODO"
                        ))
        );
        assertEquals(303, status(result));
        assertEquals(bobToDos + 1, ToDo.findTodosByUserEMail("bob@example.com").size());
    }

    @Test
    public void toDoCanBeDeleted() {
        List<ToDo> bobToDos = ToDo.findTodosByUserEMail("bob@example.com");
        Long toDoId = bobToDos.get(0).id;
        Result result = callAction(
                routes.ref.ToDos.delete(toDoId),
                fakeRequest().withSession("email", "bob@example.com")
        );
        assertEquals(200, status(result));
        assertEquals(bobToDos.size() - 1, ToDo.findTodosByUserEMail("bob@example.com").size());

        Result deleteShouldBeIdempotent = callAction(
                routes.ref.ToDos.delete(toDoId),
                fakeRequest().withSession("email", "bob@example.com")
        );
        assertEquals(200, status(deleteShouldBeIdempotent));
    }

    @Test
    public void deleteIsIdempotent() {
        List<ToDo> bobToDos = ToDo.findTodosByUserEMail("bob@example.com");
        Long toDoId = bobToDos.get(0).id;
        callAction(
                routes.ref.ToDos.delete(toDoId),
                fakeRequest().withSession("email", "bob@example.com")
        );
        Result result = callAction(
                routes.ref.ToDos.delete(toDoId),
                fakeRequest().withSession("email", "bob@example.com")
        );
        assertEquals(200, status(result));
    }

    @After
    public void tearDown() {
        stop(fakeApplication);
    }

}
