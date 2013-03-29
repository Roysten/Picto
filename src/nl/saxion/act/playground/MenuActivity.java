package nl.saxion.act.playground;

import nl.saxion.act.playground.puzzlepicker.PuzzlePickActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity{
	Button startButton, instructionButton;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        
        startButton = (Button) findViewById(R.id.startbutton);
        instructionButton = (Button) findViewById(R.id.instructionButton);
	}
	
	public void onButtonClicked(View v) {
		if(v == startButton){
		  Intent intent = new Intent(this, PuzzlePickActivity.class);
		  startActivity(intent);
		}
		else if (v == instructionButton){
			Intent intent = new Intent(this, InstructionActivity.class);
			startActivity(intent);
		}
	}
}
