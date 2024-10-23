package com.example.proyecto2fpoe.Controller;

import com.example.proyecto2fpoe.Model.Animation.*;
import com.example.proyecto2fpoe.Model.List.IList;
import com.example.proyecto2fpoe.Model.SudokuModel;
import com.example.proyecto2fpoe.View.GameStage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.*;

public class GameController {
    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Button helpButton;
    @FXML
    private Text winAdvice;
    @FXML
    private TextField attempts;
    @FXML
    private TextField correct;
    @FXML
    private TextField helpsLeft;

    private SudokuModel model;
    private int helpUses = 0;
    private int remainingHelps = 6;
    private int tries = 0;
    private int correctEntries = 0;
    private final int SIZE = 6;
    @FXML
    public void initialize() {
        model = new SudokuModel();
        model.generatePuzzle(0);
        populateGrid();
        printBoard(model.getBoard());
        assignGridIndex();
        assignListeners();
        winAdvice.setLayoutX(15);
        winAdvice.setLayoutY(200);
        winAdvice.setOpacity(0);

        attempts.setEditable(false);
        correct.setEditable(false);
        helpsLeft.setEditable(false);

        correct.setText(String.valueOf(correctEntries));
        attempts.setText(String.valueOf(tries));
        helpsLeft.setText(String.valueOf(remainingHelps));

    }

    private void assignListeners() {
        for (Node node : sudokuGrid.getChildren()) {
            if (node instanceof TextField txt) {
                txt.textProperty().addListener((observable, oldValue, newValue) -> {
                    // Check if the input is valid (only one character)
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
                            correctEntries++;
                            correct.setText(String.valueOf(correctEntries));

                            if (model.isRowComplete(row, sudokuGrid)) {
                                onRowComplete(GridPane.getRowIndex(txt));  // Trigger row animation
                            }
                            if (model.isColumnComplete(col, sudokuGrid)) {
                                onColumnComplete(GridPane.getColumnIndex(txt));
                            }
                            if (model.isSubGridComplete(sudokuGrid, row, col)) {
                                onSubGridComplete(row, col);
                            }
                            if (model.isBoardComplete(sudokuGrid)) {
                                onBoardComplete();
                                adviceAnimation();
                                winAlert("Has ganado!", "Completaste el sudoku!.");
                            }
                        } else {
                            tries++;
                            attempts.setText(String.valueOf(tries));
                            txt.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                            showAlert("Error", "Número incorrecto", "El número que ingresaste es incorrecto. Inténtalo de nuevo.");
                            txt.clear();
                        }
                    } else if (newValue.isEmpty()) {
                        txt.setStyle("");
                    }
                });

                // Listener for numbers only (and allow deletion)
                txt.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getText();

                    // Allow deletion (empty string) or valid numbers (1-6)
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
        final int  MAX_HELP_USES = 6;
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
            remainingHelps --;
            helpsLeft.setText(String.valueOf(remainingHelps));

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

    private void onRowComplete(int rowIndex) {
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

    private void onBoardComplete() {
        BoardAnimation boardAnimation = new BoardAnimation(sudokuGrid);
        boardAnimation.start();
    }

    private void adviceAnimation(){
        AdviceAnimation adviceAnimation = new AdviceAnimation();
        adviceAnimation.start(winAdvice);
    }

    public void winAlert(String title, String headerText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText("¿Quieres volver a jugar?");

        ButtonType replayButton = new ButtonType("Volver a jugar");
        ButtonType cancelButton = new ButtonType("Salir", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(replayButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == replayButton) {
                try {
                    resetGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                GameStage.deleteInstance();
            }
        });
    }

    private void resetGame() throws IOException {
        GameStage.deleteInstance();
        GameStage.getInstance();
    }
}

