package io.github.followsclosley.blokus;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Board board = Board.createTwoPlayerBoard();
        Game game = new Game(board);
        Player player0 = game.addPlayer(new Player(0, "Player 0"));
        Player player1 = game.addPlayer(new Player(1, "Player 1"));
        game.init();

        SwingSupport support = new SwingSupport()
                .board(board)
                //.showPieceNames()
                .showMoves()
                .show();


    }
}
