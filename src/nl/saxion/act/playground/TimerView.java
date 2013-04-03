package nl.saxion.act.playground;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Class waarin de timer wordt afgehandeld
 */
public class TimerView extends LinearLayout{

	private TextView timePassedValueTextView;
	private boolean pause;
	private int timePassed = -1;
	Handler handler = new Handler();
	Runnable runnable = new Runnable(){
		
		public void run() {
			if(!pause){
				timePassed++;
				setText("" + timePassed);
			}
			handler.removeCallbacks(runnable);
			handler.postDelayed(this, 1000);
		}
	};
	
	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TimerView(Context context) {
		super(context);
		init();
	}

	/**
	 * Layout inflaten en timer starten
	 */
	public void init(){
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.timerview, this);
		timePassedValueTextView = (TextView) layout.findViewById(R.id.timePassedValueTextView);
		runnable.run();	
	}
	
	/**
	 * Tijd onmiddelijk ophogen met opgegeven tijdstraf
	 * @param penalty = tijdstraf
	 */
	public void timePenalty(int penalty){
		timePassed += penalty;
		handler.post(runnable);
	}
	
	/**
	 * Zet de waarde van de timer in de bijbehorende textview
	 * @param text
	 */
	public void setText(String text){
		this.timePassedValueTextView.setText(text);
	}
	
	/**
	 * Pauzeert de timer
	 */
	public void pauseTimer(){
		pause = true;
	}
	
	/**
	 * Laat de timer verderlopen. Als de timer nog loopt, gebeurt er niets
	 */
	public void continueTimer(){
		pause = false;
	}
	
	/**
	 * Stop de timer permanent. (wordt gebruikt wanneer hij echt niet meer nodig is, zodat hij niet in de
	 * achtergrond door blijft lopen.
	 */
	public void killTimer(){
		handler.removeCallbacks(runnable);
	}
	
	/**
	 * 
	 * @return de huidige waarde van de timer
	 */
	public int getTime(){
		return timePassed;
	}
}
