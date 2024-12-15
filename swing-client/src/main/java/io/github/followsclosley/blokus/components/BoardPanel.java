package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@Slf4j
public class BoardPanel extends JPanel {
    public static final int DEFAULT_SQUARE_SIZE = 55;
    protected int squareSize;
    protected int borderWidth;
    protected int gridWidth;
    protected int smallJoint;
    protected int largeJoint;

    @Setter
    private boolean showPieceNames = false;


    protected final Color[] COLORS = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
    protected final Color[] COLORS_DARKER = {Color.GREEN.darker(), Color.BLUE.darker(), Color.RED.darker(), Color.YELLOW.darker()};
    protected final Board board;
    protected Dimension defaultDimension;

    public BoardPanel(Board board) {
        this(board, DEFAULT_SQUARE_SIZE);
    }

    public BoardPanel(Board board, int squareSize) {
        this.board = board;
        this.squareSize = squareSize;
        this.defaultDimension = new Dimension(board.getWidth() * squareSize, board.getHeight() * squareSize);
        this.resize();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                BoardPanel.this.squareSize = Math.min(e.getComponent().getWidth() / board.getWidth(), e.getComponent().getHeight() / board.getHeight());
                resize();
            }
        });
    }

    public void resize(){
        this.borderWidth = Math.max(squareSize/10,2);
        this.gridWidth = squareSize/10;
        this.smallJoint = Math.max(squareSize/4,6);
        this.largeJoint = squareSize-gridWidth-3;
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
                        g.setColor(COLORS_DARKER[piece.getPlayer().getIndex()].darker());
                        g.fillRect((x+1) * squareSize-borderWidth-1, y * squareSize+2, smallJoint, largeJoint);
                    }

                    //Draw the space between the vertical pieces
                    Piece pieceBelow = board.getPiece(x, y + 1);
                    if (pieceBelow != null && pieceBelow.equals(piece)) {
                        g.setColor(COLORS_DARKER[piece.getPlayer().getIndex()].darker());
                        g.fillRect(x * squareSize+1, (y+1) * squareSize-(borderWidth+1), largeJoint, smallJoint);
                    }


                    //Draw the piece
                    g.setColor(COLORS_DARKER[piece.getPlayer().getIndex()]);
                    g.fillRoundRect(x * squareSize, y * squareSize, squareSize - gridWidth, squareSize - gridWidth, 10, 10);
                    g.setColor(color);
                    g.fillRoundRect(x * squareSize+borderWidth, y * squareSize+borderWidth, squareSize - (borderWidth*2+gridWidth), squareSize - (borderWidth*2+gridWidth), 10, 10);

                    if(showPieceNames) {
                        g.setColor(COLORS_DARKER[piece.getPlayer().getIndex()].darker());
                        g.drawString(String.valueOf(piece.getName()), x * squareSize + 18, y * squareSize + 30);
                    }



                } else {
                    g.setColor(Color.WHITE);
                    g.fillRoundRect(x * squareSize, y * squareSize, squareSize - 5, squareSize - 5, 10, 10);
                }


            }
        }
    }
}
