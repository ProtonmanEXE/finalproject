package finalproject.server.models;

import java.util.List;

public class GameDetails extends GameCard {
    
    private String description;
    private String metacriticRating;
    private String metacriticUrl;
    private List<String> stores;
    private List<String> platforms;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public String getMetacriticUrl() {
        return metacriticUrl;
    }

    public void setMetacriticUrl(String metacriticUrl) {
        this.metacriticUrl = metacriticUrl;
    }

    public List<String> getStores() {
        return stores;
    }
    public void setStores(List<String> stores) {
        this.stores = stores;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

}
