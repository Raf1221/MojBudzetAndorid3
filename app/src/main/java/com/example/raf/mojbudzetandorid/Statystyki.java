package com.example.raf.mojbudzetandorid;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class Statystyki extends ActionBarActivity {
    public static TableLayout table_layout;
    ZarzadcaBazy zb = new ZarzadcaBazy(this);
    Spinner Operacjespin;
    Spinner KategorieStatykispin;
    public static boolean czyWlaczanyDialog = true;
    public static Context baseContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        final Button button = (Button) findViewById(R.id.BtnWykres);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
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
        Toast.makeText(Statystyki.this, "liczba rows", Toast.LENGTH_SHORT).show();
    }

    private void openChart() {
        String[] code = new String[]{
                "Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
                "IceCream Sandwich", "Jelly Bean"
        };

        double[] dystrybucja = {3.9, 12.9, 55.8, 1.9, 23.7, 1.8};

        int[] kolory = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                Color.YELLOW};
        CategorySeries serieDystrybucji = new CategorySeries("Dystrybucje androida na 01.10.2012");
        for (int i = 0; i < dystrybucja.length; i++) {
            serieDystrybucji.add(code[i], dystrybucja[i]);
        }
        DefaultRenderer podstawowyRender = new DefaultRenderer();
        for (int i = 0; i < dystrybucja.length; i++) {
            SimpleSeriesRenderer renderSerii = new SimpleSeriesRenderer();
            renderSerii.setColor(kolory[i]);
            renderSerii.setDisplayChartValues(true);
            podstawowyRender.addSeriesRenderer(renderSerii);
        }
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
            k3.moveToFirst();


            for (int i = 0; i < rows; i++) {

                TableRow row = new TableRow(this);
                // row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
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
                    // Toast.makeText(Statystyki.this, "blad "+e, Toast.LENGTH_SHORT).show();
                }

                //table_layout.addView(row);
            }
        } catch (Exception e) {
            //Toast.makeText(Statystyki.this, "blad "+e, Toast.LENGTH_SHORT).show();
        }

    }
    /////////////////////////////////////////////////////////////////////////////////////


    public void btnShowDialog(View view) {
        showInputNameDialog();

    }


    private void showInputNameDialog() {
        FragmentManager manager = getFragmentManager();
        InputDialog inputDialog = new InputDialog();
        inputDialog.setCancelable(false);
        inputDialog.setDialogTitle("Wybierz kryteria");
        inputDialog.show(manager, "Input Dialog");

       /* InputNameDialogFragment inputNameDialog = new InputNameDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setDialogTitle("Enter Name");
        inputNameDialog.show(fragmentManager, "Input Dialog");*/
    }

    public void btnCloseDialog(View view) {

    }

    private void closeInputDialog() {

    }

}
