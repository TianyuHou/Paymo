package com.tianyuhou.paymo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Yu on 10/13/2017.
 */

public class CostActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener{

    private PieChart costpie;

    private EditText startDateEdit;
    private EditText endDateEdit;
    private EditText bodyText;

    private DatePickerDialog startdatepicker;
    private DatePickerDialog enddatepicker;

    private Date date = new Date();
    private final Calendar calendar = Calendar.getInstance();

    private ArrayList<Cost> cost = new ArrayList<>();


    private Button searchbtn;
    private DBhelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cost);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startDateEdit = (EditText)findViewById(R.id.startdateEdit);
        endDateEdit = (EditText)findViewById(R.id.enddateEdit);
        bodyText = (EditText)findViewById(R.id.startdateEdit);

        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if(month+1<10){
            if(day<10){
                startDateEdit.setText(year+"-0"+(month+1)+"-0"+day);
            }else{
                startDateEdit.setText(year+"-0"+(month+1)+"-"+day);
            }
        }else{
            if(day<10){
                startDateEdit.setText(year+"-"+(month+1)+"-0"+day);
            }else{
                startDateEdit.setText(year+"-"+(month+1)+"-"+day);
            }
        }


        startDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hideKeyboard(bodyText);
                Date date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                startdatepicker = DatePickerDialog.newInstance(CostActivity.this, year, month, day);
                startdatepicker.setThemeDark(true);
                startdatepicker.setAccentColor(Color.rgb(198,40,40));
                startdatepicker.show(getFragmentManager(), "DateFragment");

            }
        });


        if(month+1<10){
            if(day<10){
                endDateEdit.setText(year+"-0"+(month+1)+"-0"+day);
            }else{
                endDateEdit.setText(year+"-0"+(month+1)+"-"+day);
            }
        }else{
            if(day<10){
                endDateEdit.setText(year+"-"+(month+1)+"-0"+day);
            }else{
                endDateEdit.setText(year+"-"+(month+1)+"-"+day);
            }
        }


        endDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hideKeyboard(bodyText);
                Date date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                enddatepicker = DatePickerDialog.newInstance(CostActivity.this, year, month, day);
                enddatepicker.setThemeDark(true);
                enddatepicker.setAccentColor(Color.rgb(198,40,40));
                enddatepicker.show(getFragmentManager(), "DateFragment");

            }
        });

        costpie = (PieChart)findViewById(R.id.costPie);

        String newEnddate = String.valueOf(endDateEdit.getText())+" 23:59:59";
        db = new DBhelper(getApplicationContext());
        cost = db.getCost(newEnddate);
        setPieChart(cost,costpie);
        db.close();


        searchbtn = (Button)findViewById(R.id.searchbtn);
        searchbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String newStart = String.valueOf(startDateEdit.getText()).replaceAll("-","");
                String newEnd = String.valueOf(endDateEdit.getText()).replaceAll("-","");

                int startY = Integer.parseInt(newStart.substring(0,4));
                int endY = Integer.parseInt(newEnd.substring(0,4));
                int startD = Integer.parseInt(newStart.substring(4));
                int endD = Integer.parseInt(newEnd.substring(4));

                if(startY < endY || (startY == endY && startD<=endD)){
                    String newStartdate = String.valueOf(startDateEdit.getText())+" 00:00:00";
                    String newEnddate = String.valueOf(endDateEdit.getText())+" 23:59:59";
                    db = new DBhelper(getApplicationContext());
                    try{
                        cost = db.getCost(newStartdate,newEnddate);
                        setPieChart(cost,costpie);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        db.close();
                    }

                }
            }
        });

    }

    private void setPieChart(ArrayList<Cost> cost,PieChart costpie){
        costpie.setUsePercentValues(false);
        costpie.getDescription().setEnabled(false);
        costpie.setExtraOffsets(5,5,5,5);

        costpie.setDragDecelerationFrictionCoef(0.85f);

        costpie.setDrawHoleEnabled(true);
        costpie.setHoleColor(Color.WHITE);
        costpie.setTransparentCircleRadius(61f);
        costpie.setDrawCenterText(true);

        float total = 0;
        ArrayList<PieEntry> costValues = new ArrayList<>();
        for (Cost c:cost){
            if(c.getName().equals("General") && c.getAmount()==0){
                continue;
            }
            costValues.add(new PieEntry((-c.getAmount()),c.getName()));
            total+=(-c.getAmount());
        }
        if(total == 0){
            costValues.add(new PieEntry(0,"General"));
        }
        costpie.setCenterText("Total Cost \n\n"+total);
        costpie.setCenterTextColor(Color.rgb(0,121,107));
        costpie.setCenterTextSize(19f);
        costpie.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet costDataSet = new PieDataSet(costValues,null);
        costDataSet.setSliceSpace(3f);
        costDataSet.setSelectionShift(5f);
        costDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData costData = new PieData((costDataSet));
        costData.setValueTextSize(10f);
        costData.setValueTextColor(Color.WHITE);

        costpie.setData(costData);
        Legend mLegend = costpie.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        mLegend.setTextColor(Color.WHITE);
        mLegend.setTextSize(15f);
    }


    public void hideKeyboard(EditText et) {

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cost);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        } else if (id == R.id.nav_dairy) {
            Intent intent = new Intent(getApplicationContext(),DairyActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cost) {
            Intent intent = new Intent(getApplicationContext(),CostActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_earn) {
            Intent intent = new Intent(getApplicationContext(), EarnActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cost);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view.equals(startdatepicker)){
            if(monthOfYear+1<10){
                if(dayOfMonth<10){
                    startDateEdit.setText(year+"-0"+(monthOfYear+1)+"-0"+dayOfMonth);
                }else{
                    startDateEdit.setText(year+"-0"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }else{
                if(dayOfMonth<10){
                    startDateEdit.setText(year+"-"+(monthOfYear+1)+"-0"+dayOfMonth);
                }else{
                    startDateEdit.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }

        }else if(view.equals(enddatepicker)){
            if(monthOfYear+1<10){
                if(dayOfMonth<10){
                    endDateEdit.setText(year+"-0"+(monthOfYear+1)+"-0"+dayOfMonth);
                }else{
                    endDateEdit.setText(year+"-0"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }else{
                if(dayOfMonth<10){
                    endDateEdit.setText(year+"-"+(monthOfYear+1)+"-0"+dayOfMonth);
                }else{
                    endDateEdit.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }
        }
    }

}
