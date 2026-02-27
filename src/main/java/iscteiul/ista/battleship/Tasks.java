/**
 * Collection of static utility methods and interactive test tasks for the
 * Discoveries Battleship Game.
 * <p>
 * Each {@code taskX()} method reads input from {@code stdin} via a {@link Scanner}
 * and exercises a progressively richer subset of the game's functionality:
 * </p>
 * <ul>
 *   <li>{@link #taskA()} — ship construction and position occupancy</li>
 *   <li>{@link #taskB()} — fleet building and status display</li>
 *   <li>{@link #taskC()} — fleet building with cheat map</li>
 *   <li>{@link #taskD()} — full round with firing, hit reporting, and win detection</li>
 * </ul>
 * <p>
 * These tasks double as manual integration tests and can be adapted into
 * automated unit tests by replacing {@code stdin} with pre-prepared input streams.
 * </p>
 * <p>
 * All output is written through the Log4j {@link #LOGGER} rather than
 * {@code System.out}, allowing log levels to control verbosity.
 * </p>
 *
 * @see Ship
 * @see Fleet
 * @see Game
 */
package iscteiul.ista.battleship;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tasks {

    /** Logger for all task output. Uses the class name as the logger identifier. */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Number of shots fired per round (used in {@link #taskA()} and
     * {@link #firingRound(Scanner, IGame)}).
     */
    private static final int NUMBER_SHOTS = 3;

    /** Message printed when the user quits any interactive task. */
    private static final String GOODBYE_MESSAGE = "Bons ventos!";

    // -------------------------------------------------------------------------
    // User-facing command strings recognised by the interactive tasks
    // -------------------------------------------------------------------------

    /** Command to build a new fleet: {@code "nova"}. */
    private static final String NOVAFROTA = "nova";

    /** Command to quit the current task: {@code "desisto"}. */
    private static final String DESISTIR = "desisto";

    /** Command to fire a round of shots: {@code "rajada"}. */
    private static final String RAJADA = "rajada";

    /** Command to display the shots fired so far: {@code "ver"}. */
    private static final String VERTIROS = "ver";

    /**
     * Cheat command that reveals the full fleet map: {@code "mapa"}.
     * Available in {@link #taskC()} and {@link #taskD()}.
     */
    private static final String BATOTA = "mapa";

    /** Command to display the current fleet status: {@code "estado"}. */
    private static final String STATUS = "estado";

    // -------------------------------------------------------------------------
    // Tasks
    // -------------------------------------------------------------------------

    /**
     * Task A — tests ship construction and position occupancy.
     * <p>
     * Reads ships and positions from {@code stdin} in a loop. For each ship read,
     * reads {@value #NUMBER_SHOTS} positions and logs whether the ship occupies
     * each one. Input ends when there is no more data.
     * </p>
     * <p>
     * Expected input format per iteration:
     * <pre>
     *   &lt;shipKind&gt; &lt;row&gt; &lt;col&gt; &lt;bearingChar&gt;
     *   &lt;row1&gt; &lt;col1&gt;
     *   &lt;row2&gt; &lt;col2&gt;
     *   &lt;row3&gt; &lt;col3&gt;
     * </pre>
     * </p>
     */
    public static void taskA() {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            Ship s = readShip(in);
            if (s != null)
                for (int i = 0; i < NUMBER_SHOTS; i++) {
                    Position p = readPosition(in);
                    LOGGER.info("{} {}", p, s.occupies(p));
                }
        }
    }

    /**
     * Task B — tests fleet building and status reporting.
     * <p>
     * Reads commands from {@code stdin} until {@code "desisto"} is entered.
     * Recognised commands:
     * </p>
     * <ul>
     *   <li>{@code "nova"} — builds a new fleet via {@link #buildFleet(Scanner)}</li>
     *   <li>{@code "estado"} — prints the current fleet status</li>
     * </ul>
     */
    public static void taskB() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Task C — extends Task B with a cheat command that reveals the full fleet map.
     * <p>
     * Reads commands from {@code stdin} until {@code "desisto"} is entered.
     * Recognised commands (in addition to those in Task B):
     * </p>
     * <ul>
     *   <li>{@code "mapa"} — logs the full fleet map (cheat / debug view)</li>
     * </ul>
     */
    public static void taskC() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    LOGGER.info(fleet);
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete lá ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    /**
     * Task D — full interactive game loop including firing rounds and win detection.
     * <p>
     * Reads commands from {@code stdin} until {@code "desisto"} is entered.
     * Recognised commands (in addition to those in Task C):
     * </p>
     * <ul>
     *   <li>{@code "rajada"} — fires {@value #NUMBER_SHOTS} shots via
     *       {@link #firingRound(Scanner, IGame)}, then logs hit/invalid/repeated
     *       shot counts and remaining ships. If all ships are sunk, logs a
     *       victory message.</li>
     *   <li>{@code "ver"} — displays all valid shots fired so far.</li>
     * </ul>
     */
    public static void taskD() {
        Scanner in = new Scanner(System.in);
        IFleet fleet = null;
        IGame game = null;
        String command = in.next();
        while (!command.equals(DESISTIR)) {
            switch (command) {
                case NOVAFROTA:
                    fleet = buildFleet(in);
                    game = new Game(fleet);
                    break;
                case STATUS:
                    if (fleet != null)
                        fleet.printStatus();
                    break;
                case BATOTA:
                    if (fleet != null)
                        game.printFleet();
                    break;
                case RAJADA:
                    if (game != null) {
                        firingRound(in, game);
                        LOGGER.info("Hits: {} Inv: {} Rep: {} Restam {} navios.",
                                game.getHits(), game.getInvalidShots(),
                                game.getRepeatedShots(), game.getRemainingShips());
                        if (game.getRemainingShips() == 0)
                            LOGGER.info("Maldito sejas, Java Sparrow, eu voltarei, glub glub glub...");
                    }
                    break;
                case VERTIROS:
                    if (game != null)
                        game.printValidShots();
                    break;
                default:
                    LOGGER.info("Que comando é esse??? Repete ...");
            }
            command = in.next();
        }
        LOGGER.info(GOODBYE_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Package-private helpers (also suitable for unit testing)
    // -------------------------------------------------------------------------

    /**
     * Builds a complete {@link Fleet} by reading ship data from the given scanner.
     * <p>
     * Reads ships one by one until {@link Fleet#FLEET_SIZE} ships have been
     * successfully added. Ships that fail validation (e.g., overlap or proximity
     * violations) are rejected and a warning is logged; the loop continues until
     * the correct total is reached.
     * </p>
     *
     * @param in the {@link Scanner} to read from; must not be {@code null}
     * @return the fully populated {@link Fleet}
     * @throws AssertionError if {@code in} is {@code null}
     *                        (requires assertions enabled with {@code -ea})
     */
    static Fleet buildFleet(Scanner in) {
        assert in != null;

        Fleet fleet = new Fleet();
        int i = 0;

        while (i <= Fleet.FLEET_SIZE) {
            IShip s = readShip(in);
            if (s != null) {
                boolean success = fleet.addShip(s);
                if (success)
                    i++;
                else
                    LOGGER.info("Falha na criacao de {} {} {}", s.getCategory(), s.getBearing(), s.getPosition());
            } else {
                LOGGER.info("Navio desconhecido!");
            }
        }
        LOGGER.info("{} navios adicionados com sucesso!", i);
        return fleet;
    }

    /**
     * Reads ship data from the given scanner, constructs, and returns the ship.
     * <p>
     * Reads (in order): the ship kind string, a position (row + column), and a
     * single character representing the compass bearing. Delegates construction
     * to {@link Ship#buildShip(String, Compass, Position)}.
     * </p>
     *
     * @param in the {@link Scanner} to read from; must not be {@code null}
     * @return the constructed {@link Ship}, or {@code null} if the kind is not recognised
     */
    static Ship readShip(Scanner in) {
        String shipKind = in.next();
        Position pos = readPosition(in);
        char c = in.next().charAt(0);
        Compass bearing = Compass.charToCompass(c);
        return Ship.buildShip(shipKind, bearing, pos);
    }

    /**
     * Reads a board position (row and column) from the given scanner.
     *
     * @param in the {@link Scanner} to read from; must not be {@code null}
     * @return a new {@link Position} with the row and column that were read
     */
    static Position readPosition(Scanner in) {
        int row = in.nextInt();
        int column = in.nextInt();
        return new Position(row, column);
    }

    /**
     * Fires a round of {@value #NUMBER_SHOTS} shots against the given game.
     * <p>
     * For each shot, reads a position and calls {@link IGame#fire(IPosition)}.
     * If a ship is hit, logs its category name with a humorous message.
     * </p>
     *
     * @param in   the {@link Scanner} to read positions from; must not be {@code null}
     * @param game the {@link IGame} context representing the fleet under attack;
     *             must not be {@code null}
     */
    static void firingRound(Scanner in, IGame game) {
        for (int i = 0; i < NUMBER_SHOTS; i++) {
            IPosition pos = readPosition(in);
            IShip sh = game.fire(pos);
            if (sh != null)
                LOGGER.info("Mas... mas... {}s nao sao a prova de bala? :-(", sh.getCategory());
        }
    }
}
