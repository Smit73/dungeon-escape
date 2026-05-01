package ca.sfu.cmpt276.dungeon;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonStyler {

    /**
     * Helper method that styles buttons with hover effects and shadows.
     *
     * @param btn The button to style.
     */
    public void styleButton(Button btn) {
        btn.setPrefWidth(250);
        btn.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 22));
        btn.setTextFill(Color.WHITE);
        btn.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #3a3a3a, #1c1c1c);
        -fx-background-radius: 12;
        -fx-border-color: gold;
        -fx-border-radius: 12;
        -fx-border-width: 2;
        -fx-cursor: hand;
    """);
        btn.setEffect(new DropShadow(10, Color.BLACK));

        // Hover animation
        btn.setOnMouseEntered(e -> btn.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #505050, #2a2a2a);
        -fx-background-radius: 12;
        -fx-border-color: gold;
        -fx-border-radius: 12;
        -fx-border-width: 2;
        -fx-cursor: hand;
    """));
        btn.setOnMouseExited(e -> btn.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #3a3a3a, #1c1c1c);
        -fx-background-radius: 12;
        -fx-border-color: gold;
        -fx-border-radius: 12;
        -fx-border-width: 2;
        -fx-cursor: hand;
    """));
    }
}