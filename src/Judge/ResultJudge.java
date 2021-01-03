package Judge;

import Board.ChessBoard;
import Piece.*;

public class ResultJudge extends Judge{

    /**
     * 单例类，判断游戏结果
     */

    private static ResultJudge me;//单例对象
    public static ResultJudge Init() {
        if(me == null)
            me = new ResultJudge();
        return me;
    }

    private ResultJudge(){}

    /**
     * 判断游戏是否结束，同时设置赢家和输家
     * @return “真”为结束
     */
    @Override
    public boolean DoJudge() {
        for (Piece piece: ChessBoard.Init().GetAllPieces()) {
            if((piece.GetName().equals(ChessPiece.P_JIANG_CHU) || piece.GetName().equals(ChessPiece.P_SHUAI_HAN)) && !piece.IsAlive()){
                winner=player_1.GetGroup()==piece.GetGroup()?player_2:player_1;
                loser=winner==player_1?player_2:player_1;
                return true;
            }
        }
        return false;
    }
}
