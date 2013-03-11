package nl.saxion.act.playground;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelPicker extends Activity{
	private Button level1;
	private Button level2;
	private Button level3;
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelpicker);
        
        level1 =(Button) findViewById(R.id.buttonlevel1);
        level2 =(Button) findViewById(R.id.buttonlevel2);
        level3 =(Button) findViewById(R.id.buttonlevel3);
        
	}
	
	public void onClickVolgend(View v) {
		  Intent intent = new Intent(this, MyGameActivity.class);
		  startActivity(intent);
		}

}
