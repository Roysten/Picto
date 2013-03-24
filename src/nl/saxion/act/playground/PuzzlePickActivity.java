package nl.saxion.act.playground;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
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
	private String[] puzzles, categories = {"5x5", "10x10", "15x15", "20x20"};
	private ArrayAdapter<String> puzzleAdapter;
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlepicker);
        
        listView = (ListView) findViewById(R.id.levelPickerList);
        listView.setOnItemClickListener(puzzleClickedHandler);
        
        puzzleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        
		assetManager = getAssets();
        
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        categoryAdapter.addAll(categories);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setListNavigationCallbacks(categoryAdapter, new categoryMenu());
	}
	
	public class categoryMenu implements OnNavigationListener{

		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			checkAssets("puzzles" + File.separator + categories[itemPosition]);
			return false;
		}
		
	}
	
	/**
	 * Laad de lijst met puzzels door in de map puzzles te kijken
	 */
	public void checkAssets(String path){
		try {
			puzzles = assetManager.list(path);
			puzzleAdapter.clear();
			puzzleAdapter.addAll(puzzles);
			listView.setAdapter(puzzleAdapter);
		} 
		catch (IOException e) {
			Log.e(TAG, "Something went wrong whilst scanning for puzzles.");
		}
	}
	
	/**
	 * Open de puzzel door een String waarde mee te geven aan de PuzzleActivity
	 * @param puzzle De naam van de starten puzzel
	 */
	public void openPuzzleView(String puzzleName, String puzzlePath){
		Intent intent = new Intent(this, PuzzleActivity.class);
		intent.putExtra("puzzleName", puzzleName);
		intent.putExtra("puzzlePath", puzzlePath);
		startActivity(intent);
	}
	
	/**
	 * Detecteer welke puzzel is aangeklikt en handel deze af in OpenPuzzleView
	 */
	private OnItemClickListener puzzleClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	openPuzzleView(puzzles[position], "puzzles" + File.separator + categories[position] + File.separator + puzzles[position]);
	    }
	};

}
