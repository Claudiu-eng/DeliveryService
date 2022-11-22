package mypack.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;

public final class Singleton {

    private static Singleton oneObject;
    private static Scene sceneLogIn;
    private String baseProduct;
    private int id_client;
    private static EmployeeController employeeController;
    private static Scene sceneEmployee, sceneClient, sceneAdmin, sceneAddProduct, sceneEditProduct, sceneRaportsPage, sceneCreateMenu, sceneMenu;
    private static LogInController logInController;
    private static AdminController adminController;
    private static AddProductController addProductController;
    private static EditProductController editProductController;
    private static RaportsPageController raportsPageController;
    private static CreateMenuController createMenuController;
    private static MenuesController menuesController;
    private static ClientController clientController;
    private static FXMLLoader fxmlLoader;
    private static Parent parent;

    public static Singleton getInstance() throws IOException {
        if (oneObject == null) {
            oneObject = new Singleton();
        }
        return oneObject;
    }

    private void showAlertWithHeaderText(String p) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("WARNING");
        alert.setContentText(p);
        alert.showAndWait();
    }

    public LogInController getLogInController() throws IOException {
        if(logInController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneLogIn");
        }
        return logInController;
    }

    public Scene getSceneLogIn() throws IOException {
        if(sceneLogIn==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/LogIn.fxml"));
            parent = fxmlLoader.load();
            sceneLogIn = new Scene(parent);
            logInController = fxmlLoader.getController();
        }
        return sceneLogIn;
    }

    public ClientController getClientController() {
        if(clientController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneClient");
        }
        return clientController;
    }

    public Scene getSceneMenu() throws IOException {
        if(sceneMenu==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/Menues.fxml"));
            parent = fxmlLoader.load();
            sceneMenu = new Scene(parent);
            menuesController = fxmlLoader.getController();
        }
        return sceneMenu;
    }

    public MenuesController getMenuesController() {
        if(menuesController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneMenu");
        }
        return menuesController;
    }

    public Scene getSceneCreateMenu() throws IOException {
        if(sceneCreateMenu==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/CreateMenu.fxml"));
            parent = fxmlLoader.load();
            sceneCreateMenu = new Scene(parent);
            createMenuController = fxmlLoader.getController();
        }
        return sceneCreateMenu;
    }

    public CreateMenuController getCreateMenuController() {
        if(createMenuController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneCreateMenu");
        }
        return createMenuController;
    }

    public Scene getSceneRaportsPage() throws IOException {
        if(sceneRaportsPage==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/RaportsPage.fxml"));
            parent = fxmlLoader.load();
            sceneRaportsPage = new Scene(parent);
            raportsPageController = fxmlLoader.getController();
        }
        return sceneRaportsPage;
    }

    public RaportsPageController getRaportsPageController() {
        if(raportsPageController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneRaports");
        }
        return raportsPageController;
    }

    public Scene getSceneEditProduct() throws IOException {
        if(sceneEditProduct==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/EditProduct.fxml"));
            parent = fxmlLoader.load();
            sceneEditProduct = new Scene(parent);
            editProductController = fxmlLoader.getController();
        }
        return sceneEditProduct;
    }

    public EditProductController getEditProductController() {
        if(editProductController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneEdit");
        }
        return editProductController;
    }

    public Scene getSceneAddProduct() throws IOException {
        if(sceneAddProduct==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/AddProduct.fxml"));
            parent = fxmlLoader.load();
            sceneAddProduct = new Scene(parent);
            addProductController = fxmlLoader.getController();
        }
        return sceneAddProduct;
    }

    public AddProductController getAddProductController() {
        if(addProductController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneAddProduct");
        }
        return addProductController;
    }

    public AdminController getAdminController() {
        if(adminController==null){
            showAlertWithHeaderText("you cannot obtain a reference to login \n" +
                    "because is null and is singleton , you have to create a sceneAdminGUI");
        }
        return adminController;
    }
    public Scene getSceneClient() throws IOException {
        if(sceneClient==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/ClientPage.fxml"));
            parent = fxmlLoader.load();
            sceneClient = new Scene(parent);
            clientController = fxmlLoader.getController();
        }
        return sceneClient;
    }
    public Scene getSceneAdmin() throws IOException {
        if(sceneAdmin==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/AdminGUI.fxml"));
            parent = fxmlLoader.load();
            sceneAdmin = new Scene(parent);
            adminController = fxmlLoader.getController();
        }
        return sceneAdmin;
    }

    public Scene getSceneEmployee() throws IOException {
        if(sceneEmployee==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/EmployeeGUI.fxml"));
            parent = fxmlLoader.load();
            sceneEmployee = new Scene(parent);
            employeeController = fxmlLoader.getController();
        }
        return sceneEmployee;
    }

    public EmployeeController getEmployeeController() throws IOException {
        if(employeeController==null){
            fxmlLoader = new FXMLLoader(Singleton.class.getResource("/mypack/EmployeeGUI.fxml"));
            parent = fxmlLoader.load();
            sceneEmployee = new Scene(parent);
            employeeController = fxmlLoader.getController();
        }
        return employeeController;
    }

    public String getBaseProduct() {
        return baseProduct;
    }

    public void setBaseProduct(String baseProduct) {
        this.baseProduct = baseProduct;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    private Singleton() {
    }

}