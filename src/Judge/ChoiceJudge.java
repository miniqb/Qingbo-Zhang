package Judge;

import Board.ChessBoard;
import Piece.NullPiece;
import Player.Player;

public class ChoiceJudge extends Judge{

    int retract_sum=1;

    private static ChoiceJudge me;
    public static ChoiceJudge Init() {
        if(me == null)
            me = new ChoiceJudge();
        return me;
    }

    private ChoiceJudge(){}
    @Override
    public boolean DoJudge() {
        ChessBoard board=ChessBoard.Init();
        boolean result=true;
        switch (player_now.GetChoice())
        {
            case C_GO:
                if(board.GetAimSelect()==board.GetNowSelect()||!is_right_position)
                    result=false;
                else {
                    player_now=player_now==player_1?player_2:player_1;
                }
                break;
            case C_RETRACT:
                if(board.GetRecordSize()==1){
                    player_now=player_now==player_1?player_2:player_1;
                }
                break;
        }
        return result;
    }
}
