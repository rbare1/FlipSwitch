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
	
	static final String GPIO_OUT = "out";
    static final String GPIO_ON = "1";
    static final String GPIO_OFF = "0";
    static final String GPIO_LIVINGROOM = "17";
    static final String GPIO_SWI="19";
    static final GpioController gpio = GpioFactory.getInstance();
				
	static final GpioPinDigitalOutput livingRoomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "LivingRoomLED", PinState.HIGH);				
	static final GpioPinDigitalOutput kitchenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "kitchenLED", PinState.HIGH);
	static final GpioPinDigitalOutput bedroomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "bedroomLED", PinState.HIGH);
	static final GpioPinDigitalOutput bathroomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "bathroomLED", PinState.HIGH);
	
	static final GpioPinDigitalInput snapSwitch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27	
	
	static String cam = "";
	static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String srgs[]) {

		String location = ""; 
		int count = 0;
		Light light;
		livingRoomPin.low();
        kitchenPin.low();
        bedroomPin.low();
        bathroomPin.low();
        
 
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
        
        //hard code to use port 8080
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            
            System.out.println("Waiting");
            
                       
            while (true) {
                
                try {
                    Socket socket = serverSocket.accept();
                    
               try{
				   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				   location = in.readLine();
				   System.out.println("Room Name " + location + ".");
				  
				   
			   }   catch(IOException e){	
				   e.printStackTrace();
				   System.out.println("Exception with bufferedReader");
			   }  
            
				TriggerLight(location);				 				 						
                       
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
	
	 private static void TriggerLight(String location){  
	
		if(location.equals("kitchen")){
			System.out.println("Turning On Kitchen");
			kitchenPin.toggle();
		}else if(location.equals("bathroom")){
			System.out.println("Turning On Bathroom");
			bathroomPin.toggle();
		}else if(location.equals("bedroom")){
			System.out.println("Turning On Bedroom");
			bedroomPin.toggle();
		}else if(location.equals("living room")){
			System.out.println("Turning On Living Room");
			livingRoomPin.toggle();
		}
	}
}
