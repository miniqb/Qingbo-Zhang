package Piece;

import Board.ChessBoard;
import Judge.Judge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessPiece implements Piece{
    public final static BufferedImage[] pieces_images=new BufferedImage[15];
    public final static BufferedImage[] pieces_select_images=new BufferedImage[15];
    public final static BufferedImage[] pieces_pre_images=new BufferedImage[15];
    public final static String P_JIANG_CHU = "将";
    public final static String P_SHI_CHU = "士";
    public final static String P_XIANG_CHU = "象";
    public final static String P_MA_CHU = "馬";
    public final static String P_JU_CHU = "車";
    public final static String P_PAO_CHU = "砲";
    public final static String P_ZU_CHU = "卒";

    public final static String P_SHUAI_HAN = "帅";
    public final static String P_SHI_HAN = "仕";
    public final static String P_XIANG_HAN = "相";
    public final static String P_MA_HAN = "傌";
    public final static String P_CHE_HAN = "车";
    public final static String P_PAO_HAN = "炮";
    public final static String P_BING_HAN = "兵";

    private final Point[] can_go;
    private final Point position;
    private final String name;
    private final byte group;
    public final byte ID;
    private final BufferedImage[] image=new BufferedImage[3];
    private boolean alive=true;
    //private boolean selected = false;

    public ChessPiece(String name,byte ID,Point pos) {
        LoadImages();
        this.name=name;
        this.ID=ID;
        this.position=pos;
        
        int subscript;
        switch (name) {
            case P_BING_HAN -> {
                subscript=0;
                this.group = Judge.G_HAN;
                can_go=new Point[4];
            }
            case P_CHE_HAN -> {
                subscript=1;
                this.group = Judge.G_HAN;
                can_go=new Point[34];
            }
            case P_JIANG_CHU -> {
                subscript=2;
                this.group = Judge.G_CHU;
                can_go=new Point[4];
            }
            case P_JU_CHU -> {
                subscript=3;
                this.group = Judge.G_CHU;
                can_go=new Point[34];
            }
            case P_MA_CHU -> {
                subscript=4;
                this.group = Judge.G_CHU;
                can_go=new Point[8];
            }
            case P_MA_HAN -> {
                subscript=5;
                this.group = Judge.G_HAN;
                can_go=new Point[8];
            }
            case P_PAO_CHU -> {
                subscript=6;
                this.group = Judge.G_CHU;
                can_go=new Point[34];
            }
            case P_PAO_HAN -> {
                subscript=7;
                this.group = Judge.G_HAN;
                can_go=new Point[34];
            }
            case P_SHI_CHU -> {
                subscript=8;
                this.group = Judge.G_CHU;
                can_go=new Point[4];
            }
            case P_SHI_HAN -> {
                subscript=9;
                this.group = Judge.G_HAN;
                can_go=new Point[4];
            }
            case P_SHUAI_HAN -> {
                subscript=10;
                this.group = Judge.G_HAN;
                can_go=new Point[4];
            }
            case P_XIANG_CHU -> {
                subscript=11;
                this.group = Judge.G_CHU;
                can_go=new Point[4];
            }
            case P_XIANG_HAN -> {
                subscript=12;
                this.group = Judge.G_HAN;
                can_go=new Point[4];
            }
            case P_ZU_CHU -> {
                subscript=13;
                this.group = Judge.G_CHU;
                can_go=new Point[4];
            }
            default -> {
                subscript=14;
                this.group = Judge.G_NULL;
                can_go=new Point[1];
            }
        }
        for (int i = 0; i < can_go.length; i++) {
            can_go[i]=new Point();
        }
        RegisterImages(subscript);
    }

    private void RegisterImages(int n){
        this.image[0] = pieces_images[n];
        this.image[1] = pieces_select_images[n];
        this.image[2] = pieces_pre_images[n];
    }

    private void LoadImages(){
        try {
            pieces_images[0]=ImageIO.read(new File("image/p_bing_han.png"));
            pieces_images[1]=ImageIO.read(new File("image/p_che_han.png"));
            pieces_images[2]=ImageIO.read(new File("image/p_jiang_chu.png"));
            pieces_images[3]=ImageIO.read(new File("image/p_ju_chu.png"));
            pieces_images[4]=ImageIO.read(new File("image/p_ma_chu.png"));
            pieces_images[5]=ImageIO.read(new File("image/p_ma_han.png"));
            pieces_images[6]=ImageIO.read(new File("image/p_pao_chu.png"));
            pieces_images[7]=ImageIO.read(new File("image/p_pao_han.png"));
            pieces_images[8]=ImageIO.read(new File("image/p_shi_chu.png"));
            pieces_images[9]=ImageIO.read(new File("image/p_shi_han.png"));
            pieces_images[10]=ImageIO.read(new File("image/p_shuai_han.png"));
            pieces_images[11]=ImageIO.read(new File("image/p_xiang_chu.png"));
            pieces_images[12]=ImageIO.read(new File("image/p_xiang_han.png"));
            pieces_images[13]=ImageIO.read(new File("image/p_zu_chu.png"));

            pieces_select_images[0]=ImageIO.read(new File("image/p_bing_han_1.png"));
            pieces_select_images[1]=ImageIO.read(new File("image/p_che_han_1.png"));
            pieces_select_images[2]=ImageIO.read(new File("image/p_jiang_chu_1.png"));
            pieces_select_images[3]=ImageIO.read(new File("image/p_ju_chu_1.png"));
            pieces_select_images[4]=ImageIO.read(new File("image/p_ma_chu_1.png"));
            pieces_select_images[5]=ImageIO.read(new File("image/p_ma_han_1.png"));
            pieces_select_images[6]=ImageIO.read(new File("image/p_pao_chu_1.png"));
            pieces_select_images[7]=ImageIO.read(new File("image/p_pao_han_1.png"));
            pieces_select_images[8]=ImageIO.read(new File("image/p_shi_chu_1.png"));
            pieces_select_images[9]=ImageIO.read(new File("image/p_shi_han_1.png"));
            pieces_select_images[10]=ImageIO.read(new File("image/p_shuai_han_1.png"));
            pieces_select_images[11]=ImageIO.read(new File("image/p_xiang_chu_1.png"));
            pieces_select_images[12]=ImageIO.read(new File("image/p_xiang_han_1.png"));
            pieces_select_images[13]=ImageIO.read(new File("image/p_zu_chu_1.png"));

            pieces_pre_images[0]=ImageIO.read(new File("image/p_bing_han_2.png"));
            pieces_pre_images[1]=ImageIO.read(new File("image/p_che_han_2.png"));
            pieces_pre_images[2]=ImageIO.read(new File("image/p_jiang_chu_2.png"));
            pieces_pre_images[3]=ImageIO.read(new File("image/p_ju_chu_2.png"));
            pieces_pre_images[4]=ImageIO.read(new File("image/p_ma_chu_2.png"));
            pieces_pre_images[5]=ImageIO.read(new File("image/p_ma_han_2.png"));
            pieces_pre_images[6]=ImageIO.read(new File("image/p_pao_chu_2.png"));
            pieces_pre_images[7]=ImageIO.read(new File("image/p_pao_han_2.png"));
            pieces_pre_images[8]=ImageIO.read(new File("image/p_shi_chu_2.png"));
            pieces_pre_images[9]=ImageIO.read(new File("image/p_shi_han_2.png"));
            pieces_pre_images[10]=ImageIO.read(new File("image/p_shuai_han_2.png"));
            pieces_pre_images[11]=ImageIO.read(new File("image/p_xiang_chu_2.png"));
            pieces_pre_images[12]=ImageIO.read(new File("image/p_xiang_han_2.png"));
            pieces_pre_images[13]=ImageIO.read(new File("image/p_zu_chu_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取图片异常！");
            System.exit(0);
        }

    }

    @Override
    public void Move(int x,int y) {
        position.x=x;
        position.y=y;
        CountCanGo();
    }

    @Override
    public BufferedImage[] GetImage() {
        return image;
    }

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public byte GetID() {
        return ID;
    }

    @Override
    public byte GetGroup() {
        return group;
    }

    @Override
    public Point GetPosition() {
        return (Point) position.clone();
    }

    @Override
    public boolean IsAlive() {
        return alive;
    }

    @Override
    public boolean IsSelected() {
        return false;
    }

    @Override
    public boolean IsAllowedAimPosition(Point pos) {
        return true;
    }

    @Override
    public void SetAlive(boolean b) {
        alive=b;
    }

    @Override
    public Point[] GetCanGo() {
        return can_go;
    }

    private void CountCanGo(){
        int poX=position.x;
        int poY=position.y;
        switch (name){
            case P_BING_HAN:
            case P_JIANG_CHU:
            case P_SHUAI_HAN:
            case P_ZU_CHU:
                can_go[0].setLocation(poX+1,poY);
                can_go[1].setLocation(poX-1,poY);
                can_go[2].setLocation(poX,poY+1);
                can_go[3].setLocation(poX,poY-1);
                break;
            case P_MA_CHU:
            case P_MA_HAN:
                can_go[0].setLocation(poX+1,poY+2);
                can_go[1].setLocation(poX-1,poY+2);
                can_go[2].setLocation(poX+1,poY-2);
                can_go[3].setLocation(poX-1,poY-2);
                can_go[4].setLocation(poX+2,poY+1);
                can_go[5].setLocation(poX-2,poY+1);
                can_go[6].setLocation(poX+2,poY-1);
                can_go[7].setLocation(poX-2,poY-1);
                break;
            case P_XIANG_CHU:
            case P_XIANG_HAN:
                can_go[0].setLocation(poX+2,poY+2);
                can_go[1].setLocation(poX-2,poY-2);
                can_go[2].setLocation(poX+2,poY-2);
                can_go[3].setLocation(poX-2,poY+2);
                break;
            case P_SHI_CHU:
            case P_SHI_HAN:
                can_go[0].setLocation(poX+1,poY+1);
                can_go[1].setLocation(poX-1,poY-1);
                can_go[2].setLocation(poX-1,poY+1);
                can_go[3].setLocation(poX+1,poY-1);
                break;
            case P_JU_CHU:
            case P_CHE_HAN:
            case P_PAO_CHU:
            case P_PAO_HAN:
                int i=-8,j=0;
                while (i<ChessBoard.WIDTH){
                    if(i!=0){
                        can_go[j].setLocation(poX+i,poY);
                        j++;
                    }
                    i++;
                }
                i=-9;
                while (i<ChessBoard.HIGH){
                    if(i!=0){
                        can_go[j].setLocation(poX,poY+i);
                        j++;
                    }
                    i++;
                }
                break;
            default:
                break;
        }
    }
}
