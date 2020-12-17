package Judge;

import Player.Player;

public abstract class Judge {
    public final static byte G_CHU = 0;         //楚方
    public final static byte G_HAN = 1;         //汉方
    public final static byte C_GO = 1;          //走子
    public final static byte C_RETRACT = 2;     //悔棋
    public final static byte C_GIVE_UP = 3;     //认输
    public final static byte THINKING = 0;      //思考ing
    private static Player winner;               //赢家
    private static Player loser;                //输家
    private static Player now_player;           //当前行动方
    private static Player player_1;             //玩家1
    private static Player player_2;             //玩家2

    public abstract boolean DoJudge();          //对选择的合法性做出判断，同时可以进行一些操作

    public static Player getNow_player() {
        return now_player;
    }

    public static Player getWinner() {
        return winner;
    }

    public static Player getLoser() {
        return loser;
    }

    public static void setPlayer_1(Player player_1,Player player_2) {
        Judge.player_1 = player_1;
        Judge.player_2 = player_2;
    }
}
