package Game;

import Board.ChessBoard;
import Judge.*;
import Player.*;

public class ChessGame {
    private ChessPlayer player_1;
    private ChessPlayer player_2;
    private ChessBoard chessboard;
    private Judge judge;
    public ChessGame(){
        player_1=new ChessPlayer();
        player_2=new ChessPlayer();
    }
    void StartGame(){
        player_1.SetGroup(Judge.G_CHU);
        player_2.SetGroup(Judge.G_HAN);

        Judge.SetPlayers(player_1,player_2);

        chessboard=ChessBoard.Init();
    }
}
