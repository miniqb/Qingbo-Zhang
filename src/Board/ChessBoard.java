package Board;

import Piece.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessBoard {
    private static ChessBoard me;
//data area
    public final static byte WIDTH = 9; //棋盘宽度
    public final static byte HIGH = 10; //棋盘高度
    //public final static byte PIECES_SUM = 36; //棋子总数
    private final Piece[][] pieces_map; //棋盘数组
    private final Piece[] pieces_all; //棋子容器
    private Piece now_piece_chu;
    private Piece aim_piece_chu;
    private Piece now_piece_han;
    private Piece aim_piece_han;
    private Piece now_select;
    private Piece aim_select;
    private BufferedImage board_image;

//draw area
    private final int WIDTH_SIZE;
    private final int HIGH_SIZE;



    private ChessBoard(){
        try {
            board_image= ImageIO.read(new File("image/b_chessboard.png"));
        } catch (IOException e) {
            System.out.println("图片加载错误！");
            e.printStackTrace();
        }
        WIDTH_SIZE=board_image.getWidth();
        HIGH_SIZE=board_image.getHeight();
        //构造棋盘数组
        pieces_map = new Piece[WIDTH+1][];
        for (int i = 0; i <= WIDTH; i++) {
            pieces_map[i] = new Piece[HIGH+1];
        }
        pieces_all = new Piece[33];
        CreatePieces();
        for (int i = 0; i <= WIDTH; i++) { //在棋盘上摆上”空子“
            for (int j = 0; j <= HIGH ; j++) {
                pieces_map[i][j]=pieces_all[0];
            }
        }
        for (int i = 1; i < pieces_all.length; i++) { //在棋盘上摆子
            Point temp=pieces_all[i].GetPosition();
            pieces_map[temp.x][temp.y]=pieces_all[i];
        }
    }
    private void CreatePieces(){//创建所有棋子对象
        pieces_all[0]=new NullPiece();
        pieces_all[1]=new ChessPiece(ChessPiece.P_JU_CHU,(byte) 1,new Point(1,10));
        pieces_all[2]=new ChessPiece(ChessPiece.P_JU_CHU,(byte) 1,new Point(9,10));
        pieces_all[3]=new ChessPiece(ChessPiece.P_MA_CHU,(byte) 2,new Point(2,10));
        pieces_all[4]=new ChessPiece(ChessPiece.P_MA_CHU,(byte) 3,new Point(8,10));
        pieces_all[5]=new ChessPiece(ChessPiece.P_XIANG_CHU,(byte) 4,new Point(3,10));
        pieces_all[6]=new ChessPiece(ChessPiece.P_XIANG_CHU,(byte) 5,new Point(7,10));
        pieces_all[7]=new ChessPiece(ChessPiece.P_SHI_CHU,(byte) 6,new Point(4,10));
        pieces_all[8]=new ChessPiece(ChessPiece.P_SHI_CHU,(byte) 7,new Point(6,10));
        pieces_all[9]=new ChessPiece(ChessPiece.P_JIANG_CHU,(byte) 8,new Point(5,10));
        pieces_all[10]=new ChessPiece(ChessPiece.P_PAO_CHU,(byte) 9,new Point(2,8));
        pieces_all[11]=new ChessPiece(ChessPiece.P_PAO_CHU,(byte) 10,new Point(7,8));
        pieces_all[12]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 11,new Point(1,7));
        pieces_all[13]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 12,new Point(3,7));
        pieces_all[14]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 13,new Point(5,7));
        pieces_all[15]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 0,new Point(7,7));
        pieces_all[16]=new ChessPiece(ChessPiece.P_ZU_CHU,(byte) 0,new Point(9,7));

        pieces_all[17]=new ChessPiece(ChessPiece.P_CHE_HAN,(byte) 0,new Point(1,1));
        pieces_all[18]=new ChessPiece(ChessPiece.P_CHE_HAN,(byte) 1,new Point(9,1));
        pieces_all[19]=new ChessPiece(ChessPiece.P_MA_HAN,(byte) 2,new Point(2,1));
        pieces_all[20]=new ChessPiece(ChessPiece.P_MA_HAN,(byte) 3,new Point(8,1));
        pieces_all[21]=new ChessPiece(ChessPiece.P_XIANG_HAN,(byte) 4,new Point(3,1));
        pieces_all[22]=new ChessPiece(ChessPiece.P_XIANG_HAN,(byte) 5,new Point(7,1));
        pieces_all[23]=new ChessPiece(ChessPiece.P_SHI_HAN,(byte) 6,new Point(4,1));
        pieces_all[24]=new ChessPiece(ChessPiece.P_SHI_HAN,(byte) 7,new Point(6,1));
        pieces_all[25]=new ChessPiece(ChessPiece.P_SHUAI_HAN,(byte) 8,new Point(5,1));
        pieces_all[26]=new ChessPiece(ChessPiece.P_PAO_HAN,(byte) 9,new Point(2,3));
        pieces_all[27]=new ChessPiece(ChessPiece.P_PAO_HAN,(byte) 10,new Point(7,3));
        pieces_all[28]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 11,new Point(1,4));
        pieces_all[29]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 12,new Point(3,4));
        pieces_all[30]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 13,new Point(5,4));
        pieces_all[31]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 0,new Point(7,4));
        pieces_all[32]=new ChessPiece(ChessPiece.P_BING_HAN,(byte) 0,new Point(9,4));
    }

    public static ChessBoard Init(){
        if(me==null)
            me=new ChessBoard();
        return me;
    }
    public void DrawBoard (){
    }
    public void Select(Point point){

    }
    public Piece GetNow(){
        return now_select;
    }
    public Piece GetAim(){
        return aim_select;
    }
    public void UpdatePieces(){

    }
    public Piece GetPiece(int x,int y){
        return pieces_map[x][y];
    }
    public void MovePiece(){

    }

}
