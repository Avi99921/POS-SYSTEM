package sample;

public class Stock {
    private String itemName;
    private int quantity;
    public Stock(){
        this.itemName="";
        this.quantity=0;
    }
    public Stock(String itemName,int quantity){
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public  void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public String getItemName(){
        return itemName;
    }
    public int getQuantity() {
        return quantity;
    }
}
