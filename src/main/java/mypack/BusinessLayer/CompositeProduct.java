package mypack.BusinessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem{
    private ArrayList<BaseProduct> baseProducts;
    private int number;

    public CompositeProduct(String title, float rating, int numberOfCalories, int numberOfProteins, int numberOfFats, int numberOfSodium, double price, ArrayList<BaseProduct> baseProducts) {
        super(title, rating, numberOfCalories, numberOfProteins, numberOfFats, numberOfSodium, price);
        this.baseProducts = baseProducts;
        number=0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CompositeProduct() {
        super();
    }

    @Override
    public double computePrice() {
        double s=0.0;
        for(MenuItem p:baseProducts){
            s=s+p.computePrice();
        }
        return s;
    }

    public ArrayList<BaseProduct> getBaseProducts() {
        return baseProducts;
    }

    public void setBaseProducts(ArrayList<BaseProduct> baseProducts) {
        this.baseProducts = baseProducts;
    }
}
