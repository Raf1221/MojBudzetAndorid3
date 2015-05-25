package com.example.raf.mojbudzetandorid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.raf.mojbudzetandorid.utils.DatabaseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raf on 2015-04-27.
 */
public class ZarzadcaBazy extends SQLiteOpenHelper {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DatabaseUtils db;
    private SQLiteDatabase baza;

    public ZarzadcaBazy(Context context) {
        super(context, "BudzetDB3.db", null, 1);//najnwsza
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Uzytkownik(" +
                "Id INTEGER PRIMARY KEY," +
                "Haslo TEXT);");
        db.execSQL("Insert into Uzytkownik(Id,Haslo) Values (1,'a');");
        db.execSQL("create table Kategoria(" +
                "Id_k INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Nazwa TEXT);");
        db.execSQL("create table Operacja(" +
                "Id_o INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Kategoria_id integer REFERENCES Kategoria(Id_k)," +
                "Data DATE," +
                "Kwota float," +
                "Opis TEXT," +
                "Typ TEXT);");
        baza = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UZYTKOWNIK;" +
                "DROP TABLE IF EXISTS KATEGORIA;" +
                "DROP TABLE IF EXISTS OPERACJA;");
        onCreate(db);
    }

    public void uzupelnijKategorie(String[] kategorie, SQLiteDatabase baza) {
        SQLiteDatabase db = baza;
        ContentValues values = new ContentValues();
        long newRowId;
        for (int i = 0; i < kategorie.length; i++) {
            values.put("nazwa", kategorie[i]);
            db.insert("Kategoria", "Nazwa", values);
        }

    }
    ////////////////////////////////////////////////////////
    //////------------------UZYTKOWNIK----------------//////
    ////////////////////////////////////////////////////////

    public void dodajUser(int Id, String Haslo, SQLiteDatabase baza) {
        SQLiteDatabase db = baza;
        ContentValues wartosci = new ContentValues();
        wartosci.put("Id", Id);
        wartosci.put("Haslo", Haslo);

        db.insert("Uzytkownik", "haslo", wartosci);
    }

    public Cursor dajWszystkie(SQLiteDatabase baza) {
        String[] kolumny = {"Haslo"};
        String[] wiersz = {"1"};
        SQLiteDatabase db = baza;
        Cursor kursor = db.query("uzytkownik", kolumny, null, null, null, null, null);

        return kursor;
    }

    public void kasujUzytkownika(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] argumenty = {"" + id};
        db.delete("Uzytkownik", "nr=?", argumenty);
    }

    public void aktualizujUzytownika(int nr, String noweHaslo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Haslo", noweHaslo);
        String args[] = {nr + ""};
        db.update("Uzytkownik", wartosci, "id=" + nr, null);

    }

    ////////////////////////////////////////////////////////
    //////------------------KATEGORIA----------------///////
    ////////////////////////////////////////////////////////
    public void dodajKategoria(int id_k, String nazwa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Id_k", id_k);
        wartosci.put("Nazwa", nazwa);
        db.insertOrThrow("Kategoria", null, wartosci);
    }

    public Cursor dajWszystkieKategoria(SQLiteDatabase baza) {
        String[] kolumny = {"Id_k", "Nazwa"};
        SQLiteDatabase db = baza;
        Cursor kursor = db.query("Kategoria", kolumny, null, null, null, null, null);

        return kursor;
    }

    public void kasujKategoria(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] argumenty = {"" + id};
        db.delete("Kategoria", "Id_k=?", argumenty);
    }

    ////////////////////////////////////////////////////////
    //////------------------OPERACJA----------------////////
    ////////////////////////////////////////////////////////
    public void dodajOperacje(int kategoria_id, Date data, float kwota, String opis, String typ, SQLiteDatabase baza) {
        SQLiteDatabase db = baza;
        ContentValues wartosci = new ContentValues();
        wartosci.put("Kategoria_id", kategoria_id);
        wartosci.put("Data", dateFormat.format(data));
        wartosci.put("Kwota", kwota);
        wartosci.put("Opis", opis);
        wartosci.put("Typ", typ);
        db.insertOrThrow("Operacja", null, wartosci);

    }

    public Cursor dajWszystkieOperacje() {
        String[] kolumny = {"k.Nazwa", "o.Data", "o.Kwota", "o.Opis"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.rawQuery("select k.Nazwa,o.Data,o.Kwota,o.Opis,o.Typ from Operacja o join Kategoria k on o.Kategoria_id=k.Id_k order by data;", null);
        return kursor;
    }

    public Cursor dajWszystkieOperacjeFiltruj(String dataOd, String dataDo, String typ, String kategoria, SQLiteDatabase baza) {

        String[] kolumny = {"k.Nazwa", "o.Data", "o.Kwota", "o.Opis"};
        SQLiteDatabase db = baza;
        Cursor kursor = db.rawQuery("select k.Nazwa,o.Data,o.Kwota,o.Opis,o.Typ " +
                "from Operacja o join Kategoria k on o.Kategoria_id=k.Id_k " +
                "where k.Nazwa='" + kategoria + "' " +
                "and o.Data BETWEEN Datetime('"+dataOd+"') and Datetime('"+dataDo+"') " +
                "and o.Typ='"+typ+"';", null);
        return kursor;


    }


}
