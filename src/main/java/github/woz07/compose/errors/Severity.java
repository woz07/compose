package github.woz07.compose.errors;

import java.awt.*;

/**
 * Severity.java
 * This is the enum that allows for us to know how severe the
 */

public enum Severity {
    SEVERE(Color.RED),      // Severe issues occurred
    WARNING(Color.ORANGE),  // Warning about something
    INFO(Color.BLUE);       // Information given

    private final Color color; // The color that the text should be regarding its severity
    Severity(Color color) {
        this.color = color;
    }

    /**
     * Returns the color
     * @return color
     */
    public Color getColor() {
        return color;
    }
}
