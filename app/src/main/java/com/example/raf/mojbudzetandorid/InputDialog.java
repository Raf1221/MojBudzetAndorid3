package com.example.raf.mojbudzetandorid;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Raf on 2015-05-01.
 */
public class InputDialog extends DialogFragment implements  AdapterView.OnItemSelectedListener {
    public static String DataOD;
    public static String DataDO;
    public static int KategoriaWyswietlania=00;
    public static String TypWyswietlania;
    Spinner spinner1;
    Spinner spinner2;
    public TextView tv1;
    public TextView tv2;
    Button filtruj;
    Context context;
    Button DataOd;
    Button DataDo;
    static String DialogboxTitle;
    SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
    ZarzadcaBazy zb=new ZarzadcaBazy(getActivity());
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface InputNameDialogListener{
        void onFinishInputDialog(String inputText);
    }
    public InputDialog() {

    }

    public void setDialogTitle(String title) {
        DialogboxTitle = title;
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle saveInstanceState){
        ZarzadcaBazy zb=new ZarzadcaBazy(getActivity());
        View view = inflater.inflate(
                R.layout.input_dialog, container);

        Statystyki.czyWlaczanyDialog=true;
        tv1=(TextView)view.findViewById(R.id.OdT);
        tv2=(TextView)view.findViewById(R.id.DoT);
        /////////////////////////USTAWIENIE DATY NA DZISIAJ//////////////////////
        int rokP,miesiacP,dzienP;
        int rok,miesiac,dzien;

        Calendar cal2=Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH,-30);
        rokP=cal2.get(Calendar.YEAR);
        miesiacP=cal2.get(Calendar.MONTH)+1;
        dzienP=cal2.get(Calendar.DAY_OF_MONTH);
        if(miesiacP<10){
            //tv1.setText("2015-05-25");
            tv1.setText(rokP+"-0"+miesiacP+"-"+dzienP);
        }else{
            tv1.setText(rokP+"-"+miesiacP+"-"+dzienP);
        }

        Calendar cal=Calendar.getInstance();
        rok=cal.get(Calendar.YEAR);
        miesiac=cal.get(Calendar.MONTH)+1;
        dzien=cal.get(Calendar.DAY_OF_MONTH)+1;

        if(miesiac<10){
            tv2.setText(rok+"-0"+miesiac+"-"+dzien);
        }else{
            tv2.setText(rok+"-"+miesiac+"-"+dzien);
        }







        /////////////////////////USTAWIENIE DATY NA DZISIAJ-30DNI//////////////////////

        spinner1=(Spinner) view.findViewById(R.id.OperacjeS);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getActivity(),R.array.Operacje,android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2=(Spinner)view.findViewById(R.id.KategorieStatystykiS);
        filtruj=(Button) view.findViewById(R.id.FiltrujB);
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(getActivity(),R.array.KategorieStatystyki,android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        getDialog().setTitle(DialogboxTitle);


        filtruj .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*try {
                    Cursor k3 = zb.dajWszystkieOperacjeFiltruj(1);
                    int rows=k3.getCount();
                    int cols=k3.getColumnCount();
                    //Toast.makeText(Statystyki.this, "liczba rows"+rows, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Statystyki.this, "liczba cols"+cols, Toast.LENGTH_SHORT).show();
                    k3.moveToFirst();





                    for(int i=0;i<rows;i++){

                        TableRow row=new TableRow(this);
                        // row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        row.setPadding(0,0,0,0);


                        table_layout.addView(row);
                        for(int j=0;j<cols;j++){
                            TextView tv = new TextView(this);
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
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
                }*/

                Statystyki stat = new Statystyki();
                String typ = spinner1.getSelectedItem().toString();
                String kategoria = spinner2.getSelectedItem().toString();
                if(!typ.equals("Wydatek")){
                    typ="IS NOT NULL";
                }
                stat.budujTabelke(tv1.getText().toString(),tv2.getText().toString(),typ,kategoria);
                getDialog().dismiss();

            }
        });

        return view;
    }








}
