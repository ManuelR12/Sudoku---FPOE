package com.example.proyecto2fpoe.Controller;

import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.SudokuModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    @FXML
    private GridPane sudokuGrid;
    private SudokuModel model;
    private final int SIZE = 6;

    @FXML
    public void initialize() {
        model = new SudokuModel();
        model.generatePuzzle(0);
        populateGrid();
        printBoard(model.getBoard());

    }


    //DELETE BEFORE FINAL VERSION. JUST FOR TESTING PURPOSES.
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


    public void populateGrid() {
        IList<IList<Integer>> board = model.getBoard();
        if (board.size() != SIZE || board.get(0).size() != SIZE) {
            throw new IllegalStateException("El tablero no tiene el tamaño esperado.");
        }

        final int NUMBERS_PER_BLOCK = 2;

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 2; blockCol++) {
                List<Integer> positions = new ArrayList<>();
                for (int row = 0; row < 2; row++) {
                    for (int col = 0; col < 3; col++) {
                        positions.add((blockRow * 2 + row) * SIZE + (blockCol * 3 + col));
                    }
                }

                Collections.shuffle(positions);

                List<Integer> cellsToShow = positions.subList(0, NUMBERS_PER_BLOCK);

                for (int row = 0; row < 2; row++) {
                    for (int col = 0; col < 3; col++) {
                        int currentRow = blockRow * 2 + row;
                        int currentCol = blockCol * 3 + col;

                        TextField textField = (TextField) getNodeFromGridPane(sudokuGrid, currentRow, currentCol);
                        Integer value = board.get(currentRow).get(currentCol);

                        int positionIndex = currentRow * SIZE + currentCol;

                        if (cellsToShow.contains(positionIndex)) {
                            assert textField != null;
                            textField.setText(value.toString());
                            textField.setEditable(false);
                        } else {
                            assert textField != null;
                            textField.setText("");
                            textField.setEditable(true);
                        }
                    }
                }
            }
        }
    }


    private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRow = GridPane.getRowIndex(node);
            Integer nodeCol = GridPane.getColumnIndex(node);
            if (nodeRow == null) nodeRow = 0;
            if (nodeCol == null) nodeCol = 0;

            if (nodeRow == row && nodeCol == col) {
                return node;
            }
        }
        return null;
    }



}
