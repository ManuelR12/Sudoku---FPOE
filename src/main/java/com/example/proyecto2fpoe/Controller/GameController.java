package com.example.proyecto2fpoe.Controller;

import com.example.proyecto2fpoe.Model.Animation.ColumnAnimation;
import com.example.proyecto2fpoe.Model.Animation.CorrectNumberAnimation;
import com.example.proyecto2fpoe.Model.Animation.RowAnimation;
import com.example.proyecto2fpoe.Model.Animation.SubGridAnimation;
import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.SudokuModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    private final int MAX_HELP_USES = 6;
    private int helpUses = 0;

    @FXML
    public void initialize() {
        model = new SudokuModel();
        model.generatePuzzle(0);
        populateGrid();
        printBoard(model.getBoard());
        assignGridIndex();
        assignListeners();
    }

    private void assignListeners() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField txt) {
                txt.textProperty().addListener((observable, oldValue, newValue) -> {

                    if (newValue.length() > 1) {
                        txt.setText(oldValue);
                    } else if (!newValue.isEmpty() && newValue.matches("^[1-6]$")) {
                        int row = GridPane.getRowIndex(txt);
                        int col = GridPane.getColumnIndex(txt);


                        Integer correctValue = model.getBoard().get(row).get(col);

                        if (Integer.parseInt(newValue) == correctValue) {
                            txt.setEditable(false);
                            CorrectNumberAnimation correctNumberAnimation = new CorrectNumberAnimation(txt);
                            correctNumberAnimation.start();

                            if (model.isRowComplete(row, sudokuGrid)) {
                                onRowComplete(GridPane.getRowIndex(txt));  // Trigger row animation
                            }
                            if (model.isColumnComplete(col, sudokuGrid)) {
                                onColumnComplete(GridPane.getColumnIndex(txt));
                            }
                            if (model.isSubGridComplete(sudokuGrid, row, col)) {
                                onSubGridComplete(row, col);
                            }
                        } else {
                            txt.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                            showAlert("Error", "Número incorrecto", "El número que ingresaste es incorrecto. Inténtalo de nuevo.");
                        }
                    } else if (newValue.isEmpty()) {
                        txt.setStyle("");
                    }
                });

                txt.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getText();

                    if (newText.isEmpty() || newText.matches("^[1-6]$")) {
                        return change;
                    }

                    return null;
                }));
            }
        }
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
        if (helpUses >= MAX_HELP_USES) {
            showAlert("Límite de ayudas", "Has alcanzado el máximo de ayudas.", "Ya no puedes usar más ayudas.");
            helpButton.setDisable(true);
            return;
        }

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

            helpUses++;

            if (helpUses >= MAX_HELP_USES) {
                showAlert("Límite de ayudas", "Has alcanzado el máximo de ayudas.", "Ya no puedes usar más ayudas .");
                helpButton.setDisable(true);
            }
        }
    }

    public void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void onRowComplete(int rowIndex) {
        RowAnimation rowAnimation = new RowAnimation(sudokuGrid, rowIndex);
        rowAnimation.start();
    }
    private void onColumnComplete(Integer columnIndex) {
        ColumnAnimation columnAnimation = new ColumnAnimation(sudokuGrid, columnIndex);
        columnAnimation.start();
    }
    private void onSubGridComplete(Integer rowIndex, Integer columnIndex) {
        SubGridAnimation subGridAnimation = new SubGridAnimation(sudokuGrid, rowIndex, columnIndex);
        subGridAnimation.start();
    }
}