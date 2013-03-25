package nl.saxion.act.playground;
import java.util.ArrayList;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
 
public class ScoreModel extends BaseModel {
 
public final static String TABLE_NAME = "Score";
public static final String KEY_NAME = "name";
public static final String KEY_TIME = "time";
public static final String KEY_PUZZLE = "puzzle";
 
 public ScoreModel(final Context context) {
  super(context, TABLE_NAME);
 }
 
 public Cursor getAllOrdered() {
  return database.query(TABLE_NAME, null, null, null, null, null, KEY_TIME + " ASC LIMIT 0,3");
 }
 
 public ArrayList<Score> getHighScores(String puzzle){
	Log.d("getHighScores","start getHighScores");
	Cursor qr=database.query(TABLE_NAME, null, KEY_PUZZLE+" = "+"\""+puzzle+"\"", null, null, null, KEY_TIME + " ASC LIMIT 0,3");
	Log.d("query","querry uitgevoerd");
	ArrayList<Score> result = new ArrayList<Score>();
	for (int i = 0; i < qr.getCount(); i++) {
		   if(!qr.moveToPosition(i)){
		    break;
		   }
		   Log.d("query teller", ""+i);
		   String value = qr.getString(qr.getColumnIndex(ScoreModel.KEY_NAME));
		   int time = Integer.parseInt(qr.getString(qr.getColumnIndex(ScoreModel.KEY_TIME)));
		   result.add(new Score(value,time));
		 
		  }
	Log.d("getHighScores", result.toString());
	 return result;
	 
 }
 public String getAllFormated(){
 
  String result = "";
 
  Cursor cursor = getAllOrdered();
 
  for (int i = 0; i < cursor.getCount(); i++) {
   if(!cursor.moveToPosition(i)){
    break;
   }
   String value = cursor.getString(cursor.getColumnIndex(ScoreModel.KEY_NAME));
   String time = cursor.getString(cursor.getColumnIndex(ScoreModel.KEY_TIME));
   String puzzle = cursor.getString(cursor.getColumnIndex(ScoreModel.KEY_PUZZLE));
 
   result+= (i+1) +".     " + value + "      " + time + "     "+ puzzle +"\n";
  }
 
  return result;
 }
 
 public void add(final String name, final int time, final String puzzle) {
  final ContentValues values = new ContentValues();
  values.put(KEY_NAME, name);
  values.put(KEY_TIME, time);
  values.put(KEY_PUZZLE, puzzle);
 
  database.insert(TABLE_NAME, null, values);
  Log.d("ScoreModel", "Entry aangemaakt"+getAllFormated());
 }
 
 @Override
 public void remove(final int id) {
  database.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
 }
 
}