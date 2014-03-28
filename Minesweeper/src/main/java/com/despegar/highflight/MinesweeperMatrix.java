/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.despegar.highflight;

import java.util.ArrayList;
import java.util.Random;

import com.despegar.highflight.utils.Matrix2DCellPosition;

public class MinesweeperMatrix
    implements Matrix<MinesweeperCell> {
    private MinesweeperCell[][] matrixArray;// Matrix of cells.
    int mines;// Number of mines.

    // Initialize the matrix.

    public MinesweeperMatrix(Integer width, Integer height) {
        // initialize the matrix && the cells.
        this.matrixArray = new MinesweeperCell[width][height];
        this.generateCells();
    }

    // Returns the number of mines.
    public int getNumberOfMines() {
        return this.mines;
    }

    // Sets a cell in the matrix.
    public void setCell(Matrix2DCellPosition position, MinesweeperCell value) {
        this.matrixArray[position.getRow()][position.getColumn()] = value;
    }

    // Returns a cell with a certain position.
    public MinesweeperCell getCell(Matrix2DCellPosition position) {
        return this.matrixArray[position.getRow()][position.getColumn()];
    }

    // Returns the height of the matrix.
    public Integer getHeight() {
        return this.matrixArray[0].length;
    }

    // Returns the width of the matrix.
    public Integer getWidth() {
        // TODO Auto-generated method stub
        return this.matrixArray.length;
    }

    // Return the area of the matrix (W*H).
    public Integer getArea() {
        return this.getHeight() * this.getWidth();
    }

    // Generate mines in the matrix.
    public void generateMines() {
        this.mines = 0;
        while (this.mines < (this.getArea() * 0.15)) {
            Random rand = new Random();
            int row = rand.nextInt(this.getWidth());
            int column = rand.nextInt(this.getHeight()) - 1;
            Matrix2DCellPosition position = new Matrix2DCellPosition(row, column);
            if (this.isValidPosition(position) && !this.thereIsMine(this.getCell(position))) {
                this.setCell(position, new MinesweeperCell('*'));
                this.mines++;
            }
        }
    }

    // Generates the cells in matrix (Calls the method generateMines() first).
    public void generateCells() {
        this.generateMines();
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                if (!this.thereIsMine(this.matrixArray[i][j])) {
                    Matrix2DCellPosition position = new Matrix2DCellPosition(i, j);
                    int mines = this.adjacentMines(this.getAdjacentCells(position));
                    this.setCell(position, new MinesweeperCell((char) (mines + 48)));
                }
            }
        }
    }

    // Converts the matrix to an matrix of ints.
    public int[][] matrixToInt() {
        int[][] matrixInt = new int[this.getWidth()][this.getHeight()];
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                MinesweeperCell cell = this.matrixArray[i][j];
                if (this.thereIsMine(cell)) {
                    matrixInt[i][j] = 1;
                } else {
                    matrixInt[i][j] = 0;
                }
            }
        }
        return matrixInt;
    }

    // Returns the number of adjacent mines.
    public int adjacentMines(ArrayList<MinesweeperCell> adjacentCells) {
        int mines = 0;
        for (MinesweeperCell cell : adjacentCells) {
            if (this.thereIsMine(cell)) {
                mines++;
            }
        }
        return mines;
    }

    // Check if the cell contains a mine.
    public boolean thereIsMine(MinesweeperCell cell) {
        try {
            if (cell.value.equals('*')) {
                return true;
            }
        } catch (NullPointerException error) {

        }
        return false;
    }

    // Returns an arrayList with the adjacent cells of a certain position. Implements 2 arrays to access the adjacent
    // positions.
    // If the position exists, then it's added to the list.
    public ArrayList<MinesweeperCell> getAdjacentCells(Matrix2DCellPosition position) {
        int[] rowIndex = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] columnIndex = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
        ArrayList<MinesweeperCell> adjacentCells = new ArrayList<MinesweeperCell>();
        for (int i = 0; i < 8; i++) {
            Matrix2DCellPosition j = new Matrix2DCellPosition(rowIndex[i] + position.getRow(), columnIndex[i]
                + position.getColumn());
            if (this.isValidPosition(j)) {
                adjacentCells.add(this.getCell(j));
            }
        }
        return adjacentCells;

    }

    // Verifies if the position exists in the matrix.
    boolean isValidPosition(Matrix2DCellPosition position) {
        if (position.getRow() < 0 || position.getRow() > this.getWidth() - 1 || position.getColumn() < 0
            || position.getColumn() > this.getHeight() - 1) {
            return false;
        }
        return true;
    }
}
