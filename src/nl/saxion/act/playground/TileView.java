package nl.saxion.act.playground;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Jan Stroet
 * Large parts of this class have been adapted from the Snake example in the Android Example library
 * TileView: a View-variant designed for handling arrays of drawables
 * 
 */
public abstract class TileView extends View {
	private static final String TAG = "TileView";

	private static final int TILESIZE = 20;
	
    /**
     * mXTileCount number of tiles in x-dimension
     * mYTileCount number of tiles in y-dimension
     * Their initial values are predefined in FIXED_GRID mode (here below)
     * Their initial values are computed in VARIABLE_GRID mode on the basis of mTileSize
     */
    protected int mXTileCount = 5; /* the number of tiles in X-dimension in FIXED_GRID - mode; will be overridden in VARIABLE_GRID mode */
    protected int mYTileCount = 5;

    /**
     * mTileSize size of the tile
     * The initial value is fixed VARIABLE_GRID mode
     * The value is computed in FIXED_GRID mode, the fill up the screen as much as possible
     */

    protected int mTileSize = TILESIZE;

    /**
     * There is a border around the tile grid
     * mXOffset x-offset in pixels: x-offset of first tile
     * mYOffset y-offset in pixels: y-offset of first tile
     */
    protected int mXOffset;
    protected int mYOffset;


    /**
     * A hash that maps integer handles specified by the subclasser to the
     * drawable that will be used for that reference
     */
    private Bitmap[] mTileArray; 

    /**
     * A two-dimensional array of integers in which the number represents the
     * index of the tile in the mTileArray that should be drawn at that location
     */
    private int[][] mTileGrid;

    private final Paint mPaint = new Paint();


    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTileSize = TILESIZE;
        mTileGrid = new int[mXTileCount][mYTileCount];
        clearTiles();
        Log.d( TAG, "initTileView( " + mTileSize + " )" );
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTileSize = TILESIZE;
        mTileGrid = new int[mXTileCount][mYTileCount];
        clearTiles();
        Log.d( TAG, "initTileView( " + mTileSize + " )" );
  }
    

    /**
     * Rests the internal array of Bitmaps used for drawing tiles, and
     * sets the maximum index of tiles to be inserted
     * 
     * @param tilecount the maximum number of tiles to be drawn
     */
    
    public void resetTiles(int tilecount) {
    	mTileArray = new Bitmap[tilecount];
    }
  
    /**
     * Function to set the specified Drawable as the tile for a particular
     * integer key.
     * 
     * @param key
     * @param tile
     */
    public void loadTile(int key, Drawable tile) {
        Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, mTileSize, mTileSize);
        tile.draw(canvas);
        
        mTileArray[key] = bitmap;
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
		int x = (int) ((event.getX() - mXOffset) / mTileSize);
		int y = (int) ((event.getY() - mYOffset) / mTileSize);
		if (x < mXTileCount && y < mYTileCount
				&& (event.getX() - mXOffset) >= 0
				&& (event.getY() - mYOffset) >= 0) {  /* Game Board touched */
			Log.d(TAG, "Touched (" + x +", " + y + ")\n"  );
			touched(x, y);
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * method to handle tile touch.
	 * @param x the x-coordinate of the tile touched
	 * @param y the y-coordinate of the tile touched
	 */
	public abstract void touched(int x, int y);
    

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	
    	if ( w!=0 || h != 0){
    	
    	mTileSize = Math.min((int) Math.floor(w / mXTileCount), (int) Math.floor(h / mYTileCount));
    	Log.d( TAG, "onSizeChanged( " + mTileSize + " )" );
		mXOffset = ((w - (mTileSize * mXTileCount)) / 2);
		mYOffset = ((h - (mTileSize * mYTileCount)) / 2);

		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();
    	}
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < mXTileCount; x++) {
            for (int y = 0; y < mYTileCount; y++) {
                if (mTileGrid[x][y] > 0) {
                	Log.d(TAG, "Draw tile (" + x + ", " + y +") plaatje:" + mTileGrid[x][y] +"  \n");
                    canvas.drawBitmap(mTileArray[mTileGrid[x][y]], 
                    		mXOffset + x * mTileSize,
                    		mYOffset + y * mTileSize,
                    		mPaint);
                }
            }
        }

    }

}

