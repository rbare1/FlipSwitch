package cs481.rbamap.flipswitch;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import house.models.Light;
import house.models.Room;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Ryan on 10/19/2014.
 */
public class LightTest extends TestCase {
    private Light testLight = new Light(null, 0, null);
    private Room room = new Room("Room");

        public void setUp() throws Exception {
            testLight.setName("Test Light");
            testLight.setStatus(1);
            testLight.setLocation(room);
        }
    public void testGetLightName(){
        String expected = "Test Light";
        String actual = testLight.getName();
        assertEquals("Testing GetLightName", expected, actual);
    }

    public void testGetLightStatus(){
        int expected =  1;
        int actual = testLight.getStatus();
        assertEquals("Testing GetLightStatus", expected, actual);
    }

    public void testGetLightRoom(){
        Room expected = room;
        Room actual = testLight.getLocation();
        assertEquals("Testing GetLightLocation", expected, actual);
    }
}
