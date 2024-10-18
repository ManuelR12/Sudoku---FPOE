package com.example.proyecto2fpoe.Model;

import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.List.ArrayList;

import java.util.Random;
import java.util.Collections;

public class SudokuModel {
    private static final int GRID_SIZE = 9;
    private final IList<IList<Integer>> board;

    public SudokuModel() {
        // Initialize the board with your custom ArrayList implementation
        board = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            IList<Integer> row = new ArrayList<>();
            for (int j = 0; j < GRID_SIZE; j++) {
                row.addLast(0); // Initialize with zeros
            }
            board.addLast(row);
        }
    }

    public void generatePuzzle(int emptyCells) {
        fillBoard(); // Fill the board with a valid solution
        removeNumbers(emptyCells); // Remove numbers to create a puzzle
    }

    public IList<IList<Integer>> getBoard() {
        return board;
    }

    // Fills the board with a valid solution
    private boolean fillBoard() {
        return fillBoardHelper(0, 0);
    }

    // Backtracking helper with randomized numbers
    private boolean fillBoardHelper(int row, int col) {
        if (row == GRID_SIZE) return true;
        if (col == GRID_SIZE) return fillBoardHelper(row + 1, 0);

        IList<Integer> numbers = getShuffledNumbers();

        for (int i = 0; i < numbers.size(); i++) {
            int num = numbers.get(i);
            if (isValid(row, col, num)) {
                board.get(row).set(col, num);
                if (fillBoardHelper(row, col + 1)) return true;
                board.get(row).set(col, 0); // Backtrack
            }
        }
        return false;
    }

    // Returns a shuffled list of numbers 1 to 9
    private IList<Integer> getShuffledNumbers() {
        IList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.addLast(i);
        }
        // Shuffle the IList numbers using Fisher-Yates
        Random random = new Random();
        for (int i = numbers.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = numbers.get(i);
            numbers.set(i, numbers.get(j));
            numbers.set(j, temp);
        }
        return numbers;
    }

    private boolean isValid(int row, int col, int num) {
        for (int c = 0; c < GRID_SIZE; c++) {
            if (board.get(row).get(c) == num) return false;
        }

        for (int r = 0; r < GRID_SIZE; r++) {
            if (board.get(r).get(col) == num) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board.get(r).get(c) == num) return false;
            }
        }

        return true;
    }

    // Removes random numbers to create a puzzle
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
