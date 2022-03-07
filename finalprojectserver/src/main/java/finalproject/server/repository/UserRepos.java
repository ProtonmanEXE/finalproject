package finalproject.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import finalproject.server.models.FullUserDetails;

import static finalproject.server.constants.SQL.*;

@Repository
public class UserRepos {

    @Autowired 
    private JdbcTemplate template;
    
    public boolean saveUser(final FullUserDetails newUser) {
        int added = template.update(SQL_SAVE_NEWUSER, 
            newUser.getUserName(),
            newUser.getPassword(),
            newUser.getEmail(),
            "ROLE_USER",
            true
        );

        return added > 0;
    }
    
}