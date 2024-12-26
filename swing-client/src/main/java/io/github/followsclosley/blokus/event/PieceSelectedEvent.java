package io.github.followsclosley.blokus.event;

import io.github.followsclosley.blokus.Piece;
import io.github.followsclosley.blokus.Player;
import lombok.Data;

@Data
public class PieceSelectedEvent {
    private final Piece piece;
    private final Player player;
}
