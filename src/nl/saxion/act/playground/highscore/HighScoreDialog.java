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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Geeft een dialoog weer aan het einde van de puzzel waarin de score/tijd wordt weergegeven
 * en waarin een speler zijn naam kan invulen.
 *
 */

public class HighScoreDialog extends Dialog{

	private EditText nameEditText;
	private Button buttonNegative, buttonPositive;
	private Activity activity;
	private TextView timeTextView;
	private TimerView timerView;
	
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
	
	/**
	 * het initialiseren van de interface
	 * @param context
	 */
	public void init(Context context){
		this.setContentView(R.layout.dialog_highscore);
		this.setTitle("Level complete!");
		
		activity = (Activity) context;
		
		nameEditText = (EditText) this.findViewById(R.id.nameEditText);
		timeTextView = (TextView) this.findViewById(R.id.timeTextView);
		
		buttonNegative = (Button) this.findViewById(R.id.buttonNegative);
		buttonPositive = (Button) this.findViewById(R.id.buttonPositive);
		
		onButtonClickedListener buttonClicked = new onButtonClickedListener();
		
		buttonNegative.setOnClickListener(buttonClicked);
		buttonPositive.setOnClickListener(buttonClicked);
		
		timerView = (TimerView) activity.findViewById(R.id.timerView1);
		timeTextView.setText(timerView.getTime() + "");
	}
	
	/**
	 * in deze methode worden de activiteiten van de knoppen afgevangen.
	 * En vervolgens verwerkt.
	 */
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
					sm.add(nameEditText.getText().toString(), timerView.getTime(), puzzleName);
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
