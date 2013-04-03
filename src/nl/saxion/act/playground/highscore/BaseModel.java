
package nl.saxion.act.playground.highscore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import nl.saxion.act.playground.highscore.ScoreDatabase;
/**
 * Een basismodel voor interactie met een database,
 * bevat enkele functies, voor standaardinteractie met databases.
 *
 */
public class BaseModel implements BaseColumns {
    protected final String TABLE_NAME;

    protected final Context context;

    protected final SQLiteDatabase database;

    public BaseModel(final Context context, final String table) {
        this.context = context;
        TABLE_NAME = table;
        database = new ScoreDatabase(context).getWritableDatabase();
    }
    /**
     * vraagt alle rijen uit een tabel op.
     * @return Cursor
     */
    public Cursor getAll() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }
    /**
     * verwijdert een rij op basis van een id. 
     * @param id
     */
    public void remove(final int id) {
        database.delete(TABLE_NAME, BaseColumns._ID + " = " + id, null);
    }
    /**
     * vraagt 1 rij uit een tabe op, op basis van een id.
     * @param id
     * @return
     */
    public Cursor get(final int id) {
        return database.query(TABLE_NAME, null, BaseColumns._ID + " = " + id, null, null, null,
                null);
    }

    public void close() {
        database.close();
    }
}
