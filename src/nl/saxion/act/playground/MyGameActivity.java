package nl.saxion.act.playground;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyGameActivity extends Activity {
	private GameView mGameView;
	private final String TAG = "GameView";
	private Button buttonFill, buttonCross, buttonBlank;
	
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
}
