package java8io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationHeaderTest {

    @Test
    public void testCreate() {
        assertEquals("Authorization: Basic amRvZTpjaGFuZ2VpdA==", new AuthorizationHeader().create("jdoe", "changeit"));
    }
}
