package cs481.rbamap.flipswitch;

import junit.framework.TestCase;

import house.models.Camera;
import house.models.Room;

/**
 * Created by Ryan on 11/10/2014.
 */
public class CameraTest extends TestCase {
    private Camera testCamera = new Camera(null, null);
    private Room room = new Room("Room");

    public void setUp() throws Exception {
        testCamera.setName("Test Camera");
        testCamera.setLocation(room);
    }
    public void testGetLightName(){
        String expected = "Test Camera";
        String actual = testCamera.getName();
        assertEquals("Testing GetCameraName", expected, actual);
    }

    public void testGetLightRoom(){
        Room expected = room;
        Room actual = testCamera.getLocation();
        assertEquals("Testing GetCameraLocation", expected, actual);
    }
}
