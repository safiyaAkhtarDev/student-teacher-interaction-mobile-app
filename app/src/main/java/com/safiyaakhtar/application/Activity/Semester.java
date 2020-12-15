package com.safiyaakhtar.application.Activity;

/**
 * Created by SafiyaAkhtar on 1/5/2017.
 */

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.safiyaakhtar.application.R;
import com.safiyaakhtar.application.Webservice.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Semester extends Activity {
    DownloadManager downloadManager;
    RelativeLayout relativeLayout;
    Button btn_even, btn_odd, btn_syllabus, btn_dept_notice, btn_Assignment, btn_notes, btn_ques_paper;
    String student_name, student_id, branch, year;
    Intent intent;
    int i;
String s;
    String sem[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semester_activity);
        //********reference from XML*********
        btn_even = (Button) findViewById(R.id.sem_even);
        btn_syllabus = (Button) findViewById(R.id.btn_syllabus);
        btn_dept_notice = (Button) findViewById(R.id.btn_dept_notice);
        btn_Assignment = (Button) findViewById(R.id.btn_assignments);
        btn_notes = (Button) findViewById(R.id.btn_notes);
        btn_ques_paper = (Button) findViewById(R.id.btn_question_paper);
        btn_odd = (Button) findViewById(R.id.sem_odd);
        relativeLayout = (RelativeLayout) findViewById(R.id.btns);
        relativeLayout.setVisibility(View.INVISIBLE);

        //********GEt Value of Intent*********
        student_name = getIntent().getExtras().getString(Config.KEY_SNAME);
        branch = getIntent().getExtras().getString(Config.KEY_BRANCH);
        student_id = getIntent().getExtras().getString(Config.KEY_SID);
        year = getIntent().getExtras().getString("itemId");

        //*******get semester by button id********
        btn_even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.equals("1")) {
                    i = 1;
                } else if (year.equals("2")) {
                    i = 3;
                } else if (year.equals("3")) {
                    i = 5;
                } else if (year.equals("4")) {
                    i = 7;
                }

                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
        btn_odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.equals("1")) {
                    i = 0;
                } else if (year.equals("2")) {
                    i = 2;
                } else if (year.equals("3")) {
                    i = 4;
                } else if (year.equals("4")) {
                    i = 6;
                }
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
        btn_syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData("syllabus");
            }
        });
        btn_dept_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData("notice");
            }
        });
        btn_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData("book");
            }
        });
        btn_ques_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchData("qpaper");
            }
        });
        btn_Assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Semester.this, No_Data.class);
                startActivity(intent);
            }
        });
    }

    private void FetchData(final String check) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response,check);
                        //  Toast.makeText(Semester.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Semester.this, "Network Connection Problem", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.KEY_BRANCH, branch);
                params.put(Config.KEY_YEAR, year);
                params.put(Config.KEY_SEM, sem[i]);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response,String s) {
        String syllabus = "";
        String syllabus_file = "";
        String notice = "";
        String notice_file = "";
        String book = "";
        String book_file = "";
        String question = "";
        String question_file = "";
        String date = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject getData = result.getJSONObject(0);
            question = getData.getString(Config.KEY_Question);
            notice = getData.getString(Config.KEY_Notice);
            book = getData.getString(Config.KEY_Book);
            syllabus = getData.getString(Config.KEY_Syllabus);
            question_file = getData.getString(Config.KEY_Question_File);
            notice_file = getData.getString(Config.KEY_Notice_File);
            date = getData.getString(Config.KEY_Date);
            book_file = getData.getString(Config.KEY_Book_File);
            syllabus_file = getData.getString(Config.KEY_Syllabus_File);
            intent = new Intent(Semester.this, Fetch_Syllabus.class);
            intent.putExtra(Config.KEY_Syllabus, syllabus);
            intent.putExtra(Config.KEY_Book, book);
            intent.putExtra(Config.KEY_Notice, notice);
            intent.putExtra("id", s);
            intent.putExtra(Config.KEY_Question, question);
            intent.putExtra(Config.KEY_Syllabus_File, syllabus_file);
            intent.putExtra(Config.KEY_Notice_File, notice_file);
            intent.putExtra(Config.KEY_Date, date);
            intent.putExtra(Config.KEY_Question_File, question_file);
            intent.putExtra(Config.KEY_Book_File, book_file);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
