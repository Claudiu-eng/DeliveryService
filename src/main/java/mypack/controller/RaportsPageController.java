package mypack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import mypack.BusinessLayer.*;
import mypack.BusinessLayer.MenuItem;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RaportsPageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;

    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button raport1Button,raport2Button,raport3Button,raport4Button;
    @FXML
    public TextField firtsCriterium,secondCriterium;

    private DeliveryService deliveryService;

    public void raport1ButtonOnAction(ActionEvent actionEvent) {
        try {
            messageTextArea.setText("");
            int startHour = Integer.parseInt(firtsCriterium.getText());
            int endHour = Integer.parseInt(secondCriterium.getText());
            Stream<Order> itemStream =deliveryService.getOrder().keySet().stream().filter(d -> d.getDate().getHour()>=startHour && d.getDate().getHour()<=endHour);
            String []msg={""};
            itemStream.forEach(p -> msg[0]=msg[0]+p.toString()+"\n");
            messageTextArea.setText(msg[0]);
        }catch (NumberFormatException a){
            showAlertWithHeaderText("for this raport ,it s necessary to introduce only digits for twice criteriums");
        }
    }

    public void raport2ButtonOnAction(ActionEvent actionEvent) {
        try {
            messageTextArea.setText("");
            int number = Integer.parseInt(firtsCriterium.getText());
            Stream<BaseProduct> itemStream=deliveryService.getBaseProductHashMap().values().stream().filter(d -> d.getOrderedTimes() >= number);
            String []msg={""};
            itemStream.forEach(p -> msg[0]=msg[0]+p.toString()+"\n");
            Stream<CompositeProduct> itemStream1=deliveryService.getCompositeProductHashMap().values().stream().filter(d -> d.getOrderedTimes() >= number);
            itemStream1.forEach(p -> msg[0]=msg[0]+p.toString()+"\n");

            messageTextArea.setText(msg[0]);
        }catch (NumberFormatException a){
            showAlertWithHeaderText("for this raport ,it s necessary to introduce only digits for first criterium");
        }
    }

    public void raport3ButtonOnAction(ActionEvent actionEvent) {
        try {
            messageTextArea.setText("");
            int number = Integer.parseInt(firtsCriterium.getText());
            int sum = Integer.parseInt(secondCriterium.getText());
            List<User> itemStream=deliveryService.getUsers().stream().filter(d -> d.getNrComenzi() >= number).collect(Collectors.toList());
            List<Order> orderStream=deliveryService.getOrder().keySet().stream().filter(d -> d.getValue() >= sum).collect(Collectors.toList());

            String []msg={""};
            for(User user:itemStream)
                for (Order order:orderStream){
                    if(user.getId()== order.getClientID())
                    {
                        msg[0]=msg[0]+user.toString()+"\n";
                    }
                }

            messageTextArea.setText(msg[0]);
        }catch (NumberFormatException a){
            showAlertWithHeaderText("for this raport ,it s necessary to introduce only integer digits for twice criteriums");
        }
    }

    public void raport4ButtonOnAction(ActionEvent actionEvent) {
        try {
            messageTextArea.setText("");
            int number = Integer.parseInt(firtsCriterium.getText());
            int sum = Integer.parseInt(secondCriterium.getText());
            List<MenuItem> itemStream=deliveryService.getBaseProductHashMap().values().stream().filter(d -> d.getOrderedTimes() >= sum).collect(Collectors.toList());
            List<MenuItem> itemStream1=deliveryService.getCompositeProductHashMap().values().stream().filter(d -> d.getOrderedTimes() >= sum).collect(Collectors.toList());
            List<Order> orderStream=deliveryService.getOrder().keySet().stream().filter(d -> d.getDate().getDay() == number).collect(Collectors.toList());
            itemStream.addAll(itemStream1);

            String []msg={""};
            for(Order order:orderStream)
                for (MenuItem menuItem:itemStream){
                    if(deliveryService.getOrder().get(order).contains(menuItem))
                    {
                        msg[0]=msg[0]+menuItem.toString()+"\n";
                    }
                }
            messageTextArea.setText(msg[0]);
        }catch (NumberFormatException a){
            showAlertWithHeaderText("for this raport ,it s necessary to introduce only integer digits for twice criteriums");
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) {
        try {
            changeScene("/mypack/AdminGUI.fxml", actionEvent);
        } catch (Exception e) {
            showAlertWithHeaderText("(Maybe doesn't exists fxml file)\n" + e.getMessage());
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            fileInputStream = new FileInputStream("C:\\Curs TP\\TP-Lab\\tema4\\date_deliveryService.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            deliveryService = (DeliveryService) objectInputStream.readObject();
            objectInputStream.close();

            Tooltip raport1Tooltip = new Tooltip();
            raport1Tooltip.setText(" time interval of the orders – orders performed \n" + "between a given start hour and a given end hour regardless the date.");
            raport1Tooltip.setShowDelay(Duration.millis(0));
            raport1Tooltip.setShowDuration(Duration.INDEFINITE);
            raport1Button.setTooltip(raport1Tooltip);

            Tooltip raport2Tooltip = new Tooltip();
            raport2Tooltip.setText("the products ordered more than a specified number of times so far.(type the val in first criterium)");
            raport2Tooltip.setShowDelay(Duration.millis(0));
            raport2Tooltip.setShowDuration(Duration.INDEFINITE);
            raport2Button.setTooltip(raport2Tooltip);

            Tooltip raport3Tooltip = new Tooltip();
            raport3Tooltip.setText("the clients that have ordered more than a specified number of times so far and the \n" +
                    "value of the order was higher than a specified amount.\n");
            raport3Tooltip.setShowDelay(Duration.millis(0));
            raport3Tooltip.setShowDuration(Duration.INDEFINITE);
            raport3Button.setTooltip(raport3Tooltip);

            Tooltip raport4Tooltip = new Tooltip();
            raport4Tooltip.setText(" the products ordered within a specified day at least (second crt ) has been ordered.(day for first criterium)");
            raport4Tooltip.setShowDelay(Duration.millis(0));
            raport4Tooltip.setShowDuration(Duration.INDEFINITE);
            raport4Button.setTooltip(raport4Tooltip);

        }catch (Exception e){
            showAlertWithHeaderText("ititilize raports page error");
        }
    }
}
