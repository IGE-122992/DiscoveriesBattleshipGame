/**
 * Defines the contract for a fleet of ships in the Battleship game.
 * <p>
 * An implementation of this interface is responsible for:
 * <ul>
 *     <li>Managing a collection of ships</li>
 *     <li>Adding ships to the fleet</li>
 *     <li>Querying ships by category</li>
 *     <li>Determining which ships are still afloat</li>
 *     <li>Determining whether a ship occupies a given position</li>
 * </ul>
 * </p>
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IFleet {

    /**
     * The size of the game board (board is BOARD_SIZE x BOARD_SIZE).
     */
    Integer BOARD_SIZE = 10;

    /**
     * The maximum number of ships allowed in the fleet.
     */
    Integer FLEET_SIZE = 10;

    /**
     * Returns all ships in the fleet.
     *
     * @return list of ships
     */
    List<IShip> getShips();

    /**
     * Adds a ship to the fleet.
     *
     * @param s the ship to add
     * @return {@code true} if the ship was successfully added,
     *         {@code false} if the fleet is full or the ship cannot be added
     */
    boolean addShip(IShip s);

    /**
     * Returns all ships belonging to a given category.
     *
     * @param category the category of ships to retrieve
     * @return list of ships matching the given category
     */
    List<IShip> getShipsLike(String category);

    /**
     * Returns all ships that are still afloat.
     *
     * @return list of ships that have not been sunk
     */
    List<IShip> getFloatingShips();

    /**
     * Returns the ship occupying a given position, if any.
     *
     * @param pos the position to check
     * @return the ship at the given position, or {@code null} if no ship occupies it
     */
    IShip shipAt(IPosition pos);

    /**
     * Prints the current status of the fleet,
     * including information about ships and whether they are floating or sunk.
     */
    void printStatus();
}
