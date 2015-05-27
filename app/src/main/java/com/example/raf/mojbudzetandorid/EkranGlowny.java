package com.example.raf.mojbudzetandorid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class EkranGlowny extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekran_glowny);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ekran_glowny, menu);
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

    public void Statystyki (View view) {
        Intent Statystyki = new Intent("com.example.raf.mojbudzetandorid.Statystyki");
        startActivity(Statystyki);
    }

    public void Wydatki(View view) {
        Intent  Dodaj= new Intent("com.example.raf.mojbudzetandorid.Wydatki");
        startActivity(Dodaj);
    }

    public void Przychody(View view) {
        Intent DodajDochody = new Intent("com.example.raf.mojbudzetandorid.Przychody");
        startActivity(DodajDochody);
    }

    public void Powrot(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
