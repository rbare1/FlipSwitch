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
import java.io.BufferedOutputStream;
import org.json.JSONObject;
import org.json.JSONException;
import com.fasterxml.jackson.*;
import java.io.*;

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
				
	static final GpioPinDigitalOutput livingRoomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "LivingRoomLED", PinState.HIGH);	//physical GPIO 13			
	static final GpioPinDigitalOutput kitchenPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "kitchenLED", PinState.HIGH); //physical GPIO 22
	static final GpioPinDigitalOutput bedroomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21, "bedroomLED", PinState.HIGH); //physical GPIO 5
	static final GpioPinDigitalOutput bathroomPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, "bathroomLED", PinState.HIGH); //physical GPIO 12
    static final GpioPinDigitalOutput garageMotor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "garageMotor", PinState.LOW); //physical GPIO 17

    static final GpioPinDigitalInput garageSensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27

	static Process audio_pr;

	static int audio = 0;
    public static void main(String srgs[]) {

		String str = "";
		String output;
		int count = 0;
		//Light light;
		livingRoomPin.low();
        kitchenPin.low();
        bedroomPin.low();
        bathroomPin.low();

        //create and register gpio pin listener
        garageSensor.addListener(new GpioPinListenerDigital(){
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
                if(event.getState() == PinState.HIGH){
                    System.out.println("garage door stop");
                    garageOpen(0);
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
                   String className = in.readLine();
                   System.out.println(className.toString());
                   Class<?> cls = String.class;
                   try{
					cls = Class.forName(className);
                   }catch(ClassNotFoundException e2){
						
					}
                   String data = in.readLine();
                   Object obj = JSON.getObjectMapper().readValue(new StringReader(data), cls);
					if(obj instanceof Light){
						System.out.println("Instance working");
						Light light = (Light) obj;
						str = light.getLocation().getName();
					}
					//}
			   }catch(IOException e2){
				   
			   }
			   System.out.println(str);
				  
				   
			  
                if(str.contains(".mp3")) {
                    TriggerAudio(str);
                }

                else if(str.equals("garageUp")){
                    garageOpen(1);
                }
                else if(str.equals("audioP")){
					PauseAudio();
				}
				else if(str.equals("audioS")){
					StopAudio();
				}
                else {
                    TriggerLight(str);
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
    
    private static void execShellCommandAudio(String pCommand){
		try{
			if(audio == 0){
				Runtime run = Runtime.getRuntime();
				audio_pr = run.exec("mpg321 -R music");
			}
			OutputStream audioStream = audio_pr.getOutputStream();
			audioStream.write(pCommand.getBytes());
			audioStream.flush();
//audio_pr = run.exec(pCommand);
		} catch (IOException ex){
			//
        }		

	}

    private static void TriggerAudio(String str){
		/*System.out.println("omxplayer -o local /home/pi/git/FlipSwitch/RaspberryPi/Music/" + str);
		execShellCommandAudio("^C");
        execShellCommandAudio("omxplayer -o local /home/pi/git/FlipSwitch/RaspberryPi/Music/" + str);*/
        if(audio == 1){
			StopAudio();
		}
		System.out.println("mpg321 -R music \nLOAD /home/pi/git/FlipSwitch/RaspberryPi/Music/" + str);
		execShellCommandAudio("LOAD /home/pi/git/FlipSwitch/RaspberryPi/Music/" + str);
		audio = 1;	
    }
    
    private static void PauseAudio(){
		System.out.println("Pausing Audio");
		execShellCommandAudio("P");
	}
	
	private static void StopAudio(){
		System.out.println("Stopping Audio");
		execShellCommandAudio("Q");
		if(audio == 1){
			audio_pr.destroy();
		}
		audio = 0;
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

    private static void garageOpen(int garageFlag){
        System.out.println("Garage open method");
        if(garageFlag == 0){
            garageMotor.low();
        }
        else{
            garageMotor.high();
        }
    }
}
