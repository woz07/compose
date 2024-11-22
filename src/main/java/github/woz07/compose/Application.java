package github.woz07.compose;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import github.woz07.BCipher;
import github.woz07.compose.components.Editor;
import github.woz07.compose.errors.Error;
import github.woz07.compose.errors.Severity;
import github.woz07.liteconfig.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Application.java
 * This is the entry class and also the class that displays the GUI
 *
 * @author woz07
 */

public class Application extends JFrame {
    // File structure
    // f = File
    private static final File fRoot = new File(System.getenv("APPDATA"), "Compose");
    private static final File fConfig = new File(fRoot, "config.txt");
    private static final File fError = new File(fRoot, "errors.txt");   // Stores
    private static final File fUnsaved = new File(fRoot, "unsaved.txt");    // Stores unsaved file's data

    // External libraries
    // e = external
    private static final Configuration eConfig = new Configuration(fConfig);
    private static final BCipher eCipher = new BCipher();

    // GUI
    private final JPanel container; // Holds everything together
    private final GridBagConstraints gbc;
    private final Editor editor;    // Input area
    public Application() {
        setTitle("Compose");
        setPreferredSize(new Dimension(Integer.parseInt(eConfig.get("width")), Integer.parseInt(eConfig.get("height"))));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Setting up menu
        // m = menu, i = item
        JMenu mFile = new JMenu("File");
        JMenuItem iTheme = new JMenuItem("Theme");
        iTheme.addActionListener(e -> updatetheme);
        // TODO
        //  Continue setting up menu then set up the entire GUI system

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save width and height of GUI
                eConfig.updateValue("width", String.valueOf(getWidth()));
                eConfig.updateValue("height", String.valueOf(getHeight()));
                super.windowClosing(e);
            }
        });
    }

    public static void main(String[] args) {
        // Set up static variables
        if (!fRoot.exists()) {
            if (!fRoot.mkdir()) {
                Error.display(null, "[1.1] Unable to create folder called 'Compose' within %APPDATA%");
                Error.log(Severity.SEVERE, Application.class, "Unable to create folder called 'Compose' within %APPDATA%");
            }
        }

        if (!fConfig.exists()) {
            try {
                if (!fConfig.createNewFile()) {
                    Error.display(null, "[1.2] Unable to create 'config.txt' in: " + fConfig.getAbsolutePath());
                    Error.log(Severity.WARNING, Application.class, "Unable to create 'config.txt' in: " + fConfig.getAbsolutePath());
                }
            } catch (Exception ignore) {
                Error.display(null, "[1.2] Unable to create 'config.txt' in: " + fConfig.getAbsolutePath());
                Error.log(Severity.WARNING, Application.class, "Unable to create 'config.txt' in: " + fConfig.getAbsolutePath());
            }
        }

        // Ensure that fConfig isn't empty
        String[] expected = {"theme", "width", "height", "show-errors", "log-errors", "prev-opened", "text-line-wrap"};
        int count = expected.length;
        for (String key : expected) {
            if (eConfig.get(key) == null) {
                count--;
            }
        }

        // If any keys are missing, then set up all the keys
        if (count != expected.length) {
            try {
                eConfig.set("theme", "light");      // Stores the theme of the GUI
                eConfig.set("width", "1280");       // Stores the width of the GUI
                eConfig.set("height", "720");       // Stores the height of the GUI
                eConfig.set("show-errors", "true"); // Stores if errors can get shown
                eConfig.set("log-errors", "true");  // Stores if errors can get logged
                eConfig.set("prev-opened", "?");    // Stores previously opened files path
                eConfig.set("text-line-wrap", "false"); // Stores if text should be wrapped or not
            } catch (Exception ignore) {}
        }

        // Setup mode
        if (eConfig.get("theme").equals("light"))
            FlatLaf.setup(new FlatMacLightLaf());
        else
            FlatLaf.setup(new FlatMacDarkLaf());

        // Run GUI
        SwingUtilities.invokeLater(Application::new);
    }

    /**
     * File to get Application.fError
     * @return fError
     */
    public static File getErrorFile() {
        return fError;
    }

    /**
     * File to get Application.eConfig
     * @return eConfig
     */
    public static Configuration getExternalConfig() {
        return eConfig;
    }
}
