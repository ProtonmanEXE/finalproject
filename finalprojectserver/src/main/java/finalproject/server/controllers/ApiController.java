package finalproject.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalproject.server.models.GameCard;
import finalproject.server.models.GameDetails;
import finalproject.server.repository.GameDetailsRepos;
import finalproject.server.service.GameDetailService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
// @CrossOrigin("*")    
public class ApiController {

    @Autowired
    GameDetailService gameDetailSvc;

    @Autowired
    GameDetailsRepos gameDetailRepo;

    @GetMapping(path="/toptengames")
    public ResponseEntity<String> getTopTenGames() {
        JsonArrayBuilder gameJsonArray = Json.createArrayBuilder();
        // gameDetailSvc.getTopTenGames() will create an array of top nine GameCard
        try {
            for (GameCard game : gameDetailSvc.getTopTenGames()) {
                JsonObjectBuilder gameCardBuilder = Json.createObjectBuilder();
                gameCardBuilder.add("name", game.getName());
                gameCardBuilder.add("releasedDate", game.getReleasedDate());
                gameCardBuilder.add("backgroundImageUrl", game.getBackgroundImageUrl());
                gameCardBuilder.add("esrbRating", game.getEsrbRating());
                gameCardBuilder.add("gameId", game.getGameId());
                gameCardBuilder.add("genres", Json.createArrayBuilder(game.getGenres()));
                gameJsonArray.add(gameCardBuilder.build());
            }
            return ResponseEntity.ok().body(gameJsonArray.build().toString());
        // if search is not successful, return error to client
        } catch (Exception e) {  
            e.printStackTrace();
            JsonObject payload = Json.createObjectBuilder()
                .add("message", "Error in finding games")
                .build();
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(payload.toString());   
        }     
    }

    @GetMapping(path="/locked/searchbytitle/{title}")
    public ResponseEntity<String> searchByName(@PathVariable String title) {
        JsonArrayBuilder gameJsonArray = Json.createArrayBuilder();
        // gameDetailSvc.getTopTenGames() will create an array of nine GameCard
        // matching closest search by name
        try {
            for (GameCard game : gameDetailSvc.searchByName(title)) {
                JsonObjectBuilder gameCardBuilder = Json.createObjectBuilder();
                gameCardBuilder.add("name", game.getName());
                gameCardBuilder.add("releasedDate", game.getReleasedDate());
                gameCardBuilder.add("backgroundImageUrl", game.getBackgroundImageUrl());
                gameCardBuilder.add("esrbRating", game.getEsrbRating());
                gameCardBuilder.add("gameId", game.getGameId());
                gameCardBuilder.add("genres", Json.createArrayBuilder(game.getGenres()));
                gameJsonArray.add(gameCardBuilder.build());
            }
            return ResponseEntity.ok().body(gameJsonArray.build().toString());
        // if search is not successful, return error to client
        } catch (Exception e) {  
            e.printStackTrace();
            JsonObject payload = Json.createObjectBuilder()
                .add("message", "Error in finding games")
                .build();
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(payload.toString());   
        }     
    }

    @GetMapping(path="/locked/gamedetails/{gameId}")
    public ResponseEntity<String> getGameDetail(@PathVariable String gameId) {
        GameDetails game = gameDetailSvc.getGameDetails(gameId);
        // gameDetailSvc.getTopTenGames() will create an array of top ten ShortGame
        try {
            JsonObjectBuilder gameDetailsBuilder = Json.createObjectBuilder();
            gameDetailsBuilder.add("gameId", game.getGameId());
            gameDetailsBuilder.add("name", game.getName());
            gameDetailsBuilder.add("description", game.getDescription());
            gameDetailsBuilder.add("metacriticRating", game.getMetacriticRating());
            gameDetailsBuilder.add("releasedDate", game.getReleasedDate());
            gameDetailsBuilder.add("backgroundImageUrl", game.getBackgroundImageUrl());
            gameDetailsBuilder.add("metacriticUrl", game.getMetacriticUrl());
            gameDetailsBuilder.add("esrbRating", game.getEsrbRating());
            gameDetailsBuilder.add("genres", Json.createArrayBuilder(game.getGenres()));
            gameDetailsBuilder.add("stores", Json.createArrayBuilder(game.getStores()));
            gameDetailsBuilder.add("platforms", Json.createArrayBuilder(game.getPlatforms()));

            return ResponseEntity.ok().body(gameDetailsBuilder.build().toString());
        // if search is not successful, return error to client
        } catch (Exception e) {  
            e.printStackTrace();
            JsonObject payload = Json.createObjectBuilder()
                .add("message", "Error in finding games")
                .build();
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(payload.toString());   
        }     
    }

    @PostMapping(path="/locked/savegamedetails", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveToWishList(
        @RequestBody GameCard game, Authentication authentication) {
        try {
            gameDetailRepo.saveToWishList(game, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
}
