package mypack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mypack.controller.LogInController;
import mypack.controller.Singleton;

import java.io.IOException;


public class Main extends Application {
    private final Singleton singleton=Singleton.getInstance();

    public Main() throws IOException {
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = singleton.getSceneLogIn();
        stage.setResizable(false);
        stage.getIcons().add(new Image("C:\\Users\\tulbu\\Downloads\\clientMan.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
     launch();


    }
}
