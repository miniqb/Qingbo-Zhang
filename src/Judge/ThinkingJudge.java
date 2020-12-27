package Judge;

import Board.ChessBoard;
import Piece.*;

import java.awt.*;

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
        boolean result=false;
        if(now.GetGroup()!=aim.GetGroup()) {
            for (Point p : can_go) {
                if (p.equals(move_now)&&!WillHeadMeeting()) {
                    int sum=HaveTunnel(now.GetPosition(),aim.GetPosition());
                    switch (now.GetName()) {
                        case ChessPiece.P_PAO_HAN:
                        case ChessPiece.P_PAO_CHU:
                            if ((sum == 0 && aim.GetID() == NullPiece.ID) || (sum == 1 && aim.GetID() != NullPiece.ID))
                                result = true;
                            break;
                        case ChessPiece.P_CHE_HAN:
                        case ChessPiece.P_JU_CHU:
                        case ChessPiece.P_XIANG_CHU:
                        case ChessPiece.P_XIANG_HAN:
                        case ChessPiece.P_MA_CHU:
                        case ChessPiece.P_MA_HAN:
                            if (sum == 0)
                                result = true;
                            break;
                        case ChessPiece.P_BING_HAN:
                        case ChessPiece.P_ZU_CHU:
                            if (now.GetGroup() == Judge.GetHome()) {
                                if (now.GetPosition().y - move_now.y == 1 || (now.GetPosition().y < 6) && Math.abs(now.GetPosition().x - move_now.x) == 1)
                                    result = true;
                            } else if (now.GetPosition().y - move_now.y == -1 || (now.GetPosition().y > 5) && Math.abs(now.GetPosition().x - move_now.x) == 1)
                                result = true;
                            break;
                        case ChessPiece.P_JIANG_CHU:
                        case ChessPiece.P_SHUAI_HAN:
                            int tmpX = aim.GetPosition().x;
                            int tmpY = aim.GetPosition().y;
                            if (now.GetGroup() == Judge.GetHome()) {
                                if (tmpY >= 8 && tmpX >= 4 && tmpX <= 6 && sum > 0)
                                    result = true;
                            } else if (tmpY <= 3 && tmpX >= 4 && tmpX <= 6 && sum > 0)
                                result = true;
                            break;
                        case ChessPiece.P_SHI_CHU:
                        case ChessPiece.P_SHI_HAN:
                            tmpX = aim.GetPosition().x;
                            tmpY = aim.GetPosition().y;
                            if (now.GetGroup() == Judge.GetHome()) {
                                if (tmpY >= 8 && tmpX >= 4 && tmpX <= 6)
                                    result = true;
                            } else if (tmpY <= 3 && tmpX >= 4 && tmpX <= 6)
                                result = true;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        is_right_position=result;
        return result;
    }

    private int HaveTunnel(Point now,Point aim){
        ChessBoard board=ChessBoard.Init();
        int sum=0;
        switch (board.GetNowSelect().GetName()){
            case ChessPiece.P_PAO_CHU:
            case ChessPiece.P_PAO_HAN:
            case ChessPiece.P_JU_CHU:
            case ChessPiece.P_CHE_HAN:
                int max = Math.max(now.x, aim.x);
                int min = Math.min(now.x, aim.x);
                for (int x = min+1; x < max; x++) {
                    if(board.GetPiece(x,now.y).GetID()!=NullPiece.ID)
                        sum++;
                }
                max = Math.max(now.y, aim.y);
                min = Math.min(now.y, aim.y);
                for (int y = min+1; y < max; y++) {
                    if(board.GetPiece(now.x,y).GetID()!=NullPiece.ID){
                        sum++;
                    }
                }
                break;
            case ChessPiece.P_XIANG_CHU:
            case ChessPiece.P_XIANG_HAN:
                if(board.GetPiece((now.x+aim.x)/2,(now.y+aim.y)/2).GetID()!=NullPiece.ID)
                    sum++;
                break;
            case ChessPiece.P_MA_CHU:
            case ChessPiece.P_MA_HAN:
                if(Math.abs(now.x-aim.x)==2 && board.GetPiece((now.x+aim.x)/2,now.y).GetID()!=NullPiece.ID)
                    sum++;
                else if(Math.abs(now.y-aim.y)==2&&board.GetPiece(now.x,(now.y+aim.y)/2).GetID()!=NullPiece.ID)
                    sum++;
                break;
            case ChessPiece.P_JIANG_CHU:
            case ChessPiece.P_SHUAI_HAN:
                Point head=
                        board.GetPiece(now.x,now.y).GetName().equals(ChessPiece.P_SHUAI_HAN)?
                        board.GetJiang().GetPosition():
                        board.GetShuai().GetPosition();
                if(head.x==aim.x){
                    max=Math.max(aim.y,head.y);
                    min=Math.min(aim.y,head.y);
                    for (int y = min+1; y < max; y++) {
                        if(board.GetPiece(aim.x,y).GetID()!=NullPiece.ID) {
                            sum++;
                            break;
                        }
                    }
                }
                else
                    sum++;
                break;
            default:
                break;
        }
        return sum;
    }

    private boolean WillHeadMeeting(){
        ChessBoard board=ChessBoard.Init();
        Point move_now=board.moving;
        Piece now=board.GetNowSelect();
        Piece aim=board.GetPiece(move_now.x,move_now.y);
        switch (now.GetName()){
            case ChessPiece.P_JIANG_CHU:
                if(move_now.x==board.GetShuai().GetPosition().x) {
                    int maxY = Math.max(board.GetShuai().GetPosition().y, aim.GetPosition().y);
                    int minY = Math.min(board.GetShuai().GetPosition().y, aim.GetPosition().y);
                    for (int y = minY + 1; y < maxY; y++) {
                        if (board.GetPiece(move_now.x, y).GetID() != NullPiece.ID)
                            return false;
                    }
                }
                else
                    return false;
                break;
            case ChessPiece.P_SHUAI_HAN:
                if(move_now.x==board.GetJiang().GetPosition().x) {
                    int maxY = Math.max(board.GetJiang().GetPosition().y, aim.GetPosition().y);
                    int minY = Math.min(board.GetJiang().GetPosition().y, aim.GetPosition().y);
                    for (int y = minY + 1; y < maxY; y++) {
                        if (board.GetPiece(move_now.x, y).GetID() != NullPiece.ID)
                            return false;
                    }
                }
                else
                    return false;
                break;
            default:
                if(board.GetShuai().GetPosition().x==board.GetJiang().GetPosition().x&&
                        now.GetPosition().x==board.GetShuai().GetPosition().x&&now.GetPosition().x!=move_now.x){
                    int maxY=Math.max(board.GetJiang().GetPosition().y,board.GetShuai().GetPosition().y);
                    int minY=Math.min(board.GetJiang().GetPosition().y,board.GetShuai().GetPosition().y);
                    for (int y = minY+1; y < maxY; y++) {
                        if(board.GetPiece(now.GetPosition().x,y).GetID()!=NullPiece.ID&&y!=now.GetPosition().y)
                            return false;
                    }
                }
                else
                    return false;
        }
        return true;
    }
}


