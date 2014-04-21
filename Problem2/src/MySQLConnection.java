import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
* Class to create database connection
*
* */
public class MySQLConnection {


    private Connection _connection;
    public MySQLConnection(String userName, String password, String dbName, int portNumber) {

        String url= "jdbc:mysql://localhost:"+portNumber+"/";
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver).newInstance();
            this._connection = DriverManager.getConnection(url + dbName, userName, password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    /*
    *
    * Helper function to read rows from excel file and insert into mysql database
    *
    * */

    public void insertToTableFromFile(String filePath, String table)  {


        //Insert statement
        String preQueryStatement = "INSERT INTO  "+table+"  VALUES(?,?,?)";
        PreparedStatement pStmnt = null;
        try{

            pStmnt = _connection.prepareStatement(preQueryStatement);


            ReadFromExcel readFromExcel = new ReadFromExcel(filePath);
            Workbook w = readFromExcel.getWorkbook();

            //We assume date is in the first sheet
            Sheet sheet = w.getSheet(0);


            for(int i = 1; i < sheet.getRows(); i++){

                //Get current row
                try{

                    Cell[] currentRow = sheet.getRow(i);
                    //Insert ID
                    Integer ID = Integer.parseInt(currentRow[0].getContents());
                    pStmnt.setInt(1, ID);

                    //Insert Name
                    pStmnt.setString(2, currentRow[1].getContents());

                    //Insert price
                    Double price = Double.parseDouble(currentRow[2].getContents());
                    pStmnt.setDouble(3, price);

                    //Execute statemt
                    pStmnt.executeUpdate();

                }
                catch (SQLException sqle){

                    sqle.printStackTrace();
                }

            }

        }
        catch (SQLException sqle){

           sqle.printStackTrace();
        }
        finally {

            //Close connections
            try{
                if(pStmnt != null )
                    pStmnt.close();

                if(_connection != null)
                    _connection.close();
            }
            catch (SQLException sqle){

                sqle.printStackTrace();
            }

        }



    }
}
