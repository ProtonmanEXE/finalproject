package finalproject.server.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
public class GameController {

    @GetMapping(path="/game")
    public String getKaboom() {
        System.out.println("Serving Kaboom game...");
        return "game";
    }
    
}
