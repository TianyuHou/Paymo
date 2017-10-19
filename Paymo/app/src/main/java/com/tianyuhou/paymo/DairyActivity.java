package com.tianyuhou.paymo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Yu on 10/13/2017.
 */

public class DairyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private ListView dairylistview;
    private DBhelper dBhelper;
    private DairyAdapter dairyAdapter;
    private Button deletebtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        dBhelper = new DBhelper(this);
        dairylistview = (ListView)findViewById(R.id.dairy);
        deletebtn = (Button)findViewById(R.id.deletebtn);
        loadListView();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dairy);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dairy);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadListView(){
        ArrayList<Dairy> dairy = dBhelper.getDairy();
        if(dairyAdapter == null){
            dairyAdapter = new DairyAdapter(this,R.layout.dairy_row,dairy);
            dairylistview.setAdapter(dairyAdapter);
        }else{
            dairyAdapter.clear();
            dairyAdapter.addAll(dairy);
            dairyAdapter.notifyDataSetChanged();
        }
    }

    public void deleteList(View view){
        final View parent = (View) view.getParent();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.decision);
        alertDialogBuilder.setPositiveButton(R.string.positive_button,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        TextView dairytime = parent.findViewById(R.id.dairytime);
                        String date = String.valueOf(dairytime.getText());
                        dBhelper.deleteDairy(date);
                        loadListView();
                    }
                });
        alertDialogBuilder.setNegativeButton(R.string.negative_button,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_dairy);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


