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
package light.java;

public class host {
	
	static final String GPIO_OUT = "out";
    static final String GPIO_ON = "1";
    static final String GPIO_OFF = "0";
    static final String GPIO_CH00="17";
    static final String GPIO_SWI="19";
	
	

    public static void main(String srgs[]) {

        int count = 0;
        int ledStatus = 0;

        //hard code to use port 8080
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            
            System.out.println("Waiting");
            
            while (true) {
                
                try {
                    Socket socket = serverSocket.accept();
             
            /***  Init GPIO port for output ***/
            
            // Open file handles to GPIO port unexport and export controls
            FileWriter unexportFile = new FileWriter("/sys/class/gpio/unexport");
            FileWriter exportFile = new FileWriter("/sys/class/gpio/export");
            

            // Reset the port
            File exportFileCheck = new File("/sys/class/gpio/gpio"+GPIO_CH00);
            if (exportFileCheck.exists()) {
                unexportFile.write(GPIO_CH00);
                unexportFile.flush();
            }
            
            
            // Set the port for use
            exportFile.write(GPIO_CH00);  
            exportFile.flush();

            // Open file handle to port input/output control
            FileWriter directionFile =
                    new FileWriter("/sys/class/gp
            exportFile.write(GPIO_CH00);  
            exportFile.flush();

            // Open file handle to port input/output control
            FileWriter directionFile =
                    new FileWriter("/sys/class/gpio/gpio"+GPIO_CH00+"/direction");
       
                    
               try{
				   System.out.println("LED Status before buffered reader: " + ledStatus);
				   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				   ledStatus = in.read();
				   System.out.println("LED Status: " + ledStatus);
				  
				   
			   }   catch(IOException e){	
				   e.printStackTrace();
				   System.out.println("Exception with bufferedReader");
			   }  
            
            // Set port for output
            directionFile.write(GPIO_OUT);
            directionFile.flush();
            
            /*** Send commands to GPIO port ***/
            
            // Open file handle to issue commands to GPIO port
            FileWriter commandFile = new FileWriter("/sys/class/gpio/gpio"+GPIO_CH00+
                    "/value");
                   
			String cam = "cam";
             if(ledStatus == 0){   
				 // Set GPIO port ON
				 
				 
				 commandFile.write(GPIO_ON);
				 commandFile.flush();
				 
				execShellCommand("raspistill -o "+cam+".jpg");
				execShellCommand("sudo mv -f "+cam+".jpg /home/pi/Desktop");
			 }

			if(ledStatus == 1){
				 // Set GPIO port OFF
				commandFile.write(GPIO_OFF);
				commandFile.flush();
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
}
