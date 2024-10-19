package com.example.proyecto2fpoe;

import com.example.proyecto2fpoe.Model.SudokuModel;
import com.example.proyecto2fpoe.Model.List.IList;

public class SudokuTest {

    public static void main(String[] args) {
        // Create an instance of the SudokuModel (6x6)
        SudokuModel sudokuModel = new SudokuModel();

        // Generate a Sudoku puzzle with 10 empty cells
        sudokuModel.generatePuzzle(0);

        // Get the board and print it to the console
        printBoard(sudokuModel.getBoard());
    }

    // Method to print the 6x6 Sudoku board in a readable format
    private static void printBoard(IList<IList<Integer>> board) {
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                int value = board.get(row).get(col);
                System.out.print((value == 0 ? "." : value) + " ");
                if ((col + 1) % 3 == 0 && col != 5) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((row + 1) % 2 == 0 && row != 5) {
                System.out.println("------+-------");
            }
        }
    }
}
