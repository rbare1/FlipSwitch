import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.*;
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
import com.fasterxml.jackson.*;

//import sun.management.Sensor;

public class roomEvent {
    static final GpioController gpio = GpioFactory.getInstance();
    static final GpioPinDigitalOutput fan = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "fan", PinState.HIGH); //physical GPIO 6
    static final GpioPinDigitalOutput heat = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "heatingLED", PinState.HIGH); //physical GPIO 25
    static final GpioPinDigitalOutput cool = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "coolingLED", PinState.HIGH); //physical GPIO 16
    static final GpioPinDigitalOutput fan2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "fan2", PinState.HIGH); //physical GPIO 12
    static final GpioPinDigitalOutput heat2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "heatingLED2", PinState.HIGH); //physical GPIO 18
    static final GpioPinDigitalOutput cool2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "coolingLED2", PinState.HIGH); //physical GPIO 23
    static final GpioPinDigitalInput snapSwitch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27
    
    static String cam = "";
    static float desiredTemp = 75.00f;
    static float cs =0.00f;
    static float fs = 0.00f;
    static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String srgs[]) {
        String str = "";
        int count = 0;
        String tempStr = "";
        heat.low();
        cool.low();
        fan.low();
        heat2.low();
        cool2.low();
        fan2.low();
       

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
                    execShellCommand("raspistill -o "+cam+".jpg");
                    execShellCommand("sudo mv -f "+cam+".jpg /home/pi/git/FlipSwitch/RaspberryPi/Photos");
                }
            }
        });

        try (ServerSocket serverSocket = new ServerSocket(8079)) {

            System.out.println("Waiting");


            while (true) {

                try {
                    String s;
                    int counter = 0;
                    FileReader reader = new FileReader("/sys/bus/w1/devices/28-000006085239/w1_slave");
                    BufferedReader br = new BufferedReader(reader);                                    
                     
                    try {
                        while ((s = br.readLine()) != null) {
                            //System.out.println(s);
                            counter++;
                            if (counter == 2) {
                                calculateTemp(s);
                            }
                        }
                    } catch (IOException ex2) {
                        //
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("File Does not exist");
                }


                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Socket Accepted");
                    
                    try{
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String className = in.readLine();
                        System.out.println(className.toString());
                        Class<?> cls = String.class;
                        try{
                            cls = Class.forName(className);
                        }catch(ClassNotFoundException e2){

                        }
                        String data = in.readLine();
                        Object obj = JSON.getObjectMapper().readValue(new StringReader(data), cls);
                        if(obj instanceof Sensor) {
                            Sensor sensor = (Sensor) obj;
                            desiredTemp = Float.parseFloat(sensor.getInfo());
                            System.out.println(desiredTemp);
                        } else if (obj instanceof Camera){
							Date date = new Date();
                            cam = dateFormat.format(date).toString();
                            takePhoto(cam);
                        }

                    }   catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Exception with bufferedReader");
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

    private static void calculateTemp(String s){
        cs = Float.parseFloat(s.substring(s.lastIndexOf("=") + 1)) / 1000;
        fs = Math.round(cs * 1.800f + 32.00f);
        System.out.println(fs);
        if(fs > desiredTemp){
            cool.high();
            heat.low();
            fan.high();
            cool2.high();
            heat2.low();
            fan2.high();
        }else if(fs < desiredTemp){
            heat.high();
            cool.low();
            fan.high();
            heat2.high();
            cool2.low();
            fan2.high();
        }else{
            cool.low();
            heat.low();
            fan.low();
            cool2.low();
            heat2.low();
            fan2.low();
        }
    }

    private static void takePhoto(String fileName){
        execShellCommand("raspistill -vf -o "+ fileName +".jpg");
        execShellCommand("sudo mv -f "+ fileName +".jpg /home/pi/git/FlipSwitch/RaspberryPi/Photos");
    }
}
