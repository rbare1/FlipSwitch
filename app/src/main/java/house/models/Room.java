package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Room {
    private String name;

    public Room(){

    }

    public Room(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int setName(String name) {
        this.name = name;
        return 1;
    }
}
