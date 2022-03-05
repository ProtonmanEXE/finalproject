package finalproject.server.constants;

public class SQL {
    
    public static final String SQL_VERIFY_USER = 
        "select userName, password, enabled from accounts where userName = ?";

    public static final String SQL_CHECK_USER_AUTHORITY = 
        "select userName, authority from accounts where userName = ?";

}
