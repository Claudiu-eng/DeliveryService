package mypack.BusinessLayer;

import java.io.IOException;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {

    public void importProducts() throws IOException;
    public void addProduct(BaseProduct menuItem);
    public void deleteProduct(String title);
    public void modifyProduct(BaseProduct product);
    public void createOrder(Order order,ArrayList<MenuItem> products);

}
