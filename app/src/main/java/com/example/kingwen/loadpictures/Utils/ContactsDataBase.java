package com.example.kingwen.loadpictures.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kingwen.loadpictures.Beans.Contacts;

/**
 * Created by kingwen on 2016/3/24.
 */
public class ContactsDataBase extends SQLiteOpenHelper{

    //数据库的名字
    private final static String DATABASE_NAME="ContactsDataBase";
    //数据库的版本号
    private  final static int DATABASE_VERSION=1;
    //数据库表的名字
    private final static String TABLE_NAME="contacts_table";
    //表中数据一列的名字id
    private final static String CONTACTS_ID="id_contacts";
    //表中联系人的名字
    private final static String CONTACTS_NAME="name_contacts";
    //表中联系人的电话号码
    private final static String CONTACTS_NUMBER="number_contacts";
    //表中联系人的照片的地址
    private final static String CONTACTS_PHOTOURL="photoUrl_contact";

    private Context mContext;


    //建表语句
    public static final String CREAT_ARTICAL
            = "CREATE TABLE " +TABLE_NAME
            +" ("
            +CONTACTS_ID +" INTEGER primary key autoincrement, "
            +CONTACTS_NAME+ " text, "
            +CONTACTS_NUMBER+ " text, "
            +CONTACTS_PHOTOURL+ " text "
            + " )";

    public ContactsDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    /**
     * 数据库的构造方法
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAT_ARTICAL);
        ContentValues cv=new ContentValues();
        cv.put(CONTACTS_NAME,"kingwen");
        cv.put(CONTACTS_NUMBER,"12345678909");
        cv.put(CONTACTS_PHOTOURL, "hello");
        // 这里的insert要包含这个表单中的所有的列，如果我们的数据中不包含这个参数，null
        db.insert(TABLE_NAME, null, cv);

    }

    /**
     * 数据库的升级操作
     * @param db  数据库对象
     * @param oldVersion  旧版本
     * @param newVersion  新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      /*
      * 数据库的升级操作，等待郭霖的更改
      * */
        String sql="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    /**
     * 数据库的插入操作
     * @param mContacts 要插入的数据
     * @return 如果插入返回值为1，否则返回值为0
     */
    public long insert(Contacts mContacts){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(CONTACTS_NAME,mContacts.getContactsName());
        cv.put(CONTACTS_NUMBER,mContacts.getContactNumber());
        cv.put(CONTACTS_PHOTOURL,mContacts.getContactImg());
        // 这里的insert要包含这个表单中的所有的列，如果我们的数据中不包含这个参数，null
        long raw=db.insert(TABLE_NAME, null, cv);
        return  raw;
    }

    /**
     * 数据库的删除操作
     * @param id 要删除的数据id
     */
    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String where=CONTACTS_ID+"=?";
        String[] whereValue={Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);

        Log.e("ContactsDataBase","delete"+id);
    }

    /**
     * 得到初始位置的游标
     * @return 返回初始位置的游标
     */
    public Cursor select(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    public void updata(int id,Contacts mContacts){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(CONTACTS_NAME,mContacts.getContactsName());
        cv.put(CONTACTS_NUMBER,mContacts.getContactNumber());
        cv.put(CONTACTS_PHOTOURL, mContacts.getContactImg());
        int raw=db.update(TABLE_NAME, cv, "id_contacts=?", new String[]{String.valueOf(id)});

        Log.e("ContactsDatabase","updata成功"+raw);
}

}
