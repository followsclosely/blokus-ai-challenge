package io.github.followsclosley.blokus;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Player {
    private int index;
    private String name;
    private List<Piece> pieces;
}
