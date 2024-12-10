package io.github.followsclosley.blokus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Coordinate {
    private final int x, y;

    public Coordinate translate(Coordinate coordinate){
        return new Coordinate(x+coordinate.getX(), y + coordinate.getY());
    }
}





