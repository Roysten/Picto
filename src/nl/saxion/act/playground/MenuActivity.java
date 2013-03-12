package nl.saxion.act.playground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity{
	Button startButton, exitButton;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        
        startButton = (Button) findViewById(R.id.startbutton);
	}
	
	public void onButtonClicked(View v) {
		if(v == startButton){
		  Intent intent = new Intent(this, PuzzlePickActivity.class);
		  startActivity(intent);
		}
	}
}
