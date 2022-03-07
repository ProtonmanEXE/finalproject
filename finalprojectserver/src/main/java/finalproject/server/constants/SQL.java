package finalproject.server.constants;

public class SQL {

    // for authenication    
    public static final String SQL_VERIFY_USER = 
        "select user_name, password, enabled from user_accounts where user_name = ?";

    public static final String SQL_CHECK_USER_AUTHORITY = 
        "select user_name, authority from user_accounts where user_name = ?";
    
    // for wishlist    
    public static final String SQL_ADD_TO_WISHLIST = 
        "insert into wishlist(game_id, user_name, title, image_url, release_date) values (?, ?, ?, ?, ?)";

    public static final String SQL_GET_GAMES_BY_USERNAME = 
        "select * from wishlist where user_name = ?";

    public static final String SQL_DELETE_WISH = 
        "delete from wishlist where user_name = ? AND game_id = ?"; 

    // to save new user    
    public static final String SQL_SAVE_NEWUSER = 
        "insert into user_accounts(user_name, password, email, authority, enabled) values (?, ?, ?, ?, ?)";
        // "insert into user_accounts(user_name, password, email, authority, enabled) values (?, sha1(?), ?, ?, ?)";

}
