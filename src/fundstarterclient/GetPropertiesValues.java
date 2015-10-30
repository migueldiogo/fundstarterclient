package fundstarterclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by xavier on 30-10-2015.
 */
public class GetPropertiesValues {
    String primaryServerIP = "";
    String backupServerIP = "";
    InputStream inputStream;

    public void getPropValues() throws IOException {

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getResourceAsStream("resources/config.properties");

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
            primaryServerIP = prop.getProperty("PrimaryIP");
            backupServerIP = prop.getProperty("SecondaryIP");


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    public String getPrimaryServerIP() {
        return primaryServerIP;
    }

    public String getBackupServerIP() {
        return backupServerIP;
    }
}