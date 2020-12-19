package Piece;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Piece {
    void Move(int x,int y);      //移动自身位置
    BufferedImage GetImage();           //画自己
    String GetName();          //返回名字
    byte GetID();              //返回Id
    byte GetGroup();           //返回阵营
    Point GetPosition();       //返回位置
    boolean IsAlive();         //是否存活
    boolean IsSelected();      //是否被选中
    boolean IsAllowedAimPosition(Point pos);         //是否是被允许的目标位置
}
