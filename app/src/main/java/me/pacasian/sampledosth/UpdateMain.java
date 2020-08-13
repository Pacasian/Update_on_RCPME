package me.pacasian.sampledosth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main);

        //Toast.makeText(this, ""+year, Toast.LENGTH_SHORT).show();
        Button btnRcPme=findViewById(R.id.btn_update_rcpme);
        btnRcPme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateMain.this,RcPme.class));
            }
        });
        Button btnNoti=findViewById(R.id.btnNote);
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateMain.this,MainActivity.class));
            }
        });
    }
}