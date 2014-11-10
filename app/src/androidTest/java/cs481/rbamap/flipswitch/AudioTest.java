package cs481.rbamap.flipswitch;

import junit.framework.TestCase;

import house.models.Audio;
import house.models.Light;
import house.models.Room;

/**
 * Created by Alana on 11/9/2014.
 */
public class AudioTest  extends TestCase {
    private Audio testAudio = new Audio(null, 0, null);
    private Room room = new Room("Room");

    public void setUp() throws Exception {
        testAudio.setName("Test Audio");
        testAudio.setStatus(1);
        testAudio.setLocation(room);
    }
    public void testGetAudioName(){
        String expected = "Test Audio";
        String actual = testAudio.getName();
        assertEquals("Testing GetAudioName", expected, actual);
    }

    public void testGetAudioStatus(){
        int expected =  1;
        int actual = testAudio.getStatus();
        assertEquals("Testing GetAudioStatus", expected, actual);
    }

    public void testGetAudioRoom(){
        Room expected = room;
        Room actual = testAudio.getLocation();
        assertEquals("Testing GetAudioLocation", expected, actual);
    }
}

