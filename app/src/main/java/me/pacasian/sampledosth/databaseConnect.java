package me.pacasian.sampledosth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnect extends Activity {

    sharedPreference sharedPref;
    //ESTABLISHES CONNECTION WITH MS SQL DATABASE
    //can be called from any class

    private static String ipFromJson="json_not_received";

    @SuppressLint("NewApi")
    public Connection ConnectDB()
    {
        String ip="192.168.43.26";
        //25.16.159.232,1600
        new JsonTask().execute("https://hits-rail.herokuapp.com/static/data.json");
        try {
            ip=ipFromJson;
            sharedPref = sharedPreference.getInstance(this);
            //ip=sharedPref.get("ip");

            if (ip.equals("json_not_received")){

                ip = sharedPref.get("ip");
            }else{
                //ip=ipFromJson;
                sharedPref.put("ip",ipFromJson);
            }


        }catch (NullPointerException e){
            System.out.println("_________________Error in connecting the server IP____________");
        }
        System.out.println(ip);
         ip="25.16.159.232";
        String port = "1600";
        String Classes = "net.sourceforge.jtds.jdbc.Driver";
        String database = "dosthDatabase";
        String username = "dosth_db_salem";
        String password = "Sumi3653";


        //ip="192.168.43.26";
        //String port = "1433";
        //String Classes = "net.sourceforge.jtds.jdbc.Driver";
        //String database = "dosthDatabase";
        //String username = "ajay";
        //String password = "ajay";

        //String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
        String url = "jdbc:jtds:sqlserver://"+"dosthdatabase.database.windows.net"+"/"+database;

        System.out.println(url);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try
        {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            //textView.setText("DATABASE CONNECTED");
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
    private static class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                JSONObject json = null;
                try {
                    json = new JSONObject(buffer.toString());
                    String aJsonString = json.getString("ip");
                    System.out.println("-----------------------");
                    System.out.println(aJsonString);
                    ipFromJson=aJsonString;
                    //sharedInfo.put("ip",aJsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return buffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            //txtJson.setText(result);


        }
    }
}
