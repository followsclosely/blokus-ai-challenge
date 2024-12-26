package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

@Slf4j
public class PlayableBoardPanel extends BoardPanel {

    @Setter
    private boolean showMoves = false;

    private final MyMouseMotionAdapter mouseMotionAdapter = new MyMouseMotionAdapter();

    public PlayableBoardPanel(Board board) {
        this(board, DEFAULT_SQUARE_SIZE);
    }
    
    public PlayableBoardPanel(Board board, int squareSize) {
        super(board, squareSize);
        addMouseMotionListener(mouseMotionAdapter);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if ( showMoves ) {
            drawPlayableSquares(g);
        }

        if( this.selectedPiece != null) {
            for(int[] xy : this.selectedPiece.getShape()){
                paintPiece(this.selectedPiece, mouseMotionAdapter.lastCoordinate.getX()+xy[0], mouseMotionAdapter.lastCoordinate.getY()+xy[1], (Graphics2D) g);
            }
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

    public final class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Getter
        public Coordinate lastCoordinate = new Coordinate(-1,-1);

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX() / squareSize;
            int y = e.getY() / squareSize;


            if( lastCoordinate.getX() != x || lastCoordinate.getY() != y ) {
                this.lastCoordinate = new Coordinate(x,y);
                System.out.println("Location " + x + ", " + y);
                PlayableBoardPanel.this.repaint();
            }
        }
    };
}