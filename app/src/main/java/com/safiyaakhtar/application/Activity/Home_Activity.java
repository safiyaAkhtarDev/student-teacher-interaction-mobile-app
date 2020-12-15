package com.safiyaakhtar.application.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.safiyaakhtar.application.R;
import com.safiyaakhtar.application.Webservice.Config;

public class Home_Activity extends AppCompatActivity {
    Spinner spinner;
    TextView txt_name,txt_reg_num,txt_student_name;
    Intent intent;
    String year;
    String student_name, student_id, branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        //********get Intent values**********
        student_id = getIntent().getExtras().getString(Config.KEY_SID);
        branch = getIntent().getExtras().getString(Config.KEY_BRANCH);
        student_name = getIntent().getExtras().getString(Config.KEY_SNAME);
        //**********reference From XML**************
//        txt_student_name= (TextView) findViewById(R.id.txt_set_student_name);
//        txt_student_name.setText(student_name);

        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_name.setText(student_name);
//        txt_reg_num= (TextView) findViewById(R.id.txt_registration_num);
//        txt_reg_num.setText(student_id);
        spinner = (Spinner) findViewById(R.id.spinner_year);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //*********navigation bar***********
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_credits) {
                    intent = new Intent(Home_Activity.this, Cradits.class);
                    startActivity(intent);
                } else if (id == R.id.nav_aboutUs) {
                    intent = new Intent(Home_Activity.this, About_Us.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    intent = new Intent(Home_Activity.this, Login.class);
                    startActivity(intent);
                    finish();
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = String.valueOf(spinner.getSelectedItemPosition());
                if (year.equals("0")) {
                    Toast.makeText(Home_Activity.this, "Please Select Your Year", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(Home_Activity.this, Semester.class);
                    intent.putExtra("itemId", year);
                    intent.putExtra(Config.KEY_SNAME, student_name);
                    intent.putExtra(Config.KEY_SID, student_id);
                    intent.putExtra(Config.KEY_BRANCH, branch);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //***********navigation method overided************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


}




