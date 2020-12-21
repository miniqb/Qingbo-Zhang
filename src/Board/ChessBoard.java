package Board;

import Judge.Judge;
import Piece.*;
import Player.ChessPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    private final Piece[][] pieces_map; //棋盘数组
    private final Piece[] pieces_all; //棋子容器
    private Piece now_piece_chu;
    private Piece aim_piece_chu;
    private Piece now_piece_han;
    private Piece aim_piece_han;
    private Piece now_select;
    private Piece aim_select;
    private BufferedImage board_image;
    private BufferedImage game_image;
//draw area
    private final int UNIT_SIZE = 67;
    private final int WIDTH_SIZE;
    private final int HIGH_SIZE;
    private final int OFFSET_W;
    private final int OFFSET_H;

    JFrame game_frame = new JFrame("象棋");
    DrawBoard draw_board = new DrawBoard();

    private ChessBoard(){
        try {
            board_image= ImageIO.read(new File("image/b_chessboard.png"));
        } catch (IOException e) {
            System.out.println("图片加载错误！");
            e.printStackTrace();
        }
        WIDTH_SIZE=board_image.getWidth();
        HIGH_SIZE=board_image.getHeight();
        OFFSET_W=(WIDTH_SIZE-WIDTH*UNIT_SIZE)/2;
        OFFSET_H=(HIGH_SIZE-HIGH*UNIT_SIZE)/2;
        game_image=new BufferedImage(WIDTH_SIZE,HIGH_SIZE,BufferedImage.TYPE_INT_RGB);
        //构造棋盘数组
        pieces_map = new Piece[WIDTH+1][];
        for (int i = 0; i <= WIDTH; i++) {
            pieces_map[i] = new Piece[HIGH+1];
        }
        pieces_all = new Piece[33];
        CreatePieces(); //创造棋子
        InitializePieces(); //按初始位置摆好棋子

        now_select=pieces_all[0];
        aim_select=pieces_all[0];


        draw_board.setPreferredSize(new Dimension(WIDTH_SIZE,HIGH_SIZE));
        UpdateFrames();
        game_frame.add(draw_board);
        game_frame.pack();
        game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game_frame.setVisible(true);
    }

    private void CreatePieces(){//创建所有棋子对象
        pieces_all[0]=new NullPiece();
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
        int constantX=Judge.GetHome()== Judge.G_HAN?WIDTH+1:0;
        int constantY=Judge.GetHome()== Judge.G_HAN?HIGH+1:0;
        for (int i = 0; i < positions.length; i++) {
            pieces_all[i].Move(Math.abs(constantX-positions[i][0]),Math.abs(constantY-positions[i][1]));
        }
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

    public static ChessBoard Init(){
        if(me==null)
            me=new ChessBoard();
        return me;
    }

    public void UpdateFrames(){
        Graphics game_imageGraphics=game_image.getGraphics();
        game_imageGraphics.drawImage(board_image,0,0,null);
        for (Piece piece:pieces_all) {
            if (piece.IsAlive()&&piece!=now_select) {
                int posX=(piece.GetPosition().x-1) * UNIT_SIZE+OFFSET_W;
                int posY=(piece.GetPosition().y-1) * UNIT_SIZE+OFFSET_H;
                game_imageGraphics.drawImage(piece.GetImage(),posX,posY,null);
            }
        }
    }

    public Piece GetPiece(int x,int y){
        return pieces_map[x][y];
    }

    public void MovePiece(){

    }

    int ss=0;
    public void StartOperation(){
        draw_board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int posX=(e.getX()-OFFSET_W)/UNIT_SIZE+1;
                int posY=(e.getY()-OFFSET_H)/UNIT_SIZE+1;
                if ((posX<=9&&posX>0)||(posY<=10&&posY>0)) {
                    if(aim_select.GetID()==NullPiece.ID&&now_select.GetID()==NullPiece.ID) {
                        System.out.println("now");
                        now_select = pieces_map[posX][posY];
                        System.out.println(now_select.GetName()+ss);
                        ss++;
                    }
                    else {
                        System.out.println("aim");
                        aim_select = pieces_map[posX][posY];
                    }
                    if(aim_select.GetID()!=NullPiece.ID)
                        Judge.getNow_player().MakeChoice(Judge.C_GO);
                }
            }
        });

        draw_board.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(aim_select.GetID()==NullPiece.ID&&now_select.GetID()!=NullPiece.ID) {
                    System.out.println(aim_select.GetID());
                    UpdateFrames();
                    game_image.getGraphics().drawImage(now_select.GetImage(), e.getX()-UNIT_SIZE/2, e.getY()-UNIT_SIZE/2, null);
                    draw_board.repaint();
                }
            }
        });


    }

    class DrawBoard extends JPanel{
        @Override
        public void paint(Graphics g) {
            //画画板
            g.drawImage(game_image,0,0,null);
        }
    }
}
