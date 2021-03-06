package nl.saxion.act.playground.highscore;

import nl.saxion.act.playground.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Een custom adapter voor het weergeven van Score-objecten in de HighScoreActivity
 */
public class ScoreAdapter extends ArrayAdapter<Score> {

	public ScoreAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.score_listview_item, null);
		}
		Score s = getItem(position);
		if (s != null) {
			TextView placeTextView = (TextView) convertView.findViewById(R.id.placeTextView);
			TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
			TextView scoreTextView = (TextView) convertView.findViewById(R.id.scoreTextView);
			nameTextView.setText(s.getName());
			scoreTextView.setText(s.getTime() + "");
			placeTextView.setText(position + 1 + ".");
		}
		return convertView;
	}
}
