package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Camera {
    private String name;
    private Room location;
    private int status;
    private String destinationFile;

    public Camera(){

    }

    public Camera(String name, Room location, int Status, String destinationFile){
        this.name = name;
        this.location = location;
        this.status = status;
        this.destinationFile = destinationFile;
    }

    public String getName(){
        return name;
    }

    public int setName(String name){
        this.name = name;
        return 1;
    }

    public Room getLocation(){
        return location;
    }

    public int setLocation(Room location){
        this.location = location;
        return 1;
    }

    public int getStatus(){
        return status;
    }

    public int setStatus(int status) {
        this.status = status;
        return 1;
    }

    public String getDestinationFile(){
        return destinationFile;
    }

    public int setDestinationFile(String destinationFile){
        this.destinationFile = destinationFile;
        return 1;
    }
}
