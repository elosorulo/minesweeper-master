/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.despegar.highflight;

import java.util.List;

public interface Minesweeper {
    void initialize(String difficulty);

    // Allow the player to uncover a cell
    void uncover(int row, int col);

    // Marking/unmarking suspicious cells
    void flagAsMine(int row, int col);

    void clearFlag(int row, int col);

    List<String> play(String operation, String row, String col);

    // Game termination
    boolean isGameOver();

    boolean isWinningGame();

    // Operations for showing the current state of game grid
    // Public/visible grid for the player
    List<String> display();

    // Grid with all cells uncovered. For debug purposes
    List<String> displayInternal();

    // Binary grid: 1 if cell has a mine, 0 otherwise. For debug purposes
    List<String> displayRaw();

    List<String> displayGameOver();

    List<String> displayTutorial();
}
