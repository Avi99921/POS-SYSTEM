package sample;

public class Profit {
    private float profit;
    public Profit(){
        this.profit=0;
    }
    public Profit(float profit){
        this.profit+=profit;
    }
    public void setProfit(float profit){
        this.profit+=profit;
    }
    public float getProfit(){
        return profit;
    }
}
