package io.github.followsclosley.blokus;

public class BlokusTestUtils {
    public static void printBoard(Board board){
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Piece piece = board.getPiece(x, y);
                if( piece != null){
                    System.out.print(Character.toString(0));
                } else {
                    PlayableSquare playable = board.getPlayable(x, y);
                    if( playable == null ){
                        System.out.print(Character.toString(183));
                    } else {
                        System.out.print("?");
                    }

                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
