package com.example.proyecto2fpoe.View;

import com.example.proyecto2fpoe.Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GameStage extends Stage {
    private GameController gameController;

    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyecto2fpoe/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Sudoku");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/proyecto2fpoe/images/sudokuIcon.png"))));
        setResizable(false);
        show();
    }

    /**
     * This static inner class holds the singleton instance of GameStage.
     */
    private static class GameStageHolder{
        private static GameStage INSTANCE;
    }

    /**
     * Returns the singleton instance of GameStage.
     * If the instance does not exist, it creates a new one.
     *
     * @return the singleton instance of GameStage.
     * @throws IOException if there is an error creating the stage.
     */
    public static GameStage getInstance() throws IOException{
        GameStageHolder.INSTANCE =
                GameStageHolder.INSTANCE != null ?
                        GameStageHolder.INSTANCE :
                        new GameStage();

        return GameStageHolder.INSTANCE;
    }

    /**
     * Deletes the current singleton instance of GameStage.
     * This closes the stage and sets the instance to null.
     */
    public static void deleteInstance(){
        GameStageHolder.INSTANCE.close();
        GameStageHolder.INSTANCE = null;
    }
}
