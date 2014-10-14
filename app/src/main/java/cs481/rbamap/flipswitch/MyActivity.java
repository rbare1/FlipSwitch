package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import house.mobilecontrollers.LightController;
import house.mobilecontrollers.SensorController;
import house.models.Light;
import house.models.Sensor;


public class MyActivity extends Activity {

    Light led = new Light("LED", 0, null);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();
    }

    Sensor trigger = new Sensor("trigger", 0, null, 0, null);

    public void setLightStatus() {
        LightController controller = new LightController();
        Log.v("", "in status");
        controller.execute(led.getStatus());
        if(led.getStatus() == 1){
            led.setStatus(0);
        } else if(led.getStatus() == 0){
            led.setStatus(1);
        }
    }

    public void setTriggerStatus() {
        SensorController controller = new SensorController();
        Log.v("", "in status");
        controller.execute(trigger.getStatus());
        if(trigger.getStatus() == 1){
            trigger.setStatus(0);
        } else if(trigger.getStatus() == 0){
            trigger.setStatus(1);
        }
    }

    public void setDefaultView(){
        setContentView(R.layout.activity_my);

        setupLEDButton();
        setupLRButton();
    }

    private void setupLEDButton(){
        Button ledTestButton = (Button) findViewById(R.id.ledTestButton);
        ledTestButton.setText("Switch LED On"); //text starts out at Off so this changes it to the proper text
        ledTestButton.setOnClickListener(new Button.OnClickListener() {
                                             boolean ledStatus = false;
                                             Button ledTestButton = (Button) findViewById(R.id.ledTestButton);
                                             public void onClick(View v) {
                                                 if(ledStatus == false) {
                                                     Log.v("", "LED is switched on now");
                                                     ledTestButton.setText("Switch LED Off");
                                                     ledStatus = true;
                                                     setLightStatus();
                                                 }
                                                 else{
                                                     Log.v("", "LED is switched off now");
                                                     ledTestButton.setText("Switch LED On");
                                                     ledStatus = false;
                                                     setLightStatus();
                                                 }
                                             }
                                         }
        );
    }

    private void setupLRButton(){
        Button lr_Button = (Button) findViewById(R.id.lr_button);
        lr_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(MyActivity.this, LivingRoom.class));
            }
        });
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
