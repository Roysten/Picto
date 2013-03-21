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

public class PuzzleActivity extends Activity implements PauseDialog.NoticeDialogListener, HighScoreDialog.NoticeDialogListener {
	private GameView mGameView;
	private final String TAG = "MyGameActivity";
	private Button buttonFill, buttonCross, buttonBlank;
	private TimerView timerView;
	private AssetManager assetManager;
	private String puzzleName;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.puzzle);
		
		buttonFill = (Button) findViewById(R.id.buttonFill);
		buttonCross = (Button) findViewById(R.id.buttonCross);
		buttonBlank = (Button) findViewById(R.id.buttonBlank);
		timerView = (TimerView) findViewById(R.id.timerView1);
		
		mGameView = (GameView) findViewById(R.id.game);
		mGameView.setActivity(this);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			setPuzzle(extras.getString("puzzle"));
		}
	}
	
	public void setPuzzle(String puzzleName){
		this.puzzleName = puzzleName;
        AssetManager assetManager = getAssets();
		try {
			Log.d(TAG, "Opening puzzle: " + "puzzles/" + puzzleName);
			mGameView.setPuzzle(new Puzzle(assetManager.open("puzzles/" + puzzleName)));
			mGameView.setTimer(timerView);
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
    @Override
  	public void onPause(){
  		super.onPause();
  		timerView.pauseTimer();
  	}
  
  	@Override
  	public void onResume(){
  		super.onResume();
  		timerView.continueTimer();
	}
  	
  	@Override
  	protected void onStart(){
  		super.onStart();
  		timerView.continueTimer();
  	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		if(dialog instanceof PauseDialog){
			timerView.continueTimer();
		}
		if(dialog instanceof HighScoreDialog){
			ScoreModel sm = new ScoreModel(this);
			sm.add(((HighScoreDialog) dialog).nameField.getText().toString(),timerView.getTime() , puzzleName);
		}
		
		
		
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
		
	}
}
