package io.github.followsclosley.blokus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void getWidth() {
        Player player = new Player(0, "Test Player");
        Piece piece = new Piece("5L", new int[][]{{0,0},{1,0},{2,0},{3,0},{3,1}}, player);

        assertEquals(4, piece.getWidth(), "Expected width to be 4");
    }

    @Test
    void getHeight() {
        Player player = new Player(0, "Test Player");
        Piece piece = new Piece("5L", new int[][]{{0,0},{1,0},{2,0},{3,0},{3,1}}, player);

        assertEquals(2, piece.getHeight(), "Expected height to be 2");
    }
}