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
				
	static final GpioPinDigitalOutput heat = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "heatingLED", PinState.HIGH);	//physical GPIO 25			
	static final GpioPinDigitalOutput cool = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "collingLED", PinState.HIGH); //physical GPIO 16
	static final GpioPinDigitalInput snapSwitch = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); //physical GPIO 27

	static String cam = "";
	static float desiredTemp = 74.00f;
	static float cs =0.00f;
	static float fs = 0.00f;
	static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	 public static void main(String srgs[]) { 
		 
		heat.low();
        cool .low();
		 
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
		
		while (true) {
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
		fs = cs * 1.800f + 32.00f;
		System.out.println(fs);
		if(fs > desiredTemp)
			cool.high();
		else if(fs < desiredTemp)
			heat.high();
		else
			cool.low();
            heat.low();
	}

}
