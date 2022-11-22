package mypack.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mypack.BusinessLayer.*;
import mypack.BusinessLayer.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class ClientController implements Initializable {

    @FXML
    private ComboBox criterias;
    @FXML
    private TextField valueTextField, priceTextField;
    private FileWriter fileWriter;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;
    private DeliveryService deliveryService;
    private double s;
    private int id_order;

    @FXML
    private TableView productsTable, orderTable;
    @FXML
    private TableColumn<MenuItem, Integer> sodium, calories, proteins, fats, sodiumOrder, caloriesOrder, proteinsOrder, fatsOrder;
    @FXML
    private TableColumn<MenuItem, Double> price, priceOrder;
    @FXML
    private TableColumn<MenuItem, String> title, titleOrder;
    @FXML
    private TableColumn<MenuItem, Float> rating, ratingOrder;
    private final Singleton singleton = Singleton.getInstance();

    public ClientController() throws IOException {
    }

    public void searchButtonOnAction(ActionEvent actionEvent) {
        try {
            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getBaseProductHashMap().values());
            ObservableList<MenuItem> menuItems1 = FXCollections.observableArrayList(deliveryService.getCompositeProductHashMap().values());
            menuItems = FXCollections.concat(menuItems, menuItems1);

            ArrayList<MenuItem> products = new ArrayList<>(menuItems);
            Stream<MenuItem> itemStream = products.stream();
            ObservableList<MenuItem> finalMenuItems;

            itemStream = getItemStream(itemStream, products);

            menuItems.clear();
            finalMenuItems = menuItems;
            itemStream.forEach(p -> finalMenuItems.add(p));
            productsTable.setItems(finalMenuItems);
        } catch (Exception n) {
            showAlertWithHeaderText("introduce valide data value!!!");
        }
    }

    private Stream<MenuItem> getItemStream(Stream<MenuItem> itemStream, ArrayList<MenuItem> products) {
        String filtre = "", name;
        int value;
        float rating;
        double price;
        filtre = (String) criterias.getValue();
        if (filtre.equals("title")) {
            name = valueTextField.getText();
            itemStream = products.stream().filter(d -> d.getTitle().contains(name));
        } else if ((!filtre.equals("price") && !filtre.equals("rating")) && valueTextField.getText().length() > 0) {
            value = Integer.parseInt(valueTextField.getText());
            if (filtre.equals("proteins"))
                itemStream = products.stream().filter((d -> d.getNumberOfProteins() == value));
            else if (filtre.equals("sodium"))
                itemStream = products.stream().filter((d -> d.getNumberOfSodium() == value));
            else if (filtre.equals("fats"))
                itemStream = products.stream().filter((d -> d.getNumberOfFats() == value));
            else if (filtre.equals("calories"))
                itemStream = products.stream().filter((d -> d.getNumberOfCalories() == value));
        } else if (filtre.equals("rating") && valueTextField.getText().length() > 0) {
            rating = Float.parseFloat(valueTextField.getText());
            itemStream = products.stream().filter((d -> Math.abs(d.getRating() - rating) <= 1e-4));
        } else if (valueTextField.getText().length() > 0) {
            price = Double.parseDouble(valueTextField.getText());
            itemStream = products.stream().filter((d -> Math.abs(d.getPrice() - price) <= 1e-4));
        }
        return itemStream;
    }

    public void deleteButtonOnAction(ActionEvent actionEvent) {
        MenuItem title = (MenuItem) orderTable.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to be insered in menu");
        else {
            orderTable.getItems().remove(title);
            s -= title.getPrice();
            priceTextField.setText(String.valueOf(s));
            orderTable.getSelectionModel().clearSelection();
        }
    }

    private void reset() {
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
        orderTable.setItems(menuItems);
        s = 0;
        valueTextField.setText("");
        priceTextField.setText("");
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        try {
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = singleton.getSceneLogIn();
            stage.setScene(scene);
            singleton.getLogInController().initialize(null,null);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
            stage.show();
        }catch (Exception e){
            showAlertWithHeaderText("back client button pb");
            e.printStackTrace();
        }
    }

    public void applyButtonOnAction(ActionEvent actionEvent) throws IOException {

        if(orderTable.getItems().size()==0 )
            showAlertWithHeaderText("no one product in thje comand");
        else {
            Random random = new Random();
            ObservableList<MenuItem> bp = orderTable.getItems();
            id_order = deliveryService.getOrder().size();
            Order order = new Order(id_order++, singleton.getId_client(), new Date(Math.abs(random.nextInt()), Math.abs(random.nextInt()),
                    Math.abs(random.nextInt()), Math.abs(random.nextInt())), Double.parseDouble(priceTextField.getText()));
            ArrayList<MenuItem> products = new ArrayList<>(bp);
            deliveryService.addObserver(singleton.getEmployeeController());
            try {
                fileWriter = new FileWriter("bill.txt", true);
                fileWriter.append("The client " + singleton.getId_client() + " has ordered " + " " + order.toString() + "\n");
                fileWriter.close();
            } catch (Exception e) {
                showAlertWithHeaderText("file for bill might is not avilable");
            }
            deliveryService.createOrder(order, products);
            serialize();
            reset();
        }
    }

    public void addButtonOnAction(ActionEvent actionEvent) {
        MenuItem title = (MenuItem) productsTable.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one product selected to be insered in menu");
        else {
            orderTable.getItems().add(title);
            s += title.getPrice();
            priceTextField.setText(String.valueOf(s));
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
            fileOutputStream = new FileOutputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject((Object) deliveryService);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists file for serialize)\n" + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reset();
            rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            sodium.setCellValueFactory(new PropertyValueFactory<>("numberOfSodium"));
            fats.setCellValueFactory(new PropertyValueFactory<>("numberOfFats"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            calories.setCellValueFactory(new PropertyValueFactory<>("numberOfCalories"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            proteins.setCellValueFactory(new PropertyValueFactory<>("numberOfProteins"));
            ratingOrder.setCellValueFactory(new PropertyValueFactory<>("rating"));
            sodiumOrder.setCellValueFactory(new PropertyValueFactory<>("numberOfSodium"));
            fatsOrder.setCellValueFactory(new PropertyValueFactory<>("numberOfFats"));
            priceOrder.setCellValueFactory(new PropertyValueFactory<>("price"));
            caloriesOrder.setCellValueFactory(new PropertyValueFactory<>("numberOfCalories"));
            titleOrder.setCellValueFactory(new PropertyValueFactory<>("title"));
            proteinsOrder.setCellValueFactory(new PropertyValueFactory<>("numberOfProteins"));

            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

            ObservableList<String> observableList = FXCollections.observableArrayList("sodium", "fats", "title", "calories", "proteins", "price", "rating");
            criterias.setItems(observableList);

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(deliveryService.getBaseProductHashMap().values());
            ObservableList<MenuItem> menuItems1 = FXCollections.observableArrayList(deliveryService.getCompositeProductHashMap().values());
            menuItems = FXCollections.concat(menuItems, menuItems1);
            productsTable.setItems(menuItems);

            s = 0;
        } catch (Exception e) {
            showAlertWithHeaderText("initilize Client page page error");
        }
    }

    public void seeComponentsButtonOnAction(ActionEvent actionEvent) {
        MenuItem title = (MenuItem) productsTable.getSelectionModel().getSelectedItem();
        if (title == null)
            showAlertWithHeaderText("no one menu selected to see the components");
        else if (!title.getClass().getSimpleName().equals("BaseProduct")) {
            String msg = "";
            CompositeProduct cp = (CompositeProduct) title;
            for (BaseProduct p : cp.getBaseProducts())
                msg = msg + p.toString() + "\n";
            showAlertWithHeaderText(msg);
        } else showAlertWithHeaderText("Item selected is not a composite product");
    }
}
