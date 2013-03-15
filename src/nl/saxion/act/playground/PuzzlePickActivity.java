package nl.saxion.act.playground;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PuzzlePickActivity extends Activity{
	
	private static final String TAG = "LevelPicker";
	
	private ListView listView;
	private AssetManager assetManager;
	private String[] puzzles;
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlepicker);
        
        listView = (ListView) findViewById(R.id.levelPickerList);
        listView.setOnItemClickListener(puzzleClickedHandler);
        checkAssets();
	}

	/**
	 * Laad de lijst met puzzels door in de map puzzles te kijken
	 */
	public void checkAssets(){
		assetManager = getAssets();
		try {
			puzzles = assetManager.list("puzzles");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
			adapter.addAll(puzzles);
			listView.setAdapter(adapter);
		} 
		catch (IOException e) {
			Log.e(TAG, "Something went wrong whilst opening the puzzle.");
		}
	}
	
	/**
	 * Open de puzzel door een String waarde mee te geven aan de PuzzleActivity
	 * @param puzzle De naam van de starten puzzel
	 */
	public void openPuzzleView(String puzzle){
		Intent intent = new Intent(this, PuzzleActivity.class);
		intent.putExtra("puzzle", puzzle);
		startActivity(intent);
	}
	
	/**
	 * Detecteer welke puzzel is aangeklikt en handel deze af in OpenPuzzleView
	 */
	private OnItemClickListener puzzleClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	openPuzzleView(puzzles[position]);
	    }
	};

}
