package io.github.followsclosley.blokus;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] board;
    private final PlayableSquare[][] playable;

    @Getter
    private final int width, height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Piece[width][height];
        this.playable = new PlayableSquare[width][height];
    }

    public void init(List<Player> players){
        //This supports just 4 player mode at this point.
        if (players.size() == 4)
        {
            playable[0][0] = new PlayableSquare(new Coordinate(0, 0));
            playable[0][0].setUpperLeft(players.get(0));

            playable[width - 1][0] = new PlayableSquare(new Coordinate(width - 1, 0));
            playable[width - 1][0].setUpperRight(players.get(1));

            playable[width - 1][height - 1] = new PlayableSquare(new Coordinate(width - 1, height - 1));
            playable[width - 1][height - 1].setLowerRight(players.get(2));

            playable[0][height - 1] = new PlayableSquare(new Coordinate(0, height - 1));
            playable[0][height - 1].setLowerLeft(players.get(3));
        }
    }

    public Piece getPiece(int x, int y) {
        return ( x<0 || x>=width || y<0 || y>=height) ? null : board[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        int[][] shape = piece.getShape();
        for (int[] xy : shape) {
            board[x + xy[0]][y + xy[1]] = piece;
        }

        //Update the playable spots
        for (int[] xy : shape) {
            playable[x + xy[0]][y + xy[1]] = null;
        }
    }

    public PlayableSquare getPlayable(int x, int y){
        return playable[x][y];
    }

    //remove piece from board
    public void removePiece(int x, int y) {
        Piece piece = getPiece(x, y);
        if (piece == null){
            return;
        }
        int[][] shape = piece.getShape();
        for (int[] xy : shape) {
            board[x + xy[0]][y + xy[1]] = null;
        }
    }

    public List<Coordinate> getPlayable(Player player){
        List<Coordinate> playableCoordinates = new ArrayList<>();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (isPlayable(x, y, player)){
                    playableCoordinates.add(new Coordinate(x, y));
                }
            }
        }
        return playableCoordinates;
    }

    public boolean isPlayable(int x, int y, Player player){
        //Is the space empty.
        if (getPiece(x, y) != null){
            return false;
        }

        //Is the space adjacent to a piece of the same color.
        for (Coordinate direction : SEARCH_DIRECTIONS_ADJACENT){
            Piece adjacent = getPiece(x + direction.getX(), y + direction.getY());
            if (adjacent != null && adjacent.getPlayer() == player){
                return false;
            }
        }

        //Is the space diagonal to a piece of the same color.
        for (Coordinate direction : SEARCH_DIRECTIONS_DIAGONAL){
            Piece diagonal = getPiece(x + direction.getX(), y + direction.getY());
            if (diagonal != null && diagonal.getPlayer() == player){
                return true;
            }
        }

        return false;
    }

    Coordinate[] SEARCH_DIRECTIONS_ADJACENT = {
            new Coordinate(1, 0),
            new Coordinate(0, 1),
            new Coordinate(-1, 0),
            new Coordinate(0, -1)
    };

    Coordinate[] SEARCH_DIRECTIONS_DIAGONAL = {
            new Coordinate(1, 1),
            new Coordinate(-1, 1),
            new Coordinate(-1, -1),
            new Coordinate(1, -1)
    };
}
