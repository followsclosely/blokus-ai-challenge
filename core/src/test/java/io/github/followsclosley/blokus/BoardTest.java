package io.github.followsclosley.blokus;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getPlayableCoordinates() {
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);
        board.setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player));

        List<Coordinate> playableCoordinates = board.getPlayable(player);
        assertEquals(4, playableCoordinates.size());

        assertTrue(playableCoordinates.contains(new Coordinate(3, 0)), "Expected (3, 0) to be playable");
        assertTrue(playableCoordinates.contains(new Coordinate(3, 2)), "Expected (3, 2) to be playable");
        assertTrue(playableCoordinates.contains(new Coordinate(0, 3)), "Expected (0, 3 to be playable");
        assertTrue(playableCoordinates.contains(new Coordinate(2, 3)), "Expected (2, 3) to be playable");
    }

    @Test
    void isPlayable() {
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);
        board.setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player));

        assertFalse(board.isPlayable(0, 0, player), "Expected (0, 0) to not be playable");
        assertFalse(board.isPlayable(1, 0, player), "Expected (1, 0) to not be playable");
        assertFalse(board.isPlayable(2, 0, player), "Expected (2, 0) to not be playable");
        assertTrue(board.isPlayable(3, 0, player), "Expected (3, 0) to be playable");
    }

    @Test
    void updatePlayable(){
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);
        board.init(player);
        BlokusTestUtils.printBoard(board);

        PlayableSquare playable = board.getPlayable(0,0);
        assertEquals(player, playable.getUpperLeft(), "The top left should be playable.");
        board.setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player));
        BlokusTestUtils.printBoard(board);


//        for(int i =0; i<256; i++){
//            System.out.println(i + " " + Character.toString(i));
//        }
    }
}