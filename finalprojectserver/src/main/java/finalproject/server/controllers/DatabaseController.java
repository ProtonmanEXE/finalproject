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

import finalproject.server.models.FullUserDetails;
import finalproject.server.models.GameCard;
import finalproject.server.repository.GameDetailsRepos;
import finalproject.server.repository.UserRepos;
import finalproject.server.service.EmailService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

@RestController
@RequestMapping(path="/jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")   

public class DatabaseController {

    @Autowired
    GameDetailsRepos gameDetailRepo;

    @Autowired
    UserRepos userRepo;

    @Autowired
    EmailService emailSvc;

    @GetMapping(path="/locked/wishlist")
    public ResponseEntity<String> saveToWishList(Authentication authentication) {
        JsonArrayBuilder gameJsonArray = Json.createArrayBuilder();
        // gameDetailSvc.getTopTenGames() will create a wishlist (array)
        try {
            for (GameCard game : 
                gameDetailRepo.getWishlist(authentication.getName())) {
                JsonObjectBuilder gameSummary = Json.createObjectBuilder();
                gameSummary.add("name", game.getName());
                gameSummary.add("releasedDate", game.getReleasedDate());
                gameSummary.add("backgroundImageUrl", game.getBackgroundImageUrl());
                gameSummary.add("gameId", game.getGameId());
                gameJsonArray.add(gameSummary.build());
            }
            return ResponseEntity.ok().body(gameJsonArray.build().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    @GetMapping(path="/locked/deletewish/{gameId}")
    public ResponseEntity<String> deleteWish(
        @PathVariable String gameId, Authentication authentication) {
        try {
            gameDetailRepo.deleteWish(Integer.valueOf(gameId), authentication.getName());
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }

    @PostMapping(path="/registration", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveToWishList(@RequestBody FullUserDetails newUser) {
        try {
            userRepo.saveUser(newUser);
            emailSvc.sendEmail(newUser.getUserName(), newUser.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
    
}
