package house.models;

import java.io.Serializable;

/**
 * Created by Ryan on 11/23/2014.
 */
public class House implements Serializable {
    public static final String PRESET = "Preset";
    public static final String BATHROOM = "Bathroom";
    public static final String LIVINGROOM = "LivingRoom";
    public static final String KITCHEN = "Kitchen";
    public static final String BEDROOM = "Bedroom";
    public static final String FRONTDOOR = "FrontDoor";
    public static final String GARAGEDOOR = "GarageDoor";

    private Door frontDoor;
    private Door garageDoor;

    private Light bathroomLight;
    private Light livingroomLight;
    private Light kitchenLight;
    private Light bedroomLight;

    private Audio audio;

    private Camera frontCamera;

    public House(Door frontDoor, Door garageDoor, Light bathroomLight, Light livingroomLight, Light kitchenLight, Light bedroomLight, Audio audio, Camera frontCamera) {
        this.frontDoor = frontDoor;
        this.garageDoor = garageDoor;
        this.bathroomLight = bathroomLight;
        this.livingroomLight = livingroomLight;
        this.kitchenLight = kitchenLight;
        this.bedroomLight = bedroomLight;
        this.audio = audio;
        this.frontCamera = frontCamera;
    }


    public Door getFrontDoor() {
        return frontDoor;
    }

    public void setFrontDoor(Door frontDoor) {
        this.frontDoor = frontDoor;
    }

    public Door getGarageDoor() {
        return garageDoor;
    }

    public void setGarageDoor(Door garageDoor) {
        this.garageDoor = garageDoor;
    }

    public Light getBathroomLight() {
        return bathroomLight;
    }

    public void setBathroomLight(Light bathroomLight) {
        this.bathroomLight = bathroomLight;
    }

    public Light getLivingroomLight() {
        return livingroomLight;
    }

    public void setLivingroomLight(Light livingroomLight) {
        this.livingroomLight = livingroomLight;
    }

    public Light getKitchenLight() {
        return kitchenLight;
    }

    public void setKitchenLight(Light kitchenLight) {
        this.kitchenLight = kitchenLight;
    }

    public Light getBedroomLight() {
        return bedroomLight;
    }

    public void setBedroomLight(Light bedroomLight) {
        this.bedroomLight = bedroomLight;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Camera getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(Camera frontCamera) {
        this.frontCamera = frontCamera;
    }
}
