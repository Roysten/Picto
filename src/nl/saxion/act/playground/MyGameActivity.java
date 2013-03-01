package nl.saxion.act.playground;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.SystemClock;  // Deze wordt pas gebruikt op het moment dat het mogelijk is om de timer te resetten aan het eind van het level
import android.widget.Chronometer;

public class MyGameActivity extends Activity {
	private GameView mGameView;
	private final String TAG = "GameView";
	private Button buttonFill, buttonCross, buttonBlank;
	Chronometer mChronometer;
	
	//hoi
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        mGameView = (GameView) findViewById(R.id.game);
        mGameView.initNewGame();
        
        buttonFill = (Button) findViewById(R.id.buttonFill);
        buttonCross = (Button) findViewById(R.id.buttonCross);
        buttonBlank = (Button) findViewById(R.id.buttonBlank);
        
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.start();
    }
    
    public void onButtonClicked(View v){
    	if(v == buttonFill){
    		mGameView.setToAdd(GameView.FILL);
    	}
    	if(v == buttonBlank){
    		mGameView.setToAdd(GameView.BLANK);
    	}
    	if(v == buttonCross){
    		mGameView.setToAdd(GameView.HINT);
    	}
    }
    
//   Met mChronometer.stop(); wordt de timer stop gezet en dan kunnen we iets doen met die tijd. 
//    Deze kan dus worden gecalled als het level over is.
    
//          
//            mChronometer.stop();
//       
//  	
//   Met de setBase wordt de timer weer op 00.00 gezet, deze kan worden gebruikt vlak voor het level begint.
//            mChronometer.setBase(SystemClock.elapsedRealtime());
//        
//   
}
