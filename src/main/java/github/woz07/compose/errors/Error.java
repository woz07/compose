package github.woz07.compose.errors;

import github.woz07.compose.Application;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

/**
 * Error.java
 * This is the class that displays the error to the user in a GUI
 *
 * @author woz07
 */

public class Error extends JFrame {
    public static void display(JFrame parent, String message) {
        // If user doesn't want error to be shown then exit function
        if (Application.getExternalConfig().get("show-errors").equals("false")) {
            return;
        }

        JFrame window = new JFrame();

        window.setTitle("Compose - Error");
        window.setPreferredSize(new Dimension(320, 200));
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // TODO
        //  Set the favicon as some error favicon
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/favicon.png")));

        // l = Label
        JLabel lError = new JLabel(message);  // Label that displays the error
        // b = Button
        JButton bOkay = new JButton("Okay");  // Button to accept that message has been read
        bOkay.addActionListener(e -> window.dispose());
        JButton bHelp = new JButton("Help");  // Button to get help
        bHelp.addActionListener(e -> {
            String link = "https://www.github.com/woz07/compose#Errors";
            try {
                URI uri = new URI(link);
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(uri);
                } else {
                    Error.display(null, "[6.1] Unable to open browser link due to permissions: \"https://www.github.com/woz07/compose#Errors\"");
                    Error.log(Severity.WARNING, Error.class, "Unable to open browser link due to permissions: " + link);
                }
            } catch (URISyntaxException | IOException err) {
                Error.display(null, "[6.1] Unable to open browser link due to permissions: \"https://www.github.com/woz07/compose#Errors\"");
                Error.log(Severity.WARNING, Error.class, "Unable to open browser link due to permissions: " + link);
            }
        });

        // Finalizing components
        // p = Panel
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.add(lError, BorderLayout.CENTER);

        JPanel pBottom = new JPanel();
        pBottom.add(bOkay);
        pBottom.add(bHelp);

        // Add components
        window.setLayout(new BorderLayout());
        window.add(pTop, BorderLayout.CENTER);
        window.add(pBottom, BorderLayout.SOUTH);

        // Finalizing
        window.pack();
        window.setLocationRelativeTo(parent);
        window.setResizable(false);
        window.setVisible(true);
    }

    /**
     * Function that logs the severity of error and which class it happened in and the message
     * along with the date
     * @param severity The severity
     * @param cls The class where it happened
     * @param message The message to log
     */
    public static void log(Severity severity, Class<?> cls, String message) {
        // If user doesn't want error to be logged then exit function
        if (Application.getExternalConfig().get("log-errors").equals("false")) {
            return;
        }

        try {
            FileWriter fw = new FileWriter(Application.getErrorFile(), true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("Severity: " + severity);
            writer.write("Date    : " + LocalDate.now());
            writer.write("Class   :" + cls.getName());
            writer.write("Message :" + message);
            writer.write("====================");
        } catch (IOException e) {
            Error.display(null, "[7.1] Unable to log errors to error file");
        }

    }
}
