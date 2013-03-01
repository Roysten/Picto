package nl.saxion.act.playground;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

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
		super(context, attrs);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void initNewGame() {
		Log.d(TAG,"Loading tiles");
		gameBoard = new int [mXTileCount][mYTileCount];
		for (int i = 0 ; i< mXTileCount; i++)
			for (int j = 0; j < mYTileCount; j++)
				gameBoard[i][j] = 0;
		
		toAdd = FILL;

		gameBoard[0][0] = GameView.FILL; /* just a start */

		update(); 
		invalidate(); 
	}

	public void setToAdd(int toAdd){
		this.toAdd = toAdd;
	}
	
	/**
	 * Handles the basic update: the visualization is updated according to objects on the game board
	 * From the game objects the visualization is copied to the tile array
	 */
	public void update() {
		for (int i=0; i< mXTileCount; i++){
			for (int j = 0; j<mYTileCount; j++){
				if (gameBoard[i][j] == 0) {
					setTile(BLANK, i,j); /* background tile */
				} else {
					setTile(gameBoard[i][j],i,j);
				}
			}
		}
	}
	
    /*
     * (non-Javadoc)
     * @see nl.saxion.act.playground.TileView#onSizeChanged(int, int, int, int)
     */
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        initNewGame();
   }
    
    /**
     * Zorgt ervoor dat de view altijd vierkant is
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

	public void touched(int x, int y){
		switch (toAdd){
		case FILL: gameBoard[x][y] = GameView.FILL; break;
		case HINT  : gameBoard[x][y] = GameView.HINT; break;
		case BLANK  : gameBoard[x][y] = GameView.BLANK;break;
		}
		update(); /* update the view */
		invalidate(); /* tell Android the view has to be redrawn */
	}

}
