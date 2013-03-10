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

public class MyGameActivity extends Activity implements PauseDialog.NoticeDialogListener {
	private GameView mGameView;
	private final String TAG = "MyGameActivity";
	private Button buttonFill, buttonCross, buttonBlank;
	private int tijdGebruikt;
	private boolean pauze = false;
	
	TextView mTime;
	Button buttonVerhoging;
	Button buttonstop;
	Timer timer = new Timer();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mGameView = (GameView) findViewById(R.id.game);
        AssetManager assetManager = getAssets();
		try {
			mGameView.setPuzzel(new Puzzel(assetManager.open("puzzels/Banana.txt")));
		} catch (IOException e) {
			System.err.println("Er ging iets mis bij het openen van de puzzel.");
			e.printStackTrace();
		}
        mGameView.initNewGame();
        
        buttonFill = (Button) findViewById(R.id.buttonFill);
        buttonCross = (Button) findViewById(R.id.buttonCross);
        buttonBlank = (Button) findViewById(R.id.buttonBlank);
        
        buttonVerhoging = (Button) findViewById(R.id.button1);
        buttonstop = (Button) findViewById(R.id.button2);
        mTime = (TextView) findViewById(R.id.textView1);
        buttonVerhoging.setText("Fout ");
        buttonstop.setText("stop tijd");
       
        mTime.setText("Tijd: ");
        
  		System.out.println("Timer restart weer oncreate");

      //        De timer moet starten bij onCreate
        timer = new Timer();
        timer.scheduleAtFixedRate(new updateTimer(), 0, 1000);

//      Buttons zitten er nu in om te testen.
//        Als de button wordt gedrukt, dit is ipv fout maken
        buttonVerhoging.setOnClickListener(new View.OnClickListener(){
        	public void onClick(View v){
        		Button buttonVerhoging = (Button)v;
        		tijdGebruikt = tijdGebruikt + 5;
        		System.out.println(tijdGebruikt);
        	}
        });
      
        buttonstop.setOnClickListener(new View.OnClickListener() {
			
        	
        	
			public void onClick(View v) {
				Button buttonstop =(Button)v;
				timer.cancel();
				System.out.println("Final Time: " + tijdGebruikt);
				}
			});
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
    	  pauze = true;
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
    
    public void setTextView(){
    	mTime.setText("Tijd" + tijdGebruikt);
    }
    
// Update timer zorgt ervoor dat deze elke seconde wordt bijgewerkt op het scherm, deze is niet nodig als we de tijd onzichtbaar laten tijdens spelen.    
  class updateTimer extends TimerTask{
	  public void run(){
		  MyGameActivity.this.runOnUiThread(new Runnable(){
	  
			  public void run(){
				  if(!pauze){
					  tijdGebruikt++;
					  mTime.setText("Tijd: " + tijdGebruikt);
					  System.out.println(tijdGebruikt);
	  				}
			  }
		  	});
	  	}
  }
  
  	protected void onPause(){
  		super.onPause();
  		pauze = true;
  		System.out.println("Timer gestopt via onPause");

  	}
  
  	protected void onResume(){
  		super.onResume();
  		System.out.println("Timer restart weer");
  		pauze = false;
	}

	public void onDialogPositiveClick(DialogFragment dialog) {
		pauze = false;
  	  	Log.d(TAG, "Timer weer gestart na weggaan uit pauze menu");
		
	}

	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO nog in te vullen actie, mogelijk terug naar hoofdmenu in sprint 2
		
	}
}
