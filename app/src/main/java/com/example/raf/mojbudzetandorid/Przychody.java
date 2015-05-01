package com.example.raf.mojbudzetandorid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;


public class Przychody extends ActionBarActivity{

    EditText CenaETa,DataETa,UwagiETa;
    int rok,miesiac,dzien;
    EditText Data;
    String DataPrzychodu;
    float Cena;
    String Uwagi;
    Button button;
    static final int DIALOG_ID=0;
    ZarzadcaBazy zb=new ZarzadcaBazy(this);
    int KategoriaID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przychody);
        CenaETa=(EditText)findViewById(R.id.CenaPrzychod);
        DataETa=(EditText)findViewById(R.id.DataWyswietla);
        UwagiETa=(EditText)findViewById(R.id.UwagiPrzychod);


        final Calendar cal=Calendar.getInstance();
        rok=cal.get(Calendar.YEAR);
        miesiac=cal.get(Calendar.MONTH);
        dzien=cal.get(Calendar.DAY_OF_MONTH);

        DajKalendarz();

    }
    public void DajKalendarz(){
        button=(Button)findViewById(R.id.Kalendarz_A);
        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void  onClick(View w){
                        showDialog(DIALOG_ID);

                    }
                }
        );
    }

    @Override//przy otwieraniu okna dodaje sie kategoria RTV/AGD o id:1
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return new DatePickerDialog(this,dpickerListener,rok,miesiac,dzien);
        }else{
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListener=
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    rok=year;
                    miesiac=monthOfYear+1;
                    dzien=dayOfMonth;
                    // Toast.makeText(DodajWydatek.this,rok+"-"+miesiac+"-"+dzien,Toast.LENGTH_LONG).show();
                    Data=(EditText)findViewById(R.id.DataWyswietla);
                    Data.setText(rok+"-"+miesiac+"-"+dzien);

                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_przychody, menu);
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
    public void Powrot(View view) {
        Intent Powrot = new Intent("com.example.raf.mojbudzetandorid.EkranGlowny");
        startActivity(Powrot);
    }

    public void dodajOperacjaPrzychod(View view){
        KategoriaID=17;
        Cena=Float.parseFloat(CenaETa.getText().toString());
        DataPrzychodu=DataETa.getText().toString();
        Uwagi=UwagiETa.getText().toString();
        String typ="Przychod";
        try{

            zb.dodajOperacje(KategoriaID, Date.valueOf(DataPrzychodu),Cena,Uwagi,typ);
            Toast.makeText(Przychody.this,"Udalo sie dodac przychod",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(Przychody.this,""+e,Toast.LENGTH_LONG).show();
        }

    }
}
