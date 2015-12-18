package motorola.com.borrowme.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import motorola.com.borrowme.database.SQLiteHelper;
import motorola.com.borrowme.database.entities.ItemEntity;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class ItemsDAO {

    public static final String TABLE_NAME = "items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COLLECTION_ID = "collection_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_PERSON_ID = "person_id";

    public static final String TB_ITEMS_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_COLLECTION_ID + " INTEGER, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_CODE + " TEXT UNIQUE, " +
                    COLUMN_PERSON_ID + " INTEGER);";

    public static final String TB_ITEMS_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase dataBase = null;
    private static ItemsDAO instance;

    public static ItemsDAO getInstance(Context context) {
        if (instance == null)
            instance = new ItemsDAO(context);
        return instance;
    }

    private ItemsDAO(Context context) {
        dataBase = SQLiteHelper.getInstance(context).getWritableDatabase();
    }

    public long insert(ItemEntity item) {
        ContentValues values = generateItemValues(item);
        return dataBase.insertOrThrow(TABLE_NAME, null, values);
    }

    public List<ItemEntity> selectAll() {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<ItemEntity> itemsList = generateItemsByCursor(cursor);

        return itemsList;
    }

    public ItemEntity selectById(long id) {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_ID + " = ? ";

        String[] params = {String.valueOf(id)};

        Cursor cursor = dataBase.rawQuery(queryReturnAll, params);
        ItemEntity itemEntity = generateItemsEntityByCursor(cursor);

        return itemEntity;
    }

    public List<ItemEntity> selectByCollection (long id) {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_COLLECTION_ID + " =  ? ";

        String[] params = {String.valueOf(id)};

        Cursor cursor = dataBase.rawQuery(queryReturnAll, params);

        return generateItemsByCursor(cursor);
    }

    public int delete(ItemEntity item) {

        String[] valuesForDelete = {
                String.valueOf(item.get_id())
        };

        int r = dataBase.delete(TABLE_NAME, COLUMN_ID + " =  ?", valuesForDelete);

        //1 for success (one or ore rows deleted)
        if (r >= 1) {
            return 1;
        } else {
            return -1;
        }
    }

    public int update(ItemEntity item) {
        ContentValues values = generateItemValues(item);

        String[] valuesForUpdate = {
                String.valueOf(item.get_id())
        };

        return dataBase.update(TABLE_NAME, values, COLUMN_ID + " = ?", valuesForUpdate);
    }

    private ItemEntity generateItemsEntityByCursor(Cursor cursor) {
        ItemEntity itemEntity = null;

        if (cursor == null)
            return itemEntity;

        try {

            if (cursor.moveToFirst()) {

                int indexID = cursor.getColumnIndex(COLUMN_ID);
                int indexCollectionID = cursor.getColumnIndex(COLUMN_COLLECTION_ID);
                int indexItemName = cursor.getColumnIndex(COLUMN_NAME);
                int indexItemDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int indexItemCode = cursor.getColumnIndex(COLUMN_CODE);
                int indexPersonID = cursor.getColumnIndex(COLUMN_PERSON_ID);

                long id = cursor.getLong(indexID);
                long collectionId = cursor.getLong(indexCollectionID);
                String name = cursor.getString(indexItemName);
                String description = cursor.getString(indexItemDescription);
                String code = cursor.getString(indexItemCode);
                long personID = cursor.getLong(indexPersonID);

                itemEntity = new ItemEntity(id, collectionId, name, description, code, personID);
            }

        } finally {
            cursor.close();
        }
        return itemEntity;
    }

    private List<ItemEntity> generateItemsByCursor(Cursor cursor) {
        List<ItemEntity> itemEntityList = null;

        if (cursor == null)
            return itemEntityList;

        itemEntityList = new ArrayList<ItemEntity>();

        try {

            if (cursor.moveToFirst()) {

                ItemEntity itemEntity;

                do {

                    int indexID = cursor.getColumnIndex(COLUMN_ID);
                    int indexCollectionID = cursor.getColumnIndex(COLUMN_COLLECTION_ID);
                    int indexItemName = cursor.getColumnIndex(COLUMN_NAME);
                    int indexItemDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                    int indexItemCode = cursor.getColumnIndex(COLUMN_CODE);
                    int indexPersonID = cursor.getColumnIndex(COLUMN_PERSON_ID);

                    long id = cursor.getLong(indexID);
                    long collectionId = cursor.getLong(indexCollectionID);
                    String name = cursor.getString(indexItemName);
                    String description = cursor.getString(indexItemDescription);
                    String code = cursor.getString(indexItemCode);
                    long personID = cursor.getLong(indexPersonID);

                    itemEntity = new ItemEntity(id, collectionId, name, description, code, personID);
                    itemEntityList.add(itemEntity);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return itemEntityList;
    }

    public void closeConnection() {
        if (dataBase != null && dataBase.isOpen())
            dataBase.close();
    }

    private ContentValues generateItemValues(ItemEntity item) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_COLLECTION_ID, item.getCollectionId());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_CODE, item.getCode());

        return values;
    }

}