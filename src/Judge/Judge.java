package Judge;

import Board.ChessBoard;
import Game.ChessGame;
import Piece.*;
import Player.*;

import java.awt.*;

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
        if(ChessGame.mod==ChessGame.HERE){
            if(player_1.GetGroup()==G_HAN)
                player_now=player_1;
            else
                player_now=player_2;
        }
        else
            player_now = player_1;
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

    /**
     * 判断“将”和“帅”是否会“见面”
     * @return 真为会见面
     */
    protected boolean WillHeadMeeting(Point start,Point end){
        ChessBoard board=ChessBoard.Init();
        /*
        Point move_now=board.moving;
        Piece now=board.GetNowSelect();
        Piece aim=board.GetPiece(move_now.x,move_now.y);
         */
        Piece now=board.GetPiece(start.x,start.y);
        
        switch (now.GetName()){
            case ChessPiece.P_JIANG_CHU:
                if(start.x==board.GetShuai().GetPosition().x) {
                    int maxY = Math.max(board.GetShuai().GetPosition().y, end.y);
                    int minY = Math.min(board.GetShuai().GetPosition().y, end.y);
                    for (int y = minY + 1; y < maxY; y++) {
                        if (board.GetPiece(start.x, y).GetID() != NullPiece.ID)
                            return false;
                    }
                }
                else
                    return false;
                break;
            case ChessPiece.P_SHUAI_HAN:
                if(start.x==board.GetJiang().GetPosition().x) {
                    int maxY = Math.max(board.GetJiang().GetPosition().y, end.y);
                    int minY = Math.min(board.GetJiang().GetPosition().y, end.y);
                    for (int y = minY + 1; y < maxY; y++) {
                        if (board.GetPiece(start.x, y).GetID() != NullPiece.ID)
                            return false;
                    }
                }
                else
                    return false;
                break;
            default:
                if(board.GetShuai().GetPosition().x==board.GetJiang().GetPosition().x&&
                        now.GetPosition().x==board.GetShuai().GetPosition().x&&now.GetPosition().x!=start.x){
                    int maxY=Math.max(board.GetJiang().GetPosition().y,board.GetShuai().GetPosition().y);
                    int minY=Math.min(board.GetJiang().GetPosition().y,board.GetShuai().GetPosition().y);
                    for (int y = minY+1; y < maxY; y++) {
                        if(board.GetPiece(now.GetPosition().x,y).GetID()!= NullPiece.ID&&y!=now.GetPosition().y)
                            return false;
                    }
                }
                else
                    return false;
        }
        return true;
    }

    protected boolean WillHeadEaten(Piece piece,Point start,Point end){
        ChessBoard board=ChessBoard.Init();
        int poX,poY;
        if(piece.GetPosition().equals(start)){
            poX=end.x;
            poY=end.y;
        }
        else {
            poX = piece.GetPosition().x;
            poY = piece.GetPosition().y;
        }
        byte group=piece.GetGroup();
        Piece tmp;
        //上下左右4格，主要找兵和车
        if (poX + 1 < 10 && !(poX + 1 == end.x && poY == end.y)) {//右一格
            tmp = board.GetPiece(poX + 1, poY);
            String name = tmp.GetName();
            if ((name.equals(ChessPiece.P_BING_HAN) || name.equals(ChessPiece.P_ZU_CHU) && tmp.GetGroup() != group))
                return true;
            if ((name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN) && tmp.GetGroup() != group))
                return true;
        }
        if(poX - 1 > 0 && !(poX - 1 == end.x && poY == end.y)){//左一格
            tmp = board.GetPiece(poX - 1, poY);
            String name=tmp.GetName();
            if((name.equals(ChessPiece.P_BING_HAN)||name.equals(ChessPiece.P_ZU_CHU)&&tmp.GetGroup()!=group))
                return true;
            if ((name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN) && tmp.GetGroup() != group))
                return true;
        }
        if(poY - 1 > 0 && !(poX == end.x && poY - 1 == end.y)){//上一格
            tmp = board.GetPiece(poX, poY - 1);
            String name=tmp.GetName();
            if(tmp.GetGroup()!=Judge.GetHome()&&((name.equals(ChessPiece.P_BING_HAN)||name.equals(ChessPiece.P_ZU_CHU))&&tmp.GetGroup()!=group))
                return true;
            if (((name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group))
                return true;
        }
        if(poY + 1 < 11 && !(poX == end.x && poY + 1 == end.y)){//下一格
            tmp = board.GetPiece(poX, poY + 1);
            String name=tmp.GetName();
            if(tmp.GetGroup()==Judge.GetHome()&&((name.equals(ChessPiece.P_BING_HAN)||name.equals(ChessPiece.P_ZU_CHU))&&tmp.GetGroup()!=group))
                return true;
            if (((name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group))
                return true;
        }

        //找马
        int[][] pos ={{-1,-2,-1,-1},{-2,-1,-1,-1},{-2,1,-1,1},{-1,2,-1,1},{1,2,1,1},{2,1,1,1},{2,-1,1,-1},{1,-2,1,-1}};
        int tmX,tmY,oX,oY;
        for (int i = 0; i < 8; i++) {
            tmX=poX+pos[i][0];
            tmY=poY+pos[i][1];
            oX=poX+pos[i][2];
            oY=poY+pos[i][3];
            if(tmX>0&&tmY>0&&tmX<10&&tmY<11&&!(tmX==end.x&&tmY==end.y)) {
                tmp = board.GetPiece(tmX, tmY);
                String name=tmp.GetName();
                if ((name.equals(ChessPiece.P_MA_HAN)||name.equals(ChessPiece.P_MA_CHU)&&tmp.GetGroup()!=group)){
                    if((start.x==oX&&start.y==oY)||(!(end.x==oX&&end.y==oY)&&board.GetPiece(oX,oY).GetID()== NullPiece.ID))
                        return true;
                }
            }
        }

        int obstacle=0;
        //找炮和车
        for (int y = 1; poY+y < 11; y++) {//向下
            tmp=board.GetPiece(poX,poY+y);
            Point p=tmp.GetPosition();
            String name=tmp.GetName();
            if(p.equals(end)){
                obstacle++;
                continue;
            }
            if(obstacle>=2)
                break;
            if(tmp.GetID()!= NullPiece.ID&&!p.equals(start)) {
                if (obstacle == 1 && (name.equals(ChessPiece.P_PAO_CHU) || name.equals(ChessPiece.P_PAO_HAN)) && tmp.GetGroup() != group)
                    return true;
                if (obstacle == 0 && (name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group)
                    return true;
                obstacle++;
            }
        }
        obstacle=0;
        for (int y = -1; poY+y > 0; y--) {//向上
            tmp=board.GetPiece(poX,poY+y);
            Point p=tmp.GetPosition();
            String name=tmp.GetName();
            if(p.equals(end)){
                obstacle++;
                continue;
            }
            if(obstacle>=2)
                break;
            if(tmp.GetID()!= NullPiece.ID&&!p.equals(start)) {
                if (obstacle == 1 && (name.equals(ChessPiece.P_PAO_CHU) || name.equals(ChessPiece.P_PAO_HAN)) && tmp.GetGroup() != group)
                    return true;
                if (obstacle == 0 && (name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group)
                    return true;
                obstacle++;
            }
        }
        obstacle=0;
        for (int x = -1; poX+x > 0; x--) {//向左
            tmp=board.GetPiece(poX+x,poY);
            Point p=tmp.GetPosition();
            String name=tmp.GetName();
            if(p.equals(end)){
                obstacle++;
                continue;
            }
            if(obstacle>=2)
                break;
            if(tmp.GetID()!= NullPiece.ID&&!p.equals(start)) {
                if (obstacle == 1 && (name.equals(ChessPiece.P_PAO_CHU) || name.equals(ChessPiece.P_PAO_HAN)) && tmp.GetGroup() != group)
                    return true;
                if (obstacle == 0 && (name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group)
                    return true;
                obstacle++;
            }
        }
        obstacle=0;
        for (int x = 1; poX+x < 10; x++) {//向右
            tmp=board.GetPiece(poX+x,poY);
            Point p=tmp.GetPosition();
            String name=tmp.GetName();
            if(p.equals(end)){
                obstacle++;
                continue;
            }
            if(obstacle>=2)
                break;
            if(tmp.GetID()!= NullPiece.ID&&!p.equals(start)) {
                if (obstacle == 1 && (name.equals(ChessPiece.P_PAO_CHU) || name.equals(ChessPiece.P_PAO_HAN)) && tmp.GetGroup() != group)
                    return true;
                if (obstacle == 0 && (name.equals(ChessPiece.P_JU_CHU) || name.equals(ChessPiece.P_CHE_HAN)) && tmp.GetGroup() != group)
                    return true;
                obstacle++;
            }
        }
        return false;
    }
}
