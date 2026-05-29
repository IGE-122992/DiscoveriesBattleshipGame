package iscteiul.ista.battleship;

/**
 * Representa um navio do tipo Fragata.
 * 
 * A Fragata:
 * - Tem dimensão 4 posições;
 * - Pode ser orientada a NORTH, SOUTH, EAST ou WEST;
 * - As posições ocupadas são calculadas a partir da posição inicial.
 * 
 * Esta classe herda da classe Ship.
 */
public class Frigate extends Ship {

    /** Dimensão fixa da Fragata. */
    private static final Integer SIZE = 4;

    /** Nome do navio. */
    private static final String NAME = "Fragata";

    /**
     * Constrói uma Fragata com determinada orientação e posição inicial.
     * 
     * As posições ocupadas pelo navio são automaticamente calculadas
     * com base na orientação (bearing).
     *
     * @param bearing orientação do navio (NORTH, SOUTH, EAST ou WEST)
     * @param pos posição inicial do navio
     * @throws IllegalArgumentException se a orientação for inválida
     */
    public Frigate(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Frigate.NAME, bearing, pos);

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
                throw new IllegalArgumentException("ERROR! invalid bearing for the frigate");
        }
    }

    /**
     * Devolve o tamanho da Fragata.
     *
     * @return dimensão do navio (4)
     */
    @Override
    public Integer getSize() {
        return Frigate.SIZE;
    }
}
