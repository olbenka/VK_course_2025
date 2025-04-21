package data.objects;

import static data.UserData.*;

public class UserData {
    public static final User VALID_USER = new User(VALID_EMAIL, VALID_PASSWORD);
    public static final User INVALID_USER = new User(INVALID_EMAIL, INVALID_PASSWORD);
}
