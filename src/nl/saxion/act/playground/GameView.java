package nl.saxion.act.playground;

import android.content.Context;
import android.content.res.Resources;

import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

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
	private int toAdd = FILLED;
	
	/*
	 * Textview to show in user interface which object to add
	 */
	private TextView tv;

	private static final String TAG = "GameView";

	/**
	 * Labels for the drawables that will be loaded into the TileView class
	 */
	
	private static final int NROFDRAWABLES = 3;
	
	public static final int FILLED = 1;
	public static final int HINT = 2;
	public static final int CELL = 3;
	
	private int [][] gameBoard;

	/**
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setText(TextView myTV) {
		tv = myTV;
	}

	//Blijkt niet gebruikt te worden
//	private void initGameView() {
//		setFocusable(true);
//		initNewGame();
//	}

	public void initNewGame() {
		Log.d(TAG,"Loading tiles");
		gameBoard = new int [mXTileCount][mYTileCount];
		for (int i = 0 ; i< mXTileCount; i++)
			for (int j = 0; j < mYTileCount; j++)
				gameBoard[i][j] = 0;
		
		
		toAdd = FILLED;
		tv.setText("Wombat");
			
		Resources r = this.getContext().getResources();

		resetTiles(NROFDRAWABLES + 1); /* zero (0) is used for empty */
		loadTile(FILLED, r.getDrawable(R.drawable.wombat));
		loadTile(HINT, r.getDrawable(R.drawable.leaf));
		loadTile(CELL, r.getDrawable(R.drawable.cell));

		gameBoard[0][0] = GameView.FILLED; /* just a start */

		update(); 
		invalidate(); 
	}

	/**
	 * Handles the basic update: the visualization is updated according to objects on the game board
	 * From the game objects the visualization is copied to the tile array
	 */
	public void update() {
		for (int i=0; i< mXTileCount; i++){
			for (int j = 0; j<mYTileCount; j++){
				if (gameBoard[i][j] == 0) {
					setTile(CELL, i,j); /* background tile */
				} else {
					setTile(gameBoard[i][j],i,j);
				}
			}
		}
	}
	
	public void switchObject(){
		toAdd = (toAdd % NROFDRAWABLES) +1;
		switch (toAdd){
		case FILLED : tv.setText("Wombat"); break;
		case HINT   : tv.setText("Leaf"); break;
		case CELL   : tv.setText("delete objects"); break;
		default     : tv.setText("error");
		}		
	}
    /*
     * (non-Javadoc)
     * @see nl.saxion.act.playground.TileView#onSizeChanged(int, int, int, int)
     */
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw, oldh);
        initNewGame();
        
        if (getLayoutParams() != null && w != h) {
            getLayoutParams().height = w;
            setLayoutParams(getLayoutParams());
        }
        
   }

	public void touched(int x, int y){
		switch (toAdd){
		case FILLED: gameBoard[x][y] = GameView.FILLED; break;
		case HINT  : gameBoard[x][y] = GameView.HINT; break;
		case CELL  : gameBoard[x][y] = 0;break;
		default    : tv.setText("error");
		}
		update(); /* update the view */
		invalidate(); /* tell Android the view has to be redrawn */
	}

}
