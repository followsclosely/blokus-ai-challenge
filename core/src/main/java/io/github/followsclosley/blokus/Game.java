package io.github.followsclosley.blokus;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private final Board board;
    private final List<Player> players = new ArrayList<>();

    public Game(Board board) {
        this.board = board;
    }

    public Player addPlayer(Player player) {
        players.add(player);
        return player;
    }

    /**
     * Initialize the board in the following way for a <b>2 player</b> game:
     * <ol>
     *     <li>Player 0 is placed in the upper left area and that square is marked as playable.</li>
     *     <li>Player 1 is placed in the lower right area and that square is marked as playable.</li>
     * </ol>
     *
     *
     * Initialize the board in the following way for a <b>4 player</b> game:
     * <ol>
     *     <li>Player 0 is placed in the upper left corner and that corner is marked as playable.</li>
     *     <li>Player 1 is placed in the upper right corner and that corner is marked as playable.</li>
     *     <li>Player 2 is placed in the lower right corner and that corner is marked as playable.</li>
     *     <li>Player 3 is placed in the lower left corner and that corner is marked as playable.</li>
     * </ol>
     */
    public void init(){
        if( players.size() == 2){
            board.setPlayable(new PlayableSquare(new Coordinate(4, 4))).setAll(players.get(0));
            board.setPlayable(new PlayableSquare(new Coordinate(board.getWidth() - 5, board.getHeight() - 5))).setAll(players.get(1));
        }
        else if ( players.size() == 4 ){
            board.setPlayable(new PlayableSquare(new Coordinate(0, 0))).setUpperLeft(players.get(0));
            board.setPlayable(new PlayableSquare(new Coordinate(board.getWidth() - 1, 0))).setUpperRight(players.get(1));
            board.setPlayable(new PlayableSquare(new Coordinate(board.getWidth() - 1, board.getHeight() - 1))).setLowerRight(players.get(2));
            board.setPlayable(new PlayableSquare(new Coordinate(0, board.getHeight() - 1))).setLowerLeft(players.get(3));
        } else {
            throw new RuntimeException("Invalid number of players: " + players.size());
        }
    }
}
