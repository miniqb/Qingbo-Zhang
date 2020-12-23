package Piece;

import Board.ChessBoard;
import Judge.Judge;
import java.awt.*;
import java.awt.image.BufferedImage;

public class NullPiece implements Piece{
    private final static NullPiece[][] null_map = new NullPiece[ChessBoard.WIDTH+1][ChessBoard.HIGH+1];
    static {
        for (int i = 0; i < null_map.length; i++) {
            for (int j = 0; j < null_map[i].length; j++) {
                null_map[i][j]=new NullPiece(i,j);
            }
        }
    }
    private final Point position;
    public final static String NAME = "Null";
    public final static byte ID = 0;

    private NullPiece() {
        position=new Point(0,0);
    }
    private NullPiece(int x,int y) {
        position=new Point(x,y);
    }
    public static Piece GetNull(int x,int y){
        return null_map[x][y];
    }
    @Override
    public void Move(int x,int y) {
        position.x=x;
        position.y=y;
    }

    @Override
    public BufferedImage[] GetImage() {
        return new BufferedImage[3];
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

    @Override
    public void SetAlive(boolean b) {}

    @Override
    public Point[] GetCanGo() {
        return null;
    }
}
