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

public class roomEvent {
	
	static final GpioController gpio = GpioFactory.getInstance();
				
	static final GpioPinDigitalOutput fan = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "fan", PinState.HIGH);	//physical GPIO 6	
	static final GpioPinDigitalOutput heat = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "heatingLED", PinState.HIGH);	//physical GPIO 25			
	static final GpioPinDigitalOutput cool = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "collingLED", PinState.HIGH); //physical GPIO 16
	static final GpioPinDigitalInput snapSwitch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27

	static String cam = "";
	static float desiredTemp = 75.00f;
	static float cs =0.00f;
	static float fs = 0.00f;
	static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	 public static void main(String srgs[]) { 
		 
        heat.low();
        cool .low();
		fan.low();

         //hard code to use port 8079
         try (ServerSocket serverSocket = new ServerSocket(8079)) {

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
        try{
            Thread.sleep(4000);
        }catch(InterruptedException iEx){
                //
        }
        try{
            String s;
            int counter = 0;
            FileReader reader = new FileReader("/sys/bus/w1/devices/28-000006085239/w1_slave");
            BufferedReader br = new BufferedReader(reader);



            try{
                while((s = br.readLine()) != null){
                    //System.out.println(s);
                    counter++;
                    if(counter == 2){
                        calculateTemp(s);
                    }
                 }
            } catch (IOException ex2){
                //
            }

        } catch (FileNotFoundException ex){
            System.out.println("File Does not exist");
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
		}else if(fs < desiredTemp){
			heat.high();
			cool.low();
			fan.high();
		}else{
			cool.low();
            heat.low();
            fan.low();
         }
	}

}
