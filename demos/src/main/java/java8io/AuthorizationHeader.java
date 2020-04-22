package java8io;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthorizationHeader {

    public String create(String username, String password) {
        String usernameAndPassword = username + ":" + password;
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(usernameAndPassword.getBytes(StandardCharsets.UTF_8));
        return "Authorization: Basic " + encoded;
    }
}
