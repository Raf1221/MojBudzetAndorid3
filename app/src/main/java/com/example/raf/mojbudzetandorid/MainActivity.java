package com.example.raf.mojbudzetandorid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raf.mojbudzetandorid.utils.DatabaseUtils;


public class MainActivity extends Activity {
    private ZarzadcaBazy zb ;
    private DatabaseUtils db;
    private SQLiteDatabase baza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] kategorie = getResources().getStringArray(R.array.kategorie);
        zb = new ZarzadcaBazy(getBaseContext());
        baza = zb.getWritableDatabase();
//        baza = this.openOrCreateDatabase("BudzetDB3.db", MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
        zb.uzupelnijKategorie(kategorie,baza);
//        zb.dodajUser(1, "a", baza);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Loguj(View view) {

        Cursor k=zb.dajWszystkie(baza);
        k.moveToFirst();
        String userPass = k.getString(k.getColumnIndexOrThrow("Haslo"));
        EditText ed = (EditText) findViewById(R.id.HasloPW);
        String givenPass = ed.getText().toString();
        try{
            if(userPass.equals(givenPass)){

                Intent PrzekEkranGlowny = new Intent("com.example.raf.mojbudzetandorid.EkranGlowny");
                startActivity(PrzekEkranGlowny);
            }else{
                Toast.makeText(this,"Podane hasło jest nieprawdziwe",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this,"blad "+e,Toast.LENGTH_SHORT).show();
        }
    }

    public void ZmianaHasla(View view) {
        Intent DodajDochody = new Intent("com.example.raf.mojbudzetandorid.ZmianaHaslaActivity");
        startActivity(DodajDochody);
    }
}
