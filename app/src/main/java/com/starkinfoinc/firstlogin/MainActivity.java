package com.starkinfoinc.firstlogin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    //Layout Declaration
    Button btnlogin;
    EditText tuser,tpass;
    ProgressBar progressBar;
    //Sql Connection string
    Connection con;
    String ip = "3.128.99.39";
    String port = "1433";
    String Classes = "net.sourceforge.jtds.jdbc.Driver";
    String database = "weighbridge";
    String username = "admin";
    String password = "Admin*399871#";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting values from Layout Objects
        btnlogin = (Button)findViewById(R.id.btnlogin);
        tuser = (EditText) findViewById(R.id.txtbox_un);
        tpass = (EditText) findViewById(R.id.txtbox_pw);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");

            }
        });

    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Intent intent =new Intent(MainActivity.this,Menu.class);
                startActivity(intent);
            }



        }

        @Override
        protected String doInBackground(String... params)
        {
            String user = tuser.getText().toString();
            String pass = tpass.getText().toString();
            if(user.trim().equals("")|| pass.trim().equals(""))
                z = "Please enter Username and Password";
            else
            {
                try
                {
                    con = connectionclass(username, password, database, ip);        // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {

                        String query = "DECLARE @responseMessage nvarchar(250);EXEC [dbo].[uspLogin] @puser = N'"+user+"',@pPass = N'"+pass+"',@responseMessage = @responseMessage OUTPUT;SELECT @responseMessage as N'@responseMessage';";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        rs.next();
                        if(rs.getString("@responseMessage").toString().equals("User successfully logged in"))
                        {
                            System.out.println(rs.getString("@responseMessage").toString());
                            z = "Login Successful";
                            isSuccess=true;
                            con.close();
                        }
                        else if (rs.getString("@responseMessage").toString().equals("Incorrect password"))
                        {
                            z = "Invalid Password!";
                            isSuccess=false;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Login!!";
                            isSuccess = false;
                            con.close();
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }


    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null ;
        String ConnectionURL;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server +"/"+ database +";user=" + user+ ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
            System.out.println();
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

}

