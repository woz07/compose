package github.woz07.compose.components;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

/**
 * Editor.java
 * The class that contains the JTextPane for the editor.
 * This class also controls all the changing for the editor.
 */
public class Editor extends JTextPane {
    private StyledDocument document;
    private Style style;
    public Editor() {

    }
}

//TODO BASED ON THIS CODE
/*

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.regex.*;

public class KeywordColoringExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KeywordColoringExample::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("JTextPane Keyword Coloring");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // JTextPane and StyledDocument setup
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();

        // Set some initial text
        textPane.setText("This is a sample text where certain keywords should be highlighted.");

        // Define a style for keywords
        Style keywordStyle = textPane.addStyle("KeywordStyle", null);
        StyleConstants.setForeground(keywordStyle, Color.RED); // set color for keywords

        // Highlight specific keywords
        highlightKeywords(doc, "sample", keywordStyle);
        highlightKeywords(doc, "keywords", keywordStyle);

        // Scroll Pane to add textPane inside a scrollable area
        JScrollPane scrollPane = new JScrollPane(textPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void highlightKeywords(StyledDocument doc, String keyword, Style style) {
        String text;
        try {
            text = doc.getText(0, doc.getLength()); // Get the text content from document
            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);\

            // Highlight all matches of the keyword
            while (matcher.find()) {
                doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), style, false);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}


 */
