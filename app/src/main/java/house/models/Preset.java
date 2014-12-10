package house.models;

/**
 * Created by Ryan on 12/8/2014.
 */
public class Preset {
    public static final String PRESET = "Preset";
    public static final String BATHROOM = "Bathroom";
    public static final String LIVINGROOM = "LivingRoom";
    public static final String KITCHEN = "Kitchen";
    public static final String BEDROOM = "Bedroom";
    public static final String FRONTDOOR = "FrontDoor";
    public static final String GARAGEDOOR = "GarageDoor";

    private String bathroom;
    private String livingroom;
    private String kitchen;
    private String bedroom;
    private String frontDoor;
    private String garageDoor;

    public Preset(){

    }

    public Preset(String bathroom, String livingroom, String kitchen, String bedroom, String frontDoor, String garageDoor){
        this.bathroom = bathroom;
        this.livingroom = livingroom;
        this.kitchen = kitchen;
        this.bedroom = bedroom;
        this.frontDoor = frontDoor;
        this.garageDoor = garageDoor;
    }


    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getLivingroom() {
        return livingroom;
    }

    public void setLivingroom(String livingroom) {
        this.livingroom = livingroom;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getFrontDoor() {
        return frontDoor;
    }

    public void setFrontDoor(String frontDoor) {
        this.frontDoor = frontDoor;
    }

    public String getGarageDoor() {
        return garageDoor;
    }

    public void setGarageDoor(String garageDoor) {
        this.garageDoor = garageDoor;
    }
}
