package com.safiyaakhtar.application.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by SafiyaAkhtar on 1/5/2017.
 */

public class Login extends Activity {

    EditText edt_reg_no;
    Button btn_next;
    Intent intent;
    String sid;
    private ProgressDialog loading;
    // SessionManagger session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edt_reg_no = (EditText) findViewById(R.id.edt_registration_num);
        btn_next = (Button) findViewById(R.id.btn_next);
        edt_reg_no.requestFocus();
        sid = edt_reg_no.getText().toString().trim();
        //  session = new SessionManagger(getApplicationContext());
        btn_next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getData();

                                        }
                                    }
        );
    }

    private void getData() {
        sid = edt_reg_no.getText().toString().trim();
        if (sid.equals("")) {
            edt_reg_no.setError("Please Enter Your Registration Number");
            return;
        }
        loading = ProgressDialog.show(Login.this, "Please wait...", "Checking...", false, false);

        String url = Config.DATA_URL + edt_reg_no.getText().toString().trim();

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
                        Toast.makeText(Login.this, "   Connection Error   ", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String student_id = "";
        String student_name = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject getData = result.getJSONObject(0);
            student_id = getData.getString(Config.KEY_SID);
            student_name = getData.getString(Config.KEY_SNAME);
            String branch = getData.getString(Config.KEY_BRANCH);
            String check = getData.getString(Config.KEY_CHECK);
            // session.createLoginSession(sid);
            if (sid.matches(student_id)) {
                if (check.matches(String.valueOf(0))) {
                    intent = new Intent(Login.this, Login_Set_Password.class);
                    intent.putExtra(Config.KEY_SNAME, student_name);
                    intent.putExtra(Config.KEY_SID, student_id);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(Login.this, Login_Password.class);
                    intent.putExtra(Config.KEY_SNAME, student_name);
                    intent.putExtra(Config.KEY_SID, student_id);
                    intent.putExtra(Config.KEY_BRANCH, branch);
                    startActivity(intent);
                    finish();
                }
            } else {
                edt_reg_no.setError("Please Enter Correct Registration Number");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
