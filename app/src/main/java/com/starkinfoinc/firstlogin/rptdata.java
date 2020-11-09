package com.starkinfoinc.firstlogin;

import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class rptdata
{
    //Sql Connection string
    Connection con;
    String ip = "3.128.99.39";
    String database = "weighbridge";
    String username = "admin";
    String password = "Admin*399871#";


    public reportstructure[] getData() throws SQLException
    {
        con = connectionclass(username, password, database, ip);

        String query1 = "select id from webapp_DATAMAS order by id desc";
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery(query1);
        rs1.next();
        int count = rs1.getInt("ID")+1;
        rs1.close();
        String query = "select * from webapp_DATAMAS";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        reportstructure[] data = new reportstructure[count];

        while(rs.next())
                {
                    reportstructure row = new reportstructure();
                    {
                        System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+ rs.getString(3)+" "+rs.getString(6)+" "+rs.getDouble(4)+" "+rs.getString(3));
                        row.id = rs.getInt(1);
                        row.Number = row.id;
                        row.Goods = rs.getString(6);
                        row.Weight = rs.getDouble(4);
                        row.Datte = rs.getTimestamp(2);
                        row.vehicleno = rs.getString(3);
                        row.vehicletype = rs.getString(5);
                    }
                    data[rs.getInt(1)] = row;
                }
        return data;
    }

        public Connection connectionclass(String user, String password, String database, String server)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            String ConnectionURL ;
            try
            {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + server +"/"+ database +";user=" + user+ ";password=" + password + ";";
                connection = DriverManager.getConnection(ConnectionURL);
            }
            catch (SQLException se)
            {
                Log.e("error here 1 : ", Objects.requireNonNull(se.getMessage()));
            }
            catch (ClassNotFoundException e)
            {
                Log.e("error here 2 : ", Objects.requireNonNull(e.getMessage()));
            }
            catch (Exception e)
            {
                Log.e("error here 3 : ", Objects.requireNonNull(e.getMessage()));
            }
            return connection;
        }

    }