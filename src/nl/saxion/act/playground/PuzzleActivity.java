package nl.saxion.act.playground;

import java.io.IOException;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PuzzleActivity extends Activity implements PauseDialog.NoticeDialogListener, HighScoreDialog.NoticeDialogListener {
	private GameView mGameView;
	private final String TAG = "MyGameActivity";
	private Button buttonFill, buttonCross, buttonBlank;
	private TimerView timerView;
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
			setPuzzle(extras.getString("puzzleName"), extras.getString("puzzlePath"));
		}
	}
	
	public void setPuzzle(String puzzleName, String puzzlePath){
		this.puzzleName = puzzleName;
        AssetManager assetManager = getAssets();
		try {
			Log.d(TAG, "Opening puzzle " + puzzleName + " at path " + puzzlePath);
			mGameView.setPuzzle(new Puzzle(assetManager.open(puzzlePath)));
			this.setTitle(puzzleName);
			mGameView.setTimer(timerView);
		} catch (IOException e) {
			Log.e(TAG, "Something went wrong whilst opening puzzle. Does the puzzle file exist?");
			e.printStackTrace();
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
			EditText nameEditText = ((HighScoreDialog) dialog).nameField; 
			if(nameEditText.getText().length() == 0){
				Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
			}
			else{
				ScoreModel sm = new ScoreModel(this);
				sm.add(((HighScoreDialog) dialog).nameField.getText().toString(), timerView.getTime(), puzzleName);
				Intent intent = new Intent(this, HighScoreActivity.class);
				intent.putExtra("puzzleName", puzzleName);
				startActivity(intent);
			}
		}
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
	}
}
