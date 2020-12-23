package Judge;

import Board.ChessBoard;
import Piece.*;

import java.awt.*;
import java.util.Objects;

public class ThinkingJudge extends Judge{
    private static ThinkingJudge me;
    public static ThinkingJudge Init() {
        if(me == null)
            me = new ThinkingJudge();
        return me;
    }

    private ThinkingJudge(){}
    @Override
    public boolean DoJudge() {
        Point move_now=ChessBoard.Init().moving;
        Piece now=ChessBoard.Init().GetNowSelect();
        Piece aim=ChessBoard.Init().GetPiece(move_now.x,move_now.y);
        Point[] can_go= now.GetCanGo();
        for (Point p:can_go) {
            if(p.equals(move_now)){
                if(now.GetGroup()==aim.GetGroup())
                    break;
                int sum=HaveTunnel(now.GetPosition(),aim.GetPosition());
                switch (now.GetName()){
                    case ChessPiece.P_PAO_HAN:
                    case ChessPiece.P_PAO_CHU:
                        if(sum==0&&aim.GetID()==NullPiece.ID)
                            return true;
                        if(sum==1){
                            if(aim.GetID()!=NullPiece.ID)
                                return true;
                        }
                        break;
                    case ChessPiece.P_CHE_HAN:
                    case ChessPiece.P_JU_CHU:
                    case ChessPiece.P_XIANG_CHU:
                    case ChessPiece.P_XIANG_HAN:
                    case ChessPiece.P_MA_CHU:
                    case ChessPiece.P_MA_HAN:
                        if(sum==0)
                            return true;
                        break;
                    case ChessPiece.P_BING_HAN:
                    case ChessPiece.P_ZU_CHU:
                        if(now.GetGroup()==Judge.GetHome()){
                            if(move_now.y-aim.GetPosition().y==-1||(move_now.y<6)&&Math.abs(move_now.x-aim.GetPosition().x)==1)
                                return true;
                        }
                        else
                            if(move_now.y-aim.GetPosition().y==1||(move_now.y>5)&&Math.abs(move_now.x-aim.GetPosition().x)==1)
                                return true;
                        break;
                    case ChessPiece.P_JIANG_CHU:
                    case ChessPiece.P_SHUAI_HAN:
                        int tmpX=aim.GetPosition().x;
                        int tmpY=aim.GetPosition().y;
                        if(now.GetGroup()==Judge.GetHome()) {
                            if (tmpY>=8 && tmpX>=4 && tmpX<=6 && sum>0)
                                return true;
                        }
                        else
                            if(tmpY<=3 && tmpX>=4 && tmpX<=6 && sum>0)
                                return true;
                            break;
                    case ChessPiece.P_SHI_CHU:
                    case ChessPiece.P_SHI_HAN:
                        tmpX=aim.GetPosition().x;
                        tmpY=aim.GetPosition().y;
                        if(now.GetGroup()==Judge.GetHome()) {
                            if (tmpY>=8 && tmpX>=4 && tmpX<=6)
                                return true;
                        }
                        else
                        if(tmpY<=3 && tmpX>=4 && tmpX<=6)
                            return true;
                        break;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private int HaveTunnel(Point now,Point aim){
        int sum=0;
        switch (ChessBoard.Init().GetNowSelect().GetName()){
            case ChessPiece.P_PAO_CHU:
            case ChessPiece.P_PAO_HAN:
            case ChessPiece.P_JU_CHU:
            case ChessPiece.P_CHE_HAN:
                int max = Math.max(now.x, aim.x);
                int min = Math.min(now.x, aim.x);
                for (int x = min; x < max; x++) {
                    if(ChessBoard.Init().GetPiece(x,now.y).GetID()!=NullPiece.ID)
                        sum++;
                }
                max = Math.max(now.y, aim.y);
                min = Math.min(now.y, aim.y);
                for (int y = min; y < max; y++) {
                    if(ChessBoard.Init().GetPiece(now.x,y).GetID()!=NullPiece.ID)
                        sum++;
                }
                break;
            case ChessPiece.P_XIANG_CHU:
            case ChessPiece.P_XIANG_HAN:
                if(ChessBoard.Init().GetPiece((now.x+aim.x)/2,(now.y+aim.y)/2).GetID()!=NullPiece.ID)
                    sum++;
                break;
            case ChessPiece.P_MA_CHU:
            case ChessPiece.P_MA_HAN:
                if(Math.abs(now.x-aim.x)==2 && ChessBoard.Init().GetPiece((now.x+aim.x)/2,now.y).GetID()!=NullPiece.ID)
                    sum++;
                else if(ChessBoard.Init().GetPiece(now.x,(now.y+aim.y)/2).GetID()!=NullPiece.ID)
                    sum++;
                break;
            case ChessPiece.P_JIANG_CHU:
            case ChessPiece.P_SHUAI_HAN:
                Point tmp1=ChessBoard.Init().GetJiang().GetPosition();
                Point tmp2=ChessBoard.Init().GetShuai().GetPosition();
                if(tmp1.x==tmp2.x){
                    max=Math.max(tmp1.y,tmp2.y);
                    min=Math.min(tmp1.y,tmp2.y);
                    for (int y = min+1; y < max; y++) {
                        if(ChessBoard.Init().GetPiece(now.x,y).GetID()!=NullPiece.ID)
                            sum++;
                    }
                }
                break;
            default:
                break;
        }
        return sum;
    }
}
