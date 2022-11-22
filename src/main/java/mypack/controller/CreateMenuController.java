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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mypack.BusinessLayer.BaseProduct;
import mypack.BusinessLayer.CompositeProduct;
import mypack.BusinessLayer.DeliveryService;
import mypack.BusinessLayer.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateMenuController implements Initializable {

    @FXML
    private TextField titleTextField, priceTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private DeliveryService deliveryService;
    private double sum = 0.0;

    @FXML
    private TableColumn<BaseProduct, Integer> sodium, calories, proteins, fats, sodiumMenu, caloriesMenu, proteinsMenu, fatsMenu;
    @FXML
    private TableColumn<BaseProduct, Double> price, priceMenu;
    @FXML
    private TableColumn<BaseProduct, String> title, titleMenu;
    @FXML
    private TableColumn<BaseProduct, Float> rating, ratingMenu;
    @FXML
    private TableView productsTable, menuTable;

    public void backButtonOnAction(ActionEvent actionEvent) {
        try {
            changeScene("/mypack/AdminGUI.fxml", actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists fxml file)\n" + e.getMessage());
        }
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        BaseProduct title = (BaseProduct) menuTable.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to be removed from menu");
        else {
            menuTable.getItems().remove(title);
            menuTable.getSelectionModel().clearSelection();
            sum = sum - title.getPrice();
            priceTextField.setText(String.valueOf(sum));
        }
    }

    private void reset(){
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
        menuTable.setItems(menuItems);
        sum=0;
        titleTextField.setText("");
        priceTextField.setText("");
    }

    public void applyButtonOnAction(ActionEvent actionEvent) {
        if (titleTextField.getText().isEmpty() || priceTextField.getText().isEmpty() || menuTable.getItems().size() < 1)
            showAlertWithHeaderText("complete all fields");
        else {
            try {
                ObservableList<BaseProduct> bp = menuTable.getItems();
                ArrayList<BaseProduct> products = new ArrayList<>();
                CompositeProduct compositeProduct = new CompositeProduct();
                for (BaseProduct p : bp) {
                    products.add(p);
                    compositeProduct.setNumberOfFats(compositeProduct.getNumberOfFats()+ p.getNumberOfFats());
                    compositeProduct.setNumberOfCalories(compositeProduct.getNumberOfCalories()+ p.getNumberOfCalories());
                    compositeProduct.setNumberOfProteins(compositeProduct.getNumberOfProteins()+ p.getNumberOfProteins());
                    compositeProduct.setNumberOfSodium(compositeProduct.getNumberOfSodium()+ p.getNumberOfSodium());
                    compositeProduct.setRating(compositeProduct.getRating()+p.getRating());
                }
                compositeProduct.setBaseProducts(products);
                Double db = Double.parseDouble(priceTextField.getText());
                compositeProduct.setTitle(titleTextField.getText());
                compositeProduct.setPrice(db);
                compositeProduct.setRating(compositeProduct.getRating()/menuTable.getItems().size());
                deliveryService.getCompositeProductHashMap().put(titleTextField.getText(), compositeProduct);
                serialize();
                reset();
            } catch (NumberFormatException e) {
                showAlertWithHeaderText("the price is incorect");
            }
        }
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        BaseProduct title = (BaseProduct) productsTable.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to be insered in menu");
        else {
            menuTable.getItems().add(title);
            sum = sum + title.getPrice();
            priceTextField.setText(String.valueOf(sum));
            productsTable.getSelectionModel().clearSelection();
        }
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

    private void changeScene(String fxmlFile, ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.close();
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();

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
            ratingMenu.setCellValueFactory(new PropertyValueFactory<>("rating"));
            sodiumMenu.setCellValueFactory(new PropertyValueFactory<>("numberOfSodium"));
            fatsMenu.setCellValueFactory(new PropertyValueFactory<>("numberOfFats"));
            priceMenu.setCellValueFactory(new PropertyValueFactory<>("price"));
            caloriesMenu.setCellValueFactory(new PropertyValueFactory<>("numberOfCalories"));
            titleMenu.setCellValueFactory(new PropertyValueFactory<>("title"));
            proteinsMenu.setCellValueFactory(new PropertyValueFactory<>("numberOfProteins"));

            sum = 0;
            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getBaseProductHashMap().values());
            productsTable.setItems(menuItems);
        } catch (Exception e) {
            showAlertWithHeaderText("initilize createMenu page error");
        }
    }
}
