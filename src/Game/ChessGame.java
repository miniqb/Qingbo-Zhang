package Game;

import Board.ChessBoard;
import Judge.*;
import Player.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessGame {
    private final ChessPlayer player_1;
    private final ChessPlayer player_2;
    private Judge judge;
    public ChessGame(){
        player_1=new ChessPlayer();
        player_2=new ChessPlayer();
    }
    void StartGame(){
        player_1.SetGroup(Judge.G_CHU);
        player_2.SetGroup(Judge.G_HAN);

        Judge.SetPlayers(player_1,player_2);

        ChessBoard chessboard = ChessBoard.Init();

        chessboard.StartOperation();



    }
}
