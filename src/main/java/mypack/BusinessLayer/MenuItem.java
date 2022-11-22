package mypack.BusinessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    private String title;
    private float rating;
    private int numberOfCalories;
    private int numberOfProteins;
    private int numberOfFats;
    private int numberOfSodium;
    private double price;
    private int orderedTimes;

    public MenuItem(String title, float rating, int numberOfCalories, int numberOfProteins, int numberOfFats, int numberOfSodium, double price) {
        this.title = title;
        this.rating = rating;
        this.numberOfCalories = numberOfCalories;
        this.numberOfProteins = numberOfProteins;
        this.numberOfFats = numberOfFats;
        this.numberOfSodium = numberOfSodium;
        this.price = price;
        orderedTimes=0;
    }

    public MenuItem() {
    }

    public int getOrderedTimes() {
        return orderedTimes;
    }

    public void setOrderedTimes(int orderedTimes) {
        this.orderedTimes = orderedTimes;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", numberOfCalories=" + numberOfCalories +
                ", numberOfProteins=" + numberOfProteins +
                ", numberOfFats=" + numberOfFats +
                ", numberOfSodium=" + numberOfSodium +
                ", price=" + price +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumberOfCalories() {
        return numberOfCalories;
    }

    public void setNumberOfCalories(int numberOfCalories) {
        this.numberOfCalories = numberOfCalories;
    }

    public int getNumberOfProteins() {
        return numberOfProteins;
    }

    public void setNumberOfProteins(int numberOfProteins) {
        this.numberOfProteins = numberOfProteins;
    }

    public int getNumberOfFats() {
        return numberOfFats;
    }

    public void setNumberOfFats(int numberOfFats) {
        this.numberOfFats = numberOfFats;
    }

    public int getNumberOfSodium() {
        return numberOfSodium;
    }

    public void setNumberOfSodium(int numberOfSodium) {
        this.numberOfSodium = numberOfSodium;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract double computePrice();
}
