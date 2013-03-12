package nl.saxion.act.playground;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

public class GameView extends TileView {
	/**
	 *  @author Jan Stroet
	 *   A game view based on a tile representation of a game board
	 *   
	 *   Its is just a demonstration of  the use of a TileView
	 *   Objects Wombat, Rock and Leafs can be placed on or removed from a tile based game board
	 *   
	 */
	
	/*
	 * What tile to add to the game board
	 */
	private int toAdd = FILL;
	
	/*
	 * Textview to show in user interface which object to add
	 */
	private static final String TAG = "GameView";
	private int[][] puzzelSolution;
	private int puzzelTotal, gameTotal;
	

	/**
	 * Labels for the drawables that will be loaded into the TileView class
	 */
	public static final int FILL = 1;
	public static final int HINT = 2;
	public static final int BLANK = 0;
	
	private int [][] gameBoard;

	/**
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void initNewGame() {
		Log.d(TAG,"Loading tiles");
		gameBoard = new int [mDimension][mDimension];
		
		toAdd = FILL;
		invalidate(); 
	}

	public void setToAdd(int toAdd){
		this.toAdd = toAdd;
	}
	
	public void setPuzzel(Puzzle puzzel){
		puzzelSolution = puzzel.getSolution();
		puzzelTotal = puzzel.getTotal();
		setDimension(puzzel.getSizeX());
		setHints(puzzel.getVerticalHints(), puzzel.getHorizontalHints());
	}
	
	/**
	 * Handles the basic update: the visualization is updated according to objects on the game board
	 * From the game objects the visualization is copied to the tile array
	 */
	public void update(int x, int y) {
		setTile(gameBoard[x][y],x,y);
	}
	
    /*
     * (non-Javadoc)
     * @see nl.saxion.act.playground.TileView#onSizeChanged(int, int, int, int)
     */
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    /**
     * Zorgt ervoor dat de view altijd vierkant is
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

	public void touched(int x, int y){
		if(!heeftWinnaar()){
			if (gameBoard[x][y] != toAdd) {
				if (toAdd == FILL && puzzelSolution[y][x] == 1) {
					gameBoard[x][y] = toAdd;
					gameTotal++;
				} 
				else if (toAdd == FILL && puzzelSolution[y][x] != 1) {
					Toast.makeText(this.getContext(), "Fout!",Toast.LENGTH_SHORT).show();
					gameBoard[x][y] = HINT;
				} 
				else if (toAdd != FILL && gameBoard[x][y] == FILL) {
					gameBoard[x][y] = toAdd;
					gameTotal--;
				} 
				else{
					gameBoard[x][y] = toAdd;
				}
				update(x, y); /* update the view */
				invalidate(); /* tell Android the view has to be redrawn */
			}
		}
		Log.d(TAG, "Game: " + gameTotal + " Puzzel: " + puzzelTotal);
		if(heeftWinnaar()) {
			Toast.makeText(this.getContext(), "Klaar!", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "De puzzel is klaar!");
		}
	}
	
	public boolean heeftWinnaar(){
		boolean heeftWinnaar = false;
		if(puzzelTotal == gameTotal){
			heeftWinnaar = true;
		}
		return heeftWinnaar;
	}
}
