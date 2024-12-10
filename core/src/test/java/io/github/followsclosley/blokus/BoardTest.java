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

        List<Coordinate> playableCoordinates = board.getPlayableCoordinates(player);
        assertEquals(4, playableCoordinates.size());

        assertEquals(new Coordinate(3, 0), playableCoordinates.get(0));
        assertEquals(new Coordinate(3, 2), playableCoordinates.get(1));
        assertEquals(new Coordinate(0, 3), playableCoordinates.get(2));
        assertEquals(new Coordinate(2, 3), playableCoordinates.get(3));
    }

    @Test
    void isPlayable() {
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);
        board.setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player));

        assertFalse(board.isPlayable(0, 0, player));
        assertFalse(board.isPlayable(1, 0, player));
        assertFalse(board.isPlayable(2, 0, player));
        assertTrue(board.isPlayable(3, 0, player));
    }
}