package pt.atp.recyclerviewactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;

public final class MyDBAdapter {
    private static final String DATABASE_NAME = "myContacts.db";
    private static final String DATABASE_TABLE = "Contacts";
    private static final int DATABASE_VERSION = 1; //Se alterarmos a estrutra da BD temos de incrementar a versão

    //Guardar a instância
    private SQLiteDatabase db;
    private final Context context;

    public static final String KEY_ID = "id_contact";
    public static final int KEY_ID_COLUMN = 0;

    //Coluna name (index 1)
    public static final String KEY_NAME = "name";
    public static final int NAME_COLUMN = 1;

    //Coluna phone_number (index 2)
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final int PHONE_NUMBER_COLUMN = 2;

    //instância de MyDBOpenHelper
    private MyDBOpenHelper dbHelper;

    public MyDBAdapter(Context context){
        this.context = context;
        dbHelper = new MyDBOpenHelper(context,DATABASE_NAME, null,DATABASE_VERSION);

    }

    public void open() throws SQLiteException{
        try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLiteException ex){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        db.close();
    }

    //Inserir
    public long insertContact(Contact contact){
        //Criar uma nova linha de valores
        ContentValues contactValues = new ContentValues();
        contactValues.put(KEY_NAME,contact.getName());
        contactValues.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

        return db.insert(DATABASE_TABLE,null,contactValues);
    }

    //Eliminar
    public boolean removeContact(long position){
        return  db.delete(DATABASE_TABLE, KEY_ID + " = " + position,null) > 0 ;
    }

    //Atualizar
    public boolean updateContact(long position, Contact contact){
        ContentValues contactValues = new ContentValues();
        contactValues.put(KEY_NAME,contact.getName());
        contactValues.put(KEY_PHONE_NUMBER, contact.getPhoneNumber());

        return db.update(DATABASE_TABLE,contactValues,KEY_ID +"="+position,null) > 0;

    }

    //Obter um cursor para todos os dados
    public Cursor getAllContacts(){
        return db.query(DATABASE_TABLE, new String[]{KEY_ID,KEY_NAME,KEY_PHONE_NUMBER},null,
                null,null,null,null);
    }

    public Contact getContact(long position){
        Cursor cursor = db.query(true,DATABASE_TABLE, new String[]{KEY_ID,KEY_NAME,KEY_PHONE_NUMBER},
                KEY_ID + "= " + position, null, null, null, null,null);

        if(cursor.getCount()==0 || !cursor.moveToFirst())
            throw new SQLiteException("Registo não encontrado");

        long id =cursor.getLong(KEY_ID_COLUMN);
        String name = cursor.getString(NAME_COLUMN);
        String phone = cursor.getString(PHONE_NUMBER_COLUMN);

        return new Contact(id,name,phone);
    }

    //Criar uma classe que extende de SQLiteOpenHelper
    private static class MyDBOpenHelper extends SQLiteOpenHelper{

        public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //SQL para criar a Base de Dados
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
                " ( " + KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME + " text not null," +
                KEY_PHONE_NUMBER + " text not null);";

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.w("TaskDBAdapter","Upgrading from version " +
                    i + " to " +
                    i1 + ", which will destroy all old data");

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        }
    }

}
