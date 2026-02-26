package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a frota de um jogador.
 *
 * A frota é um conjunto de navios (IShip) colocados numa grelha de dimensão BOARD_SIZE.
 * Esta classe é responsável por:
 * - armazenar os navios da frota;
 * - adicionar navios validando regras (limites da grelha e ausência de colisões/proximidade);
 * - obter navios por categoria e navios ainda a flutuar;
 * - determinar se existe um navio numa determinada posição.
 *
 * Implementa a interface IFleet.
 */
public class Fleet implements IFleet {

    /**
     * Imprime no ecrã todos os navios fornecidos.
     *
     * @param ships lista de navios a imprimir
     */
    static void printShips(List<IShip> ships) {
        for (IShip ship : ships)
            System.out.println(ship);
    }

    // -----------------------------------------------------

    /** Lista de navios que compõem a frota. */
    private List<IShip> ships;

    /**
     * Constrói uma frota vazia.
     */
    public Fleet() {
        ships = new ArrayList<>();
    }

    /**
     * Devolve a lista de navios da frota.
     *
     * @return lista de navios (IShip) da frota
     */
    @Override
    public List<IShip> getShips() {
        return ships;
    }

    /**
     * Adiciona um navio à frota, validando as regras:
     * - a frota não pode exceder FLEET_SIZE;
     * - o navio tem de ficar totalmente dentro do tabuleiro;
     * - o navio não pode ficar em colisão nem demasiado próximo de navios já existentes.
     *
     * @param s navio a adicionar
     * @return true se o navio for adicionado com sucesso; false caso contrário
     */
    @Override
    public boolean addShip(IShip s) {
        boolean result = false;
        if ((ships.size() <= FLEET_SIZE) && (isInsideBoard(s)) && (!colisionRisk(s))) {
            ships.add(s);
            result = true;
        }
        return result;
    }

    /**
     * Devolve todos os navios da frota cuja categoria coincide com a categoria fornecida.
     *
     * @param category categoria de navio (ex.: "Galeao", "Fragata", ...)
     * @return lista de navios da categoria indicada
     */
    @Override
    public List<IShip> getShipsLike(String category) {
        List<IShip> shipsLike = new ArrayList<>();
        for (IShip s : ships)
            if (s.getCategory().equals(category))
                shipsLike.add(s);

        return shipsLike;
    }

    /**
     * Devolve a lista de navios que ainda não foram totalmente afundados.
     *
     * @return lista de navios que ainda estão a flutuar
     */
    @Override
    public List<IShip> getFloatingShips() {
        List<IShip> floatingShips = new ArrayList<>();
        for (IShip s : ships)
            if (s.stillFloating())
                floatingShips.add(s);

        return floatingShips;
    }

    /**
     * Procura e devolve o navio que ocupa a posição indicada.
     *
     * @param pos posição a verificar
     * @return o navio que ocupa a posição; null se não existir navio nessa posição
     */
    @Override
    public IShip shipAt(IPosition pos) {
        for (int i = 0; i < ships.size(); i++)
            if (ships.get(i).occupies(pos))
                return ships.get(i);
        return null;
    }

    /**
     * Verifica se um navio está totalmente dentro dos limites do tabuleiro.
     *
     * @param s navio a validar
     * @return true se estiver dentro do tabuleiro; false caso contrário
     */
    private boolean isInsideBoard(IShip s) {
        return (s.getLeftMostPos() >= 0 && s.getRightMostPos() <= BOARD_SIZE - 1
                && s.getTopMostPos() >= 0 && s.getBottomMostPos() <= BOARD_SIZE - 1);
    }

    /**
     * Verifica se existe risco de colisão/proximidade entre o navio fornecido
     * e os navios já presentes na frota.
     *
     * @param s navio a validar
     * @return true se existir colisão/proximidade (tooCloseTo); false caso contrário
     */
    private boolean colisionRisk(IShip s) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).tooCloseTo(s))
                return true;
        }
        return false;
    }

    /**
     * Mostra o estado atual da frota, imprimindo:
     * - todos os navios;
     * - navios ainda a flutuar;
     * - navios agrupados por categoria (Descobrimentos).
     */
    public void printStatus() {
        printAllShips();
        printFloatingShips();
        printShipsByCategory("Galeao");
        printShipsByCategory("Fragata");
        printShipsByCategory("Nau");
        printShipsByCategory("Caravela");
        printShipsByCategory("Barca");
    }

    /**
     * Imprime os navios de uma determinada categoria.
     *
     * @param category categoria de navio pretendida
     * @throws AssertionError se category for null (quando assertions estão ativas)
     */
    public void printShipsByCategory(String category) {
        assert category != null;
        printShips(getShipsLike(category));
    }

    /**
     * Imprime os navios da frota que ainda se encontram a flutuar.
     */
    public void printFloatingShips() {
        printShips(getFloatingShips());
    }

    /**
     * Imprime todos os navios da frota.
     */
    void printAllShips() {
        printShips(ships);
    }
}
