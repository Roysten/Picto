package nl.saxion.act.playground.puzzlepicker;

import java.io.File;
import java.io.IOException;

import nl.saxion.act.playground.R;
import nl.saxion.act.playground.highscore.HighScoreActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Class voor het selecteren van de puzzels. 
 */
public class PuzzlePickActivity extends Activity{
	
	private static final String TAG = "LevelPicker";
	
	private ListView listView;
	private AssetManager assetManager;
	private String[] puzzles, categories = {"5x5", "10x10", "15x15", "20x20"};
	private Spinner categorySpinner;
	private ArrayAdapter<String> puzzleAdapter;
	private Button play;
	private String puzzleNameText;
		
	/**
	 * Initialiseren van de variabelen in de oncreate en de adapter setten
	 */
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzlepicker);
        
        //puzzellijst
        listView = (ListView) findViewById(R.id.levelPickerList);
        listView.setOnItemClickListener(puzzleClickedHandler);
        listView.setSelector(R.drawable.selector);
        puzzleAdapter = new PuzzleAdapter(this, android.R.layout.simple_list_item_1);
        play = (Button)findViewById(R.id.buttonPlay);
        
		assetManager = getAssets();
	}
	
	/**
	 * Optionsmenu (=actionbar) waar de spinner wordt toegevoegd.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		//actionbar dropdownbox
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View customMenuView = inflater.inflate(R.layout.menu_layout_spinner, null);
	    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
	    categoryAdapter.addAll(categories);
	    categorySpinner = (Spinner) customMenuView.findViewById(R.id.categorySpinner);
	    categorySpinner.setAdapter(categoryAdapter);
	    categorySpinner.setOnItemSelectedListener(new categorySpinnerItemSelectedListener());
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO);
	    actionBar.setCustomView(customMenuView);
	    return true;
	}
	
	/**
	 *	Als er een item geselecteerd wordt, dan wordt de betreffende map met puzzels gescand. 
	 */
	public class categorySpinnerItemSelectedListener implements OnItemSelectedListener{

		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
			checkAssets("puzzles" + File.separator + categories[position]);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
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
	 * Puzzel starten na button click
	 * @param v de button waarop is geklikt
	 */
	public void onButtonClicked(View v) {
		if(puzzleNameText != null){ 
		if(v == play){
			System.out.println("Button is Clicked");
			openPuzzleView(puzzleNameText, "puzzles" + File.separator + categories[categorySpinner.getSelectedItemPosition()] + File.separator + puzzleNameText);
		}else{
			Intent intent = new Intent(this, HighScoreActivity.class);
			intent.putExtra("puzzleName", puzzleNameText);
			startActivity(intent);
			}
		}
	}
	
	/**
	 * Detecteer welke puzzel is aangeklikt en handel deze af in OpenPuzzleView
	 */
	private OnItemClickListener puzzleClickedHandler = new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    	puzzleNameText = puzzles[position];
	    }
	};

}
