package com.safiyaakhtar.application.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.safiyaakhtar.application.R;
import com.safiyaakhtar.application.Webservice.Config;

import java.util.HashMap;
import java.util.Map;

import static com.safiyaakhtar.application.R.id.sp_question_one;

public class Login_Set_Password extends AppCompatActivity {
    TextView txtstudent_name;
    EditText edt_password, edt_confirm_password, edt_ans_one, edt_ans_two;
    Spinner sp_ques_one, sp_ques_two;
    Button btn_done,btn_reset;
    Validation validation;
    String student_name, confirm_password, ans_one, ans_two, student_id, branch, password, ques_one, ques_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set_password);
        // creating reference from xml
        txtstudent_name = (TextView) findViewById(R.id.txt_set_student_name);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password.requestFocus();
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);
        edt_ans_one = (EditText) findViewById(R.id.edt_answer_one);
        edt_ans_two = (EditText) findViewById(R.id.edt_answer_two);
        sp_ques_one = (Spinner) findViewById(sp_question_one);
        sp_ques_two = (Spinner) findViewById(R.id.sp_question_two);
        btn_done = (Button) findViewById(R.id.btn_done);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        //focus set
        edt_password.requestFocus();
        validation = new Validation();
        //get intent passes values
        student_name = getIntent().getExtras().getString(Config.KEY_SNAME);
        branch = getIntent().getExtras().getString(Config.KEY_BRANCH);
        student_id = getIntent().getExtras().getString(Config.KEY_SID);
btn_reset.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        edt_ans_one.setText(" ");
        edt_ans_two.setText(" ");
        edt_password.setText(" ");
        edt_confirm_password.setText(" ");
    }
});
        sp_ques_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ques_one = String.valueOf(sp_ques_one.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_ques_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ques_two = String.valueOf(sp_ques_two.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtstudent_name.setText(student_name);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*get String*/
                password = edt_password.getText().toString().trim();
                confirm_password = edt_confirm_password.getText().toString().trim();
                ans_one = edt_ans_one.getText().toString().trim();
                ans_two = edt_ans_two.getText().toString().trim();

                /*passwords*/

                if (validation.validatePass(password)) {
                    edt_password.setError("Please Check The Password");
                    edt_confirm_password.setError("Please Check The Password");
                } else if (!password.equals(confirm_password)) {
                    edt_password.setError("Passwords Didn't Matched");
                    edt_confirm_password.setError("Passwords Didn't Matched");
                } else

         /*answers*/

                    if (sp_ques_one.getSelectedItemId() == 0) {
                        Toast.makeText(Login_Set_Password.this, "Please Select A Question in Question 1", Toast.LENGTH_SHORT).show();
                    } else if (validation.answer(ans_one)) {
                        edt_ans_one.setError("Answer the Question");
                    } else if (sp_ques_two.getSelectedItemId() == 0) {
                        Toast.makeText(Login_Set_Password.this, "Please Select A Question in Question 2", Toast.LENGTH_SHORT).show();
                    } else if (validation.answer(ans_two)) {
                        edt_ans_two.setError("Answer The Question");
                    } else {
                        setPassword();
                    }
            }
        });
    }

    private void setPassword() {
        password = edt_password.getText().toString().trim();
        ans_one = edt_ans_one.getText().toString().trim();
        ans_two = edt_ans_two.getText().toString().trim();
        confirm_password = edt_confirm_password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SET_PASS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Login_Set_Password.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login_Set_Password.this, "Connection Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.KEY_SNAME, student_name);
                params.put(Config.KEY_password, password);
                params.put(Config.KEY_ANS_ONE, ans_one);
                params.put(Config.KEY_ANS_TWO, ans_two);
                params.put(Config.KEY_Q_ONE, ques_one);
                params.put(Config.KEY_Q_TWO, ques_two);
                params.put(Config.KEY_CHECK, "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Intent intent=new Intent(Login_Set_Password.this,Login.class);
        startActivity(intent);
        finish();
    }

}
