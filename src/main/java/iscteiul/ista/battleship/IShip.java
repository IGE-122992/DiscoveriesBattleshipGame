/**
 * Represents a ship in the Discoveries Battleship Game.
 * <p>
 * A ship has a category (e.g., Galleon, Frigate), a size measured in board cells,
 * a bearing (orientation on the board), and occupies one or more {@link IPosition}s.
 * It can receive shots and report whether it is still afloat.
 * </p>
 *
 * @see IPosition
 * @see Compass
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IShip {

    /**
     * Returns the category (type) of this ship.
     * <p>
     * Categories correspond to the Discoveries theme names defined in {@code Ship}:
     * e.g., {@code "galeao"}, {@code "fragata"}, {@code "nau"}, {@code "caravela"}, {@code "barca"}.
     * </p>
     *
     * @return a non-null {@link String} representing the ship's category
     */
    String getCategory();

    /**
     * Returns the size (number of cells) this ship occupies on the board.
     *
     * @return a positive {@link Integer} representing the ship's length in board cells
     */
    Integer getSize();

    /**
     * Returns all board positions occupied by this ship.
     *
     * @return a non-null, non-empty {@link List} of {@link IPosition} objects,
     *         one per cell the ship covers
     */
    List<IPosition> getPositions();

    /**
     * Returns the anchor (reference) position of this ship — the cell used when the
     * ship was placed, typically its top-most or left-most cell depending on bearing.
     *
     * @return the anchor {@link IPosition} of this ship; never {@code null}
     */
    IPosition getPosition();

    /**
     * Returns the compass bearing (orientation) of this ship on the board.
     * <p>
     * Determines the direction in which the ship extends from its anchor position.
     * </p>
     *
     * @return the {@link Compass} direction this ship faces; never {@code null}
     */
    Compass getBearing();

    /**
     * Indicates whether this ship is still afloat.
     * <p>
     * A ship sinks when every one of its positions has been {@linkplain IPosition#shoot() hit}.
     * </p>
     *
     * @return {@code true} if at least one position has not been hit yet;
     *         {@code false} if all positions have been hit (ship is sunk)
     */
    boolean stillFloating();

    /**
     * Returns the row index of the top-most cell occupied by this ship.
     *
     * @return the minimum row index among all of this ship's positions
     */
    int getTopMostPos();

    /**
     * Returns the row index of the bottom-most cell occupied by this ship.
     *
     * @return the maximum row index among all of this ship's positions
     */
    int getBottomMostPos();

    /**
     * Returns the column index of the left-most cell occupied by this ship.
     *
     * @return the minimum column index among all of this ship's positions
     */
    int getLeftMostPos();

    /**
     * Returns the column index of the right-most cell occupied by this ship.
     *
     * @return the maximum column index among all of this ship's positions
     */
    int getRightMostPos();

    /**
     * Determines whether this ship occupies the given board position.
     *
     * @param pos the {@link IPosition} to test; must not be {@code null}
     * @return {@code true} if this ship covers {@code pos}; {@code false} otherwise
     */
    boolean occupies(IPosition pos);

    /**
     * Determines whether this ship is placed too close to another ship,
     * violating the adjacency rule (ships may not touch, including diagonally).
     * <p>
     * Delegates to {@link #tooCloseTo(IPosition)} for each position of {@code other}.
     * </p>
     *
     * @param other the other {@link IShip} to check against; must not be {@code null}
     * @return {@code true} if any cell of {@code other} is adjacent to any cell of this ship;
     *         {@code false} if the ships are sufficiently spaced apart
     */
    boolean tooCloseTo(IShip other);

    /**
     * Determines whether this ship is too close to the given board position,
     * violating the adjacency rule.
     * <p>
     * A position is considered too close if it is adjacent (including diagonally)
     * to any cell of this ship, as determined by {@link IPosition#isAdjacentTo(IPosition)}.
     * </p>
     *
     * @param pos the {@link IPosition} to check; must not be {@code null}
     * @return {@code true} if {@code pos} falls within the exclusion zone of this ship;
     *         {@code false} otherwise
     */
    boolean tooCloseTo(IPosition pos);

    /**
     * Registers a shot at the given position.
     * <p>
     * If this ship occupies {@code pos}, the corresponding cell is marked as hit
     * via {@link IPosition#shoot()}. If the position is not covered by this ship,
     * the call has no effect.
     * </p>
     *
     * @param pos the {@link IPosition} targeted by the shot; must not be {@code null}
     */
    void shoot(IPosition pos);
}
