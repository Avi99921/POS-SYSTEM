package sample;

public class Total {
    private float total;
    public Total(){
        this.total=0;
    }
    public Total(float total){
        this.total+=total;
    }
    public void setTotal(float total){
        this.total+=total;
    }

    public float getTotal(){
        return this.total;
    }
}
