package Judge;

import Board.ChessBoard;

public class ChoiceJudge extends Judge{
    /**
     * 单例类，对选择做出合法性判断同时执行一些操作
     */

    private static ChoiceJudge me;//单例对象

    /**
     * 获取单例对象
     * @return 返回单例对象
     */
    public static ChoiceJudge Init() {
        if(me == null)
            me = new ChoiceJudge();
        return me;
    }

    private ChoiceJudge(){}
    /**
     * 对选择做判断
     */
    @Override
    public boolean DoJudge() {
        ChessBoard board=ChessBoard.Init();
        boolean result=true;    //判断结果
        switch (player_now.GetChoice())
        {
            case C_GO:
                if(board.GetAimSelect()==board.GetNowSelect()||!is_right_position)  //如果选中棋子和目标位置棋子相同或者目标位置棋子不合法
                    result=false;
                else {  //若合法，轮到下名玩家
                    player_now=player_now==player_1?player_2:player_1;
                }
                break;
            case C_RETRACT:
                if(board.GetRecordSize()==1){   //悔棋是必定合法的，因为当无法悔棋时，棋盘类中悔棋方法本就不会执行；
                    player_now=player_now==player_1?player_2:player_1;  //设置当前执子玩家
                }
                break;
        }
        return result;//返回结果
    }
}
