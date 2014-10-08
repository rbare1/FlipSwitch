package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Light {
    private String name;
    private Room location;
    private int status;

    public Light(){

    }

    public Light(String name, int status, Room location){
        this.name  = name;
        this.status = status;
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public int setName(String name){
        this.name = name;
        return 1;
    }

    public int getStatus(){
        return status;
    }

    public int setStatus(int status){
        this.status = status;
        return 1;
    }

    public Room getLocation(){
        return location;
    }

    public int setLocation(Room location){
        this.location = location;
        return 1;
    }
}
