package house.mobilecontrollers;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

//Some code used from http://helloraspberrypi.blogspot.com/2013/12/java-exercise-client-and-server-example_20.html
//as a start

import house.models.Light;
import house.models.Room;

/**
 * Created by Mandy on 9/22/2014.
 */

public class LightController extends AsyncTask<Light, Void, Void>{
    String dstAddress;
    int dstPort;
    String response;


    protected Void doInBackground(Light... light) {
        Room room = light[0].getLocation();
        String roomName = room.getName();
        JSONObject json = new JSONObject();
        try {
            json.put("Object", "light");
            json.put("Room Name", light[0].getLocation().getName());
            Log.v("json test1", json.get("Room Name").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.163.37", 8080);
            //Socket socket = new Socket("192.168.171.1", 8080);
            Log.v("", "Opened Socket");

            InputStream inputStream = socket.getInputStream();
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream());
            outputStream.println(json.toString());
            outputStream.flush();
            Log.v("json test2",json.toString());

            Log.v("", "Closing Socket");
            socket.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
