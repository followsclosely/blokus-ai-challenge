package io.github.followsclosley.blokus;

import io.github.followsclosley.blokus.components.BoardPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;


@Getter
public class SwingSupport {

    private Board board;
    private BoardPanel boardPanel;

    public SwingSupport board(Board board) {
        this.board = board;
        this.boardPanel = new BoardPanel(board);
        return this;
    }

    public SwingSupport showPieceNames() {
        this.boardPanel.setShowPieceNames(true);
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
        //frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        return this;
    }
}
