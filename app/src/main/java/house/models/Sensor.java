package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Sensor {
    private String name;
    private Room location;
    private int status;
    private int isTriggered;
    private String sourceFile;

    public Sensor(String name, int status, Room location, int isTriggered, String sourceFile){
        this.name  = name;
        this.status = status;
        this.location = location;
        this.isTriggered = isTriggered;
        this.sourceFile = sourceFile;
    }

    public String getName() {
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

    public int getIsTriggered(){
        return isTriggered;
    }

    public int setIsTriggered(int isTriggered){
        this.isTriggered = isTriggered;
        return 1;
    }

    public Room getLocation(){
        return location;
    }

    public int setLocation(Room location){
        this.location = location;
        return 1;
    }

    public String getSourceFile(){
        return sourceFile;
    }

    public int setSourceFile(String sourceFile){
        this.sourceFile = sourceFile;
        return 1;
    }

}
