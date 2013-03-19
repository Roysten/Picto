package nl.saxion.act.playground;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
	protected int[][] rowHints, columnHints;
	protected boolean[] rowDone = new boolean[15], columnDone = new boolean[15];

	/**
	 * Dimensie van het spelbord
	 */
	protected int mDimension;

	/**
	 * Grootte van een vakje wordt hierin opgeslagen. Wordt geïnitialiseerd op
	 * nul, daarna gelijk aangepast door onSizeChanged()
	 */

	private float mTileSize = 0;

	/**
	 * De array waarin alle vakjes en hun invulling zijn opgeslagen.
	 */
	protected int[][] gameBoard;

	/**
	 * Aantal lijnen dat niet getekent moeten worden (zorgt voor Picross
	 * uiterlijk)
	 */
	private static final int SPACING = 3;

	/**
	 * Initialiseren van de verschillende soorten paint
	 */
	private final Paint linePaint = new Paint();
	private final Paint rectPaint = new Paint();
	private final Paint crossPaint = new Paint();
	private final Paint textPaint = new Paint();
	private final Paint textDonePaint = new Paint();

	/**
	 * De kleuren van de paints
	 */
	private final int lineColor = Color.BLACK;
	private final int crossColor = Color.RED;
	private final int textColor = Color.BLACK;
	private final int textDoneColor = Color.LTGRAY;
	private final int rectVakjeColor = Color.LTGRAY;

	/**
	 * De dikte van de dikkere lijn in px die om een x aantal vakjes getekend
	 * wordt Wordt berekent uit een een dp waarde van 2
	 */
	private float thickLineWidth;

	/**
	 * De hoogte van de tekst die wordt gebruikt als ijkpunt om de tekst te
	 * schalen op verschillende resolutie's.
	 */
	private float textHeight, textWidth;

	/**
	 * Het vakje dat wordt gebruikt als "stempel" om alle vakjes te kleuren.
	 */
	private RectF rectVakje = new RectF();

	public TileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public TileView(Context context){
		super(context);
		init();
	}
	
	public void init(){
		initPaint();

		// Bereken de dikte van de lijnen, "dikte van 2" omzetten naar dp
		Resources r = getResources();
		thickLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				2, r.getDisplayMetrics());
	}

	public void initPaint() {
		linePaint.setColor(lineColor);
		rectPaint.setColor(rectVakjeColor);
		crossPaint.setColor(crossColor);
		textPaint.setColor(textColor);
		textDonePaint.setColor(textDoneColor);
		// textPaint.setTextAlign(Align.CENTER);
		crossPaint.setAntiAlias(true);
	}

	/**
	 * Resets all tiles to 0 (empty)
	 * 
	 */
	public void clearTiles() {
		for (int x = 0; x < mDimension; x++) {
			for (int y = 0; y < mDimension; y++) {
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
		gameBoard[x][y] = tileindex;
	}

	/**
	 * Berekent x en y van het vakje dat is aangeraakt uit de event x en y
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) (event.getX() / mTileSize) - SPACING;
		int y = (int) (event.getY() / mTileSize) - SPACING;
		Log.d(TAG, "Touched (" + x + ", " + y + ")");
		if (x >= 0 && x < mDimension && y >= 0 && y < mDimension) { /*
																	 * Game
																	 * Board
																	 * touched
																	 */
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

	/**
	 * Wanneer de grootte van het veld veranderd berekenen we de tekstgrootte
	 * opnieuw, we schalen ook de grootte van de vakjes
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mTileSize = Math.min((float) w / (mDimension + SPACING), (float) h
				/ (mDimension + SPACING));
		Log.d(TAG, "onSizeChanged( " + mTileSize + " )");

		// Bereken gewenste tekstgrootte
		textPaint.setTextSize(100);
		Rect bounds = new Rect();

		textPaint.getTextBounds("2", 0, 1, bounds);

		// Waarom hoogte? Die is voor bijna elke letter gelijk
		textHeight = bounds.bottom - bounds.top;

		float target = mTileSize / 3f;

		float textSize = (target / textHeight) * 100f;
		textPaint.setTextSize(textSize);
		textDonePaint.setTextSize(textSize);

		// Voor precieze plaatsing tekst hoogte bepalen noodzakelijk
		textPaint.getTextBounds("2", 0, 1, bounds);
		textHeight = bounds.bottom - bounds.top;
		textWidth = bounds.right - bounds.left;

		// Zet de vakjes naar de correcte grootte
		rectVakje.set(0, 0, mTileSize, mTileSize);
	}

	/**
	 * Tekent de vakjes op de goede plaats
	 * 
	 * @param canvas
	 */
	public void drawRects(Canvas canvas) {
		if(mDimension != 0 && gameBoard.length != 0){
		for (int i = 0; i < mDimension; i++) {
			for (int j = 0; j < mDimension; j++) {
					if (gameBoard[i][j] == 1) {
						rectVakje.offsetTo((i + SPACING) * mTileSize,
								(j + SPACING) * mTileSize);
						canvas.drawRect(rectVakje, rectPaint);
					}
					if (gameBoard[i][j] == 2) {
						canvas.drawLine((i + SPACING) * mTileSize,
								(j + SPACING) * mTileSize, (i + SPACING + 1)
										* mTileSize, (j + SPACING + 1)
										* mTileSize, crossPaint);
						canvas.drawLine((i + SPACING + 1) * mTileSize,
								(j + SPACING) * mTileSize, (i + SPACING)
										* mTileSize, (j + SPACING + 1)
										* mTileSize, crossPaint);
					}
				}
			}
		}
	}

	/**
	 * Teken het grid. Om de 5 vakjes komt een dikkere lijn
	 * 
	 * @param canvas
	 */
	public void drawGrid(Canvas canvas) {
		for (int i = SPACING; i < mDimension + SPACING; i++) {
			if ((i - SPACING) % 5 == 0) {
				linePaint.setStrokeWidth(thickLineWidth);
				canvas.drawLine(i * mTileSize, 0, i * mTileSize,
						canvas.getHeight(), linePaint);
				canvas.drawLine(0, i * mTileSize, canvas.getWidth(), i
						* mTileSize, linePaint);
				linePaint.setStrokeWidth(0f);
			} else {
				canvas.drawLine(i * mTileSize, 0, i * mTileSize,
						canvas.getHeight(), linePaint);
				canvas.drawLine(0, i * mTileSize, canvas.getWidth(), i
						* mTileSize, linePaint);
			}
		}
	}

	/**
	 * Tekent de tekst in de goede vakjes adhv 2 arrays met de waardes.
	 * 
	 * @param canvas
	 *            Canvas om op te tekenen
	 */
	public void drawText(Canvas canvas) {
		/*
		 * Tekst wordt als volgt getekend: x = de helft van een vakje (zodat het
		 * niet te dicht op de rand staat) y = i + spacing(om eerste rijen over
		 * te slaan) + halve teksthoogte (voor netjes uitlijnen)
		 */
		if (!isInEditMode()) { // als we in de editor zijn, nummers niet
								// tekenen, want die zijn dan null
			Paint paintToUse;
			for (int i = 0; i < mDimension; i++) {
				String rowText = "";
				if (rowHints[i][0] == 0) {
					rowText = "0";
					paintToUse = textDonePaint;
				} else {
					for (int j = 0; j < rowHints[i].length; j++) {
						if (rowHints[i][j] != 0) {
							rowText += rowHints[i][j] + "  ";
						}
					}
					paintToUse = rowDone[i] ? textDonePaint : textPaint;
				}

				canvas.drawText(rowText, .5f * mTileSize, (i + SPACING + 0.5f)
						* mTileSize + textHeight / 2, paintToUse);
			}

			/*
			 * Tekst wordt als volgt getekend: x = i + spacing(om eerste
			 * kolommen over te slaan) + helft van vakje (om te centreren) y =
			 * (j + 1) (om binnen het canvas te tekenen) * anderhalve
			 * teksthoogte (om niet over elkaar te tekenen en halve teksthoogte
			 * ruimte ertussen te houden)
			 */
			for (int i = 0; i < mDimension; i++) {
				if (columnHints[i][0] == 0) {
					paintToUse = textDonePaint;
					canvas.drawText(columnHints[i][0] + "", (i + SPACING + 0.5f) * mTileSize - (textWidth / 2), (0 + 1) * (textHeight * 1.5f), paintToUse);
				} else {
					for (int j = 0; j < rowHints[i].length; j++) {
						if (columnHints[i][j] != 0) {
							paintToUse = columnDone[i] ? textDonePaint : textPaint;
							canvas.drawText(columnHints[i][j] + "", (i + SPACING + 0.5f) * mTileSize - (textWidth / 2), (j + 1)	* (textHeight * 1.5f), paintToUse);
						}
					}
				}
			}
		}

	}

	@Override
	public void onDraw(Canvas canvas) {
		long start = System.currentTimeMillis();
		drawRects(canvas);
		drawGrid(canvas);
		drawText(canvas);
		long end = System.currentTimeMillis();
		Log.d(TAG, "drawing took: " + (end - start));
	}
}
