package nl.saxion.act.playground.highscore;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * Klasse die de communicatie met de database implementeerd.
 */
public class ScoreModel extends BaseModel {

	public final static String TABLE_NAME = "Score";
	public static final String KEY_NAME = "name";
	public static final String KEY_TIME = "time";
	public static final String KEY_PUZZLE = "puzzle";

	/**
	 * constructor die een instantie van de superklasse instantieert
	 * @param context
	 */
	public ScoreModel(final Context context) {
		super(context, TABLE_NAME);
	}

	/**
	 * functie die een querry uitvoert waarbij de scores voor een bepaalde puzzel worden teruggegeven.
	 * vervolgens worden Score Objecten toegevoegd, aan een arraylist welke wordt teruggegeven.
	 * @param puzzle
	 * @return ArrayList<Score>
	 */
	public ArrayList<Score> getHighScores(String puzzle) {
//		Cursor qr = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ? = ? ORDER BY " + KEY_TIME + " ASC", new String[] {KEY_PUZZLE, puzzle});
		Cursor qr = database.query(TABLE_NAME, null, KEY_PUZZLE + " = \"" + puzzle + "\"", null, null, null, KEY_TIME + " ASC");
		ArrayList<Score> result = new ArrayList<Score>();

		for (int i = 0; i < qr.getCount(); i++) {
			if (!qr.moveToPosition(i)) {
				break;
			}
			String value = qr.getString(qr.getColumnIndex(ScoreModel.KEY_NAME));
			int time = Integer.parseInt(qr.getString(qr.getColumnIndex(ScoreModel.KEY_TIME)));
			result.add(new Score(value, time));

		}
		return result;

	}
	/**
	 * Er wordt een nieuwe entry aan de tabel toegevoegd.
	 * @param name
	 * @param time
	 * @param puzzle
	 */
	public void add(final String name, final int time, final String puzzle) {
		final ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_TIME, time);
		values.put(KEY_PUZZLE, puzzle);

		database.insert(TABLE_NAME, null, values);
	}

	/**
	 * methode waarmee een entry uit de tabel verwijdert wordt, deze word op dit moment niet gebruikt, maar is voor testdoeleinden toegevoegd.
	 */
	@Override
	public void remove(final int id) {
		database.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
	}

}