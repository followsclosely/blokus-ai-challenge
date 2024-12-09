package io.github.followsclosley.blokus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Piece {
    private final String name;
    private Player player;
    private final int[][] shape;
}
