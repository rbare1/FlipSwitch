package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Camera {

    private String name;
    private Room location;
    private int status;
    private String sourceFile;


    public Camera(){
    }


    public Camera(String name, Room location, int status, String sourceFile) {
        this.name = name;
        this.location = location;
        this.status = status;
        this.sourceFile = sourceFile;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom(){
        return location;
    }

    public void setRoom(Room location){
        this.location = location;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getSourceFile(){
        return sourceFile;
    }

    public void setSourceFile(String sourceFile){
        this.sourceFile = sourceFile;
    }

}
