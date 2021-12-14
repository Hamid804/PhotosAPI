package com.unitech.photosapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="myDB.db";
    public static final String TABLE_PHOTO="photoTable";
    public static final String COL_ALBUM_ID="albumId";
    public static final String COL_PHOTO_ID="id";
    public static final String COL_TITLE="title";
    public static final String COL_URL="url";
    public static final String COL_THUMBNAIL_URL="thumbnailUrl";
    public static final String COL_FAVORITE="favorite";

    public DBHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_PHOTO+"(albumId Integer,id Integer,title text,url text,thumbnailUrl text,favorite text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PHOTO);
        onCreate(db);
    }

    public Cursor getData(int start,int end){
        //for getting 8 rows
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select * from "+TABLE_PHOTO+" limit "+start+","+end,null);
    }

    public void insertData(Integer albumId,Integer id,String title,String url,String thumbnailUrl,String favorite){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("albumId",albumId);
        cv.put("id",id);
        cv.put("title",title);
        cv.put("url",url);
        cv.put("thumbnailUrl",thumbnailUrl);
        cv.put("favorite",favorite);
        db.insert(TABLE_PHOTO,null,cv);
        db.close();
    }

    public void deleteData(int imgId){
        SQLiteDatabase db=this.getWritableDatabase();
        //Cursor rs=db.rawQuery("delete from "+TABLE_PHOTO+" where id="+imgId,null);
        db.delete(TABLE_PHOTO,"id=?",new String[]{Integer.toString(imgId)});
        db.close();
    }

    public Cursor getOneRecord(int imgId){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select * from "+TABLE_PHOTO+" where id="+imgId,null);
    }

    public Cursor getOneRecordNext(int startId){
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("select * from "+TABLE_PHOTO+" limit "+startId+","+1,null);
    }

    public Cursor getOneRecordPrevious(int startId){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_PHOTO,null);
        int count=cursor.getCount();
        Log.d("Total",String.valueOf(count));
        cursor.close();

        int idReverse=count-startId+1;
        Log.d("IdReverse",String.valueOf(idReverse));

        return db.rawQuery("select * from "+TABLE_PHOTO+" order by id DESC limit "+idReverse+","+1,null);
    }

    public void updateFav(int imgId,String choice){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("favorite",choice);
        db.update(TABLE_PHOTO,cv,"id=?",new String[]{Integer.toString(imgId)});
        db.close();
    }
}
