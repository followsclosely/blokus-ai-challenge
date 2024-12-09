package io.github.followsclosley.blokus;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {
    private final Board board;
    private final List<Player> players = new ArrayList<>();

    private Game(Board board) {
        this.board = board;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
