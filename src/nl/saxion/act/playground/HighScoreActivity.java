
package nl.saxion.act.playground;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class HighScoreActivity extends Activity {
	
	private TextView HSPosition1, HSPosition2, HSPosition3;
	private TextView HSName1, HSName2, HSName3;
	private TextView HSTime1, HSTime2, HSTime3;
	
	

	String puzzleName = "Camel (5x5)";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		HSPosition1 = (TextView) findViewById(R.id.HSposition1);
		HSPosition2 = (TextView) findViewById(R.id.HSposition2);
		HSPosition3 = (TextView) findViewById(R.id.HSposition3);
		HSName1 = (TextView) findViewById(R.id.HSName1);
		HSName2 = (TextView) findViewById(R.id.HSName2);
		HSName3 = (TextView) findViewById(R.id.HSName3);
		HSTime1 = (TextView) findViewById(R.id.HSTime1);
		HSTime2 = (TextView) findViewById(R.id.HSTime2);
		HSTime3 = (TextView) findViewById(R.id.HSTime3);
		initiateScores();
		
	}

	private void initiateScores() {
		ScoreModel sm = new ScoreModel(this);
		ArrayList<Score> scores = sm.getHighScores(puzzleName);
		Log.d("initiateScores", scores.get(0).getName());
		HSName1.setText(scores.get(0).getName());
		HSName2.setText(scores.get(1).getName());
		HSName3.setText(scores.get(2).getName());
		HSTime1.setText(scores.get(0).getTime()+"");
		HSTime1.setText(scores.get(1).getTime()+"");
		HSTime1.setText(scores.get(2).getTime()+"");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

}

