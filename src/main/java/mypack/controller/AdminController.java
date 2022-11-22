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
import mypack.BusinessLayer.DeliveryService;
import mypack.BusinessLayer.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private Stage stage;
    private Scene scene;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private Singleton singleton = Singleton.getInstance();

    @FXML
    private TableView menuItemsTableView;
    private DeliveryService deliveryService;
    @FXML
    private TableColumn<MenuItem, String> title;
    @FXML
    private TableColumn<MenuItem, Float> rating;
    @FXML
    private TableColumn<MenuItem, Double> price;
    @FXML
    private TableColumn<MenuItem, Integer> sodium, calories, fat, protein;

    public AdminController() throws IOException {
    }


    public void addProductOnAction(ActionEvent actionEvent) {
        try {
            scene = singleton.getSceneAddProduct();
            singleton.getAddProductController().initialize(null, null);
            changeScene(actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText(e.getMessage());
            e.printStackTrace();
        }
    }

    private void serialize(Object object) {
        try {

            fileOutputStream =
                    new FileOutputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject((Object) object);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists file for serialize)\n" + e.getMessage());
        }
    }


    public void importProductsOnAction(ActionEvent actionEvent) {
        try {
            deliveryService.importProducts();
            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getBaseProductHashMap().values());
            menuItemsTableView.setItems(menuItems);
            serialize(deliveryService);
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists)\n" + e.getMessage());

        }
    }

    public void deleteProductOnAction(ActionEvent actionEvent) {
        MenuItem title = (MenuItem) menuItemsTableView.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to fe removed");
        else {
            deliveryService.deleteProduct(title.getTitle());
            menuItemsTableView.getItems().remove(title);
            menuItemsTableView.getSelectionModel().clearSelection();
            serialize(deliveryService);

        }
    }

    public void editProductOnAction(ActionEvent actionEvent) {
        BaseProduct title = (BaseProduct) menuItemsTableView.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to be edited");
        else {
            try {
                singleton.setBaseProduct(title.getTitle());
                scene = singleton.getSceneEditProduct();
                singleton.getEditProductController().initialize(null, null);
                changeScene(actionEvent);
            } catch (Exception e) {
                showAlertWithHeaderText(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void menuesButtonOnAction(ActionEvent actionEvent) {
        try {
            scene = singleton.getSceneMenu();
            singleton.getMenuesController().initialize(null, null);
            changeScene(actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void createNewMenuButtonOnAction(ActionEvent actionEvent) {
        try {
            scene = singleton.getSceneCreateMenu();
            singleton.getCreateMenuController().initialize(null, null);
            changeScene(actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void seeReportsButtonOnAction(ActionEvent actionEvent) {
        try {
            scene = singleton.getSceneRaportsPage();
            singleton.getRaportsPageController().initialize(null, null);
            changeScene(actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        scene = singleton.getSceneLogIn();
        singleton.getLogInController().initialize(null, null);
        changeScene(actionEvent);
    }

    private void changeScene(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            sodium.setCellValueFactory(new PropertyValueFactory<>("numberOfSodium"));
            fat.setCellValueFactory(new PropertyValueFactory<>("numberOfFats"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            calories.setCellValueFactory(new PropertyValueFactory<>("numberOfCalories"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            protein.setCellValueFactory(new PropertyValueFactory<>("numberOfProteins"));

            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getBaseProductHashMap().values());
            menuItemsTableView.setItems(menuItems);
        } catch (Exception e) {
            showAlertWithHeaderText("ititilize admin page error");
        }
    }
}
