package io.github.followsclosley.blokus;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@AllArgsConstructor
public class PieceFactory {

    public static final String STANDARD_PIECES_FILE = PieceFactory.class.getPackageName().replace('.', '/') +  "/shapes.json";
    public static final String TINY_PIECES_FILE = PieceFactory.class.getPackageName().replace('.', '/') +  "/shapes-tiny.json";

    private final String piecesFile;

    public PieceFactory() {
        this(STANDARD_PIECES_FILE);
    }

    public List<Piece> getPieces(Player player) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        List<Piece> pieces = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(piecesFile)) {
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
