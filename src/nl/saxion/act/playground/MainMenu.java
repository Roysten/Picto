package nl.saxion.act.playground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity{
	Button startGame;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoofdmenu);
        
        startGame = (Button)findViewById(R.id.startbutton);	
	}
	
	public void onClickVolgend(View v) {
		  Intent intent = new Intent(this, LevelPicker.class);
		  startActivity(intent);
		}

}
