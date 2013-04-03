package nl.saxion.act.playground.puzzlepicker;

import nl.saxion.act.playground.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PuzzleAdapter extends ArrayAdapter<String> {

	/**
	 * Deze custom adapter is eigenlijk een beetje overkill, maar is nodig om de items geselecteerd te houden (!)
	 * @param context
	 * @param textViewResourceId
	 */
	
	public PuzzleAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	/**
	 * Items wisselen uit de lijst
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.puzzle_list_item, null);
		}
		String puzzleName = getItem(position);
		if(puzzleName != null){
			TextView puzzleNameTextView = (TextView) convertView.findViewById(R.id.puzzleNameTextView);
			puzzleNameTextView.setText(puzzleName);
		}
		return convertView;
	}
}
