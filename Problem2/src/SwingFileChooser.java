import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

/*
*
* Class to create a GUI which helps select a folder and spawn threads to process the xls files
*
*
* */
public class SwingFileChooser extends JFrame{

    private static final String PREF_FILE = "PreferenceExample";

    private JFileChooser _fileChooser = new JFileChooser();
    //Chooses the prefs.xml file to extract information to connect to database
    private Preferences _fPrefs = Preferences.userRoot().node(PREF_FILE);


    public SwingFileChooser(String title){

        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Create a filter to list only xls files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS Files","xls") ;

        //New Panel
        JPanel pnl = new JPanel();
        pnl.setLayout(new GridLayout(2, 1));


        //Setup folder button


        JButton folderSelectionButton = new JButton("Select Folder");
        //Only directories
        _fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //Add filter
        _fileChooser.setFileFilter(filter);

        //Set action listener
        ActionListener folderButtonActionListener;

        folderButtonActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                switch (_fileChooser.showOpenDialog(SwingFileChooser.this))
                {
                    case JFileChooser.APPROVE_OPTION:
                        JOptionPane.showMessageDialog(SwingFileChooser.this, "Selected: "+
                                _fileChooser.getSelectedFile(),
                                "SwingFileChooser",
                                JOptionPane.OK_OPTION);

                        insertToTableFromSelectedFolder();

                        break;

                    case JFileChooser.CANCEL_OPTION:

                        break;

                    case JFileChooser.ERROR_OPTION:
                        JOptionPane.showMessageDialog(SwingFileChooser.this, "Error",
                                "SwingFileChooser",
                                JOptionPane.OK_OPTION);
                }
            }


        };
        // Add listener
        folderSelectionButton.addActionListener(folderButtonActionListener);
        pnl.add(folderSelectionButton);

        setContentPane(pnl);

        pack();
        setVisible(true);
    }

    private void insertToTableFromSelectedFolder() {


        //When the folder is chosen, list all files from the directory which have .xls format
        File[] filesInDirectory = _fileChooser.getSelectedFile().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xls");

            }
        });
        //Spawn threads equal to the number of xls files to be processed. Each job creates a new connection to the database
        final ExecutorService threadPool = Executors.newFixedThreadPool(filesInDirectory.length);

        //For each file, spawn a thread
        for(final File file: filesInDirectory){

            threadPool.submit(new Runnable() {
                public void run() {
                    insertToTableFromSelectedFile(file.getAbsolutePath());
                }
            });

        }

        threadPool.shutdown();

        //Spawn a thread which waits for the jobs to complete and display dialog box with completion message
        ExecutorService taskCompletionDialogThread = Executors.newSingleThreadExecutor();
        taskCompletionDialogThread.submit(new Runnable() {
            @Override
            public void run() {

                try {

                    threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(SwingFileChooser.this, "Finished loading to database! ",
                        "SwingFileChooser",
                        JOptionPane.OK_OPTION);

            }
        });



    }

    /*
    * Function to create an instance of SwingFileChooser with a title for the window
    *
    * */
    public static void executeDisplay(final String title){

        Runnable r = new Runnable(){
            @Override
            public void run() {
                new SwingFileChooser(title);
            }
        };
        EventQueue.invokeLater(r);

    }

    /*
    *  Function which takes the path of excel and give it to handlers to insert into table
    *
    * */

    private void insertToTableFromSelectedFile(String absoluteFilePath) {


        //Extract info from prefs
        String username  = _fPrefs.get("username", null);
        String password = _fPrefs.get("password", null);
        String db = _fPrefs.get("db", null);
        String table = _fPrefs.get("table",null);
        String port = _fPrefs.get("portNumber","3306");
        int portNumber = Integer.parseInt(port);

        //Establish connection
        MySQLConnection mySQLConnection = new MySQLConnection(username, password, db, portNumber);

        mySQLConnection.insertToTableFromFile(absoluteFilePath, table);


    }


}