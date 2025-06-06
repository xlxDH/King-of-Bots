package com.kob.backend.controller.pk;

import com.kob.backend.service.impl.pk.StartGameServiceImpl;
import com.kob.backend.service.pk.StartGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class StartGameController {
    @Autowired
    private StartGameServiceImpl startGameService;

    @PostMapping("/pk/start/game/")
    public String startGame(@RequestParam MultiValueMap<String, String> map) {
        Integer aId = Integer.parseInt(Objects.requireNonNull(map.getFirst("a_id")));
        Integer bId = Integer.parseInt(Objects.requireNonNull(map.getFirst("b_id")));
        Integer aBotId = Integer.parseInt(Objects.requireNonNull(map.getFirst("a_bot_id")));
        Integer bBotId = Integer.parseInt(Objects.requireNonNull(map.getFirst("b_bot_id")));
        return startGameService.startGame(aId,aBotId, bId,bBotId);
    }

}
