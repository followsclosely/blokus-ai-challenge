package io.github.followsclosley.blokus;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PieceFactoryTest {

    @Test
    public void testDefault() throws IOException {
        PieceFactory pieceFactory = new PieceFactory();
        List<Piece> pieces = pieceFactory.getPieces(null);

        assertNotNull(pieces);
        assertEquals(21, pieces.size());
    }

    @Test
    public void testTiny() throws IOException {
        PieceFactory pieceFactory = new PieceFactory(PieceFactory.TINY_PIECES_FILE);
        List<Piece> pieces = pieceFactory.getPieces(null);

        assertNotNull(pieces);
        assertEquals(6, pieces.size());
    }

}