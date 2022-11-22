package mypack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mypack.BusinessLayer.BaseProduct;
import mypack.BusinessLayer.DeliveryService;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private Label messageFinal;

    @FXML
    private TextField ratingTextField, proteinsTextField, fatsTextField, priceTextField, caloriesTextField, sodiumTextField,titleTextField;


    private Stage stage;
    private Scene scene;
    private Parent root;
    private DeliveryService deliveryService;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private Singleton singleton=Singleton.getInstance();

    public AddProductController() throws IOException {
    }


    public void backBTNEvent(ActionEvent actionEvent) {
        try {
            changeScene("/mypack/AdminGUI.fxml", actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists fxml file)\n" + e.getMessage());
        }
    }

    private void serialize() {
        try {

            fileOutputStream =
                    new FileOutputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject((Object) deliveryService);
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            showAlertWithHeaderText("(Maybe doesn't exists file for serialize)\n" + e.getMessage());
        }
    }

    private void changeScene(String fxmlFile, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    private void showAlertWithHeaderText(String p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setContentText(p);
        alert.showAndWait();
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        messageFinal.setText("");
        if (priceTextField.getText().equals("") || caloriesTextField.getText().equals("") || fatsTextField.getText().equals("")
                || proteinsTextField.getText().equals("") || sodiumTextField.getText().equals("") || ratingTextField.getText().equals("")
        || titleTextField.getText().equals(""))
            showAlertWithHeaderText("complete all fields!!!");
        else {
            try {
                BaseProduct bp = new BaseProduct(titleTextField.getText(), Float.parseFloat(ratingTextField.getText()), Integer.parseInt(caloriesTextField.getText()),
                        Integer.parseInt(proteinsTextField.getText()), Integer.parseInt(fatsTextField.getText()), Integer.parseInt(sodiumTextField.getText()),
                        Double.parseDouble(priceTextField.getText()));
                deliveryService.addProduct(bp);
                messageFinal.setText("Succesfull!");
                serialize();
            }catch (NumberFormatException e){
                showAlertWithHeaderText("text only valid numbers");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();
        }catch (Exception e){
            showAlertWithHeaderText("problems with deserialization \n " +
                    "maybe the path is wrong ,or encoding file,or idk :(");
        }

    }
}
