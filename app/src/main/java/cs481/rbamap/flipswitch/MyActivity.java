package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import house.mobilecontrollers.LightController;
//import house.mobilecontrollers.SensorController;
import house.models.Light;
import house.models.Room;
import house.models.Sensor;


public class MyActivity extends Activity {

    TextView tempText;
    int temp = 70; //should probably be saved in a file and then pulled for persistence between runs
    TextView doorsLocked;
    int doors = 2;

    Light led = new Light("LED", 0, null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();
    }

    //Sensor trigger = new Sensor("trigger", 0, null, 0, null);

    public void triggerLight(Room room) {
        LightController controller = new LightController();
        Light light = new Light();
        light.setLocation(room);
        controller.execute(light);
        if(led.getStatus() == 1){
            led.setStatus(0);
        } else if(led.getStatus() == 0){
            led.setStatus(1);
        }
    }

    public void setDefaultView(){
        setContentView(R.layout.activity_my);

       ImageButton bathroomLight = (ImageButton) findViewById(R.id.button_BathroomLight);
       bathroomLight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Room bathroom = new Room();
                bathroom.setName("bathroom");
                triggerLight(bathroom);
             }
         }
        );

        ImageButton bedroomLight = (ImageButton) findViewById(R.id.button_BedroomLight);
        bedroomLight.setOnClickListener(new Button.OnClickListener() {
             public void onClick(View v) {
                 Room bedroom = new Room();
                 bedroom.setName("bedroom");
                 triggerLight(bedroom);
             }
         }
        );

        ImageButton kitchenLight = (ImageButton) findViewById(R.id.button_KitchenLight);
        kitchenLight.setOnClickListener(new Button.OnClickListener() {
                 public void onClick(View v) {
                     Room kitchen = new Room();
                     kitchen.setName("kitchen");
                     triggerLight(kitchen);
                 }
             }
        );

        ImageButton livingRoomLight = (ImageButton) findViewById(R.id.button_LivingRoomLight);
        livingRoomLight.setOnClickListener(new Button.OnClickListener() {
                 public void onClick(View v) {
                     Room livingRoom = new Room();
                     livingRoom.setName("living room");
                     triggerLight(livingRoom);
                 }
             }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
