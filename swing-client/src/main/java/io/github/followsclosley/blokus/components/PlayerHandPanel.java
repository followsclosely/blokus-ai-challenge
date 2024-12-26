package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerHandPanel extends BoardPanel {


    public PlayerHandPanel(Board board) {
        this(board, DEFAULT_SQUARE_SIZE);
    }

    public PlayerHandPanel(Board board, int squareSize) {
        super(board, squareSize);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / squareSize;
                int y = e.getY() / squareSize;
                System.out.println("Piece selected at " + x + ", " + y);
                if (x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
                    Piece piece = board.getPiece(x, y);
                    setSelectedPiece(piece);
                    repaint();
                }
            }
        });
    }



}
