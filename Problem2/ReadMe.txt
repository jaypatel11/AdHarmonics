Instructions:
1. To compile, you need to add the MySQL-Connector and JXL libaries to the build path provided in lib folder
2. Solution.java has the main
3. Before running, you can edit the prefs.xml file to enter username, password, database name, table name and port number to connect to MySQL 

Assumptions:
1. The program is designed to read table from excel of type (int, String, Double) and insert into mySQL table of type (int, String, Double). 
2. The database connectivity is to localhost

Design:

1. To process Excel files, I used external library called JExcelAPI. 
2. When you choose folder, a dialog appears, which shows which folder you selected. From the selected folder, only ".xls" files are considered. 
   When you click ok, a number of threads equal to the number of xls files to be processed are spawned. Each thread has its own connection instance to the database. 
3. Another thread is spawned, which checks if all the threads processing xls files have finished. If done, this thread pops up a dialog box 
   stating the task is finished