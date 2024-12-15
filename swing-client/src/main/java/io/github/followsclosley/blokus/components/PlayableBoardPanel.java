package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.PlayableSquare;
import io.github.followsclosley.blokus.Player;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class PlayableBoardPanel extends BoardPanel {

    @Setter
    private boolean showMoves = false;

    public PlayableBoardPanel(Board board) {
        super(board);
    }
    
    public PlayableBoardPanel(Board board, int squareSize) {
        super(board, squareSize);
    }




    @Override
    public void paint(Graphics g) {
        super.paint(g);
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