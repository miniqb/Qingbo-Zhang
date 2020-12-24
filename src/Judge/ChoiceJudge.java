package Judge;

import Board.ChessBoard;

public class ChoiceJudge extends Judge{
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
            case C_GIVE_UP:
            case C_RETRACT:
            default:
                break;
        }
        if(result) {
            player_now=player_now==player_1?player_2:player_1;
        }
        return result;
    }
}
