package sample;

public class UserName {
    private String uName;
    public UserName(){
        this.uName = "";
    }
    public UserName(String uName){
        this.uName=uName;
    }
    public void setUser(String uName){
        this.uName=uName;
    }
    public String getUser(){
        return uName;
    }
}