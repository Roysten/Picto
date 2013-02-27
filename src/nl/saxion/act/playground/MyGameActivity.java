package nl.saxion.act.playground;

import nl.saxion.act.playground.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyGameActivity extends Activity {
	private static final String TAG = "MyActivity";
	private GameView mGameView;
	private TextView tv;
	
	//hoi
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        mGameView = (GameView) findViewById(R.id.game);
        tv = (TextView) findViewById( R.id.tekst );
        mGameView.setText(tv);
        mGameView.initNewGame();
        
        
         Toast.makeText(getApplicationContext(), "Lets start", Toast.LENGTH_SHORT).show();
         
         registerButton1();
         registerButton2();
    }
    

	private void registerButton1() {
       	Log.d(TAG, "registerButton1()");

		final Button button1 = (Button) findViewById( R.id.button1 );
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* 
                 * ACTION of Button1
                 * Next game Object
                 */
           	 
           	 mGameView.switchObject();
            }
        });
	}
	
	private void registerButton2() {
       	Log.d(TAG, "registerButton2()");

		final Button button1 = (Button) findViewById( R.id.button2 );
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* 
                 * ACTION of Button 2
                 * start a new game
                 */
            	Toast.makeText(getApplicationContext(), "Button2Clicked",Toast.LENGTH_SHORT).show();
             mGameView.initNewGame();
            }
        });
	}


}