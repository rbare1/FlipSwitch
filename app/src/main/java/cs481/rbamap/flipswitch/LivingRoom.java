package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import house.mobilecontrollers.AudioController;
import house.mobilecontrollers.RoomController;
import house.mobilecontrollers.SensorController;
import house.models.Sensor;


public class LivingRoom extends Activity {

    int temp = 70; //should probably be saved in a file and then pulled for persistence between runs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();
    }

    public void setDefaultView(){
        setContentView(R.layout.activity_living_room);
        TextView tempText = (TextView) findViewById(R.id.lr_tempText);
        tempText.setText(String.valueOf(temp));
        setupTempDownButton();
        setupTempUpButton();
    }

    private void setupTempUpButton(){
        Button lr_Button = (Button) findViewById(R.id.lr_tempUpButton);
        lr_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                temp++;
                TextView tempText = (TextView) findViewById(R.id.lr_tempText);
                tempText.setText(String.valueOf(temp));
                SensorController controller = new SensorController();
                Sensor sensor = new Sensor();
                sensor.setInfo(Integer.toString(temp));
                controller.execute(sensor);
            }
        });
    }
    private void setupTempDownButton(){
        Button lr_Button = (Button) findViewById(R.id.lr_tempDownButton);
        lr_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                temp--;
                TextView tempText = (TextView) findViewById(R.id.lr_tempText);
                tempText.setText(String.valueOf(temp));
                SensorController controller = new SensorController();
                Sensor sensor = new Sensor();
                sensor.setInfo(Integer.toString(temp));
                controller.execute(sensor);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.living_room, menu);
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