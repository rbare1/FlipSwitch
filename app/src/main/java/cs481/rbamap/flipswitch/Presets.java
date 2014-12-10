package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import house.models.Preset;


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
        Preset preset = new Preset("0", "0", "0", "0", "0", "0");
        if(bathroomCB.isChecked()){
            preset.setBathroom("1");
        }

        if(livingroomCB.isChecked()){
            preset.setLivingroom("1");
        }

        if(kitchenCB.isChecked()){
            preset.setKitchen("1");
        }

        if(bedroomCB.isChecked()){
            preset.setBedroom("1");
        }

        if(frontdoorCB.isChecked()){
            preset.setFrontDoor("1");
        }
        if(garageCB.isChecked()){
            preset.setGarageDoor("1");
        }
        try {
            writePresets(preset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // fos.flush();
       // fos.close();
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

    public String writePresets(Preset preset) throws Exception{
        Log.v("Spinner", presetSpinner.getSelectedItem().toString());
        FileOutputStream fos = openFileOutput(presetSpinner.getSelectedItem().toString(), Context.MODE_PRIVATE);
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        
        xmlSerializer.setOutput(fos, "UTF-8");
        xmlSerializer.startDocument("UTF-8", true);

        //open tag preset
        xmlSerializer.startTag("", Preset.PRESET);

        //open tag bathroom
        xmlSerializer.startTag("", Preset.BATHROOM);
        xmlSerializer.text(preset.getBathroom());
        xmlSerializer.endTag("", Preset.BATHROOM);

        //open tag living room
        xmlSerializer.startTag("", Preset.LIVINGROOM);
        xmlSerializer.text(preset.getLivingroom());
        xmlSerializer.endTag("", Preset.LIVINGROOM);

        //open tag kitchen
        xmlSerializer.startTag("", Preset.KITCHEN);
        xmlSerializer.text(preset.getKitchen());
        xmlSerializer.endTag("", Preset.KITCHEN);

        //open tag bathroom
        xmlSerializer.startTag("", Preset.BEDROOM);
        xmlSerializer.text(preset.getBedroom());
        xmlSerializer.endTag("", Preset.BEDROOM);

        //open tag bathroom
        xmlSerializer.startTag("", Preset.FRONTDOOR);
        xmlSerializer.text(preset.getFrontDoor());
        xmlSerializer.endTag("", Preset.FRONTDOOR);

        //open tag bathroom
        xmlSerializer.startTag("", Preset.GARAGEDOOR);
        xmlSerializer.text(preset.getGarageDoor());
        xmlSerializer.endTag("", Preset.GARAGEDOOR);

        //close preset tag
        xmlSerializer.endTag("", Preset.PRESET);

        xmlSerializer.endDocument();
        return writer.toString();
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
