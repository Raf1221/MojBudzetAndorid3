package com.example.raf.mojbudzetandorid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;


public class Wydatki extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    ZarzadcaBazy zb = new ZarzadcaBazy(getBaseContext());
    private SQLiteDatabase baza;
    Spinner spinner;
    Button button;
    EditText Data;
    int rok,miesiac,dzien;
    static final int DIALOG_ID=0;
    String DataWydatku;
    float Cena;
    String Uwagi;
    EditText CenaET,DataET,UwagiET;

    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki);
        spinner=(Spinner) findViewById(R.id.spinner_Kategorie);
        CenaET=(EditText)findViewById(R.id.CenaWydatek);
        DataET=(EditText)findViewById(R.id.DataWyswietla);
        UwagiET=(EditText)findViewById(R.id.UwagiWydatek);
        baza= this.openOrCreateDatabase("BudzetDB3.db", MODE_ENABLE_WRITE_AHEAD_LOGGING, null);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.kategorie,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        final Calendar cal=Calendar.getInstance();
        rok=cal.get(Calendar.YEAR);
        miesiac=cal.get(Calendar.MONTH);
        dzien=cal.get(Calendar.DAY_OF_MONTH);
        int spinnerPostion = adapter.getPosition(R.array.kategorie);
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
                    Data=(EditText)findViewById(R.id.DataWyswietla);
                    Data.setText(rok+"-"+miesiac+"-"+dzien);

                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wydatki, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText=(TextView) view;
        if(myText.getText()!=null){

           // Toast.makeText(this,"Wybrano "+myText.getText()+" "+position,Toast.LENGTH_SHORT).show();
            num=position+1;
           // Toast.makeText(this,"num "+num,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void dodajOperacjaWydatek(View view){
        Cena=Float.parseFloat(CenaET.getText().toString());
        DataWydatku=DataET.getText().toString();
        Uwagi=UwagiET.getText().toString();
        String typ="Wydatek";
        try{
            //zb.dodajWydatek(1, Date.valueOf("2008-9-2"),5000,"pensja xd");
            zb.dodajOperacje(num, Date.valueOf(DataWydatku),Cena,Uwagi,typ,baza);
            Toast.makeText(Wydatki.this,"Udalo sie dodac wydatek",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(Wydatki.this,""+e,Toast.LENGTH_LONG).show();
        }

    }

}
