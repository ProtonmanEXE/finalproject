package finalproject.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import finalproject.server.models.GameCard;

import static finalproject.server.constants.SQL.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class GameDetailsRepos {

    @Autowired 
    private JdbcTemplate template;
    
    public boolean saveToWishList(final GameCard game, final String userName) {
        int added = template.update(SQL_ADD_TO_WISHLIST, 
            game.getGameId(),
            userName,
            game.getName(),
            game.getBackgroundImageUrl(),
            game.getReleasedDate()
        );

        return added > 0;
    }

    public List<GameCard> getWishlist(String userName) {
        final List<GameCard> wishlist = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(
            SQL_GET_GAMES_BY_USERNAME, userName);

        // loop thru row set 
        while (rs.next()) {
            // Process a row
            final GameCard game = GameCard.populate(rs);
            wishlist.add(game);
        }
        return wishlist;
    }

    public boolean deleteWish(final int gameId, final String userName) {
        int delete = template.update(SQL_DELETE_WISH, 
            userName,
            gameId
        );

        return delete > 0;
    }
    
}