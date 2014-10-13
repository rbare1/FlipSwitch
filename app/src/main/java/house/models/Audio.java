package house.models;

/**
 * Created by Mandy on 9/22/2014.
 */
public class Audio {
    private String name, audioFile;
    private int status;
    private Room location;

    public Audio(){

    }

    public Audio(String name, String audioFile, int status, Room location){
        this.name  = name;
        this.audioFile = audioFile;
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

    public String getAudioFile(){
        return audioFile;
    }

    public int setAudioFile(String audioFile){
        this.audioFile = audioFile;
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

    public int setLocation(Room location) {
        this.location = location;
        return 1;
    }
}
