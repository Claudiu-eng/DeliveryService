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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mypack.BusinessLayer.BaseProduct;
import mypack.BusinessLayer.CompositeProduct;
import mypack.BusinessLayer.DeliveryService;
import mypack.BusinessLayer.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuesController implements Initializable {

    @FXML
    public TableView menuItemsTableView;
    @FXML
    private TableColumn<CompositeProduct, Integer> sodium, calories, proteins, fats;
    @FXML
    private TableColumn<CompositeProduct, Double> price;
    @FXML
    private TableColumn<CompositeProduct, String> title;
    @FXML
    private TableColumn<CompositeProduct, Float> rating;


    private Stage stage;
    private Scene scene;
    private Parent root;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private DeliveryService deliveryService;

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

    public void deleteMenuOnAction(ActionEvent actionEvent) {
        CompositeProduct title = (CompositeProduct) menuItemsTableView.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one menu selected to be removed from menues");
        else {
            menuItemsTableView.getItems().remove(title);
            menuItemsTableView.getSelectionModel().clearSelection();
            deliveryService.getCompositeProductHashMap().remove(title.getTitle());
            serialize();
        }
    }

    public void seeComponentsButtonOnAction(ActionEvent actionEvent) {
        CompositeProduct title = (CompositeProduct) menuItemsTableView.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one menu selected to see the components");
        else {
            String msg="";
            for(BaseProduct p:title.getBaseProducts())
                msg=msg+p.toString()+"\n";
            showAlertWithHeaderText(msg);
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        try {
            changeScene("/mypack/AdminGUI.fxml", actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists fxml file)\n" + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            sodium.setCellValueFactory(new PropertyValueFactory<>("numberOfSodium"));
            fats.setCellValueFactory(new PropertyValueFactory<>("numberOfFats"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            calories.setCellValueFactory(new PropertyValueFactory<>("numberOfCalories"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            proteins.setCellValueFactory(new PropertyValueFactory<>("numberOfProteins"));

            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getCompositeProductHashMap().values());
            menuItemsTableView.setItems(menuItems);
        } catch (Exception e) {
            showAlertWithHeaderText("ititilize createMenu page error");
        }
    }
}
