package com.codeup.springblog.controllers;

import com.codeup.springblog.models.DiceSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class DiceController {

    @GetMapping("/roll-dice")
    public String getUserGuess() {
        return ("roll_guess");
    }

    @PostMapping("/roll-dice")
    public String processUserGuess(@RequestParam(name = "guess") int guess, Model model) {

        // play five roles
        int limit = 5;
        int countCorrect = 0;
        ArrayList<DiceSet> diceSets = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            DiceSet diceSet = new DiceSet();
            diceSet.die1 = (byte) ((Math.random() * 6) + 1);
            diceSet.die2 = (byte) ((Math.random() * 6) + 1);
            if (diceSet.die1 + diceSet.die2 == guess) {
                countCorrect++;
                diceSet.result = "Winner, Winner, Chicken dinner!";
            } else {
                diceSet.result = "Born to loose. ;-(";
            }
            diceSets.add(diceSet);
        }

        String header = String.format("Your guess was %d.", guess);
        String message = String.format("You got %d out of %d rolls correct.",
                countCorrect, limit);
        model.addAttribute("header", header);
        model.addAttribute("message", message);
        model.addAttribute("dice", diceSets);

        return ("roll_result");
    }
}
