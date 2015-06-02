package com.example.raf.mojbudzetandorid;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Statystyki extends ActionBarActivity {
    public static TableLayout table_layout;
    ZarzadcaBazy zb = new ZarzadcaBazy(this);
    public static boolean czyWlaczanyDialog = true;
    public static Context baseContext;
    public static TextView tV1;
    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        final Button button = (Button) findViewById(R.id.BtnWykres);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        tV1=(TextView)findViewById(R.id.textViewIleOp);


        budujTabelke();

        Button btn = (Button) findViewById(R.id.filtrujB);
        Button btnWykres = (Button) findViewById(R.id.BtnWykres);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChart();
            }
        };
        btnWykres.setOnClickListener(clickListener);
        baseContext = getBaseContext();
        Saldo();


    }

    private void openChart() {
        Set<String> codeSet = new HashSet<String>();
        List<String> codeI = new ArrayList<String>();
        List<Double> dystrybucjaI = new ArrayList<Double>();
        class DoSsumowania{
            private String typ;
            private Double cena;

            public String getTyp() {
                return typ;
            }

            public Double getCena() {
                return cena;
            }

            public void setCena(Double cena) {
                this.cena = cena;
            }

            public void setTyp(String typ) {
                this.typ = typ;
            }
        }
        DoSsumowania doSsumowania;
        boolean dodano= true;
        List<DoSsumowania> doSsumowaniaLista= new ArrayList<DoSsumowania>();
        for (int i = 0, j = table_layout.getChildCount(); i < j; i++) {
            doSsumowania = new DoSsumowania();
            View view = table_layout.getChildAt(i);
            TextView temp = null;
            if (view instanceof TableRow) {
                TableRow t = (TableRow) view;
                for (int k = 0, l = t.getChildCount(); k < l; k++) {
                    TextView text = (TextView) t.getChildAt(k);

                    if (!text.getText().equals("Przychod") & (k % 5) == 0) {
                        dodano= codeSet.add(text.getText().toString());

                        codeI.add(text.getText().toString());
                        doSsumowania.setTyp(text.getText().toString());
                    }
                    if (k == 2) {
                        temp = (TextView) t.getChildAt(k - 2);
                    }
                    if (temp != null && !temp.getText().equals("Przychod") & k != 0 & (k % 2) == 0 & k != 4) {
                        dystrybucjaI.add(Double.valueOf(text.getText().toString()));
                        doSsumowania.setCena(Double.valueOf(text.getText().toString()));
                        if(dodano==false){
                            doSsumowaniaLista.add(doSsumowania);
                        }
                    }
                }
            }
        }
        boolean prawda = codeI.size() == dystrybucjaI.size() ? true : false;
        String[] code = new String[codeI.size()];
        Double[] dystrybucja = new Double[dystrybucjaI.size()];
        for (int i = 0; i < dystrybucjaI.size(); i++) {
            dystrybucja[i] = dystrybucjaI.get(i);
        }
        for (int i = 0; i < codeI.size(); i++) {
            code[i] = codeI.get(i);
        }
        Integer[] kolory = new Integer[dystrybucjaI.size()];
        for (int i = 0; i < dystrybucjaI.size(); i++) {
            int RGB = 0xff + 1;
            kolory[i] = Color.rgb((int) Math.floor(Math.random() * RGB), (int) Math.floor(Math.random() * RGB), (int) Math.floor(Math.random() * RGB));
        }
        for(int i =0 ; i <codeI.size()-doSsumowaniaLista.size();i++){
            for(int j = 0 ; j<dystrybucjaI.size()- doSsumowaniaLista.size(); j++){
                if(j>i & codeI.get(i).equals(codeI.get(j))){
                    code[j] = null ;
                    dystrybucja[i]+=dystrybucja[j];
                    dystrybucja[j]= null;
                    kolory[j]=null;

                }
            }
        }

        CategorySeries serieDystrybucji = new CategorySeries("Dane z tabeli");
        for (int i = 0; i < dystrybucja.length; i++) {
            if(code[i]!=null && dystrybucja[i]!=null)
            serieDystrybucji.add(code[i] + " " + dystrybucja[i] + " PLN", dystrybucja[i]);
        }
        DefaultRenderer podstawowyRender = new DefaultRenderer();
        for (int i = 0; i < dystrybucja.length; i++) {
            if(kolory[i]!=null) {
                SimpleSeriesRenderer renderSerii = new SimpleSeriesRenderer();
                renderSerii.setColor(kolory[i]);
                renderSerii.setDisplayChartValues(true);
                podstawowyRender.addSeriesRenderer(renderSerii);
            }
        }
        podstawowyRender.setBackgroundColor(Color.BLACK);
        podstawowyRender.setApplyBackgroundColor(true);
        podstawowyRender.setChartTitle("Wykres kołowy");
        podstawowyRender.setChartTitleTextSize(30);
        podstawowyRender.setLabelsTextSize(30);
        podstawowyRender.setLegendTextSize(30);
        podstawowyRender.setShowLegend(true);
        podstawowyRender.setShowGrid(true);
        podstawowyRender.setZoomButtonsVisible(true);
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), serieDystrybucji, podstawowyRender, "WykresKolowy");
        startActivity(intent);
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

    public void budujTabelke() {
        try {
            Cursor k3 = zb.dajWszystkieOperacje();
            int rows = k3.getCount();
            int cols = k3.getColumnCount();
            tV1.setText(" "+rows);
            k3.moveToFirst();
            for (int i = 0; i < rows; i++) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                row.setPadding(0, 0, 0, 0);
                table_layout.addView(row);
                for (int j = 0; j < cols; j++) {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(14);
                    tv.setPadding(0, 0, 0, 0);
                    if (j==2){
                        tv.setText(k3.getString(j)+ " PLN");
                        row.setTextAlignment(Layout.DIR_LEFT_TO_RIGHT);
                        row.addView(tv);
                    }else {
                        tv.setText(k3.getString(j));
                        if (k3.getString(j).equals("Wydatek")) {
                            row.setBackgroundColor(Color.rgb(255, 102, 102));
                        }
                        row.addView(tv);
                    }
                }
                k3.moveToNext();

                try {
                    table_layout.addView(row);


                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }

    }

    public void budujTabelke(String dataOd, String dataDo, Object typ, Object kategoria) {
        try {
            table_layout.removeAllViews();

            if (dataOd.equals("")) {
                Toast.makeText(getApplicationContext(), "Data od jest błędna", Toast.LENGTH_LONG).show();
            } else if (dataDo.equals("")) {
                Toast.makeText(getApplicationContext(), "Data do jest błędna", Toast.LENGTH_LONG).show();
            } else {

                Cursor k3 = zb.dajWszystkieOperacjeFiltruj(dataOd, dataDo, (String) typ, (String) kategoria,MainActivity.baza);
                int rows = k3.getCount();

                int cols = k3.getColumnCount();
                k3.moveToFirst();
                for (int i = 0; i < rows; i++) {
                    TableRow row = new TableRow(baseContext);
                    row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                    row.setPadding(0, 0, 0, 0);
                    table_layout.addView(row);
                    for (int j = 0; j < cols; j++) {
                        TextView tv = new TextView(baseContext);
                        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(14);
                        tv.setPadding(0, 0, 0, 0);
                        tv.setText(k3.getString(j));
                        if (k3.getString(j).equals("Wydatek")) {
                            row.setBackgroundColor(Color.rgb(255, 102, 102));
                        }
                        row.addView(tv);

                    }
                    k3.moveToNext();

                    try {
                        table_layout.addView(row);
                    } catch (Exception e) {
                    }
                }
                tV1.setText(" "+rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnShowDialog(View view) {
        showInputNameDialog();
    }

    private void showInputNameDialog() {
        FragmentManager manager = getFragmentManager();
        InputDialog inputDialog = new InputDialog();
        inputDialog.setCancelable(true);
        inputDialog.setDialogTitle("Wybierz kryteria");
        inputDialog.show(manager, "Input Dialog");
    }
    public void Wyloguj(View view){
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    public void Saldo(){

        Cursor k1 = zb.SumaPrzychody();
        Cursor k2 = zb.SumaWydatki();
        TextView tvSaldoo=(TextView)findViewById(R.id.tvSaldo);
        k1.moveToFirst();
        k2.moveToFirst();

        String s1=k1.getString(0);
        String s2=k2.getString(0);
        double n1=Double.parseDouble(s1);
        double n2=Double.parseDouble(s2);
       tvSaldoo.setText(df.format(n1-n2)+"PLN");


    }
}
