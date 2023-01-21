package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setScene(new Scene(root, 1300, 600));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void  main(String[] args) {

        launch(args);
    }
}
