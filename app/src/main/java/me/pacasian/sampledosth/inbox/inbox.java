package me.pacasian.sampledosth.inbox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.pacasian.sampledosth.R;
import me.pacasian.sampledosth.databaseConnect;

public class inbox extends AppCompatActivity {

    TextView actionTittle;
    ImageView actionIcon;
    EditText pfET,subjectET,bodyET;
    Button sendBtn;
    String pf,subject,body,dateSend;
    Connection con;
    String[] myList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_inbox);
        con =new databaseConnect().ConnectDB(); // Connect to database

        pfET=findViewById(R.id.inboxPf);
        subjectET=findViewById(R.id.inboxSubject);
        bodyET=findViewById(R.id.inboxBody);
        sendBtn=findViewById(R.id.inboxSendBtn);
        pfET.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (pfET.getText().toString().length()==11){
                    GetData getData=new GetData();
                    getData.execute();
                }
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pf=pfET.getText().toString();
                subject=subjectET.getText().toString();
                body=bodyET.getText().toString();
                if(TextUtils.isEmpty(pf)){
                    pfET.setError("Please enter PF");
                    pfET.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(subject)) {
                    subjectET.setError("Please enter Subject");
                    subjectET.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(body))
                {
                    bodyET.setError("Please enter body");
                    bodyET.requestFocus();
                    return;
                }
                InsertData insertData=new InsertData();
                insertData.execute("");
            }
        });
    }

    public  class InsertData extends AsyncTask<String,Integer,String>
    {
        String message = "Couldn't connect to server, try again later";
        Boolean isSuccess = false;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(inbox.this, "Synchronising",
                    "Inserting! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {

            try
            {

                if (con == null)
                {
                    message = "Check Your Internet Access!";
                }
                else
                {
                    //GETTING SENDING DATE AND TIME
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    dateSend = sdf.format(new Date());
                    //END

                    //FOR PUSHING DATA TO DB
                    String double1 = subject.replace("'","''").replace("\"","\\\"");

                    String double2 = body.replace("'","''").replace("\"","\\\"");

                    String query ="INSERT INTO inbox(pf,subject,body,dateSend,tick,admin) values("+pf+",'"+double1+"','"+double2+"','"+dateSend+"','0','1');";
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    isSuccess=true;
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
            progress.dismiss();
            if(isSuccess)
            {
                clearEntries();
                Toast.makeText(inbox.this , "Inbox send successfully" , Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(inbox.this,message, Toast.LENGTH_LONG).show();
        }

    }

    private void clearEntries() {
        pfET.setText("");
        subjectET.setText("");
        bodyET.setText("");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }
    public void onGroupItemClick(MenuItem item) {
        switch (item.getItemId()) {
            /*
            case R.id.history:
                Intent intent = new Intent(inbox.this, inbox_listView.class);
                intent.putExtra("FROM","history");
                startActivity(intent);
                break;

             */
        }
    }
    
    private class GetData extends AsyncTask<String, String, String>
    {
        String msg = "Couldn't connect to server, try again later";
        ProgressDialog progress;
        boolean success;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(inbox.this, "Synchronising",
                    "Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)
        {
            try
            {

                if (con == null)
                {
                    success = false;
                }
                else {
                    String query="";

                        query = "SELECT * FROM bioData WHERE pf='"+pfET.getText().toString().trim()+"';";

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null)
                    {
                        while (rs.next())
                        {
                            try {
                                myList = new String[]{"PF Number : "+rs.getString("pf"),"Name : "+rs.getString("name"),"Sex : "+ rs.getString("sex"),
                                        "Date of Birth : "+rs.getString("dob"),"Contact info : "+rs.getString("contactNo"),
                                        "Emergency No. : "+rs.getString("emergencyNo"), "State : "+rs.getString("state"),
                                        "Designation : "+rs.getString("designation"), "Current Station : "+rs.getString("currentStation"),
                                        "Section : "+rs.getString("section")};
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        success = true;
                    } else {
                        success = false;
                    }
                }
            } catch (Exception e)
            {

                msg = e.getMessage();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my listview
        {
            progress.dismiss();
            if (!success)
            {
                Toast.makeText(inbox.this, msg, Toast.LENGTH_LONG).show();
            }
            else {
                String st="";
                for(int i=0;i<myList.length;i++){
                    st=st+myList[i]+"\n\n";
                }
                //System.out.println(st);
                AlertDialog alertDialog = new AlertDialog.Builder(inbox.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Employee Information ")
                        .setMessage(st)

                        .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                //Toast.makeText(getApplicationContext(),",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        }
    }

}
/*

et1.addTextChangedListener(new TextWatcher() {
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // TODO Auto-generated method stub
    }

    @Override
    public void afterTextChanged(Editable s) {

        // TODO Auto-generated method stub
    }
});

 */