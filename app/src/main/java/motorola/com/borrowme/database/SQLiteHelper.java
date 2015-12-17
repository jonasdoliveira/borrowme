package motorola.com.borrowme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import motorola.com.borrowme.database.dao.CollectionDAO;
import motorola.com.borrowme.database.dao.ItemsDAO;

/**
 * Created by Caio on 15/12/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Borrow.db";

    private static SQLiteHelper instance;
    static private Context c;

    public static SQLiteHelper getInstance(Context context) {
        if(instance == null){
            instance = new SQLiteHelper(context);
            c = context;
        }

        return instance;
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CollectionDAO.TB_COLLECTION_CREATE);
            db.execSQL(ItemsDAO.TB_ITEMS_CREATE);
        }
        catch (SQLiteException e){
            
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
