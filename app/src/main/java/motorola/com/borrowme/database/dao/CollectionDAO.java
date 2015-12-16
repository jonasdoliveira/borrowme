package motorola.com.borrowme.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import motorola.com.borrowme.database.SQLiteHelper;
import motorola.com.borrowme.database.entities.CollectionEntity;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class CollectionDAO {

    public static final String TABLE_NAME = "collections";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public static final String TB_COLLECTION_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL UNIQUE);";

    public static final String TB_COLLECTION_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase dataBase = null;
    private static CollectionDAO instance;

    public static CollectionDAO getInstance(Context context) {
        if (instance == null)
            instance = new CollectionDAO(context);
        return instance;
    }

    private CollectionDAO(Context context) {
        dataBase = SQLiteHelper.getInstance(context).getWritableDatabase();
    }

    public long insert(CollectionEntity collection) {
        ContentValues values = generateCollectionsValues(collection);
        return dataBase.insertOrThrow(TABLE_NAME, null, values);
    }

    public List<CollectionEntity> selectAll() {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<CollectionEntity> collectionsList = generateCollectionsByCursor(cursor);

        return collectionsList;
    }

    public CollectionEntity selectById(long id) {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_ID + " = " + id;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        CollectionEntity CollectionEntity = generateCollectionEntityByCursor(cursor);

        return CollectionEntity;
    }

    public int delete(CollectionEntity collection) {

        String[] valuesForDelete = {
                String.valueOf(collection.get_id())
        };

        int r = dataBase.delete(TABLE_NAME, COLUMN_ID + " =  ?", valuesForDelete);

        //1 for success (one or ore rows deleted)
        if (r >= 1) {
            return 1;
        } else {
            return -1;
        }
    }

    public int update(CollectionEntity collection) {
        ContentValues values = generateCollectionsValues(collection);

        String[] valuesForUpdate = {
                String.valueOf(collection.get_id())
        };

        return dataBase.update(TABLE_NAME, values, COLUMN_ID + " = ?", valuesForUpdate);
    }

    private CollectionEntity generateCollectionEntityByCursor(Cursor cursor) {
        CollectionEntity CollectionEntity = null;

        if (cursor == null)
            return CollectionEntity;

        try {

            if (cursor.moveToFirst()) {

                int indexID = cursor.getColumnIndex(COLUMN_ID);
                int indexCollectionName = cursor.getColumnIndex(COLUMN_NAME);

                long id = cursor.getLong(indexID);
                String name = cursor.getString(indexCollectionName);

                CollectionEntity = new CollectionEntity(id, name);
            }

        } finally {
            cursor.close();
        }
        return CollectionEntity;
    }

    private List<CollectionEntity> generateCollectionsByCursor(Cursor cursor) {
        List<CollectionEntity> CollectionEntityList = null;

        if (cursor == null)
            return CollectionEntityList;

        CollectionEntityList = new ArrayList<CollectionEntity>();

        try {

            if (cursor.moveToFirst()) {

                CollectionEntity CollectionEntity;

                do {

                    int indexID = cursor.getColumnIndex(COLUMN_ID);
                    int indexCollectionName = cursor.getColumnIndex(COLUMN_NAME);

                    long id = cursor.getLong(indexID);
                    String name = cursor.getString(indexCollectionName);

                    CollectionEntity = new CollectionEntity(id, name);

                    CollectionEntityList.add(CollectionEntity);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return CollectionEntityList;
    }

    public void closeConnection() {
        if (dataBase != null && dataBase.isOpen())
            dataBase.close();
    }

    private ContentValues generateCollectionsValues(CollectionEntity collection) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, collection.getName());

        return values;
    }

}