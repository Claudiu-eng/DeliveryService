package mypack.BusinessLayer;

public class BaseProduct extends MenuItem{

    public BaseProduct(String title, float rating, int numberOfCalories, int numberOfProteins, int numberOfFats, int numberOfSodium, double price) {
        super(title, rating, numberOfCalories, numberOfProteins, numberOfFats, numberOfSodium, price);
    }

    public BaseProduct() {
    }

    @Override
    public double computePrice() {
        return super.getPrice();
    }
}
