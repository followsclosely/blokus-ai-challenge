package io.github.followsclosley.blokus;

import lombok.Getter;

public class Board {
    private final Piece[][] board;
    @Getter
    private final int width, height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Piece[width][height];
    }

    public Piece getPiece(int x, int y) {
        return ( x<0 || x>=width || y<0 || y>=height) ? null : board[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        int[][] shape = piece.getShape();
        for (int[] xy : shape) {
            board[x + xy[0]][y + xy[1]] = piece;
        }

    }
}
