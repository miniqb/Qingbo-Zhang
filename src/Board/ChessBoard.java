package Board;

import Judge.Judge;
import Piece.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class ChessBoard {
    private static ChessBoard me;
//data area
    private static final int[][] positions ={
          {0,0}, {1,10}, {9,10}, {2,10}, {8,10}, {3,10}, {7,10},
          {4,10}, {6,10}, {5,10}, {2,8}, {8,8}, {1,7}, {3,7}, {5,7},
          {7,7}, {9,7}, {1,1}, {9,1}, {2,1}, {8,1}, {3,1}, {7,1}, {4,1},
          {6,1}, {5,1}, {2,3}, {8,3}, {1,4}, {3,4}, {5,4}, {7,4}, {9,4},
    };
    public final static byte WIDTH = 9; //棋盘宽度
    public final static byte HIGH = 10; //棋盘高度
    public final static Piece Null = NullPiece.GetNull(0,0);

    private final Piece[][] pieces_map; //棋盘数组
    private final Piece[] pieces_all; //棋子容器

    private Stack<Step> record= new Stack<>();
    private final int max_records=2;

    public final Point moving=new Point(0,0);
    private Piece now_select;
    private Piece aim_select;

    private BufferedImage board_image;

    private ChessBoard(){
        try {
            board_image= ImageIO.read(new File("image/b_chessboard.png"));
        } catch (IOException e) {
            System.out.println("图片加载错误！");
            e.printStackTrace();
        }

        //构造棋盘数组
        pieces_map = new Piece[WIDTH+1][];
        for (int i = 0; i <= WIDTH; i++) {
            pieces_map[i] = new Piece[HIGH+1];
        }

        pieces_all = new Piece[33];
        CreatePieces(); //创造棋子
        InitializePieces(); //按初始位置摆好棋子

        now_select=Null;
        aim_select=Null;
    }

    public static ChessBoard Init(){
        if(me==null) {
            if (Judge.GetHome() == Judge.G_NULL) {
                System.out.println("玩家尚未分配阵营！");
                System.exit(0);
            }
            me = new ChessBoard();
        }
        return me;
    }

    private void CreatePieces(){//创建所有棋子对象
        pieces_all[0]=Null;
        pieces_all[1]=new ChessPiece(ChessPiece.P_CHE_HAN,(byte) 1,new Point(1,10));
        pieces_all[2]=new ChessPiece(ChessPiece.P_CHE_HAN,(byte) 2,new Point(9,10));
        pieces_all[3]=new ChessPiece(ChessPiece.P_MA_HAN,(byte) 3,new Point(2,10));
        pieces_all[4]=new ChessPiece(ChessPiece.P_MA_HAN,(byte) 4,new Point(8,10));
        pieces_all[5]=new ChessPiece(ChessPiece.P_XIANG_HAN,(byte) 5,new Point(3,10));
        pieces_all[6]=new ChessPiece(ChessPiece.P_XIANG_HAN,(byte) 6,new Point(7,10));
        pieces_all[7]=new ChessPiece(ChessPiece.P_SHI_HAN,(byte) 7,new Point(4,10));
        pieces_all[8]=new ChessPiece(ChessPiece.P_SHI_HAN,(byte) 8,new Point(6,10));
        pieces_all[9]=new ChessPiece(ChessPiece.P_SHUAI_HAN,(byte) 9,new Point(5,10));
        pieces_all[10]=new ChessPiece(ChessPiece.P_PAO_HAN,(byte) 10,new Point(2,8));
        pieces_all[11]=new ChessPiece(ChessPiece.P_PAO_HAN,(byte) 11,new Point(8,8));
        pieces_all[12]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 12,new Point(1,7));
        pieces_all[13]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 13,new Point(3,7));
        pieces_all[14]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 14,new Point(5,7));
        pieces_all[15]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 15,new Point(7,7));
        pieces_all[16]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 16,new Point(9,7));

        pieces_all[17]=new ChessPiece(ChessPiece.P_JU_CHU,(byte) 17,new Point(1,1));
        pieces_all[18]=new ChessPiece(ChessPiece.P_JU_CHU,(byte) 18,new Point(9,1));
        pieces_all[19]=new ChessPiece(ChessPiece.P_MA_CHU,(byte) 19,new Point(2,1));
        pieces_all[20]=new ChessPiece(ChessPiece.P_MA_CHU,(byte) 20,new Point(8,1));
        pieces_all[21]=new ChessPiece(ChessPiece.P_XIANG_CHU,(byte) 21,new Point(3,1));
        pieces_all[22]=new ChessPiece(ChessPiece.P_XIANG_CHU,(byte) 22,new Point(7,1));
        pieces_all[23]=new ChessPiece(ChessPiece.P_SHI_CHU,(byte) 23,new Point(4,1));
        pieces_all[24]=new ChessPiece(ChessPiece.P_SHI_CHU,(byte) 24,new Point(6,1));
        pieces_all[25]=new ChessPiece(ChessPiece.P_JIANG_CHU,(byte) 25,new Point(5,1));
        pieces_all[26]=new ChessPiece(ChessPiece.P_PAO_CHU,(byte) 26,new Point(2,3));
        pieces_all[27]=new ChessPiece(ChessPiece.P_PAO_CHU,(byte) 27,new Point(8,3));
        pieces_all[28]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 28,new Point(1,4));
        pieces_all[29]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 29,new Point(3,4));
        pieces_all[30]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 30,new Point(5,4));
        pieces_all[31]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 31,new Point(7,4));
        pieces_all[32]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 32,new Point(9,4));
    }

    public void InitializePieces(){
            int constantX = Judge.GetHome() == Judge.G_CHU ? WIDTH + 1 : 0;
            int constantY = Judge.GetHome() == Judge.G_CHU ? HIGH + 1 : 0;
            for (int i = 0; i < positions.length; i++) {
                pieces_all[i].Move(Math.abs(constantX - positions[i][0]), Math.abs(constantY - positions[i][1]));
            }
            for (int i = 0; i <= WIDTH; i++) { //在棋盘上摆上”空子“
                for (int j = 0; j <= HIGH; j++) {
                    pieces_map[i][j] = NullPiece.GetNull(i,j);
                }
            }
            for (int i = 1; i < pieces_all.length; i++) { //在棋盘上摆子
                Point temp = pieces_all[i].GetPosition();
                pieces_map[temp.x][temp.y] = pieces_all[i];
            }
        }

    public Piece GetPiece(int x,int y){
        return pieces_map[x][y];
    }

    public BufferedImage GetBoardImage(){
        return board_image;
    }

    public Piece[] GetAllPieces(){
        return pieces_all;
    }

    public Piece GetNowSelect(){
        return now_select;
    }

    public Piece GetAimSelect(){
        return aim_select;
    }

    public void SetNowSelect(int x,int y){
        now_select=pieces_map[x][y];
    }

    public void SetAimSelect(int x,int y){
        aim_select=pieces_map[x][y];
    }

    public void MovePiece(){
        if(record.size()>=max_records) {
            Step temp=record.pop();
            record.pop();
            record.push(temp);
        }
        record.push(new Step(now_select.GetID(),aim_select.GetID(),now_select.GetPosition(),aim_select.GetPosition()));

        aim_select.SetAlive(false);
        Point pos_aim=aim_select.GetPosition();
        Point pos_now=now_select.GetPosition();
        pieces_map[pos_aim.x][pos_aim.y]=now_select;
        pieces_map[pos_now.x][pos_now.y]=NullPiece.GetNull(pos_now.x,pos_now.y);
        now_select.Move(pos_aim.x,pos_aim.y);
    }

    public void Retract(){
        while (!record.empty()){
            Step step = record.pop();
            pieces_all[step.eaten].SetAlive(true);
            pieces_map[step.end.x][step.end.y] = step.eaten==Null.GetID()?NullPiece.GetNull(step.end.x,step.end.y):pieces_all[step.eaten];
            pieces_map[step.start.x][step.start.y] = pieces_all[step.piece];
            pieces_all[step.piece].Move(step.start.x, step.start.y);
        }
    }

    public int GetRecordSize(){
        return record.size();
    }

    public void ResetSelect(){
        now_select=Null;
        aim_select=Null;
    }

    public Piece GetJiang(){
        return pieces_all[25];
    }

    public Piece GetShuai(){
        return pieces_all[9];
    }

    private class Step{
        byte piece;
        byte eaten;
        Point start;
        Point end;
        Step(){}
        Step(byte piece,byte eaten,Point start,Point end){
            this.piece=piece;
            this.eaten=eaten;
            this.start=start;
            this.end=end;
        }
    }
}
