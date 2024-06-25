package highlighting;

import java.awt.Color;

/**
 * DAO to store the region of a match and the highlighting colour.
 *
 * @param start the start of a match region
 * @param end the end of a match region
 * @param color the color to be used to highlight this region
 */
public record Lexem(int start, int end, Color color) {}
