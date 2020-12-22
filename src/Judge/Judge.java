package Judge;

import Player.*;

public abstract class Judge {
    public final static byte G_NULL = 0;        //中间
    public final static byte G_CHU = 1;         //楚方
    public final static byte G_HAN = 2;         //汉方
    public final static byte C_GO = 1;          //走子
    public final static byte C_RETRACT = 2;     //悔棋
    public final static byte C_GIVE_UP = 3;     //认输
    public final static byte THINKING = 0;      //思考ing
    protected static Player winner;               //赢家
    protected static Player loser;                //输家
    protected static Player player_now;           //当前行动方
    protected static Player player_1;
    protected static Player player_2;

    public abstract boolean DoJudge();          //对选择的合法性做出判断，同时可以进行一些操作

    public static Player getWinner() {
        return winner;
    }

    public static Player getLoser() {
        return loser;
    }

    public static ChessPlayer GetNowPlayer(){
        return (ChessPlayer) player_now;
    }

    public static void SetPlayers(Player p1,Player p2) {
        player_1=p1;
        player_2=p2;
        if(player_1.GetGroup()==G_HAN)
            player_now=player_1;
        else
            player_now=player_2;
    }

    public static byte GetHome() {
        if(player_1==null)
            return G_CHU;
        return player_1.GetGroup();
    }
}
