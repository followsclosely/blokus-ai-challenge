package io.github.followsclosley.blokus;

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
            support.getBoard().setPiece(0,0, new Piece("5Q", new int[][]{{1,0},{1,1},{1,2},{0,0},{2,1}}, player0));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(7,0, new Piece("5V", new int[][]{{0,0},{1,0}, {2,0}, {2,1},{2,2}}, player1));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(7,7, new Piece("5V", new int[][]{{0,0},{0,1}, {0,2}, {1,2},{2,2}}, player2));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().setPiece(0+1,7-4, new Piece("5V", new int[][]{{0,0},{0,1}, {0,2}, {1,0},{2,0}}, player3));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().removePiece(0+1,7-4);
            support.getBoard().setPiece(0+2,7-4, new Piece("5V", new int[][]{{0,0},{0,1}, {0,2}, {1,0},{2,0}}, player3));
            support.getBoardPanel().repaint();
        });

        Thread.sleep(1000);
        SwingUtilities.invokeAndWait(() -> {
            support.getBoard().removePiece(0+2,7-4);
            support.getBoard().setPiece(0+2,7-3, new Piece("5V", new int[][]{{0,0},{0,1}, {0,2}, {1,0},{2,0}}, player3));
            support.getBoardPanel().repaint();
        });
    }
}
