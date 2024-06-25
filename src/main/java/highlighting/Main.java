package highlighting;

import java.awt.*;
import java.util.List;

/** Configure the lexer and start the demo. */
public class Main {
    /**
     * Launch the demo.
     *
     * @param args arguments for the program (not used)
     */
    public static void main(String... args) {
        String defaultText =
                """
        package controller;

        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;

        /* ApplicationListener that delegates to the MainGameController. Just some setup. */
        public class LibgdxSetup extends Game {
            private final MainController mc;

            /**
             * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
             * batch.
             */
            private SpriteBatch batch;
            // This batch is used to //draw the HUD /*elements*/ on it.\040
            private SpriteBatch hudBatch;

            /**
             * "ApplicationListener" that delegates to the "MainGameController". Just some setup.
             */
            public LibgdxSetup(MainController mc) {
                this.mc = mc;
            }

            @Over-ride 'someText'
            public void create() {
                // new ...
                char ch = new Character('a');
                return null;
            }
        }
        """;

        LexerUI.show(defaultText, Lexer.of(setupTokens()));
    }

    /**
     * TODO: Homework! Define the patterns for the individual tokens here (see comments).
     *
     * @return list of tokens for the syntax parts to be highlighted
     */
    private static List<Token> setupTokens() {
        return List.of(
                // Strings
                // Zeichenketten, die in '"' eingeschlossen sind

                // Einzelne Zeichen
                // Zeichen, die in "'" eingeschlossen sind

                // KeyWords: package, import, class, public, private, final, return, null, new

                // Annotation
                // Fangen mit "@" an, beispielsweise "@Override"

                // Einzeiliger Kommentar
                // Fängt mit "//" an und geht bis zum Ende der Zeile

                // Mehrzeiliger Kommentar
                // Fängt mit "/*" and und bis zum nächsten "*/", kann potentiell mehrere Zeilen
                // umfassen

                // Java-Doc-Kommentar
                // Wie ein mehrzeiliger Kommentar, beginnt aber mit "/**"
                );
    }
}
