import java.io.FileOutputStream;
import java.util.prefs.Preferences;

public class PreferenceExample {
    public static void main(String args[]) throws Exception {
        Preferences prefsRoot = Preferences.userRoot();
        Preferences myPrefs = prefsRoot.node("PreferenceExample");

        myPrefs.put("username", "root");
        myPrefs.put("password", "jaypatel");
        myPrefs.put("db", "mydbtest");
        myPrefs.put("table","mytable");
        myPrefs.put("portNumber","3306");

        FileOutputStream fos = new FileOutputStream("prefs.xml");

        myPrefs.exportSubtree(fos);
        fos.close();

    }
}
