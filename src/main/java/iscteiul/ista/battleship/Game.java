/**
 * Represents a Battleship game instance.
 * <p>
 * The Game class is responsible for:
 * <ul>
 *     <li>Managing the fleet of ships</li>
 *     <li>Registering fired shots</li>
 *     <li>Validating shots</li>
 *     <li>Counting statistics such as hits, invalid shots, repeated shots and sunk ships</li>
 * </ul>
 * </p>
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the {@link IGame} interface.
 * Handles the core game logic for firing shots and tracking game state.
 *
 * @author fba
 */
public class Game implements IGame {

    /** Fleet of ships used in the game */
    private IFleet fleet;

    /** List of valid shots that have been fired */
    private List<IPosition> shots;

    /** Number of invalid shots attempted */
    private Integer countInvalidShots;

    /** Number of repeated shots attempted */
    private Integer countRepeatedShots;

    /** Number of successful hits */
    private Integer countHits;

    /** Number of ships sunk */
    private Integer countSinks;

    /**
     * Creates a new game with the specified fleet.
     *
     * @param fleet the fleet of ships to be used in the game
     */
    public Game(IFleet fleet) {
        shots = new ArrayList<>();
        countInvalidShots = 0;
        countRepeatedShots = 0;
        countHits = 0;
        countSinks = 0;
        this.fleet = fleet;
    }

    /**
     * Fires a shot at a given position.
     *
     * <p>
     * The method:
     * <ul>
     *     <li>Validates the shot coordinates</li>
     *     <li>Checks if the shot was already fired</li>
     *     <li>Registers hits and sunk ships</li>
     * </ul>
     * </p>
     *
     * @param pos the position to fire at
     * @return the ship that was sunk by this shot, or {@code null} if no ship was sunk
     */
    @Override
    public IShip fire(IPosition pos) {
        if (!validShot(pos))
            countInvalidShots++;
        else {
            if (repeatedShot(pos))
                countRepeatedShots++;
            else {
                shots.add(pos);
                IShip s = fleet.shipAt(pos);
                if (s != null) {
                    s.shoot(pos);
                    countHits++;
                    if (!s.stillFloating()) {
                        countSinks++;
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the list of valid shots fired so far.
     *
     * @return list of fired positions
     */
    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    /**
     * Returns the number of repeated shots attempted.
     *
     * @return number of repeated shots
     */
    @Override
    public int getRepeatedShots() {
        return this.countRepeatedShots;
    }

    /**
     * Returns the number of invalid shots attempted.
     *
     * @return number of invalid shots
     */
    @Override
    public int getInvalidShots() {
        return this.countInvalidShots;
    }

    /**
     * Returns the number of successful hits.
     *
     * @return number of hits
     */
    @Override
    public int getHits() {
        return this.countHits;
    }

    /**
     * Returns the number of ships that have been sunk.
     *
     * @return number of sunk ships
     */
    @Override
    public int getSunkShips() {
        return this.countSinks;
    }

    /**
     * Returns the number of ships still afloat.
     *
     * @return number of remaining ships
     */
    @Override
    public int getRemainingShips() {
        List<IShip> floatingShips = fleet.getFloatingShips();
        return floatingShips.size();
    }

    /**
     * Validates whether a shot is within board boundaries.
     *
     * @param pos position to validate
     * @return {@code true} if the shot is within bounds, {@code false} otherwise
     */
    private boolean validShot(IPosition pos) {
        return (pos.getRow() >= 0 && pos.getRow() <= Fleet.BOARD_SIZE
                && pos.getColumn() >= 0 && pos.getColumn() <= Fleet.BOARD_SIZE);
    }

    /**
     * Checks whether a shot has already been fired at the given position.
     *
     * @param pos position to check
     * @return {@code true} if the shot was already fired, {@code false} otherwise
     */
    private boolean repeatedShot(IPosition pos) {
        for (int i = 0; i < shots.size(); i++)
            if (shots.get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * Prints a board representation marking specific positions with a given character.
     *
     * @param positions list of positions to mark
     * @param marker character used to mark the positions
     */
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++)
            for (int c = 0; c < Fleet.BOARD_SIZE; c++)
                map[r][c] = '.';

        for (IPosition pos : positions)
            map[pos.getRow()][pos.getColumn()] = marker;

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++)
                System.out.print(map[row][col]);
            System.out.println();
        }
    }

    /**
     * Prints the board showing all valid shots that have been fired.
     * Fired positions are marked with 'X'.
     */
    public void printValidShots() {
        printBoard(getShots(), 'X');
    }

    /**
     * Prints the board showing the positions of all ships in the fleet.
     * Ship positions are marked with '#'.
     */
    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<IPosition>();

        for (IShip s : fleet.getShips())
            shipPositions.addAll(s.getPositions());

        printBoard(shipPositions, '#');
    }
}
