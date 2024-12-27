package io.github.followsclosley.blokus.components;

import io.github.followsclosley.blokus.Board;
import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.event.PieceSelectedEvent;
import io.github.followsclosley.blokus.event.PieceSelectedEventListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

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

    protected final Color[] SELECTED_COLORS = {Color.GREEN.brighter(), Color.BLUE.brighter(), Color.RED.brighter(), Color.YELLOW.brighter()};
    protected final Color[] SELECTED_COLORS_DARKER = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW};

    protected final Board board;
    protected Dimension defaultDimension;

    protected Piece selectedPiece;

    private final List<PieceSelectedEventListener> pieceSelectedEventListeners = new ArrayList<>();

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
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        super.paintComponent(g);

        //Draw the pieces on the board
        for (int y = 0, height = board.getHeight(); y < height; y++) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {

                Piece piece = board.getPiece(x, y);

                setComposite(piece, x, y, g);

                if(piece != null) {
                    paintPiece(piece, x, y, g);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRoundRect(x * squareSize, y * squareSize, squareSize - 5, squareSize - 5, 10, 10);
                }
            }
        }
    }

    public void paintPiece(Piece piece, int x, int y, Graphics2D g) {
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
        g.setColor(COLORS[piece.getPlayer().getIndex()]);
        g.fillRoundRect(x * squareSize+borderWidth, y * squareSize+borderWidth, squareSize - (borderWidth*2+gridWidth), squareSize - (borderWidth*2+gridWidth), 10, 10);

        if(showPieceNames) {
            g.setColor(COLORS_DARKER[piece.getPlayer().getIndex()].darker());
            g.drawString(String.valueOf(piece.getName()), x * squareSize + 18, y * squareSize + 30);
        }
    }

    public void setComposite(Piece piece, int x, int y, Graphics2D g){}

    public void addPieceSelectedEventListener(PieceSelectedEventListener listener) {
        this.pieceSelectedEventListeners.add(listener);
    }

    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
        if( this.selectedPiece != null ) {
            PieceSelectedEvent event = new PieceSelectedEvent(piece, piece.getPlayer());
            this.pieceSelectedEventListeners.forEach(l -> l.onPieceSelected(event));
        }
    }
}
