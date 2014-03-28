package com.despegar.highflight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.despegar.highflight.Minesweeper;

@Controller
public class MinesweeperController {
    private Minesweeper game;

    // Play command.
    @RequestMapping(value = "/minesweeper/{operation}/{row}/{column}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> play(@PathVariable("operation") String operation, @PathVariable("row") String row,
        @PathVariable("column") String column) {
        List<String> output = this.game.play(operation, row, column);
        if (this.game.isGameOver()) {
            if (this.game.isWinningGame()) {
                output = this.game.displayInternal();
                output.add("You Win!");
                output.add("To restart de game use the command /minesweeper/(difficulty)");
            } else {
                output = this.game.displayGameOver();
                output.add("Game Over.");
                output.add("To restart de game use the command /minesweeper/(difficulty)");
            }
            this.game.initialize("easy");// In case that the player types a command, the game will be initialized.
        }
        return new ResponseEntity<List<String>>(output, HttpStatus.OK);
    }

    // Home(Tutorial).
    @RequestMapping(value = "/minesweeper", method = RequestMethod.GET)
    public ResponseEntity<List<String>> home() {
        List<String> output = this.game.displayTutorial();
        return new ResponseEntity<List<String>>(output, HttpStatus.OK);
    }

    // Set difficulty.
    @RequestMapping(value = "/minesweeper/{difficulty}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> setDifficulty(@PathVariable("difficulty") String difficulty) {
        this.game.initialize(difficulty);
        List<String> output = this.game.display();
        return new ResponseEntity<List<String>>(output, HttpStatus.OK);
    }

    public Minesweeper getGame() {
        return this.game;
    }

    public void setGame(Minesweeper game) {
        this.game = game;
    }
}
