package cs481.rbamap.flipswitch;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.apache.http.client.ClientProtocolException;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.xml.parsers.ParserConfigurationException;
import house.mobilecontrollers.AudioController;
import house.mobilecontrollers.CameraController;
import house.mobilecontrollers.GarageController;
import house.mobilecontrollers.LightController;
import house.mobilecontrollers.PlayerController;
import house.models.Audio;
import house.models.Camera;
import house.models.Door;
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
    public void triggerAudio(Audio audio) {
        AudioController controller = new AudioController();
        Toast.makeText(MyActivity.this, audio.getName(), Toast.LENGTH_SHORT).show();
        controller.execute(audio);
    }
    public void triggerCamera(Camera camera) {
        CameraController controller = new CameraController();
        Log.v("Event", "Camera triggered");
        controller.execute(camera);
    }
    public void triggerGarageUp(Door garageDoor) {
        GarageController controller = new GarageController();
        Log.v("Event", "Garage up triggered");
        controller.execute(garageDoor);
    }
    public void triggerAudioChange(String str){
        PlayerController controller = new PlayerController();
        Log.v("Event", "Change in audio");
        controller.execute(str);
    }
    public void getAudioList() throws URISyntaxException, ClientProtocolException,
            IOException, ParserConfigurationException, SAXException{
        String [] listOfFiles = getAssets().list("Music");
        Audio[] audioList = new Audio[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            Audio music1 = new Audio();
            music1.setName(listOfFiles[i]);
            audioList[i] = music1;
        }
        if(audioList != null) {
            displayAudioView(audioList);
        } else {
            Toast.makeText(MyActivity.this, "No Music Found!", Toast.LENGTH_SHORT).show();
        }
    }
    public void setDefaultView(){
        setContentView(R.layout.activity_my);
        ImageButton preset3 = (ImageButton) findViewById(R.id.preset3_Button);
        preset3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, Presets.class);
                startActivity(intent);
            }
        });

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
                                                   //setContentView(R.layout.activity_living_room);
                                               }
                                           }
        );

        ImageButton audio = (ImageButton) findViewById(R.id.button_audio);
        audio.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAudioList();
                } catch (Exception e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        ImageButton temperatureButton = (ImageButton) findViewById(R.id.tempButton);
        temperatureButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, LivingRoom.class);
                startActivity(intent);
            }
        });

        ImageButton camera = (ImageButton) findViewById(R.id.button_camera);
        camera.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Camera cam = new Camera();
                    triggerCamera(cam);
                } catch (Exception e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        ImageButton garageUp = (ImageButton) findViewById(R.id.open_garage);
        garageUp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Door garageDoor = new Door();
                    triggerGarageUp(garageDoor);
                } catch (Exception e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        ImageButton audioPause = (ImageButton) findViewById(R.id.preset1_Button);
        audioPause.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    triggerAudioChange("audioP");
                } catch (Exception e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        ImageButton audioStop = (ImageButton) findViewById(R.id.preset2_Button);
        audioStop.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    triggerAudioChange("audioS");
                } catch (Exception e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
    private void displayAudioView(final Audio[] musicName) {
// Create Linear layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
// Add back button
        Button backButton = new Button(this);
        backButton.setText("Back");
        backButton.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultView();
            }
        });
// Add button to layout
        layout.addView(backButton);
        String listArray [] = new String[musicName.length];
        for(int i = 0; i < musicName.length; i++){
            String str = musicName[i].getName();
            listArray[i] = str;
        }
        ListAdapter la = new ArrayAdapter<String>(this, R.layout.list_item, listArray);
        ListView lv = new ListView(this);
        lv.setAdapter(la);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                triggerAudio(musicName[position]);
            }
        });
        layout.addView(lv);
// Make inventory view visible
        setContentView(layout,llp);
    }
}