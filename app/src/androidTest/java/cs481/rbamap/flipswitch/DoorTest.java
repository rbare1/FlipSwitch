package cs481.rbamap.flipswitch;

import junit.framework.TestCase;

import house.models.Door;

/**
 * Created by Ryan on 11/10/2014.
 */
public class DoorTest extends TestCase {
    private Door testDoor = new Door(null, 0);

    public void setUp() throws Exception {
        testDoor.setName("Test Door");
        testDoor.setStatus(1);
    }
    public void testGetLightName(){
        String expected = "Test Door";
        String actual = testDoor.getName();
        assertEquals("Testing GetDoorName", expected, actual);
    }

    public void testGetLightStatus(){
        int expected =  1;
        int actual = testDoor.getStatus();
        assertEquals("Testing GetDoorStatus", expected, actual);
    }
}
