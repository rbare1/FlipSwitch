package cs481.rbamap.flipswitch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                                                 }
                                                 else{
                                                     Log.v("", "LED is switched off now");
                                                     ledTestButton.setText("Switch LED On");
                                                     ledStatus = false;
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
