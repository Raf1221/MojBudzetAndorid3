package com.example.raf.mojbudzetandorid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raf on 2015-04-27.
 */
public class ZarzadcaBazy extends SQLiteOpenHelper {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public ZarzadcaBazy(Context context){

        super(context,"BudzetDB3.db",null,3);//najnwsza

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Uzytkownik(" +
                "Id INTEGER PRIMARY KEY," +
                "Haslo TEXT);");
        db.execSQL("create table Kategoria(" +
                "Id_k INTEGER PRIMARY KEY," +
                "Nazwa TEXT);");
        db.execSQL(
                "create table Operacja(" +
                        "Id_o INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Kategoria_id integer REFERENCES Kategoria(Id_k)," +
                        "Data DATE," +
                        "Kwota float," +
                        "Opis TEXT," +
                        "Typ TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ////////////////////////////////////////////////////////
    //////------------------UZYTKOWNIK----------------//////
    ////////////////////////////////////////////////////////

    public void dodajUser(int Id,String Haslo){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues wartosci=new ContentValues();
        wartosci.put("Id",Id);
        wartosci.put("Haslo",Haslo);

        //db.insertOrThrow("Uzytkownik",null,wartosci);
    }

    public Cursor dajWszystkie(){
        String[] kolumny={"Id","Haslo"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor kursor=db.query("Uzytkownik",kolumny,null,null,null,null,null);

        return  kursor;
    }

    public void kasujUzytkownika(int id){
        SQLiteDatabase db=getWritableDatabase();
        String [] argumenty={""+id};
        db.delete("Uzytkownik","nr=?",argumenty);
    }
    public void aktualizujUzytownika(int nr,String noweHaslo){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues wartosci=new ContentValues();
        wartosci.put("haslo",noweHaslo);
        String args[]={nr+""};
        db.update("Uzytkownik",wartosci,"nr=?",args);

    }
    ////////////////////////////////////////////////////////
    //////------------------KATEGORIA----------------///////
    ////////////////////////////////////////////////////////
    public void dodajKategoria(int id_k,String nazwa){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues wartosci=new ContentValues();
        wartosci.put("Id_k",id_k);
        wartosci.put("Nazwa",nazwa);
        db.insertOrThrow("Kategoria",null,wartosci);
    }
    public Cursor dajWszystkieKategoria(){
        String[] kolumny={"Id_k","Nazwa"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor kursor=db.query("Kategoria",kolumny,null,null,null,null,null);

        return  kursor;
    }
    public void kasujKategoria(int id){
        SQLiteDatabase db=getWritableDatabase();
        String [] argumenty={""+id};
        db.delete("Kategoria","Id_k=?",argumenty);
    }
    ////////////////////////////////////////////////////////
    //////------------------OPERACJA----------------////////
    ////////////////////////////////////////////////////////
    public void dodajOperacje(int kategoria_id,Date data,float kwota,String opis,String typ){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues wartosci=new ContentValues();
        wartosci.put("Kategoria_id",kategoria_id);
        wartosci.put("Data",dateFormat.format(data));
        wartosci.put("Kwota",kwota);
        wartosci.put("Opis",opis);
        wartosci.put("Typ",typ);
        db.insertOrThrow("Operacja",null,wartosci);

    }
    public Cursor dajWszystkieOperacje(){
        String[] kolumny={"k.Nazwa","o.Data","o.Kwota","o.Opis","o.Typ"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor kursor=db.rawQuery("select k.Nazwa,o.Data,o.Kwota,o.Opis from Operacja o join Kategoria k on o.Kategoria_id=k.Id_k order by data;",null);
        return  kursor;
    }
        /*
        public Cursor dajWszystkieWydatek(){
        String[] kolumny={"k.nazwa","w.data","w.kwota","w.opis"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor kursor=db.rawQuery("select k.nazwa,w.data,w.kwota,w.opis from Wydatki w join Kategoria k on w.kategoria_id=k.id_k order by data;",null);
         /*String[] kolumny={"k.id_w","w.data","w.kwota","w.opis"};
        SQLiteDatabase db=getReadableDatabase();
        Cursor kursor=db.rawQuery("select w.id_w,w.data,w.kwota,w.opis from Wydatki w join Kategoria k on w.kategoria_id=k.id_k order by data;",null);*/
    //Cursor kursor=db.query("Wydatki w join Kategoria k",kolumny,"k.id_k=1",null,null,null,null);


}
