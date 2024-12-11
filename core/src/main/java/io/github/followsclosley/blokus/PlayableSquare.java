package io.github.followsclosley.blokus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PlayableSquare {
    private final Coordinate coordinate;
    private Player upperLeft, upperRight, lowerLeft, LowerRight;
}
