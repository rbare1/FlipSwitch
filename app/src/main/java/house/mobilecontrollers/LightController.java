package house.mobilecontrollers;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

//Some code used from http://helloraspberrypi.blogspot.com/2013/12/java-exercise-client-and-server-example_20.html
//as a start

import house.models.JSON.JSON;
import house.models.Light;
import house.models.Room;

/**
 * Created by Mandy on 9/22/2014.
 */

public class LightController extends AsyncTask<Light, Void, Void>{
    protected Void doInBackground(Light... light) {

        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.163.37", 8080);
            //Socket socket = new Socket("192.168.171.1", 8080);
            Log.v("", "Opened Socket");

            Object obj = light[0];
            Log.v("", obj.getClass().getName());
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream());

            outputStream.println("Light");
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
