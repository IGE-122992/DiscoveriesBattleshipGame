/**
 * Classe abstrata que representa um navio no jogo Battleship Discoveries.
 * <p>
 * Implementa a interface {@link IShip} fornecendo lógica comum para:
 * - posições ocupadas,
 * - verificação de acertos,
 * - proximidade com outros navios,
 * - marcação de tiros.
 * </p>
 * <p>
 * As subclasses concretas (ex.: {@code Barge}, {@code Caravel}, {@code Carrack},
 * {@code Frigate}, {@code Galleon}) apenas definem o tamanho e
 * populam a lista de posições ocupadas.
 * </p>
 *
 * @see IShip
 * @see IPosition
 * @see Compass
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Ship implements IShip {

    /** Nome do navio "galeao" em português. */
    private static final String GALEAO = "galeao";

    /** Nome do navio "fragata" em português. */
    private static final String FRAGATA = "fragata";

    /** Nome do navio "nau" em português. */
    private static final String NAU = "nau";

    /** Nome do navio "caravela" em português. */
    private static final String CARAVELA = "caravela";

    /** Nome do navio "barca" em português. */
    private static final String BARCA = "barca";

    /**
     * Cria uma instância de navio do tipo correto com base na categoria.
     *
     * @param shipKind categoria do navio (barca, caravela, nau, fragata, galeao)
     * @param bearing  orientação do navio
     * @param pos      posição âncora do navio
     * @return instância correspondente ao tipo ou null se a categoria não for reconhecida
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        Ship s;
        switch (shipKind) {
            case BARCA: s = new Barge(bearing, pos); break;
            case CARAVELA: s = new Caravel(bearing, pos); break;
            case NAU: s = new Carrack(bearing, pos); break;
            case FRAGATA: s = new Frigate(bearing, pos); break;
            case GALEAO: s = new Galleon(bearing, pos); break;
            default: s = null;
        }
        return s;
    }

    /** Categoria do navio (ex.: "galeao"). */
    private String category;

    /** Orientação do navio. */
    private Compass bearing;

    /** Posição âncora do navio. */
    private IPosition pos;

    /** Lista de posições ocupadas pelo navio. */
    protected List<IPosition> positions;

    /**
     * Construtor do navio.
     *
     * @param category categoria do navio
     * @param bearing  orientação do navio
     * @param pos      posição âncora do navio
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        assert bearing != null;
        assert pos != null;

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        positions = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Retorna a lista de posições ocupadas pelo navio.
     *
     * @return lista não nula de posições
     */
    @Override
    public List<IPosition> getPositions() {
        return positions;
    }

    /** {@inheritDoc} */
    @Override
    public IPosition getPosition() {
        return pos;
    }

    /** {@inheritDoc} */
    @Override
    public Compass getBearing() {
        return bearing;
    }

    /**
     * Verifica se o navio ainda está flutuando.
     *
     * @return true se houver alguma posição não atingida, false caso contrário
     */
    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();
        return top;
    }

    /** {@inheritDoc} */
    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();
        return bottom;
    }

    /** {@inheritDoc} */
    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();
        return left;
    }

    /** {@inheritDoc} */
    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();
        return right;
    }

    /**
     * Verifica se o navio ocupa uma posição específica.
     *
     * @param pos posição a verificar
     * @return true se o navio estiver nessa posição, false caso contrário
     */
    @Override
    public boolean occupies(IPosition pos) {
        assert pos != null;

        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;
        return false;
    }

    /**
     * Verifica se este navio está muito próximo de outro navio.
     *
     * @param other outro navio
     * @return true se alguma posição do outro navio estiver adjacente, false caso contrário
     */
    @Override
    public boolean tooCloseTo(IShip other) {
        assert other != null;

        Iterator<IPosition> otherPos = other.getPositions().iterator();
        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;
        return false;
    }

    /**
     * Verifica se uma posição está muito próxima deste navio.
     *
     * @param pos posição a verificar
     * @return true se a posição estiver adjacente a alguma posição do navio
     */
    @Override
    public boolean tooCloseTo(IPosition pos) {
        for (int i = 0; i < this.getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;
        return false;
    }

    /**
     * Marca um tiro na posição fornecida, caso este navio a ocupe.
     *
     * @param pos posição alvo
     */
    @Override
    public void shoot(IPosition pos) {
        assert pos != null;

        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}
