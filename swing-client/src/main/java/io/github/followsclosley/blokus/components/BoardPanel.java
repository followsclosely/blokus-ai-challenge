package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.Player;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@Slf4j
public class BoardPanel extends JPanel {

    public static final int PIECE_SIZE = 55;
    private final Color[] COLORS = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
    private final Board board;
    protected Dimension defaultDimension;

    @Setter
    private boolean showPieceNames = false;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE, board.getHeight() * PIECE_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the pieces on the board
        for (int y = 0, height = board.getHeight(); y < height; y++) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {

                Piece piece = board.getPiece(x, y);
                if(piece != null) {
                    Color color = COLORS[piece.getPlayer().getIndex()];

                    //Draw the space between the horizontal pieces
                    Piece pieceToTheRight = board.getPiece(x + 1, y);
                    if (pieceToTheRight != null && pieceToTheRight.equals(piece)) {
                        g.setColor(color.darker().darker());
                        g.fillRect((x+1) * PIECE_SIZE-10, y * PIECE_SIZE+2, 20, PIECE_SIZE-9);
                    }

                    //Draw the space between the vertical pieces
                    Piece pieceBelow = board.getPiece(x, y + 1);
                    if (pieceBelow != null && pieceBelow.equals(piece)) {
                        g.setColor(color.darker().darker());
                        g.fillRect(x * PIECE_SIZE+2, (y+1) * PIECE_SIZE-10, PIECE_SIZE-9, 20);
                    }

                    //Draw the piece
                    g.setColor(color.darker());
                    g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, 10, 10);
                    g.setColor(color);
                    g.fillRoundRect(x * PIECE_SIZE+5, y * PIECE_SIZE+5, PIECE_SIZE - 15, PIECE_SIZE - 15, 10, 10);

                    if(showPieceNames) {
                        g.setColor(color.darker().darker());
                        g.drawString(String.valueOf(piece.getName()), x * PIECE_SIZE + 18, y * PIECE_SIZE + 30);
                    }
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, 10, 10);
                }
            }
        }



        //This is a hack to draw the playable coordinates on top of the pieces
        java.util.List<Player> players = new ArrayList<>();

        for (int y = 0, height = board.getHeight(); y < height; y++) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    Player player = piece.getPlayer();
                    if (player != null && !players.contains(player)) {
                        players.add(piece.getPlayer());
                    }
                }
            }
        }

        for (Player player : players) {
            if (player != null) {
                //Draw playable coordinates
                g.setColor(COLORS[player.getIndex()]);
                board.getPlayableCoordinates(player).forEach(coordinate ->
                    g.fillOval(coordinate.getX() * PIECE_SIZE + 20, coordinate.getY() * PIECE_SIZE + 20, 15, 15));
            }
        }

    }
}