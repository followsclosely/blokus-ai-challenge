package io.github.followsclosley.blokus;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Board hand = new Board(15, 28);
        Board board = new Board(14,14);

        Game game = new Game(board);
        Player player0 = game.addPlayer(new Player(0, "Player 0"));
        Player player1 = game.addPlayer(new Player(1, "Player 1"));
        game.init();



        PieceFactory factory = new PieceFactory();

        int x = 0;
        int y = 0;
        int maxY = 0;
        for (Piece piece : factory.getPieces(player0)) {

            hand.setPiece(x,y, piece);

            //Move to the next position
            x += piece.getWidth() + 1;
            maxY = Math.max(maxY, piece.getHeight());

            //If we are at the end of the board, move to the next row
            if( x+1 >= hand.getWidth()) {
                x = 0;
                y += (maxY + 1);
            }
        }

        SwingSupport support = new SwingSupport()
                .board(board, 50)
                .hand(hand, 25)
                //.showPieceNames()
                .showMoves()
                .show();

    }
}
