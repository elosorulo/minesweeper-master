/*
 * Interface Cell: For multiple purposes. Interface Matrix: For multiple purposes. Class: MinesweeperCell: Adapts the
 * Cell interface for minesweeper. Class: MinesweeperMatrix: Matrix of MinesweeperCells. Adapts the matrix Interface for
 * minesweeper. Class: MinesweeperImp: Implementation of the Minesweeper interface.
 */
package com.despegar.highflight;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.despegar.highflight.utils.Matrix2DCellPosition;
import com.despegar.highflight.utils.MatrixUtils;

public class MinesweeperImp
    implements Minesweeper {
    MinesweeperMatrix matrix;
    int uncoveredCells;
    boolean lastCellIsMine;

    // Init.
    public MinesweeperImp() {
        this.matrix = new MinesweeperMatrix(4, 4);
        this.lastCellIsMine = false;
    }

    // Initialize the game depending on the dificulty).
    public void initialize(String difficulty) {

        int d = this.validateDifficulty(difficulty);
        switch (d) {
        case 1:
            this.matrix = new MinesweeperMatrix(8, 8);
            break;
        case 2:
            this.matrix = new MinesweeperMatrix(12, 16);
            break;
        default:
            this.matrix = new MinesweeperMatrix(4, 4);
        }
        this.uncoveredCells = 0;
        this.lastCellIsMine = false;
    }

    // Uncover the cells returned in the cascade method. Checks if the cell was already uncovered.
    public void uncover(int row, int col) {
        Set<Matrix2DCellPosition> positions = MatrixUtils.cascade(this.matrix.matrixToInt(), row, col);
        for (Matrix2DCellPosition position : positions) {
            if (this.matrix.getCell(position).isCovered()) {
                this.uncoveredCells++;
            }
            this.matrix.getCell(position).unCover();
        }
        if (positions.isEmpty()) {
            this.lastCellIsMine = true;
        }
    }

    // Flags a cell as mine, only if the cell is covered.
    public void flagAsMine(int row, int col) {
        Matrix2DCellPosition position = new Matrix2DCellPosition(row, col);
        if (this.matrix.getCell(position).isCovered()) {
            this.matrix.getCell(position).flag();
        }
    }

    // Unflags a cell.
    public void clearFlag(int row, int col) {
        Matrix2DCellPosition position = new Matrix2DCellPosition(row, col);
        this.matrix.getCell(position).unFlag();
    }

    // Checks if there is a mine. Calls the method thereIsMine from the matrix object.
    public boolean thereIsMine(Matrix2DCellPosition position) {
        if (this.matrix.thereIsMine(this.matrix.getCell(position))) {
            return true;
        }
        return false;
    }

    // Checks if the last cell is a mine and calls the method isWinningGame().
    public boolean isGameOver() {
        if (this.lastCellIsMine) {
            return true;
        }
        return this.isWinningGame();
    }

    public boolean isWinningGame() {
        // TODO Auto-generated method stub
        if (this.uncoveredCells == (this.matrix.getArea() - this.matrix.getNumberOfMines())) {
            return true;
        }
        return false;
    }

    // General display method(Performs a certain display depending on the parameter).
    public List<String> display(int displayType) {
        List<String> outputList = new ArrayList();
        outputList.add(this.uncoveredCells + "/" + (this.matrix.getArea() - this.matrix.getNumberOfMines()));
        outputList.add("");
        for (int i = 0; i < this.matrix.getWidth(); i++) {
            String output = "|";
            for (int j = 0; j < this.matrix.getWidth(); j++) {
                MinesweeperCell cell = this.matrix.getCell(new Matrix2DCellPosition(i, j));
                switch (displayType) {
                case 0:// Normal display.
                    output += cell.display();
                    break;
                case 1:// Internal display.
                    output += cell.getValue();
                    break;
                case 2:// Binary display.
                    if (this.thereIsMine(new Matrix2DCellPosition(i, j))) {
                        output += 1;
                    } else {
                        output += 0;
                    }
                    break;
                case 3:// Game Over display.
                    if (this.thereIsMine(new Matrix2DCellPosition(i, j))) {
                        output += "*";
                    } else {
                        output += cell.display();
                    }
                }
                output += "|";
            }
            outputList.add(output);
        }
        return outputList;
    }

    public List<String> display() {
        // TODO Auto-generated method stub
        return this.display(0);
    }

    public List<String> displayInternal() {
        return this.display(1);
    }

    public List<String> displayRaw() {

        return this.display(2);
    }

    public List<String> displayGameOver() {
        return this.display(3);
    }

    // Validates the position.
    public boolean isValidPosition(Matrix2DCellPosition position) {
        if (this.matrix.isValidPosition(position)) {
            return true;
        }
        return false;
    }

    // Checks if the text is numeric.
    private boolean isNumeric(String text) {
        for (Character c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // Validates the operation.
    private int validateOperation(String operation) {
        if (operation.equalsIgnoreCase("flag")) {
            return 1;
        } else if (operation.equalsIgnoreCase("unflag")) {
            return 2;
        } else if (operation.equalsIgnoreCase("uncover")) {
            return 3;
        }
        return 0;
    }

    // Validates the difficulty.
    private int validateDifficulty(String difficulty) {
        if (difficulty.equalsIgnoreCase("intermediate")) {
            return 1;
        } else if (difficulty.equalsIgnoreCase("expert")) {
            return 2;
        }
        return 0;
    }

    // Play action (operation + row + col).
    public List<String> play(String operation, String row, String col) {
        if (!this.isNumeric(row) || !this.isNumeric(col)) {
            return this.display();
        }
        int r = Integer.parseInt(row) - 1;
        int c = Integer.parseInt(col) - 1;
        if (this.isValidPosition(new Matrix2DCellPosition(r, c))) {
            int op = this.validateOperation(operation);
            switch (op) {
            case 1:
                this.flagAsMine(r, c);
                break;
            case 2:
                this.clearFlag(r, c);
                break;
            case 3:
                this.uncover(r, c);
                break;
            }
        }
        return this.display(0);
    }

    // Tutorial.
    @Override
    public List<String> displayTutorial() {
        List<String> tutorial = new ArrayList<String>();
        tutorial.add("Welcome to Minesweeper!");
        tutorial.add("");
        tutorial.add("Choose Difficulty:");
        tutorial.add("");
        tutorial.add("(default) Easy (4*4) command: 'minesweeper/easy'");
        tutorial.add("Intermediate (8*8) command: 'minesweeper/intermediate'");
        tutorial.add("Expert (12*16) command: 'minesweeper/expert'");
        tutorial.add("");
        tutorial.add("Play:");
        tutorial.add("");
        tutorial.add("To perform an operation on a certain position(flag, unflag or uncover) use the following command");
        tutorial.add("'minesweeper/(command)/(row)/(column)'");
        tutorial.add("");
        tutorial.add("Remember that the intial position is 1.");
        tutorial.add("Have fun!");
        return tutorial;
    }
}
