package motorola.com.borrowme.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import motorola.com.borrowme.database.SQLiteHelper;
import motorola.com.borrowme.database.entities.PersonEntity;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class PersonDAO {

    public static final String TABLE_NAME = "persons";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "telefone";
    public static final String COLUMN_EMAIL = "email";

    public static final String TB_PERSONS_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_EMAIL + " TEXT);";

    public static final String TB_PERSONS_DELETE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private SQLiteDatabase dataBase = null;
    private static PersonDAO instance;

    public static PersonDAO getInstance(Context context) {
        if (instance == null)
            instance = new PersonDAO(context);
        return instance;
    }

    private PersonDAO(Context context) {
        dataBase = SQLiteHelper.getInstance(context).getWritableDatabase();
    }

    public long insert(PersonEntity person) {
        ContentValues values = generatePersonValues(person);
        return dataBase.insertOrThrow(TABLE_NAME, null, values);
    }

    public List<PersonEntity> selectAll() {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<PersonEntity> personsList = generatePersonsByCursor(cursor);

        return personsList;
    }

    public PersonEntity selectById(long id) {
        String queryReturnAll = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_ID + " = " + id;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        PersonEntity personEntity = generatePersonsEntityByCursor(cursor);

        return personEntity;
    }

    public int delete(PersonEntity person) {

        String[] valuesForDelete = {
                String.valueOf(person.get_id())
        };

        int r = dataBase.delete(TABLE_NAME, COLUMN_ID + " =  ?", valuesForDelete);

        //1 for success (one or ore rows deleted)
        if (r >= 1) {
            return 1;
        } else {
            return -1;
        }
    }

    public int update(PersonEntity person) {
        ContentValues values = generatePersonValues(person);

        String[] valuesForUpdate = {
                String.valueOf(person.get_id())
        };

        return dataBase.update(TABLE_NAME, values, COLUMN_ID + " = ?", valuesForUpdate);
    }

    private PersonEntity generatePersonsEntityByCursor(Cursor cursor) {
        PersonEntity PersonEntity = null;

        if (cursor == null)
            return PersonEntity;

        try {

            if (cursor.moveToFirst()) {

                int indexID = cursor.getColumnIndex(COLUMN_ID);
                int indexPersonName = cursor.getColumnIndex(COLUMN_NAME);
                int indexPersonPhone = cursor.getColumnIndex(COLUMN_PHONE);
                int indexPersonEmail = cursor.getColumnIndex(COLUMN_EMAIL);

                long id = cursor.getLong(indexID);
                String name = cursor.getString(indexPersonName);
                String phone = cursor.getString(indexPersonPhone);
                String email = cursor.getString(indexPersonEmail);

                PersonEntity = new PersonEntity(id, name, phone, email);
            }

        } finally {
            cursor.close();
        }
        return PersonEntity;
    }

    private List<PersonEntity> generatePersonsByCursor(Cursor cursor) {
        List<PersonEntity> personEntityList = null;

        if (cursor == null)
            return personEntityList;

        personEntityList = new ArrayList<PersonEntity>();

        try {

            if (cursor.moveToFirst()) {

                PersonEntity personEntity;

                do {

                    int indexID = cursor.getColumnIndex(COLUMN_ID);
                    int indexPersonName = cursor.getColumnIndex(COLUMN_NAME);
                    int indexPersonPhone = cursor.getColumnIndex(COLUMN_PHONE);
                    int indexPersonEmail = cursor.getColumnIndex(COLUMN_EMAIL);

                    long id = cursor.getLong(indexID);
                    String name = cursor.getString(indexPersonName);
                    String phone = cursor.getString(indexPersonPhone);
                    String email = cursor.getString(indexPersonEmail);

                    personEntity = new PersonEntity(id, name, phone, email);

                    personEntityList.add(personEntity);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return personEntityList;
    }

    public void closeConnection() {
        if (dataBase != null && dataBase.isOpen())
            dataBase.close();
    }

    private ContentValues generatePersonValues(PersonEntity person) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_PHONE, person.getPhone());
        values.put(COLUMN_EMAIL, person.getEmail());

        return values;
    }

}