package com.safiyaakhtar.application.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class Forget_Password extends AppCompatActivity {
    EditText edt_new_pass, edt_confirm_password, edt_ans;
    Button btn_cancel, btn_update;
    String new_pass, confirm_pass, answer;
    Spinner sp_question;
    String q;
    String Student_id;
    Validation validation;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);
        edt_new_pass = (EditText) findViewById(R.id.edt_new_password);
        edt_new_pass.requestFocus();
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);
        edt_ans = (EditText) findViewById(R.id.edt_answer);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_update = (Button) findViewById(R.id.btn_update);
        sp_question = (Spinner) findViewById(R.id.spquestion);
        Student_id = getIntent().getStringExtra("id");
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forget_Password.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_pass = edt_new_pass.getText().toString().trim();
                confirm_pass= edt_confirm_password.getText().toString().trim();
                answer = edt_ans.getText().toString().trim();

                /*passwords*/

                if (new_pass.length()!=7 &&confirm_pass.length()!=7) {
                    edt_new_pass.setError("Please Check The Password");
                    edt_confirm_password.setError("Please Check The Password");
                } else if (!new_pass.equals(confirm_pass)) {
                    edt_new_pass.setError("Passwords Didn't Matched");
                    edt_confirm_password.setError("Passwords Didn't Matched");
                } else {
                        FetchData();
                    }


            }
        });
    }

    private void FetchData() {
        new_pass = edt_new_pass.getText().toString().trim();
        loading = ProgressDialog.show(Forget_Password.this, "Please wait...", "Checking...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPDATE_PASS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        showJSON(response);
                        //  Toast.makeText(Semester.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Forget_Password.this, "Network Connection Problem", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.KEY_SID, Student_id);
                params.put(Config.KEY_password, new_pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        q = String.valueOf(sp_question.getSelectedItemPosition());
        answer = edt_ans.getText().toString().trim();
        String question = "";
        String answerc = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject getData = result.getJSONObject(0);
            question = getData.getString(Config.KEY_Q_ONE);
            answerc = getData.getString(Config.KEY_ANS_ONE);
            if (question.equals(q) && answerc.equals(answer)) {
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Forget_Password.this,Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Your Selected Ques And Ans Did not Match", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
