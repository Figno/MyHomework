package cn.liguanrui.myhomework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;

public class DBManager {
    Context context;
    DBHelper dbHelper;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context,"players.db",null,1);
    }
    //增加一条数据
    public void insert(String name ,String password,String grade){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "insert into players (name,password,grade)values(?,?,?)";
        sqLiteDatabase.execSQL(sql,new String[]{name,password,grade});

    }

    //查询数据 用来验证用户名和密码
    public Cursor query(String name, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from players where name = ? and password = ?";
        return sqLiteDatabase.rawQuery(sql,new String[]{name,password});
    }


    //用于检测新注册的用户是否被注册
    public Cursor requery(String name) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from players where name = ?";
        return sqLiteDatabase.rawQuery(sql,new String[]{name});
    }
    //更新一条数据
    public void Update(String name,String newgrade){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("grade",newgrade);
        sqLiteDatabase.update("players",values,"name = ?",new String []{name});
    }
    //删除一条记录
    public void delete(String name){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        String sql = "delete from players where name = ?";
        sqLiteDatabase.delete("players","name = ?",new String[]{name});
    }


    public String[] allSql(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from players";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        String [] data =new String[]{};
        while (cursor.moveToNext()){
            data = Arrays.copyOf(data,data.length+1);
            data [data.length-1] = cursor.getString(1) +"                                                                        "+ cursor.getString(3);
        }
        return data;

    }
}