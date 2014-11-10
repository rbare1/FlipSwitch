package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Sensor {
    private String name;
    private Room location;
    private int status;
    private String info;

    public Sensor(){

    }

    public Sensor(String name, int status, String information,  Room location){
        this.name  = name;
        this.status = status;
        this.location = location;
        this.info = information;
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

    public String getInfo(){
        return info;
    }

    public int setInfo(String information){
        this.info = information;
        return 1;
    }


}
