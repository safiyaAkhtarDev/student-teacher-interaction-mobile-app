package com.safiyaakhtar.application.Activity;

/**
 * Created by SafiyaAkhtar on 1/5/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Login_Password extends AppCompatActivity {
    Button btn_login, btn_forgot;
    Intent intent;
    EditText edt_pass;
    String pass;
    String student_name, student_id, branch;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot = (Button) findViewById(R.id.btn_forgot_pass);
        edt_pass = (EditText) findViewById(R.id.edt_password);
        edt_pass.requestFocus();
        student_id = getIntent().getExtras().getString(Config.KEY_SID);
        branch = getIntent().getExtras().getString(Config.KEY_BRANCH);
        student_name = getIntent().getExtras().getString(Config.KEY_SNAME);
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Password.this, Forget_Password.class);
                intent.putExtra("id", student_id);
                startActivity(intent);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData();
            }
        });
    }

    private void getData() {
        pass = edt_pass.getText().toString().trim();
        if (pass.equals("")) {
            edt_pass.setError("Please Enter Your Password");
            return;
        }
        loading = ProgressDialog.show(Login_Password.this, "Please wait...", "Checking...", false, false);

        String url = Config.MATCH_PASS_URL + student_id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login_Password.this, "   Connection Error   ", Toast.LENGTH_LONG).show();
                        //loading.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Login_Password.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String password = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject getData = result.getJSONObject(0);
            password = getData.getString(Config.KEY_password);
            if (pass.matches(password)) {
                intent = new Intent(Login_Password.this, Home_Activity.class);
                intent.putExtra(Config.KEY_SNAME, student_name);
                intent.putExtra(Config.KEY_SID, student_id);
                intent.putExtra(Config.KEY_BRANCH, branch);
                startActivity(intent);
                finish();
            } else {
                edt_pass.setError("Please Enter Correct Password");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
