package com.longkd.android.core.demosqlite.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.longkd.android.core.demosqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME="ChiTieu.db";
    private final static int DATABASE_VERSION=1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table items(" +
                "id integer primary key autoincrement," +
                "title text, category text, price text, date text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Item> getItems(){
        List<Item> list=new ArrayList<>();
        SQLiteDatabase st=getReadableDatabase();
        String order = "date DESC";

        Cursor rs=st.query("items",null, null, null, null, null, order);
        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String p = rs.getString(3);
            String date = rs.getString(4);

            list.add(new Item(id, title, c, p, date));
        }
        return list;
    }

    public long insertItem(Item t){
        ContentValues values=new ContentValues();
        values.put("title",t.getTitle());
        values.put("category",t.getCategory());
        values.put("price",t.getPrice());
        values.put("date",t.getDate());
        SQLiteDatabase st=getWritableDatabase();
        return st.insert("items",null,values);
    }

    public List<Item> getFromDateToDateAndName(String name){
        List<Item> list=new ArrayList<>();
        String query = "SELECT * FROM items WHERE title LIKE '%' || ? || '%'";
        String[] selectionArgs = { name};
        SQLiteDatabase st=getReadableDatabase();

        Cursor rs = st.rawQuery(query, selectionArgs);

        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String p = rs.getString(3);
            String d = rs.getString(4);
            list.add(new Item(id, title, c, p, d));
        }
        return list;
    }
    public List<Item> getByDate(String date){
        List<Item> list=new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st=getReadableDatabase();

        Cursor rs = st.query("items",null, whereClause, whereArgs, null, null, null);

        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String p = rs.getString(3);

            list.add(new Item(id, title, c, p, date));
        }
        return list;
    }

    public int updateItem(Item t){
        ContentValues values=new ContentValues();
        values.put("title",t.getTitle());
        values.put("category",t.getCategory());
        values.put("price",t.getPrice());
        values.put("date",t.getDate());
        String where="id=?";
        String[] agrs={Integer.toString(t.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("items",values,where,agrs);
    }

    public int deleteItem(int id){
        String where="id=?";
        String[] agrs={Integer.toString(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("items",where,agrs);
    }

//    public List<Item> searchItemBykey(String key){
//        List<Item> list=new ArrayList<>();
//        String sql="select t.id,t.name,t.price,t.date,c.id,c.name " +
//                "from categories c inner join items t " +
//                "on (c.id=t.cid) where t.name like ? or c.name like ?";
//        String[] agrs={"%"+key+"%","%"+key+"%"};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.rawQuery(sql,agrs);
//        while(rs!=null && rs.moveToNext()){
//            Category c=new Category(rs.getInt(4),rs.getString(5));
//            list.add(new Item(rs.getInt(0),rs.getString(1),rs.getDouble(2),
//                    rs.getString(3),c));
//        }
//        rs.close();
//        return list;
//    }
//    public List<Item> getItemByfromPricetoPrice(double from,double to){
//        List<Item> list=new ArrayList<>();
//        String where="price between ? and ?";
//        String[] agrs={Double.toString(from),Double.toString(to)};
//        String orderby="price desc";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,where,agrs,null,
//                null,orderby);
//        while(rs!=null && rs.moveToNext()){
//            list.add( new Item(rs.getInt(0),rs.getString(1),rs.getDouble(3),
//                    rs.getString(4),new Category(rs.getInt(2),"")));
//
//        }
//        return list;
//    }
//    public List<Item> searchByKey(String key){
//        List<Item> list=new ArrayList<>();
//        String where="name like ? or date like ?";
//        String[] agrs={"%"+key+"%","%"+key+"%"};
//        String orderby="date desc";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs=st.query("items",null,where,agrs,null,
//                null,orderby);
//        while(rs!=null && rs.moveToNext()){
//            list.add( new Item(rs.getInt(0),rs.getString(1),rs.getDouble(3),
//                    rs.getString(4),new Category(rs.getInt(2),"")));
//
//        }
//        return list;
//    }

}
