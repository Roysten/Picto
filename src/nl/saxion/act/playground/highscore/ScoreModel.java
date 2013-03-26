package nl.saxion.act.playground.highscore;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

public class ScoreModel extends BaseModel {

	public final static String TABLE_NAME = "Score";
	public static final String KEY_NAME = "name";
	public static final String KEY_TIME = "time";
	public static final String KEY_PUZZLE = "puzzle";

	public ScoreModel(final Context context) {
		super(context, TABLE_NAME);
	}

	public ArrayList<Score> getHighScores(String puzzle) {
		Cursor qr = database.query(TABLE_NAME, null, KEY_PUZZLE + " = \"" + puzzle + "\"", null, null, null, KEY_TIME + " ASC");
		System.out.println("\"" + puzzle + "\"");
		ArrayList<Score> result = new ArrayList<Score>();

		for (int i = 0; i < qr.getCount(); i++) {
			if (!qr.moveToPosition(i)) {
				break;
			}
			String value = qr.getString(qr.getColumnIndex(ScoreModel.KEY_NAME));
			int time = Integer.parseInt(qr.getString(qr.getColumnIndex(ScoreModel.KEY_TIME)));
			result.add(new Score(i + 1, value, time));

		}
		return result;

	}

	public void add(final String name, final int time, final String puzzle) {
		final ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_TIME, time);
		values.put(KEY_PUZZLE, puzzle);

		database.insert(TABLE_NAME, null, values);
	}

	@Override
	public void remove(final int id) {
		database.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
	}

}