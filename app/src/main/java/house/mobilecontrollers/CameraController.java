package house.mobilecontrollers;

import android.os.AsyncTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.util.Log;
import java.io.PrintWriter;

//Some code used from http://helloraspberrypi.blogspot.com/2013/12/java-exercise-client-and-server-example_20.html
//as a start

import house.models.Light;

/**
 * Created by Mandy on 9/22/2014.
 */

public class CameraController extends AsyncTask<Integer, Void, Void>{
    String dstAddress;
    int dstPort;
    String response;


    protected Void doInBackground(Integer... lightStatus) {
        int status = lightStatus[0];
        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.163.37", 8080);
            Log.v("", "Opened Socket");
            InputStream inputStream = socket.getInputStream();
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.write(status);
                out.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
