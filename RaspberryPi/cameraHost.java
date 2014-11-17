import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class host {
    static final GpioController gpio = GpioFactory.getInstance();

    static final GpioPinDigitalInput snapSwitch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27

    static String cam = "";
    static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String srgs[]) {

        String str = "";
        int count = 0;

        //create and register gpio pin listener
        snapSwitch.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                if(event.getState() == PinState.HIGH){
                    // take a photo
                    Date date = new Date();
                    cam = dateFormat.format(date).toString();
                    System.out.println("Snap action switch triggered, taking a photo");
                    System.out.println(cam);
                    takePhoto(cam);
                }
            }
        });

        //hard code to use port 8080
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            System.out.println("Waiting");


            while (true) {

                try {
                    Socket socket = serverSocket.accept();

                    try{
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        str = in.readLine();
                        System.out.println("String " + str + ".");


                    }   catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Exception with bufferedReader");
                    }

                    if(str.equals("cam")){
                        Date date = new Date();
                        cam = dateFormat.format(date).toString();
                        System.out.println("Camera button pressed, taking a photo");
                        System.out.println(cam);
                        takePhoto(cam);
                    }

                    count++;
                    System.out.println("Connection!");

                    HostThread thisHostThread = new HostThread(socket, count);
                    thisHostThread.start();

                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private static class HostThread extends Thread{

        private Socket hostThreadSocket;

        HostThread(Socket socket, int c){
            hostThreadSocket = socket;
        }
    }

    private static void execShellCommand(String pCommand){
        try{
            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(pCommand);
            pr.waitFor();
        } catch (IOException | InterruptedException ex){

        }
    }

    private static void takePhoto(String fileName){
        execShellCommand("raspistill -o "+ fileName +".jpg");
        //execShellCommand("sudo mv -f "+ fileName +".jpg /home/pi/git/FlipSwitch/RaspberryPi/Photos");
        execShellCommand("sudo mv -f "+ fileName +".jpg /var/lib/btsync");
    }
}
