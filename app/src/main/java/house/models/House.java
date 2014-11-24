package house.models;

import java.io.Serializable;

/**
 * Created by Ryan on 11/23/2014.
 */
public class House implements Serializable {
    private Door frontDoor;
    private Door backDoor;
    private Door garageDoor;

    private Light bathroomLight;
    private Light livingroomLight;
    private Light kitchenLight;
    private Light bedroomLight;

    private Audio audio;

    private Camera frontCamera;


    public Door getFrontDoor() {
        return frontDoor;
    }

    public void setFrontDoor(Door frontDoor) {
        this.frontDoor = frontDoor;
    }

    public Door getBackDoor() {
        return backDoor;
    }

    public void setBackDoor(Door backDoor) {
        this.backDoor = backDoor;
    }
    public Door getGarageDoor() {
        return garageDoor;
    }

    public void setGarageDoor(Door garageDoor) {
        this.garageDoor = garageDoor;
    }
}
