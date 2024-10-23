package com.example.proyecto2fpoe;

import com.example.proyecto2fpoe.View.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the application that extends the JavaFX Application class.
 * This class is responsible for launching the application and setting up the primary stage.
 */
public class Main extends Application {

    /**
     * The main entry point of the JavaFX application.
     * This method launches the application by calling the launch method.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the primary stage and sets up the game stage.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameStage.getInstance();
    }
}
