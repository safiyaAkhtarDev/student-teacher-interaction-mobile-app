package com.safiyaakhtar.application.Activity;

/**
 * Created by SafiyaAkhtar on 1/5/2017.
 */

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.Button;

        import com.safiyaakhtar.application.R;

public class Cradits extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.cradits);
       // button = (Button) findViewById(R.id.btn_back);

        }
        }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Cradits.this, Home_Activity.class);
//                startActivity(intent);
//            }
//        });
