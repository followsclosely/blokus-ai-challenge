package io.github.followsclosley.blokus;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@AllArgsConstructor
public class PieceFactory {

    public static final String STANDARD_PIECES_FILE = "shapes.json";
    public static final String TINY_PIECES_FILE = "shapes-tiny.json";

    private final String piecesFile;

    public PieceFactory() {
        this(STANDARD_PIECES_FILE);
    }

    public List<Piece> getPieces(Player player) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        List<Piece> pieces = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(piecesFile)) {
            if (inputStream != null) {
                PieceInformation[] piecesArray = mapper.readValue(inputStream, PieceInformation[].class);
                for (PieceInformation pieceInformation : piecesArray) {
                    pieces.add(new Piece(pieceInformation.getName(), pieceInformation.getShape(), player));
                }
            }
        }

        return pieces;
    }

    @Data
    public static class PieceInformation {
        private String name;
        private int[][] shape;
    }
}
