package com.example.proyecto2fpoe;

import com.example.proyecto2fpoe.Model.SudokuModel;

public class Test {
    public static void main(String[] args) {
        SudokuModel generator = new SudokuModel();
        generator.generatePuzzle(0); // Generate a puzzle with 40 empty cells
    }
}
