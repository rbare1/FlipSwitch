package house.mobilecontrollers;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Ryan on 12/2/2014.
 */
public class TempRequestController extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        String response;


protected Void doInBackground(Void... Void) {
    String temp = "";
    try {
        Log.v("", "Opening Socket");
        //Socket socket = new Socket("192.168.163.37", 8080);
        Socket socket = new Socket("192.168.171.1", 8080);
        Log.v("", "Opened Socket");

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.write("temprequest");
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            temp = in.readLine();
            Log.v("RESPONSE", temp);
        } catch (IOException e){
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
