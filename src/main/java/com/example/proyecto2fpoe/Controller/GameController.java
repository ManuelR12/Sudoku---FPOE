package com.example.proyecto2fpoe.Controller;

import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.SudokuModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameController {
    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Button helpButton;

    private SudokuModel model;
    private final int SIZE = 6;
    private final int MAX_HELP_USES = 3;

    @FXML
    public void initialize() {
        model = new SudokuModel();
        model.generatePuzzle(0);
        populateGrid();
        printBoard(model.getBoard());
        assignGridIndex();

    }

    private void assignGridIndex() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                if (rowIndex == null) rowIndex = 0;
                if (colIndex == null) colIndex = 0;

                GridPane.setRowIndex(node, rowIndex);
                GridPane.setColumnIndex(node, colIndex);
            }
        }
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

    @FXML
    public void handleHelp() {
        List<TextField> emptyCells = new ArrayList<>();
        IList<IList<Integer>> board = model.getBoard();

        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField textField) {
                if (textField.getText().isEmpty() && textField.isEditable() && textField.getPromptText().isEmpty()) {
                    emptyCells.add(textField);
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            TextField selectedCell = emptyCells.get(random.nextInt(emptyCells.size()));

            int row = GridPane.getRowIndex(selectedCell);
            int col = GridPane.getColumnIndex(selectedCell);

            Integer recommendedValue = board.get(row).get(col);

            selectedCell.setPromptText(String.valueOf(recommendedValue));
        }
    }

}
