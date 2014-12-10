package house.mobilecontrollers;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;
import java.io.PrintWriter;

//Some code used from http://helloraspberrypi.blogspot.com/2013/12/java-exercise-client-and-server-example_20.html
//as a start

import house.models.Camera;
import house.models.JSON.JSON;
import house.models.Light;
import house.models.Room;

/**
 * Created by Mandy on 9/22/2014.
 */

public class CameraController extends AsyncTask<Camera, Void, Void>{
    String dstAddress;
    int dstPort;
    String response;


    protected Void doInBackground(Camera... Void) {
        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.171.26", 8079);
            Log.v("", "Opened Socket");

            Object obj = new Camera();
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream());

            outputStream.println("Camera");
            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, obj);
            outputStream.println(sw.toString());
            outputStream.flush();

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
