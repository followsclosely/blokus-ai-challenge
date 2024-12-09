package io.github.followsclosley.blokus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Piece {
    private String name;
    private Player player;
    private int[][] shape;
    private int width,height;

    public Piece(String name, int[][] shape, Player player){
        this.name = name;
        this.shape = shape;
        this.player = player;

        for (int[] xy : shape) {
            width = Math.max(width, xy[0]);
            height = Math.max(height, xy[1]);
        }
    }
}
