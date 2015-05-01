package com.example.raf.mojbudzetandorid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;


public class Statystyki extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    TableLayout table_layout;
    ZarzadcaBazy zb=new ZarzadcaBazy(this);
    Spinner Operacjespin;
    Spinner KategorieStatykispin;
    String[] s = { "India ", "Arica", "India ", "Arica", "India ", "Arica",
            "India ", "Arica", "India ", "Arica" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        budujTabelke();
        Button btn=(Button)findViewById(R.id.filtrujB);
        /*btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statystyki, menu);
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
    public void budujTabelke(){

        try {
            Cursor k3 = zb.dajWszystkieOperacje();
            int rows=k3.getCount();
            int cols=k3.getColumnCount();
            //Toast.makeText(Statystyki.this, "liczba rows"+rows, Toast.LENGTH_SHORT).show();
            //Toast.makeText(Statystyki.this, "liczba cols"+cols, Toast.LENGTH_SHORT).show();
            k3.moveToFirst();





            for(int i=0;i<rows;i++){

               TableRow row=new TableRow(this);
               // row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                row.setPadding(0,0,0,0);


                table_layout.addView(row);
                for(int j=0;j<cols;j++){
                   TextView tv = new TextView(this);
                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                          LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(16);
                    tv.setPadding(0,0,0,0);

                    tv.setText(k3.getString(j));

                    row.addView(tv);

                }
                k3.moveToNext();

                try{
                    table_layout.addView(row);
                }catch(Exception e){
                   // Toast.makeText(Statystyki.this, "blad "+e, Toast.LENGTH_SHORT).show();
                }

                //table_layout.addView(row);
            }
        }catch (Exception e){
            //Toast.makeText(Statystyki.this, "blad "+e, Toast.LENGTH_SHORT).show();
        }

    }





    protected void showInputDialog(View view) {
    InputDialog inputDialog=new InputDialog();
        inputDialog.show(getFragmentManager(),"My alert");


       /* LayoutInflater layoutInflater = LayoutInflater.from(Statystyki.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Statystyki.this);
        alertDialogBuilder.setView(promptView);
        AlertDialog alert = alertDialogBuilder.create();

        TextView tv=(TextView)findViewById(R.id.OdT);
        tv.setText("witaj napis");
        alert.show();*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item1 = Operacjespin.getSelectedItem();
        if (item1.equals("Wydatki")) {

        } else if (item1.equals("Przychody")) {

        } else if (item1.equals("Wszystko")) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
