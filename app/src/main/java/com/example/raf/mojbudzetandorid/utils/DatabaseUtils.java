package com.example.raf.mojbudzetandorid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.raf.mojbudzetandorid.ZarzadcaBazy;

/**
 * Created by kl0c3k on 2015-05-17.
 */
public class DatabaseUtils  {
    private ZarzadcaBazy zarzadcaBazy;
    private SQLiteDatabase baza;

    public DatabaseUtils(Context context){
        zarzadcaBazy = new ZarzadcaBazy(context);
    }

    public SQLiteDatabase otworzBazE() {
        try {
            baza = zarzadcaBazy.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return baza;
    }
    public void zamknijBaze(){
        zarzadcaBazy.close();
    }

    public void uzupelnijKategorie(String[] kategorie,SQLiteDatabase baza ) {
        SQLiteDatabase db = baza;
        ContentValues values = new ContentValues();
        long newRowId;
        for (int i = 0; i <= kategorie.length; i++) {
            values.put("nazwa",kategorie[i]);
        }
        newRowId = db.insert("kategoria", "nazwa",values);
    }


}
