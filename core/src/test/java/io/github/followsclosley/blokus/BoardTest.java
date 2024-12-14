package io.github.followsclosley.blokus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void updatePlayable5Q() {
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);
        board.setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player));

        assertNull(board.getPlayable(0, 0), "Expected (0, 0) to not be playable");
        assertNull(board.getPlayable(1, 0), "Expected (1, 0) to not be playable");
        assertNull(board.getPlayable(2, 0), "Expected (2, 0) to not be playable");
        assertNotNull(board.getPlayable(3, 0), "Expected (3, 0) to be playable");
    }

    @Test
    void updatePlayableStartThen5Q(){
        Player player = new Player(0, "Test Player");
        Board board = new Board(6, 6);

        board.setPiece(0,0, new Piece("5Q", new int[][]{{0,0},{1,0},{1,1},{1,2},{2,1}}, player));
        BlokusTestUtils.printBoard(board);

        assertNull( board.getPlayable(0,0), "The (0,0) should NOT be playable" );
        assertNotNull( board.getPlayable(3,0), "The (3,0) should be playable" );
        assertNull( board.getPlayable(3,1), "The (3,1) should NOT be playable" );
        assertNotNull( board.getPlayable(3,2), "The (3,2) should be playable" );
        assertNull( board.getPlayable(3,3), "The (3,3) should NOT be playable" );
        assertNotNull( board.getPlayable(0,3), "The (0,3) should be playable" );
        assertNotNull( board.getPlayable(2,3), "The (2,3) should be playable" );
    }
}