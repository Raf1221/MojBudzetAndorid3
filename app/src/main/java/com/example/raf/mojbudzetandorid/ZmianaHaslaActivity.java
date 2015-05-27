package com.example.raf.mojbudzetandorid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ZmianaHaslaActivity extends ActionBarActivity {
    private ZarzadcaBazy zarzadca= new ZarzadcaBazy(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmiana_hasla);
        Button button = (Button) findViewById(R.id.button2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zmiana_hasla, menu);
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

    public void zmienHaslo(View v) {
        Intent PrzekEkranGlowny = new Intent(this,MainActivity.class);
        EditText ed = (EditText) findViewById(R.id.editText);
        zarzadca.aktualizujUzytownika(1,ed.getText().toString());
        startActivity(PrzekEkranGlowny);
    }
    public void Powrot(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
