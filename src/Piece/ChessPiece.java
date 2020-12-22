package Piece;

import Board.ChessBoard;
import Judge.Judge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessPiece implements Piece{
    public final static BufferedImage[] pieces_images=new BufferedImage[14];
    public final static BufferedImage[] pieces_select_images=new BufferedImage[14];
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
        switch (name) {
            case P_BING_HAN -> {
                this.image[0] = pieces_images[0];
                this.image[1] = pieces_select_images[0];
                this.group = Judge.G_HAN;
            }
            case P_CHE_HAN -> {
                this.image[0] = pieces_images[1];
                this.image[1] = pieces_select_images[1];
                this.group = Judge.G_HAN;
            }
            case P_JIANG_CHU -> {
                this.image[0] = pieces_images[2];
                this.image[1] = pieces_select_images[2];
                this.group = Judge.G_CHU;
            }
            case P_JU_CHU -> {
                this.image[0] = pieces_images[3];
                this.image[1] = pieces_select_images[3];
                this.group = Judge.G_CHU;
            }
            case P_MA_CHU -> {
                this.image[0] = pieces_images[4];
                this.image[1] = pieces_select_images[4];
                this.group = Judge.G_CHU;
            }
            case P_MA_HAN -> {
                this.image[0] = pieces_images[5];
                this.image[1] = pieces_select_images[5];
                this.group = Judge.G_HAN;
            }
            case P_PAO_CHU -> {
                this.image[0] = pieces_images[6];
                this.image[1] = pieces_select_images[6];
                this.group = Judge.G_CHU;
            }
            case P_PAO_HAN -> {
                this.image[0] = pieces_images[7];
                this.image[1] = pieces_select_images[7];
                this.group = Judge.G_HAN;
            }
            case P_SHI_CHU -> {
                this.image[0] = pieces_images[8];
                this.image[1] = pieces_select_images[8];
                this.group = Judge.G_CHU;
            }
            case P_SHI_HAN -> {
                this.image[0] = pieces_images[9];
                this.image[1] = pieces_select_images[9];
                this.group = Judge.G_HAN;
            }
            case P_SHUAI_HAN -> {
                this.image[0] = pieces_images[10];
                this.image[1] = pieces_select_images[10];
                this.group = Judge.G_HAN;
            }
            case P_XIANG_CHU -> {
                this.image[0] = pieces_images[11];
                this.image[1] = pieces_select_images[11];
                this.group = Judge.G_CHU;
            }
            case P_XIANG_HAN -> {
                this.image[0] = pieces_images[12];
                this.image[1] = pieces_select_images[12];
                this.group = Judge.G_HAN;
            }
            case P_ZU_CHU -> {
                this.image[0] = pieces_images[13];
                this.image[1] = pieces_select_images[13];
                this.group = Judge.G_CHU;
            }
            default -> {
                this.image[0] = null;
                this.image[1] = null;
                this.group = Judge.G_NULL;
            }
        }
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


}
