package Game;

import Board.ChessBoard;
import Judge.*;
import Player.*;

public class ChessGame {
    private ChessPlayer player_1;
    private Player player_2;
    private ChessBoard chessboard;
    private Judge judge;
    public ChessGame(){
        player_1=new ChessPlayer();
        player_2=new ChessPlayer();
        chessboard=ChessBoard.Init();
    }
    void StartGame(){

    }
}
