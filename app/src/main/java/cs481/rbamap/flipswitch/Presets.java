package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Presets extends Activity {
    CheckBox bathroomCB;
    CheckBox livingroomCB;
    CheckBox kitchenCB;
    CheckBox bedroomCB;
    CheckBox frontdoorCB;
    CheckBox garageCB;
    Spinner presetSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultView();
    }

    private void executeSaveSettings() throws IOException {
        Log.v("Spinner", presetSpinner.getSelectedItem().toString());
        FileOutputStream fos = openFileOutput(presetSpinner.getSelectedItem().toString(), Context.MODE_PRIVATE);
        if(bathroomCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }

        if(livingroomCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }

        if(kitchenCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }

        if(bedroomCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }

        if(frontdoorCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }
        if(garageCB.isChecked()){
            fos.write("1,".getBytes());
        }
        else{
            fos.write("0,".getBytes());
        }
        fos.flush();
        fos.close();
    }

    public void setDefaultView(){
        setContentView(R.layout.activity_presets);
        setupCheckBoxes();
        setupSaveSettingsButton();
        setupPresetSpinner();
    }

    private void setupCheckBoxes(){
       bathroomCB = (CheckBox) findViewById(R.id.bathroomLightCheckBox);
       livingroomCB = (CheckBox) findViewById((R.id.livingroomLightCheckBox));
       kitchenCB = (CheckBox) findViewById(R.id.kitchenLightCheckBox);
       bedroomCB = (CheckBox) findViewById(R.id.bedroomLightCheckBox);
       frontdoorCB = (CheckBox) findViewById(R.id.frontDoorCheckBox);
       garageCB = (CheckBox) findViewById(R.id.garageDoorCheckBox);
    }

    private void setupPresetSpinner(){
        presetSpinner = (Spinner) findViewById(R.id.presetSpinner);
    }

    private void setupSaveSettingsButton() {
        Button saveSettings = (Button) findViewById(R.id.savePresetButton);
        saveSettings.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    executeSaveSettings();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.presets, menu);
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
