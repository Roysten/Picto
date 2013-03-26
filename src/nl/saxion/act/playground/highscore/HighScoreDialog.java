package nl.saxion.act.playground.highscore;

import nl.saxion.act.playground.PuzzleActivity;
import nl.saxion.act.playground.R;
import nl.saxion.act.playground.TimerView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HighScoreDialog extends Dialog{

	private EditText nameEditText;
	private Button buttonNegative, buttonPositive;
	private Activity activity;
	
	public HighScoreDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}
	
	public HighScoreDialog(Context context, int theme){
		super(context, theme);
		init(context);
	}
	
	public HighScoreDialog(Context context){
		super(context);
		init(context);
	}
	
	public void init(Context context){
		this.setContentView(R.layout.dialog_highscore);
		this.setTitle("Level complete!");
		
		activity = (Activity) context;
		
		nameEditText = (EditText) this.findViewById(R.id.nameEditText);
		
		buttonNegative = (Button) this.findViewById(R.id.buttonNegative);
		buttonPositive = (Button) this.findViewById(R.id.buttonPositive);
		
		onButtonClickedListener buttonClicked = new onButtonClickedListener();
		
		buttonNegative.setOnClickListener(buttonClicked);
		buttonPositive.setOnClickListener(buttonClicked);
	}
	
	public class onButtonClickedListener implements android.view.View.OnClickListener{
		@Override
		public void onClick(View v) {
			if(v.equals(buttonNegative)){
				dismiss();
				activity.finish();
			}
			else if(v.equals(buttonPositive)){
				if(nameEditText.getText().length() != 0){
					String puzzleName = ((PuzzleActivity) activity).getPuzzleName();
					ScoreModel sm = new ScoreModel(activity);
					TimerView tv = (TimerView) activity.findViewById(R.id.timerView1);
					sm.add(nameEditText.getText().toString(), tv.getTime(), puzzleName);
					Intent intent = new Intent(activity, HighScoreActivity.class);
					intent.putExtra("puzzleName", puzzleName);
					dismiss();
					activity.startActivity(intent);
					activity.finish();
				}
				else{
					Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}