 /**
 * Represents a Barge (Barca) ship in the Discoveries Battleship Game.
 *
 * <p>The Barge is the smallest vessel in the fleet, occupying a single cell on the grid.
 * It corresponds to the "Submarino" (Submarine) in the classic Battleship game.
 * Each fleet contains exactly 4 Barges.</p>
 *
 * <p>Since the Barge occupies only one cell, its bearing has no effect on placement.</p>
 *
 * <table border="1">
 *   <caption>Fleet composition reference</caption>
 *   <tr><th>Classic Name</th><th>Discoveries Name</th><th>Size</th><th>Count</th></tr>
 *   <tr><td>Submarine</td><td>Barge (Barca)</td><td>1</td><td>4</td></tr>
 * </table>
 *
 * @author  IGE-XXXXX
 * @version 1.0
 * @see     Ship
 * @see     IPosition
 */
package iscteiul.ista.battleship;

public class Barge extends Ship {

    /** The number of cells this ship occupies on the grid. */
    private static final Integer SIZE = 1;

    /** The display name of this ship in the Discoveries theme. */
    private static final String NAME = "Barca";

    /**
     * Constructs a new Barge at the specified position.
     *
     * <p>Because the Barge occupies only a single cell, the {@code bearing} parameter
     * is accepted for interface consistency but does not affect the ship's placement.
     * The ship always occupies exactly the cell at {@code pos}.</p>
     *
     * @param bearing the compass direction the Barge faces; ignored for placement
     *                since the ship occupies a single cell, but must be non-null
     * @param pos     the grid position (row, column) where the Barge is placed;
     *                must be within the valid 10×10 grid bounds
     * @throws NullPointerException     if {@code pos} is {@code null}
     * @throws IllegalArgumentException if {@code pos} falls outside the grid boundaries
     */
    public Barge(Compass bearing, IPosition pos) {
        super(Barge.NAME, bearing, pos);
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
    }

    /**
     * Returns the size of this Barge, i.e., the number of grid cells it occupies.
     *
     * @return {@code 1}, since a Barge always occupies exactly one cell
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }
}
