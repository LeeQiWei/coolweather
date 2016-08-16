package com.lee.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lee.coolweather.model.City;
import com.lee.coolweather.model.County;
import com.lee.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/8/16.
 */
public class CoolWeatherDB {

    /**
     *数据库名
     */
    public static final String DB_NAME="cool_weather";
    /**
     *数据库版本
     */
    public static final int VERSION=1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelpter dbHelpter=new CoolWeatherOpenHelpter(context,DB_NAME,null,VERSION);
        db=dbHelpter.getWritableDatabase();
    }
    /**
     *获取CoolWeatherDB的实例
     */
    public synchronized  static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB==null){
            coolWeatherDB=new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    /**
     *将Province实例存储到数据库
     */
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    /**
     *从数据库读取全国所有的省份信息
     */
    public List<Province>loadProvinces(){
        List<Province>list=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }
            while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
    /**
     *将city实例存储到数据库
     */
    public void saveCity(City city){
        if (city!=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            db.insert("City",null,values);
        }
    }
    /**
     *从数据库读取全国所有的城市信息
     */
    public List<City>loadCities(int provinceId){
        List<City>list=new ArrayList<City>();
        Cursor cursor=db.query("city",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                list.add(city);
            }
            while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
    /**
     *将County实例存储到数据库
     */
    public void saveCounty(County county){
        if (county!=null){
            ContentValues values=new ContentValues();
            values.put("County_name",county.getCountyName());
            values.put("County_code",county.getCountyCode());
            db.insert("County",null,values);
        }
    }
    /**
     *从数据库读取全国所有的县信息
     */
    public List<County>loadCounties(int cityId){
        List<County>list=new ArrayList<County>();
        Cursor cursor=db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                County county=new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("County_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("County_code")));
                list.add(county);
            }
            while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }
}

