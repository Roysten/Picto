package nl.saxion.act.playground;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

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

	public void init(){
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.timerview, this);
		timePassedValueTextView = (TextView) layout.findViewById(R.id.timePassedValueTextView);
		runnable.run();	
	}
	
	public void timePenalty(int penalty){
		timePassed += penalty;
		handler.post(runnable);
	}
	
	public void setText(String text){
		this.timePassedValueTextView.setText(text);
	}
	
	public void pauseTimer(){
		pause = true;
	}
	
	public void continueTimer(){
		pause = false;
	}
	
	public void killTimer(){
		handler.removeCallbacks(runnable);
	}
}
