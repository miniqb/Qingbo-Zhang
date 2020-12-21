package Judge;

import Player.ChessPlayer;

public abstract class Judge {
    public final static byte G_NULL = 0;        //中间
    public final static byte G_CHU = 1;         //楚方
    public final static byte G_HAN = 2;         //汉方
    public final static byte C_GO = 1;          //走子
    public final static byte C_RETRACT = 2;     //悔棋
    public final static byte C_GIVE_UP = 3;     //认输
    public final static byte THINKING = 0;      //思考ing
    private static ChessPlayer winner;               //赢家
    private static ChessPlayer loser;                //输家
    private static ChessPlayer now_player;           //当前行动方
    private static ChessPlayer player_1;
    private static ChessPlayer player_2;

    public abstract boolean DoJudge();          //对选择的合法性做出判断，同时可以进行一些操作

    public static ChessPlayer getNow_player() {
        return now_player;
    }

    public static ChessPlayer getWinner() {
        return winner;
    }

    public static ChessPlayer getLoser() {
        return loser;
    }

    public static void SetPlayers(ChessPlayer p1,ChessPlayer p2) {
        player_1=p1;
        player_2=p2;
        if(player_1.GetGroup()==G_CHU)
            now_player=player_1;
        else
            now_player=player_2;
    }

    public static byte GetHome() {
        return player_1.GetGroup();
    }
}
