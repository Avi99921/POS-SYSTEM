package sample;

public class Table {
    private String name;
    private int quantity;
    private float unitPrice;
    private String amount;
    private float discount;
    private String itemNumber;
    private String category;

    public Table(){
        this.name="";
        this.quantity=0;
        this.unitPrice=0;
        this.amount ="";
        this.discount =0;
        this.itemNumber ="";
        this.category = "";
    }
    public Table(String itemNumber,String name,String category,float unitPrice,int quantity,String amount,float discount){
        this.itemNumber=itemNumber;
        this.name=name;
        this.quantity=quantity;
        this.unitPrice=unitPrice;
        this.amount =amount;
        this.discount=discount;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUnitPrice(int unitPrice){
        this.unitPrice=unitPrice;
    }
    public float getUnitPrice(){
        return unitPrice;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }
    public String getAmount(){
        return amount;
    }
    public void setDiscount(float discount){
        this.discount=discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public float getDiscount() {
        return discount;
    }
}
