package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.PlayableSquare;
import io.github.followsclosley.blokus.Player;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@Slf4j
public class BoardPanel extends JPanel {

    public static final int DEFAULT_SQUARE_SIZE = 55;
    private int squareSize;
    private int borderWidth;
    private int gridWidth;
    private int smallJoint;
    private int largeJoint;
    
    private final Color[] COLORS = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};
    private final Board board;
    protected Dimension defaultDimension;

    @Setter
    private boolean showPieceNames = false;
    @Setter
    private boolean showMoves = false;

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

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    public void resize(){


        this.borderWidth = Math.max(squareSize/10,2);
        this.gridWidth = squareSize/10;
        this.smallJoint = Math.max(squareSize/4,6);
        this.largeJoint = squareSize-gridWidth-4;
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
                        g.fillRect((x+1) * squareSize-borderWidth-1, y * squareSize+2, smallJoint, largeJoint);
                    }

                    //Draw the space between the vertical pieces
                    Piece pieceBelow = board.getPiece(x, y + 1);
                    if (pieceBelow != null && pieceBelow.equals(piece)) {
                        g.setColor(color.darker().darker());
                        g.fillRect(x * squareSize+1, (y+1) * squareSize-(borderWidth+2), largeJoint, smallJoint);
                    }


                    //Draw the piece
                    g.setColor(color.darker());
                    g.fillRoundRect(x * squareSize, y * squareSize, squareSize - gridWidth, squareSize - gridWidth, 10, 10);
                    g.setColor(color);

                    g.fillRoundRect(x * squareSize+borderWidth, y * squareSize+borderWidth, squareSize - (borderWidth*2+gridWidth), squareSize - (borderWidth*2+gridWidth), 10, 10);

                    if(showPieceNames) {
                        g.setColor(color.darker().darker());
                        g.drawString(String.valueOf(piece.getName()), x * squareSize + 18, y * squareSize + 30);
                    }



                } else {
                    g.setColor(Color.WHITE);
                    g.fillRoundRect(x * squareSize, y * squareSize, squareSize - 5, squareSize - 5, 10, 10);
                }


            }
        }

        if ( showMoves ) {
            drawPlayableSquares(g);
        }



    }

    private void drawPlayableSquares(Graphics g){
        for (int y = 0, height = board.getHeight(); y < height; y++) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                PlayableSquare playable = board.getPlayable(x,y);
                if( playable != null ) {

                    Player upperLeft = playable.getUpperLeft();
                    if (upperLeft != null) {
                        g.setColor(COLORS[upperLeft.getIndex()]);
                        g.fillOval(playable.getCoordinate().getX() * squareSize + 5, playable.getCoordinate().getY() * squareSize + 5, 10, 10);
                    }

                    Player upperRight = playable.getUpperRight();
                    if (upperRight != null) {
                        g.setColor(COLORS[upperRight.getIndex()]);
                        g.fillOval(playable.getCoordinate().getX() * squareSize + squareSize-20, playable.getCoordinate().getY() * squareSize + 5, 10, 10);
                    }

                    Player lowerRight = playable.getLowerRight();
                    if (lowerRight != null) {
                        g.setColor(COLORS[lowerRight.getIndex()]);
                        g.fillOval(playable.getCoordinate().getX() * squareSize + squareSize-20, playable.getCoordinate().getY() * squareSize + squareSize-20, 10, 10);
                    }

                    Player lowerLeft = playable.getLowerLeft();
                    if (lowerLeft != null) {
                        g.setColor(COLORS[lowerLeft.getIndex()]);
                        g.fillOval(playable.getCoordinate().getX() * squareSize + 5, playable.getCoordinate().getY() * squareSize + squareSize-20, 10, 10);
                    }
                }
            }
        }
    }
}