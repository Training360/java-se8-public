package java8io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizationHeaderTest {

    @Test
    public void testCreate() {
        assertEquals("Authorization: Basic amRvZTpjaGFuZ2VpdA==", new AuthorizationHeader().create("jdoe", "changeit"));
    }
}
