
package nl.saxion.act.playground.highscore;

import java.util.ArrayList;

import nl.saxion.act.playground.R;
import nl.saxion.act.playground.R.id;
import nl.saxion.act.playground.R.layout;
import nl.saxion.act.playground.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class HighScoreActivity extends Activity {
	
	private ListView scoreListView;
	private ScoreAdapter adapter;
	private ScoreModel scoreModel;
	private String puzzleName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_score);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			puzzleName = extras.getString("puzzleName");
		}
		
		adapter = new ScoreAdapter(this, android.R.layout.simple_list_item_1);
		
		scoreListView = (ListView) findViewById(R.id.scoreListView);
		scoreListView.setAdapter(adapter);
		
		scoreModel = new ScoreModel(this);
		
		initiateScores();
		
	}

	private void initiateScores() {
		ArrayList<Score> scores = scoreModel.getHighScores(puzzleName);
		adapter.addAll(scores);
		System.out.println(scores.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_score, menu);
		return true;
	}

}

