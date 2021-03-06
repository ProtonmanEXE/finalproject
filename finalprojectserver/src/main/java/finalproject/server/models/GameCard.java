package finalproject.server.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class GameCard {
    
    private String name;
    private String releasedDate;
    private String backgroundImageUrl;
    private String esrbRating;
    private List<String> genres;
    private Integer gameId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public static GameCard populate(SqlRowSet rs) {
        final GameCard game = new GameCard();
        game.setGameId(rs.getInt("game_id"));
        game.setName(rs.getString("title"));
        game.setBackgroundImageUrl(rs.getString("image_url"));
        game.setReleasedDate(rs.getString("release_date"));
        return game;
    }

}
