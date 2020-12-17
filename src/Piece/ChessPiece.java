package Piece;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChessPiece implements Piece{
    private Point position;
    private final String name;
    private final byte group;
    private final byte ID;
    private final BufferedImage image;

    ChessPiece(String name,byte group,byte ID,BufferedImage image){
        this.name=name;
        this.group=group;
        this.ID=ID;
        this.image=image;
    }

    @Override
    public void Move(Point pos) {

    }

    @Override
    public BufferedImage GetImage() {
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
        return position;
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
