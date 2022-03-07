package finalproject.server.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import finalproject.server.models.GameCard;
import finalproject.server.models.GameDetails;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import static finalproject.server.constants.Constants.*;

@Service
public class GameDetailService {
    
    // private final static Logger logging = LoggerFactory.getLogger(GameDetailService.class);

    public List<GameCard> getTopTenGames() {

        // build url to search for top nine games, getting json object
        final String url = UriComponentsBuilder // url to search by title
                .fromUriString(URL_TOPTENGAMES)  
                .queryParam("key", ENV_RAWG_API_KEY) 
                .queryParam("dates", "2021-06-01,2022-02-01") 
                .queryParam("page_size", "9")
                .queryParam("ordering", "-rating")
                .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) // if bad response
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString())
            );
        final String body = resp.getBody(); // if ok response
        // logging.info("payload: %s".formatted(body));

        List<GameCard> store = new ArrayList<GameCard>();     
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray result2 = result.getJsonArray("results");

        // create GameCard object and compile the list of top nine games
            for (JsonValue i : result2) {
                GameCard game = new GameCard();
                
                game.setName(i.asJsonObject().getString("name"));
                game.setBackgroundImageUrl(
                    i.asJsonObject().getString("background_image")); 
                game.setGameId(i.asJsonObject().getJsonNumber("id").intValue());

                try {
                    game.setReleasedDate(i.asJsonObject().getString("released")); 
                } catch (Exception e) {
                    game.setReleasedDate("Unknown");
                }

                try {
                    game.setEsrbRating(
                        i.asJsonObject()
                            .getJsonObject("esrb_rating").getString("name")); 
                } catch (Exception e) {
                    game.setEsrbRating("No rating available");
                }

                JsonArray genresJsonArray = i.asJsonObject().getJsonArray("genres");
                List<String> genres = new ArrayList<String>();      
                for (JsonValue g : genresJsonArray) {
                    genres.add(g.asJsonObject().getString("name"));
                }
                game.setGenres(genres);
                
                store.add(game);
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }

        return store;      
    }

    public GameDetails getGameDetails(String gameId) {

        // build url to search by title, getting json object
        final String url = UriComponentsBuilder // url to search by title
                .fromUriString(URL_GAME.concat(gameId))  
                .queryParam("key", ENV_RAWG_API_KEY) 
                .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) // if bad response
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString())
            );
        final String body = resp.getBody(); // if ok response
        // logging.info("payload: %s".formatted(body));

        GameDetails game = new GameDetails();
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();

        // create GameDetails object and compile the list of top nine games
            game.setGameId(result.getJsonNumber("id").intValue());
            game.setName(result.getString("name"));
            game.setDescription(result.getString("description")); 
            game.setBackgroundImageUrl(result.getString("background_image")); 
            game.setMetacriticUrl(result.getString("metacritic_url")); 

            try {
                game.setReleasedDate(result.getString("released")); 
            } catch (Exception e) {
                game.setReleasedDate("Unknown");
            }

            try {
                game.setMetacriticRating(
                    String.valueOf(result.getJsonNumber("metacritic").intValue()));
            } catch (Exception e) {
                game.setMetacriticRating("No rating available");
            }

            try {
                game.setEsrbRating(
                    result.getJsonObject("esrb_rating").getString("name")); 
            } catch (Exception e) {
                game.setEsrbRating("No rating available");
            }
            
            JsonArray platformsJsonArray = result.getJsonArray("platforms");
            List<String> platforms = new ArrayList<String>();      
            for (JsonValue p : platformsJsonArray) {
                platforms.add(
                    p.asJsonObject().getJsonObject("platform").getString("name"));
            }
            game.setPlatforms(platforms);

            List<String> stores = new ArrayList<String>();  
            JsonArray storesJsonArray = result.getJsonArray("stores");  
            if (!result.getJsonArray("stores").isEmpty()) {
                storesJsonArray = result.getJsonArray("stores");
                for (JsonValue s : storesJsonArray) {
                    stores.add(
                        s.asJsonObject().getJsonObject("store").getString("name"));
                }
            } else {
                stores.add("Not yet available in online stores");
            }
            game.setStores(stores);

            JsonArray genresJsonArray = result.getJsonArray("genres");
            List<String> genres = new ArrayList<String>();      
            for (JsonValue g : genresJsonArray) {
                genres.add(g.asJsonObject().getString("name"));
            }
            game.setGenres(genres);
            
        } catch (Exception e) { 
            e.printStackTrace();
        }

        return game;
    }

    public List<GameCard> searchByName(String searchTerm) {

        // build url to search for top nine games closest to given query (title), 
        // getting json object
        final String url = UriComponentsBuilder // url to search by title
                .fromUriString(URL_TOPTENGAMES)  
                .queryParam("key", ENV_RAWG_API_KEY) 
                .queryParam("search", searchTerm.trim().replace(" ", "+")) 
                .queryParam("page_size", "9")
                .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) // if bad response
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString())
            );
        final String body = resp.getBody(); // if ok response
        // logging.info("payload: %s".formatted(body));

        List<GameCard> store = new ArrayList<GameCard>();     
        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            final JsonObject result = reader.readObject();
            final JsonArray result2 = result.getJsonArray("results");

        // create GameCard object and compile the list of top nine games
            for (JsonValue i : result2) {
                GameCard game = new GameCard();
                
                game.setName(i.asJsonObject().getString("name"));
                game.setBackgroundImageUrl(
                    i.asJsonObject().getString("background_image")); 
                game.setGameId(i.asJsonObject().getJsonNumber("id").intValue());

                try {
                    game.setReleasedDate(i.asJsonObject().getString("released")); 
                } catch (Exception e) {
                    game.setReleasedDate("Unknown");
                }

                try {
                    game.setEsrbRating(
                        i.asJsonObject()
                            .getJsonObject("esrb_rating").getString("name")); 
                } catch (Exception e) {
                    game.setEsrbRating("No rating available");
                }

                JsonArray genresJsonArray = i.asJsonObject().getJsonArray("genres");
                List<String> genres = new ArrayList<String>();      
                for (JsonValue g : genresJsonArray) {
                    genres.add(g.asJsonObject().getString("name"));
                }
                game.setGenres(genres);

                store.add(game);
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }

        return store;
        }
}
