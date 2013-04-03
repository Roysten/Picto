package nl.saxion.act.playground.highscore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import nl.saxion.act.playground.highscore.ScoreModel;
 
/**
 * Klasse die verantwoordelijk is voor het indien nodig creeren van de database en de tabellen.
 *
 */
public class ScoreDatabase extends SQLiteOpenHelper{
 
 public ScoreDatabase(Context context) {
  super(context, "score.db", null, 1);
}
 
 @Override
 /**
  * creert indien nodig fe database, bij de eerste installatie van de app
  * vervolgens creert de app ook de eerste keer de tabel.
  */
 public void onCreate(SQLiteDatabase db) {
  // TODO Create your tables here
  Log.i("ScoreExample", "Create database: " + ScoreModel.TABLE_NAME);
 
  /*Creates the table in the database.
   * id: to access a single entry; for example for deleting a row
   * value: is the score which we want to save
   *
   */
 
  db.execSQL("create table " + ScoreModel.TABLE_NAME + " (" +
          BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          ScoreModel.KEY_NAME + " TEXT," + 
          ScoreModel.KEY_TIME + " INTEGER NOT NULL," +ScoreModel.KEY_PUZZLE + " TEXT);");
 
 }
 
 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
 }
 
}