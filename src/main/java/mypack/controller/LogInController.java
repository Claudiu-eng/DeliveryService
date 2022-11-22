package mypack.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mypack.BusinessLayer.DeliveryService;
import mypack.BusinessLayer.User;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public final class LogInController implements Initializable {


    @FXML
    private ComboBox rolesBox;
    @FXML
    private TextField usernameTextField, passwordTextField;

    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private DeliveryService deliveryService;

    private Stage stage;
    private Scene scene;
    private Singleton singleton = Singleton.getInstance();


    private int id = 1;

    public LogInController() throws IOException {
    }
    /*
     * objectInputStream poate citi din fisier doar dupa ce s-a facut serializarea cu
     * outputStream
     * la prima rulare,cand fisieru e gol,ap pus try-catch ca sa sara peste
     * daca nu puneam =>crapa programu
     * */

    private void init() {
        id = 0;
        deliveryService = new DeliveryService(new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>());
        deliveryService.getUsers().add(new User(id++, "tulbure_claudiu@yahoo.ro", "1234", "Admin"));
        deliveryService.getUsers().add(new User(id, "claudiu tulbure", "1234", "Employee"));
        serialize();
    }

    private void deserialize() {
        try {
            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

        } catch (Exception e) {
        }
        if (deliveryService == null) {
            init();
        }
    }

    private boolean showAlertWithHeaderTextConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setContentText(msg);
        if (alert.showAndWait().get() == ButtonType.OK) {
            showAlertWithHeaderText("Congrats , you was registred!!");
            return true;
        } else return false;
    }

    public void logInButtonOnAction(ActionEvent actionEvent) throws IOException {

        if (rolesBox.getValue() == null || usernameTextField.getText().equals("") || passwordTextField.getText().equals(""))
            showAlertWithHeaderText("completati toate campurile");
        else {
            deserialize();

            User user=search();
            if (user == null && !rolesBox.getValue().equals("Admin") && !rolesBox.getValue().equals("Employee") && canCreateClientAccount()) {
                if (!showAlertWithHeaderTextConfirmation("you didn t have an account at our delivery food ,do you wish one?"))
                    return;
                id = deliveryService.getUsers().get(deliveryService.getUsers().size() - 1).getId() + 1;
                user = new User(id++, usernameTextField.getText(), passwordTextField.getText(), (String) rolesBox.getValue());
                deliveryService.getUsers().add(user);
                serialize();
            } else if (!canCreateClientAccount() && user == null) showAlertWithHeaderText("email already exists!!");

            //print();
            if (user != null) {
                switch (user.getRol()) {
                    case "Admin":
                        changeScene("/mypack/AdminGUI.fxml", actionEvent);
                        break;
                    case "Client":
                        changeScene("/mypack/ClientPage.fxml", actionEvent);
                        singleton.setId_client(user.getId());
                        break;
                    case "Employee":
                        changeScene("/mypack/EmployeeGUI.fxml", actionEvent);
                        break;
                }
            }
        }
    }

    private void print() {
        for (User user : deliveryService.getUsers())
            System.out.println(user.toString());
    }

    private boolean canCreateClientAccount() {
        for (User u : deliveryService.getUsers())
            if (u.getEmail().equals(usernameTextField.getText()))
                return false;
        return true;

    }

    private User search() {
        for (User u : deliveryService.getUsers()) {
            if (u.getEmail().equals(usernameTextField.getText()) && u.getPassword().equals(passwordTextField.getText()) && u.getRol().equals((String) rolesBox.getValue()))
                return u;
        }
        return null;
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


    private void changeScene(String fxmlFile, ActionEvent actionEvent) throws IOException {
        if(fxmlFile.endsWith("ClientPage.fxml")){
            scene= singleton.getSceneClient();
            singleton.getClientController().initialize(null,null);
        }else if(fxmlFile.endsWith("AdminGUI.fxml")){
            scene= singleton.getSceneAdmin();
            singleton.getAdminController().initialize(null,null);
        }else{
            scene=singleton.getSceneEmployee();
        }
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        ObservableList<String> observableList = FXCollections.observableArrayList("Admin", "Client", "Employee");
        rolesBox.setItems(observableList);
        usernameTextField.setText("");
        passwordTextField.setText("");
    }



}
