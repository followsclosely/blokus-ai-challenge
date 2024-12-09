package io.github.followsclosley;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.PieceFactory;
import io.github.followsclosley.blokus.Player;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Player player0 = new Player(0, "Player 0");
        Player player1 = new Player(1, "Player 1");
        Player player2 = new Player(2, "Player 2");
        Player player3 = new Player(3, "Player 3");

        SwingSupport support = new SwingSupport()
                .board(new Board(10, 10))
                .showPieceNames()
                .show();

        //This is a hack to show a few random pieces on the board
        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(0,0, new Piece("5V", player0, new int[][]{{0,0},{1,0}, {2,0}, {2,1},{2,2}}));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(7,0, new Piece("5V", player1, new int[][]{{0,0},{1,0}, {2,0}, {2,1},{2,2}}));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(7,7, new Piece("5V", player2, new int[][]{{0,0},{0,1}, {0,2}, {1,2},{2,2}}));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(0,7, new Piece("5V", player3, new int[][]{{0,0},{0,1}, {0,2}, {1,0},{2,0}}));
            support.getBoardPanel().repaint();
        });
    }
}
