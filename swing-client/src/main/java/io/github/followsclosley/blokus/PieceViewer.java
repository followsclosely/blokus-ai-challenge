package io.github.followsclosley.blokus;

import javax.swing.*;

public class PieceViewer {
    public static void main(String[] args) throws Exception {

        SwingSupport support = new SwingSupport()
                .board(new Board(22, 16), 65)
                .showPieceNames()
                .showMoves()
                .show();

        Player player0 = new Player(0, "Player 0");

        PieceFactory factory = new PieceFactory();

        int x = 0;
        int y = 0;
        int maxY = 0;
        for (Piece piece : factory.getPieces(player0)) {

            support.getBoard().setPiece(x,y, piece);

            //Move to the next position
            x += piece.getWidth() + 1;
            maxY = Math.max(maxY, piece.getHeight());

            //If we are at the end of the board, move to the next row
            if( x+1 >= support.getBoard().getWidth()) {
                x = 0;
                y += (maxY + 1);
            }
        }

        SwingUtilities.invokeAndWait(() -> support.getBoardPanel().repaint());

    }
}
