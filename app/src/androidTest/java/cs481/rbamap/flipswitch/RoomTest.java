package cs481.rbamap.flipswitch;

/**
 * Created by Alana on 11/9/2014.
 */

import junit.framework.TestCase;

import house.models.Light;
import house.models.Room;

public class RoomTest extends TestCase {
    private Room testRoom = new Room();

    public void setUp() throws Exception {
        testRoom.setName("Test Room");
    }
    public void testGetRoomName(){
        String expected = "Test Room";
        String actual = testRoom.getName();
        assertEquals("Testing GetRoomName", expected, actual);
    }
}
