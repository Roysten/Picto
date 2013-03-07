package nl.saxion.act.playground;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Timer;
import java.util.TimerTask;

//import com.example.timertest.MyGameActivity;
//import com.example.timertest.Main.updateTimer;

import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class MyGameActivity extends Activity {
	private GameView mGameView;
	private final String TAG = "GameView";
	private Button buttonFill, buttonCross, buttonBlank;
	private int tijdGebruikt;
	TextView mTime;
	Button buttonVerhoging;
	Button buttonstop;
	Timer timer = new Timer();
		
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
        
        buttonVerhoging = (Button) findViewById(R.id.button1);
        buttonstop = (Button) findViewById(R.id.button2);
        mTime = (TextView) findViewById(R.id.textView1);
        buttonVerhoging.setText("Fout ");
        buttonstop.setText("stop tijd");
       
        mTime.setText("Tijd: ");
        
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
				  tijdGebruikt++;
				  System.out.println(tijdGebruikt);
				  mTime.setText("Tijd: " + tijdGebruikt);
	  				}
		  	});
	  	}
  }
    

}
