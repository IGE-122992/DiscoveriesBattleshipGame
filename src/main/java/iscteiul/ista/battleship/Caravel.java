/**
 * Represents a Caravel (Caravela) ship in the Discoveries Battleship Game.
 *
 * <p>The Caravel is the second-smallest vessel in the fleet, occupying 2 consecutive
 * cells on the grid. It corresponds to the "Navio de 2 canhões" (2-cannon ship) in
 * the classic Battleship game. Each fleet contains exactly 3 Caravels.</p>
 *
 * <p>The cells occupied depend on the bearing specified at construction:</p>
 * <ul>
 *   <li>{@link Compass#NORTH} or {@link Compass#SOUTH} — occupies 2 cells vertically
 *       (same column, rows {@code row} and {@code row+1})</li>
 *   <li>{@link Compass#EAST} or {@link Compass#WEST} — occupies 2 cells horizontally
 *       (same row, columns {@code col} and {@code col+1})</li>
 * </ul>
 *
 * <p><b>Note:</b> The caller is responsible for ensuring that all occupied cells remain
 * within the valid 10×10 grid boundaries.</p>
 *
 * <table border="1">
 *   <caption>Fleet composition reference</caption>
 *   <tr><th>Classic Name</th><th>Discoveries Name</th><th>Size</th><th>Count</th></tr>
 *   <tr><td>2-cannon ship</td><td>Caravel (Caravela)</td><td>2</td><td>3</td></tr>
 * </table>
 *
 * @author  IGE-XXXXX
 * @version 1.0
 * @see     Ship
 * @see     Compass
 * @see     IPosition
 */
package iscteiul.ista.battleship;

public class Caravel extends Ship {

    /** The number of cells this ship occupies on the grid. */
    private static final Integer SIZE = 2;

    /** The display name of this ship in the Discoveries theme. */
    private static final String NAME = "Caravela";

    /**
     * Constructs a new Caravel at the specified position with the given bearing.
     *
     * <p>The ship's occupied cells are computed from {@code pos} as the top-left (or
     * top-most / left-most) anchor, extending in the direction indicated by
     * {@code bearing}:</p>
     * <ul>
     *   <li>{@code NORTH} / {@code SOUTH} → cells at
     *       {@code (row, col)} and {@code (row+1, col)}</li>
     *   <li>{@code EAST} / {@code WEST}   → cells at
     *       {@code (row, col)} and {@code (row, col+1)}</li>
     * </ul>
     *
     * @param bearing the compass direction determining the Caravel's orientation;
     *                must be one of {@link Compass#NORTH}, {@link Compass#SOUTH},
     *                {@link Compass#EAST}, or {@link Compass#WEST}
     * @param pos     the grid position used as the anchor (top-left cell) for placement;
     *                must be within the valid 10×10 grid bounds such that all cells
     *                of the ship remain on the grid
     * @throws NullPointerException     if {@code bearing} or {@code pos} is {@code null}
     * @throws IllegalArgumentException if {@code bearing} is not a valid {@link Compass}
     *                                  value recognised by the switch statement
     */
    public Caravel(Compass bearing, IPosition pos)
            throws NullPointerException, IllegalArgumentException {
        super(Caravel.NAME, bearing, pos);
        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the caravel");
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
                throw new IllegalArgumentException("ERROR! invalid bearing for the caravel");
        }
    }

    /**
     * Returns the size of this Caravel, i.e., the number of grid cells it occupies.
     *
     * @return {@code 2}, since a Caravel always occupies exactly two cells
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }
}
