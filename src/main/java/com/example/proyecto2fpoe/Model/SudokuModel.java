package com.example.proyecto2fpoe.Model;

import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.List.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * A model for a 6x6 Sudoku puzzle. It provides methods to generate a Sudoku board,
 * validate rows, columns, and subgrids, and create a Sudoku puzzle by removing cells from a valid solution.
 */
public class SudokuModel {
    private static final int GRID_SIZE = 6;  // The size of the Sudoku grid (6x6)
    private final IList<IList<Integer>> board;

    /**
     * Constructs a new SudokuModel and initializes the board as a 6x6 grid with zeros.
     */
    public SudokuModel() {
        board = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            IList<Integer> row = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                row.addLast(0);  // Initialize with zeros
            }
            board.addLast(row);
        }
    }

    /**
     * Generates a Sudoku puzzle by filling the board with a valid solution and then removing numbers
     * to create the puzzle.
     *
     * @param emptyCells the number of cells to remove to create the puzzle
     */
    public void generatePuzzle(int emptyCells) {
        fillBoard();          // Fill the board with a valid solution
        removeNumbers(emptyCells);  // Remove numbers to create a puzzle
    }

    /**
     * Returns the current state of the Sudoku board.
     *
     * @return the 6x6 board as an IList of ILists
     */
    public IList<IList<Integer>> getBoard() {
        return board;
    }

    /**
     * Fills the board with a valid Sudoku solution using backtracking.
     *
     * @return {@code true} if the board is successfully filled, otherwise {@code false}
     */
    private boolean fillBoard() {
        return fillBoardHelper(0, 0);
    }

    /**
     * Helper method for {@link #fillBoard()} using a backtracking algorithm to fill the board.
     *
     * @param row the current row index
     * @param col the current column index
     * @return {@code true} if the board is successfully filled, otherwise {@code false}
     */
    private boolean fillBoardHelper(int row, int col) {
        if (row == GRID_SIZE) return true;
        if (col == GRID_SIZE) return fillBoardHelper(row + 1, 0);

        IList<Integer> numbers = getShuffledNumbers();

        for (int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);
            if (isValid(row, col, num)) {
                board.get(row).set(col, num);
                if (fillBoardHelper(row, col + 1)) return true;
                board.get(row).set(col, 0);  // Backtrack
            }
        }
        return false;
    }

    /**
     * Returns a shuffled list of numbers from 1 to 6 using the Fisher-Yates algorithm.
     *
     * @return a shuffled {@link IList} of integers from 1 to 6
     */
    private IList<Integer> getShuffledNumbers() {
        IList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= GRID_SIZE; i++) {
            numbers.addLast(i);
        }
        // Shuffle the list using Fisher-Yates
        Random random = new Random();
        for (int i = numbers.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = numbers.get(i);
            numbers.set(i, numbers.get(j));
            numbers.set(j, temp);
        }
        return numbers;
    }

    /**
     * Checks if placing a number at a given (row, col) position is valid according to Sudoku rules.
     *
     * @param row the row index
     * @param col the column index
     * @param num the number to place
     * @return {@code true} if the number can be placed, otherwise {@code false}
     */
    private boolean isValid(int row, int col, int num) {
        // Check row and column
        for (int c = 0; c < GRID_SIZE; c++) {
            if (board.get(row).get(c) == num) return false;
        }
        for (int r = 0; r < GRID_SIZE; r++) {
            if (board.get(r).get(col) == num) return false;
        }

        // Check 2x3 subgrid
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board.get(r).get(c) == num) return false;
            }
        }

        return true;
    }

    /**
     * Checks if a specific row is complete in the Sudoku grid.
     *
     * @param row the row index
     * @param grid the {@link GridPane} representing the UI Sudoku grid
     * @return {@code true} if the row is complete, otherwise {@code false}
     */
    public boolean isRowComplete(int row, GridPane grid) {
        boolean[] seen = new boolean[GRID_SIZE + 1];
        for (int col = 0; col < GRID_SIZE; col++) {
            TextField cell = getTextFieldAt(grid, row, col);
            if (cell == null) return false;
            String text = cell.getText().trim();
            int num;
            try {
                num = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return false;
            }
            if (num == 0 || seen[num]) return false;
            seen[num] = true;
        }
        return true;
    }

    /**
     * Checks if a specific column is complete in the Sudoku grid.
     *
     * @param col the column index
     * @param grid the {@link GridPane} representing the UI Sudoku grid
     * @return {@code true} if the column is complete, otherwise {@code false}
     */
    public boolean isColumnComplete(int col, GridPane grid) {
        boolean[] seen = new boolean[GRID_SIZE + 1];
        for (int row = 0; row < GRID_SIZE; row++) {
            TextField cell = getTextFieldAt(grid, row, col);
            if (cell == null) return false;
            String text = cell.getText().trim();
            int num;
            try {
                num = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return false;
            }
            if (num == 0 || seen[num]) return false;
            seen[num] = true;
        }
        return true;
    }

    /**
     * Checks if a 2x3 subgrid is complete.
     *
     * @param sudokuGrid the {@link GridPane} representing the UI Sudoku grid
     * @param row the row index of the subgrid
     * @param col the column index of the subgrid
     * @return {@code true} if the subgrid is complete, otherwise {@code false}
     */
    public boolean isSubGridComplete(GridPane sudokuGrid, int row, int col) {
        boolean[] seen = new boolean[GRID_SIZE + 1];
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                TextField cell = getTextFieldAt(sudokuGrid, r, c);
                if (cell != null) {
                    String text = cell.getText().trim();
                    if (text.isEmpty() || text.equals("0")) return false;
                    int num = Integer.parseInt(text);
                    if (seen[num]) return false;
                    seen[num] = true;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the entire Sudoku board is complete.
     *
     * @param sudokuGrid the {@link GridPane} representing the UI Sudoku grid
     * @return {@code true} if the board is complete, otherwise {@code false}
     */
    public boolean isBoardComplete(GridPane sudokuGrid) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                TextField cell = getTextFieldAt(sudokuGrid, row, col);
                if (cell == null || cell.getText().trim().isEmpty() || cell.getText().trim().equals("0")) return false;
            }
        }
        return true;
    }

    /**
     * Helper method to get the {@link TextField} at a specific position in the {@link GridPane}.
     *
     * @param gridPane the {@link GridPane} to search
     * @param row the row index
     * @param col the column index
     * @return the {@link TextField} at the given position, or {@code null} if none exists
     */
    private TextField getTextFieldAt(GridPane gridPane, int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;
            }
        }
        return null;
    }

    /**
     * Removes a specified number of random cells from the Sudoku board to create a puzzle.
     *
     * @param emptyCells the number of cells to remove
     */
    private void removeNumbers(int emptyCells) {
        Random random = new Random();
        int removed = 0;

        while (removed < emptyCells) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            if (board.get(row).get(col) != 0) {
                board.get(row).set(col, 0);
                removed++;
            }
        }
    }
}
