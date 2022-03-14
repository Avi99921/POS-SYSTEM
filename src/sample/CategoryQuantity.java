package sample;

public class CategoryQuantity {
    private int foodItems;
    private int cleaningItem;
    private int personalCare;
    private int beverages;
    private int other;

    public CategoryQuantity(){
        this.foodItems=0;
        this.cleaningItem=0;
        this.personalCare=0;
        this.beverages=0;
        this.other=0;
    }


    public CategoryQuantity(int foodItems, int cleaningItem, int personalCare, int beverages, int other) {
        this.foodItems += foodItems;
        this.cleaningItem += cleaningItem;
        this.personalCare += personalCare;
        this.beverages += beverages;
        this.other += other;
    }

    public int getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(int foodItems) {
        this.foodItems += foodItems;
    }

    public int getCleaningItem() {
        return cleaningItem;
    }

    public void setCleaningItem(int cleaningItem) {
        this.cleaningItem += cleaningItem;
    }

    public int getPersonalCare() {
        return personalCare;
    }

    public void setPersonalCare(int personalCare) {
        this.personalCare += personalCare;
    }

    public int getBeverages() {
        return beverages;
    }

    public void setBeverages(int beverages) {
        this.beverages += beverages;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other += other;
    }
}
