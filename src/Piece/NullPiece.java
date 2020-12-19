package Piece;

import Judge.Judge;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NullPiece implements Piece{
    private final Point position;
    public final static String NAME = "Null";
    public final static byte ID = 0;

    public NullPiece() {
        position=new Point(0,0);
    }
    public NullPiece(int x,int y) {
        position=new Point(x,y);
    }
    @Override
    public void Move(int x,int y) {
        position.x=x;
        position.y=y;
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
