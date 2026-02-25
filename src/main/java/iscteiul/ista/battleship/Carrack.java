/**
 * Represents a Carrack (Nau) ship in the Discoveries Battleship Game.
 *
 * <p>The Carrack is a medium-sized vessel occupying 3 consecutive cells on the grid.
 * It corresponds to the "Navio de 3 canhões" (3-cannon ship) in the classic Battleship
 * game. Each fleet contains exactly 2 Carracks.</p>
 *
 * <p>The cells occupied depend on the bearing specified at construction:</p>
 * <ul>
 *   <li>{@link Compass#NORTH} or {@link Compass#SOUTH} — occupies 3 cells vertically
 *       (same column, rows {@code row}, {@code row+1}, and {@code row+2})</li>
 *   <li>{@link Compass#EAST} or {@link Compass#WEST} — occupies 3 cells horizontally
 *       (same row, columns {@code col}, {@code col+1}, and {@code col+2})</li>
 * </ul>
 *
 * <p><b>Note:</b> The caller is responsible for ensuring that all occupied cells remain
 * within the valid 10×10 grid boundaries and that the ship does not overlap or touch
 * any other ship already placed on the grid.</p>
 *
 * <table border="1">
 *   <caption>Fleet composition reference</caption>
 *   <tr><th>Classic Name</th><th>Discoveries Name</th><th>Size</th><th>Count</th></tr>
 *   <tr><td>3-cannon ship</td><td>Carrack (Nau)</td><td>3</td><td>2</td></tr>
 * </table>
 *
 * @author  IGE-XXXXX
 * @version 1.0
 * @see     Ship
 * @see     Compass
 * @see     IPosition
 */
package iscteiul.ista.battleship;

public class Carrack extends Ship {

    /** The number of cells this ship occupies on the grid. */
    private static final Integer SIZE = 3;

    /** The display name of this ship in the Discoveries theme. */
    private static final String NAME = "Nau";

    /**
     * Constructs a new Carrack at the specified position with the given bearing.
     *
     * <p>The ship's occupied cells are computed from {@code pos} as the top-left (or
     * top-most / left-most) anchor, extending in the direction indicated by
     * {@code bearing}:</p>
     * <ul>
     *   <li>{@code NORTH} / {@code SOUTH} → cells at
     *       {@code (row, col)}, {@code (row+1, col)}, and {@code (row+2, col)}</li>
     *   <li>{@code EAST}  / {@code WEST}  → cells at
     *       {@code (row, col)}, {@code (row, col+1)}, and {@code (row, col+2)}</li>
     * </ul>
     *
     * @param bearing the compass direction determining the Carrack's orientation;
     *                must be one of {@link Compass#NORTH}, {@link Compass#SOUTH},
     *                {@link Compass#EAST}, or {@link Compass#WEST}
     * @param pos     the grid position used as the anchor (top-left cell) for placement;
     *                must be within the valid 10×10 grid bounds such that all 3 cells
     *                of the ship remain on the grid
     * @throws NullPointerException     if {@code bearing} or {@code pos} is {@code null}
     * @throws IllegalArgumentException if {@code bearing} is not a valid {@link Compass}
     *                                  value recognised by the switch statement
     */
    public Carrack(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Carrack.NAME, bearing, pos);
        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the carrack");
        }
    }

    /**
     * Returns the size of this Carrack, i.e., the number of grid cells it occupies.
     *
     * @return {@code 3}, since a Carrack always occupies exactly three cells
     */
    @Override
    public Integer getSize() {
        return Carrack.SIZE;
    }
}
