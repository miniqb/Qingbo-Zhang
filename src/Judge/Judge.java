package Judge;

import Player.*;

public abstract class Judge {

    /**
     * 裁判类，让游戏按照规则进行
     */

    protected static boolean is_right_position=false;//目标位置是否合法

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
    protected static Player player_1;           //玩家1
    protected static Player player_2;           //玩家2

    public abstract boolean DoJudge();          //对选择的合法性做出判断，同时可以进行一些操作

    /**
     * 获取胜利玩家
     * @return 返回胜利玩家
     */
    public static Player getWinner() {
        return winner;
    }

    /**
     * 获取失败玩家
     * @return 返回失败玩家
     */
    public static Player getLoser() {
        return loser;
    }

    /**
     * 获取当前执子玩家
     * @return 返回当前执子玩家
     */
    public static ChessPlayer GetNowPlayer(){
        return (ChessPlayer) player_now;
    }

    /**
     * 获取玩家
     * @param p1 玩家1
     * @param p2 玩家2
     */
    public static void SetPlayers(Player p1,Player p2) {
        player_1=p1;
        player_2=p2;
        //设置首发玩家，固定为汉阵营玩家
        if(player_1.GetGroup()==G_HAN)
            player_now=player_1;
        else
            player_now=player_2;
    }

    /**
     * 获取主场阵营
     * @return 返回主场阵营
     */
    public static byte GetHome() {
        if(player_1==null)
            return G_NULL;
        return player_1.GetGroup();
    }
}
