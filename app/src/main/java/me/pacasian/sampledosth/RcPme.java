package me.pacasian.sampledosth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class RcPme extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Spinner spinner,spinner2;
Button btn_filter;
    String stMonth;
    private ArrayList<Model_rc_pme> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private RecyclerView recyclerView; //RecyclerView
    private LayoutManager mLayoutManager;
    EditText edYear;
    private boolean success = false; // boolean
    Connection con;
    int btn_count=0;
    String[]  months={"01","02","03","04","05","06","07","08","09","10","11","12"};
    String[] pme_or_rc={"PME","RC"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc_pme);
        spinner=findViewById(R.id.spinner23);
        spinner2=findViewById(R.id.spinner24);
        btn_filter=findViewById(R.id.button);
        edYear=findViewById(R.id.edYear);
        spinner.setOnItemSelectedListener( this);
        spinner2.setOnItemSelectedListener( this);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,months);
        ArrayAdapter<String> adapter2=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,pme_or_rc);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        con=new databaseConnect().ConnectDB();
        if (con == null)
        {
            Toast.makeText(this, "Check Your Internet Access ---x---", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Database Connected --->---", Toast.LENGTH_SHORT).show();
            //sharedPref.putInt("bioNote",0);
            //MainActivity.checkNotification checkNotification= new MainActivity.checkNotification();
            //checkNotification.execute("");
        }

        recyclerView =  findViewById(R.id.recyclerView); //Recylcerview Declaration
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);


        itemArrayList = new ArrayList<Model_rc_pme>();
        /*
        SyncData orderData = new SyncData();
        orderData.execute("");

         */

        btn_filter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                stMonth=spinner.getSelectedItem().toString();
                int year = Calendar.getInstance().get(Calendar.YEAR);
                String yearMonthFormat=year+"-"+stMonth;
                Toast.makeText(RcPme.this, yearMonthFormat+"", Toast.LENGTH_SHORT).show();
                 */
                if(btn_count!=0){
                    itemArrayList.clear();
                    myAppAdapter.notifyDataSetChanged();
                }
                btn_count++;
                SyncData1 syncData1 = new SyncData1();
                syncData1.execute("");





            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("StaticFieldLeak")
    /*private class SyncData extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(RcPme.this, "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            System.out.println("11111111111111111111");
            try
            {
                //Connection Object
                if (con == null)
                {
                    success = false;
                }
                else {
                    // Change below query according to your own database.
                    String query = "SELECT * FROM pmeRc  ;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                System.out.println("222222222222222222222222");
                                itemArrayList.add(new Model_rc_pme(rs.getString("pf"),rs.getString("upPme"),rs.getString("upRc")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg)
        {
            progress.dismiss();
            Toast.makeText(RcPme.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(itemArrayList , RcPme.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {
                    System.out.println("00000000000000000");
                    System.out.println("-->"+ex);
                }

            }
        }
    }

     */
    private class SyncData1 extends AsyncTask<String, String, String>
    {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(RcPme.this, "Synchronising",
                    "RecyclerView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            System.out.println("11111111111111111111");
            try
            {
                //Connection Object
                if (con == null)
                {
                    success = false;
                }
                else {
                    // Change below query according to your own database.

                    stMonth=spinner.getSelectedItem().toString();
                    //int year =
                    String st_rc_or_pme="upPme";
                    if (spinner2.getSelectedItem().toString().equals("PME")){
                        st_rc_or_pme="upPme";
                    }else{
                        st_rc_or_pme="upRc";
                    }
                    String year=edYear.getText().toString();
                    if (year.equals("")){
                        //Toast.makeText(RcPme.this, "year is null ", Toast.LENGTH_SHORT).show();
                        year=(Calendar.getInstance().get(Calendar.YEAR))+"";
                        year=year.trim();
                    }
                    String yearMonthFormat=year+"-"+stMonth;
                    String query = "SELECT * FROM pmeRc where  "+st_rc_or_pme+"  like '"+yearMonthFormat+"%';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next())
                        {
                            try {
                                System.out.println("222222222222222222222222");
                                itemArrayList.add(new Model_rc_pme(rs.getString("pf"),rs.getString("upPme"),rs.getString("upRc")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg)
        {
            progress.dismiss();
            Toast.makeText(RcPme.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false)
            {
            }
            else {
                try
                {
                    myAppAdapter = new MyAppAdapter(itemArrayList , RcPme.this);
                    recyclerView.setAdapter(myAppAdapter);
                } catch (Exception ex)
                {
                    System.out.println("00000000000000000");
                    System.out.println("-->"+ex);
                }

            }
        }
    }
    public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.ViewHolder> {
        private List<Model_rc_pme> values;
        public RcPme context;



        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            // public image title and image url
            public TextView txt_rcpme_pf;
            public TextView txt_rcpme_pmeDate;
            public TextView txt_rcpme_rcDate;
            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                v.setOnClickListener(this);
                layout = v;
                txt_rcpme_pf = (TextView) v.findViewById(R.id.txt_rcpme_pf);
                txt_rcpme_pmeDate = (TextView) v.findViewById(R.id.txt_rcpme_pmeDate);
                txt_rcpme_rcDate = (TextView) v.findViewById(R.id.txt_rcpme_rcDate);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position = " + itemArrayList.get(getLayoutPosition()).upPme, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(RcPme.this,Sumith.class);
                intent.putExtra("idNumber",itemArrayList.get(getLayoutPosition()).upPme+"");
                startActivity(intent);
                //System.out.println(itemArrayList.getClass());
                //String wh,loca,add;
                if(itemArrayList.contains(3)){//check if the list contains the element
                    System.out.println(itemArrayList.get(itemArrayList.indexOf(3)));//get the element by passing the index of the element
                }
                //go through each item if you have few items within recycler view
                if(getLayoutPosition()==0){
                    //Do whatever you want here

                }else if(getLayoutPosition()==1){
                    //Do whatever you want here

                }else if(getLayoutPosition()==2){

                }else if(getLayoutPosition()==3){

                }else if(getLayoutPosition()==4){

                }else if(getLayoutPosition()==5){

                }

                //or you can use For loop if you have long list of items. Use its length or size of the list as
                for(int i = 0; i<itemArrayList.size(); i++){
                    //itemArrayList.set(1)
                    //System.out.println(itemArrayList.get(i));
                    //System.out.println("itemArrayList.get(i)");
                    // System.out.println(());
                    /**
                     */
                }
            }
        }

        // Constructor
        public MyAppAdapter(ArrayList<Model_rc_pme> myDataset, RcPme context)
        {
            values = myDataset;
            this.context = context;
        }

        // Create new views (invoked by the layout manager) and inflates
        @Override
        public MyAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.rc_pme_model_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Binding items to the view
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final Model_rc_pme model_rc_pme = values.get(position);
            holder.txt_rcpme_pf.setText(model_rc_pme.getPf());
            holder.txt_rcpme_pmeDate.setText(model_rc_pme.getPme());
            holder.txt_rcpme_rcDate.setText(model_rc_pme.getRc());

            //Picasso.with(context).load("http://"+classListItems.getImg()).into(holder.imageView);
        }


        // get item count returns the list item count
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
}