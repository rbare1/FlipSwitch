package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Camera {

    private String name;
    private Room location;


    public Camera(){
    }


    public Camera(String name, Room location) {
        this.name = name;
        this.location = location;

    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getLocation(){
        return location;
    }

    public void setLocation(Room location){
        this.location = location;
    }



}
