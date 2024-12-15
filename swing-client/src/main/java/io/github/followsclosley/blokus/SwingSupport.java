package io.github.followsclosley.blokus;

import io.github.followsclosley.blokus.components.BoardPanel;
import io.github.followsclosley.blokus.components.PlayableBoardPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;


@Getter
public class SwingSupport {
    private Board hand;
    private Board board;
    private PlayableBoardPanel boardPanel;
    private BoardPanel handPanel;

    public SwingSupport board(Board board) {
        this.board = board;
        this.boardPanel = new PlayableBoardPanel(board);
        return this;
    }

    public SwingSupport board(Board board, int pieceSize) {
        this.boardPanel = new PlayableBoardPanel(this.board = board, pieceSize);
        return this;
    }

    public SwingSupport hand(Board board, int pieceSize) {
        this.handPanel = new BoardPanel(this.hand = board, pieceSize);
        return this;
    }

    public SwingSupport showPieceNames() {
        this.boardPanel.setShowPieceNames(true);
        return this;
    }

    public SwingSupport showMoves() {
        this.boardPanel.setShowMoves(true);
        return this;
    }

    public SwingSupport show()
    {
//        JPanel statusPanel = new JPanel(new BorderLayout());
//        JTextField status = new JTextField("");
//        status.setEditable(false);
//        statusPanel.add(status, BorderLayout.CENTER);
//        JButton undo = new JButton("<");
//        undo.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> {}));
//        statusPanel.add(undo, BorderLayout.EAST);

        JFrame frame = new JFrame("Blokus AI:Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        //GridBagConstraints c = new GridBagConstraints();
        frame.add(this.boardPanel, BorderLayout.CENTER);
        if( handPanel != null ) {
            frame.add(handPanel, BorderLayout.EAST);
        }
        frame.pack();
        frame.setVisible(true);

        return this;
    }
}
