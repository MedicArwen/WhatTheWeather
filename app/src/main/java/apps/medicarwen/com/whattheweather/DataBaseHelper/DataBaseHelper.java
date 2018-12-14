package apps.medicarwen.com.whattheweather.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.models.AirInfo;
import apps.medicarwen.com.whattheweather.models.City;
import apps.medicarwen.com.whattheweather.models.Coord;
import apps.medicarwen.com.whattheweather.models.Weather;


public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="WeatherDatabase";
    private final static int DATABASE_VERSION=1;


    private static final String TABLE_CITY = "city";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_CITY = "id_city";
    private static final String KEY_NAME = "name";
    private static final String KEY_TEMP = "temp";
    private static final String KEY_DESC = "desc";
    private static final String KEY_RES_ICON = "res_icon";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";

    private final static String CREATE_TABLE_CITY="CREATE TABLE "+TABLE_CITY+" ("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_CITY + " INTEGER,"
            + KEY_NAME + " TEXT,"
            + KEY_TEMP + " TEXT,"
            + KEY_DESC+" TEXT,"
            + KEY_RES_ICON + " INTEGER,"
            + KEY_LAT + " DECIMAL (3, 10),"
            + KEY_LNG + " DECIMAL (3, 10))";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        Log.d("WTF", "Constructor DataBaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("WTF", "onCreate DataBaseHelper");
        db.execSQL(CREATE_TABLE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("WTF", "onUpgrade DataBaseHelper");
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CITY);
        onCreate(db);
    }

    public long insertCity(City pCity)
    {
        long retour;

        Log.d("WTF", "insertCity DataBaseHelper");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(KEY_ID_CITY,pCity.mCityId); //0
        insertValues.put(KEY_NAME,pCity.mName); //1
        insertValues.put(KEY_TEMP,pCity.mInfoAir.temp); //2
        insertValues.put(KEY_DESC,pCity.mMeteo.description); //3
        insertValues.put(KEY_RES_ICON,pCity.mMeteo.icon); //4
        insertValues.put(KEY_LAT,pCity.mCoordonnees.lat);//5
        insertValues.put(KEY_LNG,pCity.mCoordonnees.lon);//6
        retour = db.insert(TABLE_CITY,null,insertValues);
        db.close();
        return retour;
    }
    public int deleteCity(int pIdCity)
    {

        Log.d("WTF", "deleteCity DataBaseHelper");
        SQLiteDatabase db = this.getWritableDatabase();
        int nbrowsDeleted=0;
        nbrowsDeleted= db.delete(TABLE_CITY,KEY_ID_CITY+"=?",new String[]{String.valueOf(pIdCity)});
        db.close();
        return nbrowsDeleted;
    }
    public ArrayList<City> selectAllCities()
    {

        Log.d("WTF", "selectAllCities DataBaseHelper");
        ArrayList<City> cityList= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CITY,null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            do {
                Weather meteo = new Weather(cursor.getString(3),cursor.getString(4));
                Coord coord = new Coord(cursor.getDouble(6),cursor.getDouble(5));
                AirInfo airInfo= new AirInfo(cursor.getDouble(2));

                City newCity = new City(cursor.getString(2),cursor.getInt(1),airInfo,meteo,coord);
                cityList.add(newCity);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cityList;
    }


}
