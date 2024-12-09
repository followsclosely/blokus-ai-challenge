package io.github.followsclosley;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.PieceFactory;
import io.github.followsclosley.blokus.Player;

import javax.swing.*;

public class PieceViewer {
    public static void main(String[] args) throws Exception {
        SwingSupport support = new SwingSupport()
                .board(new Board(22, 16))
                .show();

        Player player0 = new Player(0, "Player 0", null);

        PieceFactory factory = new PieceFactory();

        int x = 0;
        int y = 0;
        int maxY = 0;
        for (Piece piece : factory.getPieces(player0)) {
            support.getBoard().setPiece(x,y, piece);

            int[] max = factory.getMaxXY(piece);
            x += max[0] + 2;
            maxY = Math.max(maxY, max[1]);

            if( x+1 >= support.getBoard().getWidth()) {
                x = 0;
                y += maxY + 2;
            }
        }

        SwingUtilities.invokeAndWait(() -> support.getBoardPanel().repaint());

    }
}
