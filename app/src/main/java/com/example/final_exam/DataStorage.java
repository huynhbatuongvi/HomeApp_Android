package com.example.final_exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataStorage extends SQLiteOpenHelper {
    private static final int VERSION = 1; // phiên bản của cơ sở dữ liệu.

    // room table
    private static  final  String ROOM_TABLE_NAME = "ROOM_TABLE"; //  tên bảng để lưu trữ thông tin về các phòng
    public   static final String C_ID = "_CID"; // ID của bảng
    public static final String ROOM_NAME_KEY = "CLASS_NAME";// chứa tên của phòng

    private  static final String CREATE_ROOM_TABLE = "CREATE TABLE " + ROOM_TABLE_NAME + "(" +
            C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ROOM_NAME_KEY
            + " TEXT NOT NULL, " + "UNIQUE (" + ROOM_NAME_KEY + ")" + ")"; //tạo bảng "ROOM_TABLE"

    private  static final String DROP_ROOM_TABLE = "DROP TABLE IF EXISTS " + ROOM_TABLE_NAME; // xóa bảng "ROOM_TABLE"
    private  static final String SELECT_ROOM_TABLE = "SELECT * FROM " + ROOM_TABLE_NAME;//  lấy tất cả dữ liệu từ bảng "ROOM_TABLE"

    // device table
    private static  final  String DEVICE_TABLE_NAME = "DEVICE_TABLE";// tên bảng để lưu trữ thông tin về các thiết bị
    public   static final String D_ID = "_DID";// ID của thiết bị
    public   static final String DEVICE_NAME_KEY = "DEVICE_NAME";//chứa tên của thiết bị
    private  static final String CREATE_DEVICE_TABLE = "CREATE TABLE " + DEVICE_TABLE_NAME + "(" +
            D_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + C_ID + " INTEGER NOT NULL, " + DEVICE_NAME_KEY
            + " TEXT NOT NULL, " + "FOREIGN KEY (" + C_ID + ") REFERENCES " +
            ROOM_TABLE_NAME + "(" + C_ID +  ")" + ")"; // tạo bảng "DEVICE_TABLE"

    private  static final String DROP_DEVICE_TABLE = "DROP TABLE IF EXISTS " + DEVICE_TABLE_NAME; // xóa bảng "DEVICE_TABLE"
    private  static final String SELECT_DEVICE_TABLE = "SELECT * FROM " + DEVICE_TABLE_NAME;//ấy tất cả dữ liệu từ bảng "DEVICE_TABLE"

    public DataStorage(@Nullable Context context) {
        super(context, "DeviceControl.db", null, VERSION);
        // context cung cấp để tạo hoặc mở cơ sở dữ liệu có tên "DeviceControl.db"
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(CREATE_ROOM_TABLE);
    db.execSQL(CREATE_DEVICE_TABLE);
    // để tạo bảng ROOM_TABLE và DEVICE_TABLE tương ứng
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_ROOM_TABLE);
            db.execSQL(DROP_DEVICE_TABLE);
            // được thực thi để xóa bảng ROOM_TABLE và DEVICE_TABLE
        } catch (SQLException e) // trường hợp ngoại lệ
        {
            e.printStackTrace(); // in ra thông tin xảy ra lỗi
        }
    }



   long addRoom(String nameroom) //thêm một phòng mới vào cơ sở dữ liệu
    {
        SQLiteDatabase database = this.getWritableDatabase(); // thực hiện hoạt động thêm, cập nhật hoặc xóa
        ContentValues values = new ContentValues();
        values.put(ROOM_NAME_KEY, nameroom);//  chèn dữ liệu vào bảng ROOM_TABLE_NAME

       return database.insert(ROOM_TABLE_NAME,null,values);
       // chèn dữ liệu từ ContentValues vào bảng
    }
    Cursor getRoomTable() //truy vấn toàn bộ dữ liệu từ bảng ROOM_TABLE
    {
        SQLiteDatabase database = this.getReadableDatabase(); //thực hiện các truy vấn đọc dữ liệu

        return database.rawQuery(SELECT_ROOM_TABLE,null);
        //tùy chỉnh trên cơ sở dữ liệu
    }

    int deleteRoom(long cid){ // xóa một phòng khỏi bảng ROOM_TABLE
        SQLiteDatabase database = this.getReadableDatabase(); // thực hiện các truy vấn đọc dữ liệu
       return database.delete(ROOM_TABLE_NAME, C_ID+ "=?", new String[]{String.valueOf(cid)});
       //để xóa một phòng khỏi bảng ROOM_TABLE dựa trên C_ID

    }

    long addDevice (long cid, String namedevice) // thêm thông tin của một thiết bị trong bảng
    {
        SQLiteDatabase database = this.getWritableDatabase(); // thực hiện hoạt động thêm, cập nhật hoặc xóa
        ContentValues values = new ContentValues();
        values.put(C_ID, cid);
        values.put(DEVICE_NAME_KEY,  namedevice);
        //  chèn dữ liệu vào bảng DEVICE_TABLE_NAME

        return  database.insert(DEVICE_TABLE_NAME,null,values);
        // chèn dữ liệu từ ContentValues vào bảng
    }

    Cursor getDeviceTable(long cid){ //truy vấn toàn bộ dữ liệu từ bảng DEVICE_TABLE
        SQLiteDatabase database = this.getReadableDatabase(); // thực hiện các truy vấn đọc dữ liệu
        return  database.query(DEVICE_TABLE_NAME,null,C_ID+"=?",new String[]{String.valueOf(cid)},null,null,null);
        // để truy vấn các dòng trong bảng DEVICE_TABLE_NAME với một điều kiện
    }

    int deleteDevice(long did){ // xóa thông tin của một thiết bị trong bảng
        SQLiteDatabase database = this.getReadableDatabase(); // thực hiện các truy vấn đọc dữ liệu
        return  database.delete(DEVICE_TABLE_NAME,D_ID+"=?", new String[]{String.valueOf(did)});
        // cập nhật thông tin một phòng khỏi bảng DEVICE_TABLE dựa trên D_ID
    }


}
