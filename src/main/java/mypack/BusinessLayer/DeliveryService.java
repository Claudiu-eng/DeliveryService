package mypack.BusinessLayer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing, Serializable {
    private HashMap<Order, ArrayList<MenuItem>> order;
    private HashMap<String, CompositeProduct> compositeProductHashMap;
    private HashMap<String, BaseProduct> baseProductHashMap;
    private ArrayList<User> users;

    public HashMap<String, CompositeProduct> getCompositeProductHashMap() {
        return compositeProductHashMap;
    }

    public void setCompositeProductHashMap(HashMap<String, CompositeProduct> compositeProductHashMap) {
        this.compositeProductHashMap = compositeProductHashMap;
    }

    public HashMap<String, BaseProduct> getBaseProductHashMap() {
        return baseProductHashMap;
    }

    public void setBaseProductHashMap(HashMap<String, BaseProduct> baseProductHashMap) {
        this.baseProductHashMap = baseProductHashMap;
    }

    public DeliveryService(HashMap<Order, ArrayList<MenuItem>> order, HashMap<String, CompositeProduct> compositeProductHashMap, HashMap<String, BaseProduct> baseProductHashMap, ArrayList<User> users) {
        this.order = order;
        this.compositeProductHashMap = compositeProductHashMap;
        this.baseProductHashMap = baseProductHashMap;
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public HashMap<Order, ArrayList<MenuItem>> getOrder() {
        return order;
    }

    public void setOrder(HashMap<Order, ArrayList<MenuItem>> order) {
        this.order = order;
    }

    @Override
    public void importProducts() throws IOException {

        Stream<String> lines = Files.lines(Paths.get("src\\products.csv"));
        List<List<String>> values = lines.skip(1).map((line) -> Arrays.asList(line.split(",")))
                .collect(Collectors.toList());
        values.forEach(value -> baseProductHashMap.put(value.get(0), new BaseProduct(value.get(0), Float.parseFloat(value.get(1)), Integer.parseInt(value.get(2)), Integer.parseInt(value.get(3)),
                Integer.parseInt(value.get(4)), Integer.parseInt(value.get(5)), Double.parseDouble(value.get(6)))));

    }

    @Override
    public void addProduct(BaseProduct menuItem) {
        this.baseProductHashMap.put(menuItem.getTitle(), menuItem);
    }

    @Override
    public void deleteProduct(String title) {
        baseProductHashMap.remove(title);
    }

    @Override
    public void modifyProduct(BaseProduct product) {
        assert product != null;
        BaseProduct p = (BaseProduct) baseProductHashMap.get(product.getTitle());
        p.setNumberOfCalories(product.getNumberOfCalories());
        p.setPrice(product.getPrice());
        p.setRating(product.getRating());
        p.setNumberOfSodium(product.getNumberOfSodium());
        p.setNumberOfFats(product.getNumberOfFats());
        p.setNumberOfProteins(product.getNumberOfProteins());
    }

    @Override
    public void createOrder(Order order1, ArrayList<MenuItem> products) {
        order.put(order1, products);
        for(User user:users){
            if(user.getId()==order1.getClientID()) {
                user.setNrComenzi(user.getNrComenzi() + 1);
                break;
            }
        }
        for(MenuItem menuItem:products)
            menuItem.setOrderedTimes(menuItem.getOrderedTimes()+1);
        String t = "Client = " + order1.getClientID() + " has ordered = " + order1.getOrderID() + '\n';
        setChanged();
        notifyObservers(t);
    }
}
