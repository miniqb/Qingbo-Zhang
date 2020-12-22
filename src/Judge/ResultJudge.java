package Judge;

import Board.ChessBoard;
import Piece.*;

public class ResultJudge extends Judge{
    private static ResultJudge me;
    public static ResultJudge Init() {
        if(me == null)
            me = new ResultJudge();
        return me;
    }

    private ResultJudge(){}
    @Override
    public boolean DoJudge() {
        for (Piece piece: ChessBoard.Init().GetAllPieces()) {
            if((piece.GetName().equals(ChessPiece.P_JIANG_CHU) || piece.GetName().equals(ChessPiece.P_SHUAI_HAN)) && !piece.IsAlive()){
                winner=player_1.GetGroup()==piece.GetGroup()?player_2:player_1;
                return true;
            }
        }
        return false;
    }
}
