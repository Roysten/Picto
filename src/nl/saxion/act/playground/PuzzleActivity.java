package nl.saxion.act.playground;

import java.io.IOException;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PuzzleActivity extends Activity implements PauseDialog.NoticeDialogListener {
	private GameView mGameView;
	private final String TAG = "MyGameActivity";
	private Button buttonFill, buttonCross, buttonBlank;
	private TimerView timerView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle);
		
		mGameView = (GameView) findViewById(R.id.game);
		buttonFill = (Button) findViewById(R.id.buttonFill);
		buttonCross = (Button) findViewById(R.id.buttonCross);
		buttonBlank = (Button) findViewById(R.id.buttonBlank);
		timerView = (TimerView) findViewById(R.id.timerView1);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			setPuzzle(extras.getString("puzzle"));
		}
	}
	
	public void setPuzzle(String puzzleName){
        AssetManager assetManager = getAssets();
		try {
			Log.d(TAG, "Opening puzzle: " + "puzzles/" + puzzleName);
			mGameView.setPuzzel(new Puzzle(assetManager.open("puzzles/" + puzzleName)));
			mGameView.setTimer(timerView);
			mGameView.initNewGame();
		} catch (IOException e) {
			Log.e(TAG, "Something went wrong whilst opening puzzles.");
		}
	}
	
    /**
     * Het creeren van een actionbar op basis van menu.xml
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu, menu);
      return true;
    } 
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if(item.getItemId()==(R.id.menu_pause)){
    	  timerView.pauseTimer();
    	  Log.d(TAG, "Timer gepauzeert via actionbarpauze knop");
    	  PauseDialog pauseDialog = new PauseDialog();
    	  FragmentManager fm = getFragmentManager();
    	  pauseDialog.show(fm, TAG);
      }
      return true;
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
    
  	protected void onPause(){
  		super.onPause();
  		timerView.pauseTimer();
  	}
  
  	protected void onResume(){
  		super.onResume();
  		timerView.continueTimer();
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		timerView.continueTimer();
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
	}
}
