package highlighting;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/** A simple GUI for demonstrating our syntax highlighting. */
public final class LexerUI {
    /**
     * Start a new GUI for a given text and a lexer.
     *
     * @param defaultText the text to be displayed when starting the GUI
     * @param lexer the lexer to be used for syntax highlighting
     */
    public static void show(String defaultText, Lexer lexer) {
        new LexerUI(defaultText, lexer);
    }

    private LexerUI(String defaultText, Lexer lexer) {
        // setup output textarea
        JTextPane outputPane = new JTextPane();
        outputPane.setEditable(false);

        // setup input textarea
        JTextArea inputArea = new JTextArea();
        inputArea
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                update(inputArea, outputPane, lexer);
                            }

                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                update(inputArea, outputPane, lexer);
                            }

                            @Override
                            public void changedUpdate(DocumentEvent e) {
                                update(inputArea, outputPane, lexer);
                            }
                        });
        inputArea.setText(defaultText);

        // setup remaining UI
        JFrame frame = new JFrame("highlighting.LexerUI");
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel("Input:"));
        labelPanel.add(new JLabel("Output:"));
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(new JScrollPane(inputArea));
        mainPanel.add(new JScrollPane(outputPane));
        frame.add(labelPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        // show UI
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void update(JTextArea inputArea, JTextPane outputPane, Lexer lexer) {
        String inputText = inputArea.getText();
        outputPane.setText(inputText);

        // sort the list so that other surrounding elements are placed last
        List<Lexem> lexems = lexer.tokenize(inputText);
        for (int i = 0; i < lexems.size(); i++) {
            for (int j = i + 1; j < lexems.size(); j++) {
                if (isSurrounded(lexems.get(i), lexems.get(j))) {
                    Collections.swap(lexems, i, j);
                }
            }
        }

        // add highlighting
        Highlighter hl = outputPane.getHighlighter();
        try {
            for (Lexem match : lexems) {
                hl.addHighlight(
                        match.start(),
                        match.end(),
                        new DefaultHighlighter.DefaultHighlightPainter(match.color()));
            }
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSurrounded(Lexem inner, Lexem outer) {
        return inner != outer && outer.start() <= inner.start() && outer.end() >= inner.end();
    }
}
