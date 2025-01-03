package io.github.followsclosley.blokus;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * The board class represents the game board.
 */
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
    public static Board createTwoPlayerBoard(){
        return new Board(14, 14);
    }
    public static Board createFourPlayerBoard(){
        return new Board(20, 20);
    }

    public Piece getPiece(Coordinate c){
        return getPiece(c.getX(), c.getY());
    }
    public Piece getPiece(int x, int y) {
        return ( x<0 || x>=width || y<0 || y>=height) ? null : board[x][y];
    }

    public void setPiece(Coordinate c, Piece piece) {
        setPiece(c.getX(), c.getY(), piece);
    }
    /**
     * Set the piece on the board.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param piece the piece
     */
    public void setPiece(int x, int y, Piece piece) {
        for (int[] xy : piece.getShape()) {
            board[x + xy[0]][y + xy[1]] = piece;
        }

        updatePlayable(x,y,piece);
    }

    /**
     * Update the playable squares on the board.
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param piece the piece
     */
    public void updatePlayable(int x, int y, Piece piece){
        Coordinate locationOfPlay = new Coordinate(x,y);
        //null out the spaces consumed
        for (int[] xy : piece.getShape()) {
            //log.info("Nulling out {} {}", x + xy[0], y + xy[1]);
            playable[x + xy[0]][y + xy[1]] = null;
        }

        for (int[] xy : piece.getShape()) {
            Coordinate locationOfPiece = locationOfPlay.translate(xy);

            //clear the current player from the adjacent squares
            for(Coordinate direction : SEARCH_DIRECTIONS_ADJACENT){
                Coordinate adjacentCoordinate = locationOfPiece.translate(direction);
                PlayableSquare playableSquare = getPlayable(adjacentCoordinate);
                if( playableSquare != null ){
                    playableSquare.clear(piece.getPlayer());
                }
            }

            //Create the PlayableSquare and set the current player on the diagonal squares if:
            // 1. The diagonal square is on the board.
            // 2. The diagonal square is empty.
            // 3. The adjacent squares are not the same color.
            for(int i=0, length=SEARCH_DIRECTIONS_DIAGONAL.length; i<length; i++){
                Coordinate delta = SEARCH_DIRECTIONS_DIAGONAL[i];
                Coordinate diagonalCoordinate = locationOfPiece.translate(delta);

                if ( isOnBoard(diagonalCoordinate) ) {

                    Piece diagonal = getPiece(diagonalCoordinate);
                    //If the diagonal is empty and the adjacent spaces are not the same color, then it is playable.
                    if (diagonal == null) {
                        boolean valid = true;
                        for (Coordinate adjacent : SEARCH_DIRECTIONS_ADJACENT) {
                            Coordinate translated = diagonalCoordinate.translate(adjacent);
                            Piece adjacentPiece = getPiece(translated);
                            if (adjacentPiece != null && adjacentPiece.getPlayer().equals(piece.getPlayer())) {
                                valid = false;
                                break;
                            }
                        }

                        PlayableSquare playable = getPlayable(diagonalCoordinate);

                        if (valid) {
                            if (playable == null) {
                                setPlayable(playable = new PlayableSquare(diagonalCoordinate));
                            }
                            setPlayerOn(playable, i).accept(piece.getPlayer());
                        } else {
                            if (playable != null) {
                                setPlayerOn(playable, i).accept(null);
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Set the player on the playable square using the index i.
     *
     * @param playable the playable square
     * @param i the index
     * @return the consumer
     */
    private Consumer<Player> setPlayerOn(PlayableSquare playable, int i){
        return switch(i){
            case 0 -> playable::setUpperLeft;
            case 1 -> playable::setUpperRight;
            case 2 -> playable::setLowerRight;
            case 3 -> playable::setLowerLeft;
            default -> throw new IllegalArgumentException("Invalid index: " + i);
        };
    }

    public PlayableSquare setPlayable(PlayableSquare playable){
        this.playable[playable.getCoordinate().getX()][playable.getCoordinate().getY()] = playable;
        return playable;
    }
    public PlayableSquare getPlayable(Coordinate c){
        return getPlayable(c.getX(), c.getY());
    }
    public PlayableSquare getPlayable(int x, int y){
        return (x>=0 && x<width && y>=0 && y<height) ? playable[x][y] : null;
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

    public boolean isOnBoard(Coordinate c){
        return isOnBoard(c.getX(), c.getY());
    }
    public boolean isOnBoard(int x, int y){
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private static final Coordinate[] SEARCH_DIRECTIONS_ADJACENT = {
            new Coordinate(1, 0),
            new Coordinate(0, 1),
            new Coordinate(-1, 0),
            new Coordinate(0, -1)
    };

    private static final Coordinate[] SEARCH_DIRECTIONS_DIAGONAL = {
            new Coordinate(1, 1),
            new Coordinate(-1, 1),
            new Coordinate(-1, -1),
            new Coordinate(1, -1)
    };
}
