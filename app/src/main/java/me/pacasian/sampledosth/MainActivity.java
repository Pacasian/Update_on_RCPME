package me.pacasian.sampledosth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import me.pacasian.sampledosth.inbox.inbox;

public class MainActivity extends AppCompatActivity {
LinearLayout l_inbox,l_grievance,l_bio;
TextView txt_inbox,txt_grievance,txt_bio;
    int[] k=new int[3];
    private sharedPreference sharedPref;
    Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=new databaseConnect().ConnectDB();
        l_inbox=findViewById(R.id.inbox);
        l_grievance=findViewById(R.id.grievance);
        l_bio=findViewById(R.id.bio);
        txt_inbox=findViewById(R.id.txt_note_inbox);
        txt_grievance=findViewById(R.id.txt_note_gri);
        txt_bio=findViewById(R.id.txt_note_bio);
        sharedPref=sharedPreference.getInstance(getApplicationContext());
        if (con == null)
        {
            Toast.makeText(this, "Check Your Internet Access ---x---", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Database Connected --->---", Toast.LENGTH_SHORT).show();
            //sharedPref.putInt("bioNote",0);
            checkNotification checkNotification= new checkNotification();
            checkNotification.execute("");
        }
        /*
        sharedPref.putInt("BIOFILLED",0);m
         */
    l_inbox.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, inbox.class));
            txt_inbox.setVisibility(View.INVISIBLE);
        }
    });
    l_grievance.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,Sumith.class));
            txt_grievance.setVisibility(View.INVISIBLE);
        }
    });
    l_bio.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,Sumith.class));
            txt_bio.setVisibility(View.INVISIBLE);
        }
    });
    }
    public  class checkNotification extends AsyncTask<String,Integer,String>
    {
        String message = "Can't connect to server, try again";
        Boolean isSuccess = false;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            //progress = ProgressDialog.show(MainActivity.this, "Synchronising", "Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {

            try
            {
                System.out.println("===============1");
                if (con == null)
                {
                    message = "Check Your Internet Access!";
                }
                else
                {
                    String[] query =new String[]{"SELECT TOP 1 * FROM bioData ORDER BY id DESC ;","SELECT TOP 1 * FROM grievances ORDER BY id DESC ;","SELECT TOP 1 * FROM inbox ORDER BY id DESC ;"};
                    for(int i=0;i<query.length;i++){
                        Statement stmt = con.createStatement();
                        ResultSet resultSet = stmt.executeQuery(query[i]);
                        if(resultSet.next()){
                            isSuccess=true;
                            System.out.println("==============="+resultSet.getInt(1));
                            k[i] = Integer.parseInt(resultSet.getString("id"));
                            //k=resultSet.getInt(1);
                            //Toast.makeText(MainActivity.this, ""+resultSet.getInt(1), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            isSuccess=false;
                            message="Invalid credentials";
                        }
                    }

                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
           // progress.dismiss();
            if(isSuccess)
            {
                System.out.println("******"+k[0]+" "+k[1]+" "+k[2]+"*****");
                Toast.makeText(MainActivity.this, k[0]+" "+k[1]+" "+k[2], Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, sharedPref.getInt("bioNote")+" ", Toast.LENGTH_SHORT).show();
                if(sharedPref.getInt("bioNote")!=k[0]){
                    txt_bio.setVisibility(View.VISIBLE);
                    sharedPref.putInt("bioNote",k[0]);
                    Toast.makeText(MainActivity.this, sharedPref.getInt("bioNote")+" ", Toast.LENGTH_SHORT).show();
                }
                if(sharedPref.getInt("griNote")!=k[1]){
                    txt_grievance.setVisibility(View.VISIBLE);
                    sharedPref.putInt("griNote",k[1]);
                    Toast.makeText(MainActivity.this, sharedPref.getInt("griNote")+" ", Toast.LENGTH_SHORT).show();
                }
                if(sharedPref.getInt("inboxNote")!=k[2]){
                    txt_inbox.setVisibility(View.VISIBLE);
                    sharedPref.putInt("inboxNote",k[2]);
                    Toast.makeText(MainActivity.this, sharedPref.getInt("inboxNote")+" ", Toast.LENGTH_SHORT).show();
                }

                //CHECK IF BIO ALREADY FILLED
               // Toast.makeText(MainActivity.this, "Done"+k, Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
        }

    }
}