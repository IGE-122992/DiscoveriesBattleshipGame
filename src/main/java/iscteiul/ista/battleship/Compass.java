package iscteiul.ista.battleship;

/**
 * Enumeração que representa a orientação (direção) de um navio na grelha.
 * 
 * As direções possíveis são:
 * - NORTH ('n')
 * - SOUTH ('s')
 * - EAST  ('e')
 * - WEST  ('o')
 * - UNKNOWN ('u') – utilizada quando o carácter não corresponde
 *   a nenhuma direção válida.
 * 
 * Cada direção está associada a um carácter que a representa.
 */
public enum Compass {

    NORTH('n'),
    SOUTH('s'),
    EAST('e'),
    WEST('o'),
    UNKNOWN('u');

    /** Carácter associado à direção. */
    private final char c;

    /**
     * Construtor da enumeração Compass.
     *
     * @param c carácter representativo da direção
     */
    Compass(char c) {
        this.c = c;
    }

    /**
     * Devolve o carácter associado à direção.
     *
     * @return carácter da direção
     */
    public char getDirection() {
        return c;
    }

    /**
     * Devolve a representação textual da direção.
     *
     * @return string correspondente ao carácter da direção
     */
    @Override
    public String toString() {
        return "" + c;
    }

    /**
     * Converte um carácter numa direção Compass.
     *
     * @param ch carácter a converter
     * @return direção correspondente ao carácter;
     *         UNKNOWN se o carácter não for válido
     */
    static Compass charToCompass(char ch) {
        Compass bearing;

        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}
