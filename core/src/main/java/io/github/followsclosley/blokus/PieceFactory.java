package io.github.followsclosley.blokus;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.*;

public class PieceFactory {

    private ObjectMapper mapper = new ObjectMapper();

    public List<Piece> getPieces(Player player) throws IOException
    {
        List<Piece> pieces = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("io/github/followsclosley/blokus/shapes.json")) {
            if (inputStream != null) {
                PieceInformation[] piecesArray = mapper.readValue(inputStream, PieceInformation[].class);
                for (PieceInformation pieceInformation : piecesArray) {
                    pieces.add(new Piece(pieceInformation.getName(), player, pieceInformation.getShape()));
                }
            }
        }

        return pieces;
    }

    public int[] getMaxXY(Piece piece) {
        int[] max = {0,0};
        for (int[] xy : piece.getShape()) {
            max[0] = Math.max(max[0], xy[0]);
            max[1] = Math.max(max[1], xy[1]);
        }
        return max;
    }

    @Data
    public static class PieceInformation {
        private String name;
        private int[][] shape;
    }
}
