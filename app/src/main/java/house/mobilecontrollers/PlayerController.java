package house.mobilecontrollers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import house.models.Audio;

/**
 * Created by Ryan on 11/16/2014.
 */
public class PlayerController extends AsyncTask<String, Void, Void> {

    protected Void doInBackground(String... str) {
        String audioCmd = str[0];
        try {
            Log.v("", "Opening Socket");
            Socket socket = new Socket("192.168.163.37", 8080);
            Log.v("", "Opened Socket");
            InputStream inputStream = socket.getInputStream();
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.write(audioCmd);
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