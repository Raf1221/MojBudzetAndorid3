package com.example.raf.mojbudzetandorid;
import android.support.v7.app.ActionBarActivity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    ZarzadcaBazy zb=new ZarzadcaBazy(this);
    Spinner spinner;
    Button button;
    EditText Data;
    int rok,miesiac,dzien;
    static final int DIALOG_ID=0;
    int KategriaID;
    String DataWydatku;
    float Cena;
    String Uwagi;
    EditText CenaET,DataET,UwagiET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki);
        spinner=(Spinner) findViewById(R.id.spinner_Kategorie);
        CenaET=(EditText)findViewById(R.id.CenaWydatek);
        DataET=(EditText)findViewById(R.id.DataWyswietla);
        UwagiET=(EditText)findViewById(R.id.UwagiWydatek);


        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.kategorie,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final Calendar cal=Calendar.getInstance();
        rok=cal.get(Calendar.YEAR);
        miesiac=cal.get(Calendar.MONTH);
        dzien=cal.get(Calendar.DAY_OF_MONTH);

        DajKalendarz();
        try{
            /*zb.dodajKategoria(1,"Alkohol");
            zb.dodajKategoria(2,"Dom");
            zb.dodajKategoria(3,"Dziecko");
            zb.dodajKategoria(4,"Elektronika");
            zb.dodajKategoria(5,"Firma");
            zb.dodajKategoria(6,"Jedzenie");
            zb.dodajKategoria(7,"Kultura");
            zb.dodajKategoria(8,"Moda");
            zb.dodajKategoria(9,"Motoryzacja");
            zb.dodajKategoria(10,"Rozrywka");
            zb.dodajKategoria(11,"Sport");
            zb.dodajKategoria(12,"Sztuka");
            zb.dodajKategoria(13,"Uroda");
            zb.dodajKategoria(14,"Usługi");
            zb.dodajKategoria(15,"Wypoczynek");
            zb.dodajKategoria(16,"Zdrowie");
            zb.dodajKategoria(17,"Przychod");*/
            //Toast.makeText(this,"Została wybrała kategoria",Toast.LENGTH_SHORT).show();


        }catch(Exception e){
            Toast.makeText(this,"blad: "+e,Toast.LENGTH_SHORT).show();
        }

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
            Toast.makeText(this,"Wybrano"+myText.getText(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void dodajOperacjaWydatek(View view){
        switch (spinner.getSelectedItem().toString()){
            case "Alkohol":
                KategriaID=1;break;
            //Toast.makeText(DodajWydatek.this,"Wybrano alkohol",Toast.LENGTH_LONG).show();
            case "Dom":
                KategriaID=2; break;
            case "Dziecko":
                KategriaID=3; break;
            case "Elektronika":
                KategriaID=4; break;
            case "Firma":
                KategriaID=5; break;
            case "Jedzenie":
                KategriaID=6; break;
            case "Kultura":
                KategriaID=7; break;
            case "Moda":
                KategriaID=8; break;
            case "Motoryzacja":
                KategriaID=9; break;
            case "Rozrywka":
                KategriaID=10; break;
            case "Sport":
                KategriaID=11; break;
            case "Sztuka":
                KategriaID=12; break;
            case "Uroda":
                KategriaID=13; break;
            case "Usługi":
                KategriaID=14; break;
            case "Wypoczynek":
                KategriaID=15; break;
            case "Zdrowie":
                KategriaID=16; break;
            default:
                Toast.makeText(Wydatki.this,"Kategoria nie została wybrana",Toast.LENGTH_LONG).show(); break;
        }
        Cena=Float.parseFloat(CenaET.getText().toString());
        DataWydatku=DataET.getText().toString();
        Uwagi=UwagiET.getText().toString();
        String typ="Wydatek";
        try{
            //zb.dodajWydatek(1, Date.valueOf("2008-9-2"),5000,"pensja xd");
            zb.dodajOperacje(KategriaID, Date.valueOf(DataWydatku),Cena,Uwagi,typ);
            Toast.makeText(Wydatki.this,"Udalo sie dodac wydatek",Toast.LENGTH_LONG).show();
        }catch(Exception e){
            Toast.makeText(Wydatki.this,""+e,Toast.LENGTH_LONG).show();
        }

    }

}
