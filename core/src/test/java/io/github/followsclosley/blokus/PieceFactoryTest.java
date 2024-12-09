package io.github.followsclosley.blokus;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PieceFactoryTest {
    @Test
    public void test() throws IOException {
        PieceFactory pieceFactory = new PieceFactory();
        pieceFactory.getPieces(null);
    }

}