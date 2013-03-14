package nl.saxion.act.playground;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
import android.widget.TextView;

public class PuzzleActivity extends Activity implements PauseDialog.NoticeDialogListener {
	private GameView mGameView;
	private final String TAG = "MyGameActivity";
	private Button buttonFill, buttonCross, buttonBlank;
	private int timePassed;
	private boolean pause = false;
	private TextView mTime;
	
	Timer timer = new Timer();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puzzle);
		
		mGameView = (GameView) findViewById(R.id.game);
		buttonFill = (Button) findViewById(R.id.buttonFill);
		buttonCross = (Button) findViewById(R.id.buttonCross);
		buttonBlank = (Button) findViewById(R.id.buttonBlank);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			setPuzzle(extras.getString("puzzle"));
		}

		mTime = (TextView) findViewById(R.id.textView1);

		System.out.println("Timer restart weer oncreate");

		// De timer moet starten bij onCreate
		timer = new Timer();
		timer.scheduleAtFixedRate(new updateTimer(), 0, 1000);

	}
	
	public void setPuzzle(String puzzleName){
        AssetManager assetManager = getAssets();
		try {
			Log.d(TAG, "Opening puzzle: " + "puzzles/" + puzzleName);
			mGameView.setPuzzel(new Puzzle(assetManager.open("puzzles/" + puzzleName)));
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
    	  pause = true;
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
    
// Update timer zorgt ervoor dat deze elke seconde wordt bijgewerkt op het scherm, deze is niet nodig als we de tijd onzichtbaar laten tijdens spelen.    
  class updateTimer extends TimerTask{
	  public void run(){
		  PuzzleActivity.this.runOnUiThread(new Runnable(){
	  
			  public void run(){
				  if(!pause){
					  timePassed++;
					  mTime.setText("Time passed: " + timePassed);
					  System.out.println(timePassed);
	  				}
			  }
		  	});
	  	}
  }
  
  	protected void onPause(){
  		super.onPause();
  		pause = true;
  		System.out.println("Timer gestopt via onPause");

  	}
  
  	protected void onResume(){
  		super.onResume();
  		System.out.println("Timer restart weer");
  		pause = false;
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		pause = false;
  	  	Log.d(TAG, "Timer weer gestart na weggaan uit pauze menu");
		
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO nog in te vullen actie, mogelijk terug naar hoofdmenu in sprint 2
		finish();
	}
}
