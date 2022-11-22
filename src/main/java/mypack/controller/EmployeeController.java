package mypack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class EmployeeController implements Observer ,Initializable{

    @FXML
    private TextArea messageTextArea;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Singleton singleton=Singleton.getInstance();

    public EmployeeController() throws IOException {
        this.messageTextArea = new TextArea();
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = singleton.getSceneLogIn();
        stage.setScene(scene);
        singleton.getLogInController().initialize(null,null);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();

    }

    @Override
    public void update(Observable o, Object arg) {
        String m=(String)arg;
        messageTextArea.appendText(m);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageTextArea.setText("");

    }
}
