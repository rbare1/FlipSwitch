package house.models;

/**
 * Created by Ryan on 11/10/2014.
 */
public class Door {
    private String name;
    private int status;

    public Door(){

    }

    public Door(String name, int status){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
