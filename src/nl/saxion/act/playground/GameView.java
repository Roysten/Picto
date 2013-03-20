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
	private TimerView timer;
	/*
	 * Textview to show in user interface which object to add
	 */
	private static final String TAG = "GameView";
	private int[][] puzzleSolution;
	private int[] rowTotals, columnTotals;
	private int puzzleTotal, gameTotal;
	

	public static final int FILL = 1;
	public static final int HINT = 2;
	public static final int BLANK = 0;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setTimer(TimerView timer){
		this.timer = timer;
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
	
	/**
	 * Puzzel setten, nodig omdat android geen eigen constructor toelaat binnen een View
	 * Eigenschappen puzzel zetten op de protected variabelen van TileView
	 * @param puzzle
	 */
	public void setPuzzle(Puzzle puzzle){
		puzzleSolution = puzzle.getSolution();
		puzzleTotal = puzzle.getTotal();
		mDimension = puzzle.getDimension();
		rowHints = puzzle.getRowHints();
		columnHints = puzzle.getColumnHints();
		rowTotals = puzzle.getRowTotals();
		columnTotals = puzzle.getColumnTotals();
		rowDone = new boolean[mDimension];
		columnDone = new boolean[mDimension];
		initNewGame();
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
		if(!hasWinner()){
			if (gameBoard[x][y] != toAdd) {
				if (toAdd == FILL && puzzleSolution[y][x] == 1) {
					gameBoard[x][y] = toAdd;
					gameTotal++;
					rowDone[y] = checkRow(y);
					columnDone[x] = checkColumn(x);
				} 
				else if (toAdd == FILL && puzzleSolution[y][x] != 1) {
					Toast.makeText(this.getContext(), "Fout!",Toast.LENGTH_SHORT).show();
					gameBoard[x][y] = HINT;
					timer.timePenalty(120);
				} 
				else if (toAdd != FILL && gameBoard[x][y] == FILL) {
					gameBoard[x][y] = toAdd;
					gameTotal--;
					rowDone[y] = checkRow(y);
					columnDone[x] = checkColumn(x);
				} 
				else{
					gameBoard[x][y] = toAdd;
				}
				invalidate(); /* tell Android the view has to be redrawn */
			}
		}
		if(hasWinner()) {
			Toast.makeText(this.getContext(), "Klaar!", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "De puzzel is klaar!");
			timer.killTimer();
		}
	}
	
	public boolean checkRow(int row){
		int currentDone = 0;
		for(int i = 0; i < mDimension; i++){
			currentDone += gameBoard[i][row] == 1 ? 1 : 0;
		}
		return currentDone == rowTotals[row];
	}
	
	public boolean checkColumn(int column){
		int currentDone = 0;
		for(int i = 0; i < mDimension; i++){
			currentDone += gameBoard[column][i] == 1 ? 1 : 0;
		}
		return currentDone == columnTotals[column];
	}
	
	public boolean hasWinner(){
		boolean heeftWinnaar = false;
		if(puzzleTotal == gameTotal){
			heeftWinnaar = true;
		}
		return heeftWinnaar;
	}
}
