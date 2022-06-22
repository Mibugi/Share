package com.mibugi.share;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 用户服务类
 */
public class Userservice {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    public Userservice(Context context){
        mySQLiteOpenHelper=new MySQLiteOpenHelper(context);
    }

    //验证数据库是否存在相同的用户名
    public boolean login1(String username){
        SQLiteDatabase sdb=mySQLiteOpenHelper.getReadableDatabase();
        String sql="select * from user where username=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    //验证登录
    public boolean login(String username,String password){
        SQLiteDatabase sdb=mySQLiteOpenHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    //注册
    public boolean register(User user, byte [] touxiangByte){
        SQLiteDatabase sdb=mySQLiteOpenHelper.getReadableDatabase();
        String sql="insert into user(username,password,touxiang) values(?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),touxiangByte};
        sdb.execSQL(sql, obj);
        return true;
    }
    //获取数据库头像的图片
    public Bitmap getBmp(String username){
        SQLiteDatabase db=mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from user where username=?",new String[]{username});
        while(cursor.moveToFirst()){
            byte[] photoByte=cursor.getBlob(cursor.getColumnIndex("touxiang"));
            Bitmap bitmap= BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
            return bitmap;
        }
        cursor.close();
        db.close();
        return null;
    }

    //修改密码
    public boolean changepas(User user){
        SQLiteDatabase sdb=mySQLiteOpenHelper.getReadableDatabase();
        String sql="update user set password=? where username=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{user.getPassword(),user.getUsername()});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    //修改头像
    public boolean changetouxiang(String username,byte [] touxiangByte){
        SQLiteDatabase sdb=mySQLiteOpenHelper.getReadableDatabase();
        String sql="update user set touxiang=? where username=?";
        Object obj[]={touxiangByte,username};
        sdb.execSQL(sql, obj);
        return true;
    }
}
