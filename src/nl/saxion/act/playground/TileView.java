package nl.saxion.act.playground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Jan Stroet Large parts of this class have been adapted from the Snake
 *         example in the Android Example library TileView: a View-variant
 *         designed for handling arrays of drawables
 * 
 */
public abstract class TileView extends View {
	private static final String TAG = "TileView";

	/**
	 * mXTileCount number of tiles in x-dimension mYTileCount number of tiles in
	 * y-dimension Their initial values are predefined in FIXED_GRID mode (here
	 * below) Their initial values are computed in VARIABLE_GRID mode on the
	 * basis of mTileSize
	 */
	protected int mXTileCount = 10; /*
									 * the number of tiles in X-dimension in
									 * FIXED_GRID - mode; will be overridden in
									 * VARIABLE_GRID mode
									 */
	protected int mYTileCount = 10;

	/**
	 * mTileSize size of the tile The initial value is fixed VARIABLE_GRID mode
	 * The value is computed in FIXED_GRID mode, the fill up the screen as much
	 * as possible
	 */

	protected float mTileSize = 1;

	/**
	 * A two-dimensional array of integers in which the number represents the
	 * index of the tile in the mTileArray that should be drawn at that location
	 */
	private int[][] mTileGrid;
	public static final int SPACING = 2;

	private final Paint linePaint = new Paint();
	private final Paint rectPaint = new Paint();
	
	private RectF rectVakje = new RectF();

	public TileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();
		Log.d(TAG, "initTileView( " + mTileSize + " )");
		initPaint();
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();
		Log.d(TAG, "initTileView( " + mTileSize + " )");
		initPaint();
	}
	
	public void initPaint(){
		linePaint.setColor(Color.BLACK);
		rectPaint.setColor(Color.LTGRAY);
	}


	/**
	 * Resets all tiles to 0 (empty)
	 * 
	 */
	public void clearTiles() {
		for (int x = 0; x < mXTileCount; x++) {
			for (int y = 0; y < mYTileCount; y++) {
				setTile(0, x, y);
			}
		}
	}

	/**
	 * Used to indicate that a particular tile (set with loadTile and referenced
	 * by an integer) should be drawn at the given x/y coordinates during the
	 * next invalidate/draw cycle.
	 * 
	 * @param tileindex
	 * @param x
	 * @param y
	 */
	public void setTile(int tileindex, int x, int y) {
		mTileGrid[x][y] = tileindex;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) ((event.getX() - mTileSize * SPACING) / mTileSize);
		int y = (int) ((event.getY() - mTileSize * SPACING) / mTileSize);
		if (x < mXTileCount && x >=0 && y >= 0 && y < mYTileCount) { /* Game Board touched */
			Log.d(TAG, "Touched (" + x + ", " + y + ")\n");
			touched(x, y);
		}
		return super.onTouchEvent(event);
	}

	/**
	 * method to handle tile touch.
	 * 
	 * @param x
	 *            the x-coordinate of the tile touched
	 * @param y
	 *            the y-coordinate of the tile touched
	 */
	public abstract void touched(int x, int y);

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mTileSize = Math.min((float)w / (mXTileCount + SPACING), (float)h / (mYTileCount + SPACING));
		Log.d(TAG, "onSizeChanged( " + mTileSize + " )");
		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();
		
		rectVakje.set(0, 0, mTileSize, mTileSize);
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for(int i = 0; i < mXTileCount; i++){
			for(int j = 0; j < mYTileCount; j++){
				if(mTileGrid[i][j] > 0){
					rectVakje.offsetTo((i + SPACING) * mTileSize, (j + SPACING) * mTileSize);
					canvas.drawRect(rectVakje, rectPaint);
					if(mTileGrid[i][j] == 2){
						canvas.drawLine((i + SPACING) * mTileSize, (j + SPACING) * mTileSize, (i + SPACING + 1) * mTileSize, (j + SPACING + 1) * mTileSize, linePaint);
						canvas.drawLine((i + SPACING + 1) * mTileSize, (j + SPACING) * mTileSize, (i + SPACING) * mTileSize, (j + SPACING + 1) * mTileSize, linePaint);
					}
				}
			}
		}

		for (int i = SPACING; i < mXTileCount + SPACING; i++) {
			canvas.drawLine(i * mTileSize, 0, i * mTileSize, canvas.getHeight(), linePaint);
		}
		
		for (int j = SPACING; j < mYTileCount + SPACING; j++) {
			canvas.drawLine(0, j * mTileSize, canvas.getWidth(), j * mTileSize, linePaint);
		}

	}

}
