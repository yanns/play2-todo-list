package controllers;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Yaml;
import play.mvc.*;
import play.test.*;

import java.util.List;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

public class CredentialTest {

    private FakeApplication fakeApplication;

    @Before
    public void setUp() {
        fakeApplication = fakeApplication(inMemoryDatabase());
        start(fakeApplication);
        Ebean.save((List) Yaml.load("test-data.yml"));
    }

    @Test
    public void authenticateSuccess() {
        Result result = callAction(
                controllers.routes.ref.Credential.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "bob@example.com",
                        "password", "secret"))
        );
        assertEquals(303, status(result));
        assertEquals("bob@example.com", session(result).get("email"));
    }

    @Test
    public void authenticateFailure() {
        Result result = callAction(
                controllers.routes.ref.Credential.authenticate(),
                fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
                        "email", "bob@example.com",
                        "password", "badpassword"))
        );
        assertEquals(400, status(result));
        assertNull(session(result).get("email"));
    }

    @Test
    public void authenticated() {
        Result result = callAction(
                controllers.routes.ref.Application.index(),
                fakeRequest().withSession("email", "bob@example.com")
        );
        assertEquals(200, status(result));
    }

    @Test
    public void notAuthenticated() {
        Result result = callAction(
                controllers.routes.ref.Application.index(),
                fakeRequest()
        );
        assertEquals(303, status(result));
        assertEquals("/login", header("Location", result));
    }

    @After
    public void tearDown() {
        stop(fakeApplication);
    }
}
