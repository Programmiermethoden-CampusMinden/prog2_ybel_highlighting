package highlighting;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This lexer applies a sequence of tokens to the input text in the {@link Lexer#tokenize} method.
 */
public final class Lexer {
    private final List<Token> tokenizer;

    /**
     * Create a new lexer for a sequence of tokens.
     *
     * @param t sequence of tokens that are to be used in this order when matching in the {@link
     *     Lexer#tokenize} method
     * @return a new lexer
     */
    public static Lexer of(List<Token> t) {
        return new Lexer(t);
    }

    private Lexer(List<Token> t) {
        tokenizer = t;
    }

    /**
     * Apply all tokens in sequence to the input string.
     *
     * @param s string to be tokenized
     * @return sequence of lexemes, i.e. regions that have been matched plus the assigned highlight
     *     colour
     */
    public List<Lexem> tokenize(String s) {
        return tokenizer.stream().flatMap(t -> t.test(s).stream()).collect(Collectors.toList());
    }
}
