package nl.saxion.act.playground;

import java.io.IOException;

import nl.saxion.act.playground.R;
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

/**
 * Activity voor het spelen van de puzzle
 */
public class PuzzleActivity extends Activity implements PauseDialog.NoticeDialogListener{
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
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			setPuzzle(extras.getString("puzzleName"), extras.getString("puzzlePath"));
		}
	}
	
	/**
	 * Wordt gebruikt om de puzzel aan de puzzleactivity door te geven.
	 * @param puzzleName naam vd puzzel
	 * @param puzzlePath lokatie van de puzzel
	 */
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
    
    /**
     * Methode voor pauzeknopje
     */
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
    
    /**
     * Geeft de naam vd puzzel terug
     * @return
     */
    public String getPuzzleName(){
    	return puzzleName;
    }
    
    /**
     * Methode voor de radiobuttons
     * @param v View van de radiobutton
     */
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
    
    /**
     * Als er op pauze wordt gedrukt, wordt de timer gepauzeerd.
     */
    @Override
  	public void onPause(){
  		super.onPause();
  		timerView.pauseTimer();
  	}
  
    /**
     * Als het spel ontwaakt uit pauze, gaat de timer weer verder.
     */
  	@Override
  	public void onResume(){
  		super.onResume();
  		timerView.continueTimer();
	}
  	
  	/**
  	 * Als het spel ontwaakt uit pauze, gaat de timer weer verder.
  	 */
  	@Override
  	protected void onStart(){
  		super.onStart();
  		timerView.continueTimer();
  	}

  	/**
  	 * Methode voor het klikken op continue in het pauze dialoog.
  	 */
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(dialog instanceof PauseDialog){
			timerView.continueTimer();
		}
	}

	/**
	 * Methode voor het klikken op stoppen in het pauze dialoog.
	 */
	public void onDialogNegativeClick(DialogFragment dialog) {
		finish();
	}
}
