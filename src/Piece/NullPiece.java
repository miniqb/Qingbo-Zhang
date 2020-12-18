package Piece;

import Judge.Judge;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NullPiece implements Piece{
    private Point position;
    public final static String NAME = "Null";
    public final static byte ID = 0;

    NullPiece() {
        position=new Point(0,0);
    }
    NullPiece(int x,int y) {
        position=new Point(x,y);
    }
    @Override
    public void Move(Point pos) {
        position=pos;
    }

    @Override
    public BufferedImage GetImage() {
        return null;
    }

    @Override
    public String GetName() {
        return NullPiece.NAME;
    }

    @Override
    public byte GetID() {
        return NullPiece.ID;
    }

    @Override
    public byte GetGroup() {
        return Judge.G_NULL;
    }

    @Override
    public Point GetPosition() {
        return (Point) position.clone();
    }

    @Override
    public boolean IsAlive() {
        return false;
    }

    @Override
    public boolean IsSelected() {
        return false;
    }

    @Override
    public boolean IsAllowedAimPosition(Point pos) {
        return false;
    }
}
