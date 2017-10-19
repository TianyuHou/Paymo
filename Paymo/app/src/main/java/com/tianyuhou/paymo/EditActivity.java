package com.tianyuhou.paymo;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Yu on 10/13/2017.
 */

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,NavigationView.OnNavigationItemSelectedListener {
    private EditText dateEdit;
    private EditText timeEdit;
    private EditText bodyText;
    private EditText titleEdit;
    private EditText descEdit;
    private EditText amountEdit;
    private TextView category;
    private ToggleButton isCost;
    private Button save;
    private TextView food;
    private TextView drink;
    private TextView social;
    private TextView entertainment;
    private TextView transport;
    private TextView shopping;
    private TextView book;
    private TextView medical;

    private String SQLtime;
    private Date date = new Date();
    private final Calendar calendar = Calendar.getInstance();
    private boolean isEarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        titleEdit = (EditText)findViewById(R.id.titleEdit);
        descEdit = (EditText)findViewById(R.id.descEdit);
        amountEdit = (EditText)findViewById(R.id.amounttext);
        category = (TextView)findViewById(R.id.categorytext);
        isCost = (ToggleButton)findViewById(R.id.toggleButton);
        isCost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    isEarn = true;
                    isCost.setBackgroundColor(Color.rgb(0,105,92));
                }else{
                    isEarn = false;
                    isCost.setBackgroundColor(Color.rgb(198,40,40));
                }
            }
        });



        food = (TextView)findViewById(R.id.foodicon);
        food.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Food");
                setColor(food);
            }
        });

        drink = (TextView)findViewById(R.id.drinkicon);
        drink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Drink");
                setColor(drink);
            }
        });

        social = (TextView)findViewById(R.id.socialicon);
        social.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Social");
                setColor(social);
            }
        });

        entertainment = (TextView)findViewById(R.id.entertainicon);
        entertainment.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Entertainment");
                setColor(entertainment);
            }
        });

        transport = (TextView)findViewById(R.id.transporticon);
        transport.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Transportation");
                setColor(transport);
            }
        });

        medical = (TextView)findViewById(R.id.medicalicon);
        medical.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Medical");
                setColor(medical);
            }
        });

        book = (TextView)findViewById(R.id.bookicon);
        book.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Book");
                setColor(book);
            }
        });

        shopping = (TextView)findViewById(R.id.shoppingicon);
        shopping.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                category.setText("");
                category.setText("Shopping");
                setColor(shopping);
            }
        });

        dateEdit = (EditText)findViewById(R.id.dateEdit);
        timeEdit = (EditText)findViewById(R.id.timeEdit);
        bodyText = (EditText)findViewById(R.id.titleEdit);


        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (hour<10){
            if(minutes < 10){
                timeEdit.setText("0"+hour+":0"+minutes);
                if(second<10){
                    SQLtime = String.valueOf("0"+hour+":0"+minutes+":0"+second);
                }else{
                    SQLtime = String.valueOf("0"+hour+":0"+minutes+":"+second);
                }
            }else{
                timeEdit.setText("0"+hour+":"+minutes);
                if(second<10){
                    SQLtime = String.valueOf("0"+hour+":"+minutes+":0"+second);
                }else{
                    SQLtime = String.valueOf("0"+hour+":"+minutes+":"+second);
                }
            }
        }else{
            if(minutes < 10){
                timeEdit.setText(hour+":0"+minutes);
                if(second<10){
                    SQLtime = String.valueOf(hour+":0"+minutes+":0"+second);
                }else{
                    SQLtime = String.valueOf(hour+":0"+minutes+":"+second);
                }
            }else{
                timeEdit.setText(hour+":"+minutes);
                if(second<10){
                    SQLtime = String.valueOf(hour+":"+minutes+":0"+second);
                }else{
                    SQLtime = String.valueOf(hour+":"+minutes+":"+second);
                }
            }
        }
        if(month+1<10){
            if(day<10){
                dateEdit.setText(year+"-0"+(month+1)+"-0"+day);
            }else{
                dateEdit.setText(year+"-0"+(month+1)+"-"+day);
            }
        }else{
            if(day<10){
                dateEdit.setText(year+"-"+(month+1)+"-0"+day);
            }else{
                dateEdit.setText(year+"-"+(month+1)+"-"+day);
            }
        }

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hideKeyboard(bodyText);
                Date date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(EditActivity.this, year, month, day);
                datePickerDialog.setThemeDark(true);
                datePickerDialog.setAccentColor(Color.rgb(198,40,40));
                datePickerDialog.show(getFragmentManager(), "DateFragment");

            }
        });


         timeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(bodyText);

                Date date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(EditActivity.this, hour, minute, DateFormat.is24HourFormat(EditActivity.this));
                timePickerDialog.setThemeDark(true);
                timePickerDialog.setAccentColor(Color.rgb(198,40,40));
                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });

        save = (Button)findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                float newCost = 0;
                String title = String.valueOf(titleEdit.getText());
                String desc = String.valueOf(descEdit.getText());
                String dairydate = new String(String.valueOf(dateEdit.getText())+" "+SQLtime);
                String dairycategory = category.getText().toString().equals("Choose Category")? "General" : category.getText().toString();
                String cost = String.valueOf(amountEdit.getText());
                if(cost == null || cost.length() == 0){
                    newCost = 0;
                }else{
                    newCost = Float.parseFloat(cost);
                    if(!isEarn){
                        newCost = -newCost;
                    }
                }

                Dairy dairy = new Dairy(title,desc,dairydate,dairycategory,newCost);
                DBhelper db = new DBhelper(getApplicationContext());


                if(db.insertDairy(dairy)){
                    Toast toast = Toast.makeText(getApplicationContext(), "Save Successfully", LENGTH_SHORT);
                    toast.show();
                    try{
                        Intent intent = new Intent(getApplicationContext(),DairyActivity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
//                    Toast toast = Toast.makeText(getApplicationContext(), "Save Failed, Please Re-enter", LENGTH_SHORT);
                    Toast toast = Toast.makeText(getApplicationContext(), dairy.getCategory(), LENGTH_SHORT);
                    toast.show();
                }



            }
        });

    }

    private void setColor(TextView view){
        int color = Color.rgb(255,167,38);
        food.setTextColor(Color.WHITE);
        drink.setTextColor(Color.WHITE);
        social.setTextColor(Color.WHITE);
        entertainment.setTextColor(Color.WHITE);
        transport.setTextColor(Color.WHITE);
        shopping.setTextColor(Color.WHITE);
        book.setTextColor(Color.WHITE);
        medical.setTextColor(Color.WHITE);
        view.setTextColor(color);
        category.setTextColor(color);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void hideKeyboard(EditText et) {

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(monthOfYear+1<10){
            if (dayOfMonth<10){
                dateEdit.setText(year+"-0"+(monthOfYear+1)+"-0"+dayOfMonth);
            }else {
                dateEdit.setText(year+"-0"+(monthOfYear+1)+"-"+dayOfMonth+"-"+year);
            }
        }else {
            if (dayOfMonth<10){
                dateEdit.setText(year+"-"+(monthOfYear+1)+"-0"+dayOfMonth);
            }else {
                dateEdit.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        if(hourOfDay<10){
            if(minute<10){
                timeEdit.setText("0"+hourOfDay+":0"+minute);
                if(calendar.get(Calendar.SECOND)<10){
                    SQLtime = String.valueOf("0"+hourOfDay+":0"+minute+":0"+calendar.get(Calendar.SECOND));
                }else{
                    SQLtime = String.valueOf("0"+hourOfDay+":0"+minute+":"+calendar.get(Calendar.SECOND));
                }
            }else{
                timeEdit.setText("0"+hourOfDay+":"+minute);
                if(calendar.get(Calendar.SECOND)<10){
                    SQLtime = String.valueOf("0"+hourOfDay+":"+minute+":0"+calendar.get(Calendar.SECOND));
                }else{
                    SQLtime = String.valueOf("0"+hourOfDay+":"+minute+":"+calendar.get(Calendar.SECOND));
                }
            }
        }else{
            if(minute<10){
                timeEdit.setText(hourOfDay+":0"+minute);
                if(calendar.get(Calendar.SECOND)<10){
                    SQLtime = String.valueOf(hourOfDay+":0"+minute+":0"+calendar.get(Calendar.SECOND));
                }else{
                    SQLtime = String.valueOf(hourOfDay+":0"+minute+":"+calendar.get(Calendar.SECOND));
                }
            }else{
                timeEdit.setText(hourOfDay+":"+minute);
                if(calendar.get(Calendar.SECOND)<10){
                    SQLtime = String.valueOf(hourOfDay+":"+minute+":0"+calendar.get(Calendar.SECOND));
                }else{
                    SQLtime = String.valueOf(hourOfDay+":"+minute+":"+calendar.get(Calendar.SECOND));
                }
            }
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
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_edit);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
