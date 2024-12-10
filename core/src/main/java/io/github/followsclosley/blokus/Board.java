package io.github.followsclosley.blokus;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public void init(Player... players){
        //This supports just 4 player mode at this point.
        if (players.length > 0) {
            playable[0][0] = new PlayableSquare(new Coordinate(0, 0));
            playable[0][0].setUpperLeft(players[0]);
        }

        if (players.length > 1) {
            playable[width - 1][0] = new PlayableSquare(new Coordinate(width - 1, 0));
            playable[width - 1][0].setUpperRight(players[1]);
        }

        if (players.length > 2) {
            playable[width - 1][height - 1] = new PlayableSquare(new Coordinate(width - 1, height - 1));
            playable[width - 1][height - 1].setLowerRight(players[2]);
        }

        if (players.length > 3) {
            playable[0][height - 1] = new PlayableSquare(new Coordinate(0, height - 1));
            playable[0][height - 1].setLowerLeft(players[3]);
        }
    }

    public Piece getPiece(Coordinate c){
        return getPiece(c.getX(), c.getY());
    }
    public Piece getPiece(int x, int y) {
        return ( x<0 || x>=width || y<0 || y>=height) ? null : board[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        for (int[] xy : piece.getShape()) {
            board[x + xy[0]][y + xy[1]] = piece;
        }

        updatePlayable(x,y,piece);
    }

    public void updatePlayable(int x, int y, Piece piece){
        for (int[] xy : piece.getShape()) {
            //null out the spaces consumed
            playable[x + xy[0]][y + xy[1]] = null;


            for (Coordinate delta : SEARCH_DIRECTIONS_DIAGONAL){
                Coordinate diagonalCoordinate = new Coordinate(x + delta.getX(), y + delta.getY());
                Piece diagonal = getPiece(diagonalCoordinate);
                if( diagonal == null){
                    boolean valid = true;
                    for (Coordinate adjacent : SEARCH_DIRECTIONS_ADJACENT) {
                        Coordinate translated = diagonalCoordinate.translate(adjacent);
                        Piece adjacentPiece = getPiece(translated);
                        if( adjacentPiece != null && adjacentPiece.getPlayer().equals(piece.getPlayer())){
                            valid = false;
                            break;
                        }
                    }

                    if ( valid ){
                        PlayableSquare playable = getPlayable(diagonalCoordinate);
                        if( playable == null ){
                            this.playable[diagonalCoordinate.getY()][diagonalCoordinate.getY()] = playable = new PlayableSquare(diagonalCoordinate);
                        }

                        //Set the correct direction?
                        //playable.
                    }
                }
            }
        }
    }

    public PlayableSquare getPlayable(Coordinate c){
        return getPlayable(c.getX(), c.getY());
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

    private static final class SearchDirection extends Coordinate{
        public SearchDirection(int x, int y){
            super(x, y);
        }
    }
}
