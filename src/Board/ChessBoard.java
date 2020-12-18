package Board;

import Piece.Piece;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChessBoard {
    private Piece[][] pieces_map;
    private Piece[] pieces_all;
    private Piece now_piece_chu;
    private Piece aim_piece_chu;
    private Piece now_piece_han;
    private Piece aim_piece_han;
    private Piece now_select;
    private Piece aim_select;
    private BufferedImage image;

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
    public Piece GetPiece(Point point){
        return pieces_map[point.y][point.x];
    }
    public void MovePiece(){

    }

}
