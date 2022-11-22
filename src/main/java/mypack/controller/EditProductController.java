package mypack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class EditProductController implements Initializable {

    @FXML
    private Label messageFinal;
    @FXML
    private Label productName;
    @FXML
    private TextField newRatingTextField, newProteinsTextField, newFatsTextField, newPriceTextField, newCaloriesTextField, newSodiumTextField;
    @FXML
    private TextField oldRatingTextField, oldProteinsTextField, oldFatsTextField, oldPriceTextField, oldCaloriesTextField, oldSodiumTextField;

    private Stage stage;
    private Scene scene;
    private DeliveryService deliveryService;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private final Singleton singleton = Singleton.getInstance();

    public EditProductController() throws IOException {
    }


    public void backBTNEvent(ActionEvent actionEvent) {
        try {
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = singleton.getSceneAdmin();
            stage.setScene(scene);
            singleton.getAdminController().initialize(null, null);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
            stage.show();
        } catch (Exception e) {
            showAlertWithHeaderText("back client button pb");
            e.printStackTrace();
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
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists file for serialize)\n" + e.getMessage());
        }
    }


    private void showAlertWithHeaderText(String p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setContentText(p);
        alert.showAndWait();
    }

    public void editButtonOnAction(ActionEvent actionEvent) {
        messageFinal.setText("");
        if (newPriceTextField.getText().equals("") || newCaloriesTextField.getText().equals("") || newFatsTextField.getText().equals("")
                || newProteinsTextField.getText().equals("") || newSodiumTextField.getText().equals("") || newRatingTextField.getText().equals(""))
            showAlertWithHeaderText("complete all fields!!!");
        else {
            try {
                BaseProduct bp = new BaseProduct(productName.getText(), Float.parseFloat(newRatingTextField.getText()), Integer.parseInt(newCaloriesTextField.getText()),
                        Integer.parseInt(newProteinsTextField.getText()), Integer.parseInt(newFatsTextField.getText()), Integer.parseInt(newSodiumTextField.getText()),
                        Double.parseDouble(newPriceTextField.getText()));
                deliveryService.modifyProduct(bp);
                messageFinal.setText("Succesfull!");
                serialize();
            } catch (NumberFormatException e) {
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
            BaseProduct product = (BaseProduct) deliveryService.getBaseProductHashMap().get(singleton.getBaseProduct());
            productName.setText(product.getTitle());
            oldRatingTextField.setText(String.valueOf(product.getRating()));
            oldPriceTextField.setText(String.valueOf(product.getPrice()));
            oldFatsTextField.setText(String.valueOf(product.getNumberOfFats()));
            oldCaloriesTextField.setText(String.valueOf(product.getNumberOfCalories()));
            oldProteinsTextField.setText(String.valueOf(product.getNumberOfProteins()));
            oldSodiumTextField.setText(String.valueOf(product.getNumberOfSodium()));
            newRatingTextField.setText("");
            newSodiumTextField.setText("");
            newPriceTextField.setText("");
            newFatsTextField.setText("");
            newProteinsTextField.setText("");
            newCaloriesTextField.setText("");
            messageFinal.setText("");
        } catch (Exception e) {
            showAlertWithHeaderText("problems with deserialization \n " +
                    "maybe the path is wrong ,or encoding file,or idk :(");
        }

    }
}
