package highlighting;

import java.awt.Color;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A token is used to check whether a text has a particular form.
 *
 * <p>The class encapsulates a precompiled regular expression ({@link Pattern}), which is applied to
 * a text in the {@link Token#test} method. Additionally, each token has a colour that is later used
 * to highlight the matched text. You can also specify the matching group that is to be used in
 * highlighting - this is usually the value 0 (i.e. the entire match will be highlighted), but can
 * be set to a custom value if groups are used in the regular expression (pattern) and you want to
 * highlight a specific group in the highlighting.
 *
 * @param pattern the precompiled regular expression to be applied to a text in the {@link
 *     Token#test} method
 * @param matchingGroup the matching group to be used in highlighting: 0 to use the entire match
 * @param color the colour to be used for highlighting for this token
 */
public record Token(Pattern pattern, int matchingGroup, Color color) {
    /**
     * Create a new token.
     *
     * @param pattern the precompiled regular expression to be applied to a text in the {@link
     *     Token#test} method
     * @param matchingGroup the matching group to be used in highlighting
     * @param color the colour to be used for highlighting for this token
     * @return
     */
    public static Token of(Pattern pattern, int matchingGroup, Color color) {
        return new Token(pattern, matchingGroup, color);
    }

    /**
     * Create a new token using the entire match for highlighting.
     *
     * @param pattern the precompiled regular expression to be applied to a text in the {@link
     *     Token#test} method
     * @param color the colour to be used for highlighting for this token
     * @return a new token object
     */
    public static Token of(Pattern pattern, Color color) {
        return new Token(pattern, 0, color);
    }

    /**
     * Apply this token (regexp) to an input string.
     *
     * @param s the string onto this pattern is applied
     * @return a list of all matches that could be found using the pattern
     */
    public List<Lexem> test(String s) {
        return pattern.matcher(s).results().map(this::toLexem).collect(Collectors.toList());
    }

    private Lexem toLexem(MatchResult mr) {
        return new Lexem(mr.start(matchingGroup), mr.end(matchingGroup), color);
    }
}
