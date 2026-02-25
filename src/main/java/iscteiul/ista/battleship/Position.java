/**
 * Concrete implementation of {@link IPosition} representing a single cell
 * on the Battleship game board.
 * <p>
 * A position is identified by its {@code row} and {@code column} coordinates
 * (both zero-based). It tracks two independent boolean states:
 * whether a ship currently occupies it ({@link #isOccupied()}) and whether
 * it has been targeted by a shot ({@link #isHit()}).
 * </p>
 * <p>
 * Two {@code Position} objects are considered equal if and only if they share
 * the same row and column values, regardless of their occupied or hit state —
 * see {@link #equals(Object)}.
 * </p>
 *
 * @see IPosition
 */
package iscteiul.ista.battleship;

import java.util.Objects;

public class Position implements IPosition {

    /** Zero-based row coordinate of this position. */
    private int row;

    /** Zero-based column coordinate of this position. */
    private int column;

    /**
     * Whether a ship currently occupies this cell.
     * Set to {@code true} by {@link #occupy()}.
     */
    private boolean isOccupied;

    /**
     * Whether this cell has been targeted by a shot.
     * Set to {@code true} by {@link #shoot()}.
     */
    private boolean isHit;

    /**
     * Constructs a new {@code Position} at the given row and column.
     * <p>
     * The position is initially neither occupied nor hit.
     * </p>
     *
     * @param row    the zero-based row index on the game board
     * @param column the zero-based column index on the game board
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumn() {
        return column;
    }

    /**
     * Returns a hash code based on the row, column, hit state, and occupied state.
     *
     * @return an integer hash code for this position
     */
    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /**
     * Compares this position to another object for equality.
     * <p>
     * Two positions are equal if they are at the same row and column, regardless
     * of their hit or occupied state. The {@code other} object only needs to
     * implement {@link IPosition} — it does not need to be a {@code Position} instance.
     * </p>
     *
     * @param otherPosition the object to compare with; may be {@code null}
     * @return {@code true} if {@code otherPosition} is an {@link IPosition} at the
     *         same row and column as this position; {@code false} otherwise
     */
    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;
        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        } else {
            return false;
        }
    }

    /**
     * Determines whether this position is adjacent to (or the same as) the given position.
     * <p>
     * Adjacency includes all 8 surrounding cells (orthogonal and diagonal neighbours),
     * as well as the cell itself. More precisely, two positions are adjacent if the
     * absolute difference in both their rows and columns is at most 1.
     * </p>
     *
     * @param other the {@link IPosition} to compare against; must not be {@code null}
     * @return {@code true} if the Chebyshev distance between this and {@code other}
     *         is at most 1; {@code false} otherwise
     */
    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1
                && Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * Marks this position as occupied by a ship.
     * <p>
     * Called during fleet placement to indicate that a ship covers this cell.
     * Subsequent calls have no additional effect.
     * </p>
     */
    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * Marks this position as hit by a shot.
     * <p>
     * Called when a player fires at this cell. Once hit, the state cannot be reversed.
     * </p>
     */
    @Override
    public void shoot() {
        isHit = true;
    }

    /**
     * Returns whether a ship occupies this position.
     *
     * @return {@code true} if {@link #occupy()} has been called on this position;
     *         {@code false} otherwise
     */
    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Returns whether this position has been hit by a shot.
     *
     * @return {@code true} if {@link #shoot()} has been called on this position;
     *         {@code false} otherwise
     */
    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * Returns a human-readable string representation of this position.
     * <p>
     * Format: {@code "Linha = R Coluna = C"} where {@code R} and {@code C}
     * are the row and column values respectively.
     * </p>
     *
     * @return a formatted string with this position's coordinates
     */
    @Override
    public String toString() {
        return ("Linha = " + row + " Coluna = " + column);
    }
}
