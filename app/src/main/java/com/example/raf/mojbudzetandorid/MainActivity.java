package com.example.raf.mojbudzetandorid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
ZarzadcaBazy zb=new ZarzadcaBazy(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            //ten użytkownik tworzy się tyllko raz przy pierwszym uruchamianiu programu
           // zb.dodajUser(1,"");
           // Toast.makeText(MainActivity.this, "uzytkownik jest", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            //Toast.makeText(MainActivity.this, "blac " + e, Toast.LENGTH_SHORT).show();
            TextView tv=(TextView) findViewById(R.id.textView1);
            tv.setText("blad"+e);
            //Toast.makeText(Login.this,"blac "+e,Toast.LENGTH_SHORT).show();
            //
            // tv.setText("podany uytkownik juz jest ");
        }
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

        TextView tx=(TextView) findViewById(R.id.LogowanieL);
        Cursor k=zb.dajWszystkie();
        k.moveToFirst();
        String hasloUzytkownika = k.getString(1);
        EditText ed = (EditText) findViewById(R.id.HasloPW);
        String haslo=ed.getText().toString();

        try{
            if(haslo.equals(hasloUzytkownika)){

                Intent PrzekEkranGlowny = new Intent("com.example.raf.mojbudzetandorid.EkranGlowny");
                startActivity(PrzekEkranGlowny);
            }else{
                Toast.makeText(this,"Podane hasło jest nieprawdziwe",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this,"blad "+e,Toast.LENGTH_SHORT).show();
        }



    }
}
