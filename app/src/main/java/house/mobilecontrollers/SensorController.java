package house.mobilecontrollers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import house.models.Audio;
import house.models.JSON.JSON;
import house.models.Sensor;

/**
 * Created by Mandy on 9/22/2014.
 */
public class SensorController extends AsyncTask<Sensor, Void, Void> {

    protected Void doInBackground(Sensor... sensor) {

        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.171.26", 8079);
            Log.v("", "Opened Socket");

            Object obj = sensor[0];
            PrintWriter outputStream = new PrintWriter(socket.getOutputStream());

            outputStream.println("Sensor");
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
