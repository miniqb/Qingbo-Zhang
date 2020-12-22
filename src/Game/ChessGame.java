package Game;

import Board.ChessBoard;
import Judge.*;
import Piece.*;
import Player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class ChessGame {
    private final int UNIT_SIZE = 67;
    private final int WIDTH_SIZE;
    private final int HIGH_SIZE;
    private final int OFFSET_W;
    private final int OFFSET_H;

    private BufferedImage game_image;

    JFrame game_frame = new JFrame("象棋");
    DrawBoard draw_board = new DrawBoard();

    private final ChessPlayer player_1;
    private final ChessPlayer player_2;
    private Judge judge;
    private final ChessBoard board=ChessBoard.Init();

    public ChessGame(){
        player_1=new ChessPlayer();
        player_2=new ChessPlayer();

        WIDTH_SIZE=board.GetBoardImage().getWidth();
        HIGH_SIZE=board.GetBoardImage().getHeight();
        OFFSET_W=(WIDTH_SIZE-ChessBoard.WIDTH*UNIT_SIZE)/2;
        OFFSET_H=(HIGH_SIZE-ChessBoard.HIGH*UNIT_SIZE)/2;

        game_image=new BufferedImage(WIDTH_SIZE,HIGH_SIZE,BufferedImage.TYPE_INT_RGB);
        draw_board.setPreferredSize(new Dimension(WIDTH_SIZE,HIGH_SIZE));
        UpdateFrames();
        game_frame.add(draw_board);
        game_frame.pack();
        game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game_frame.setVisible(true);
    }
    void StartGame(){
        player_1.SetGroup(Judge.G_CHU);
        player_2.SetGroup(Judge.G_HAN);
        Judge.SetPlayers(player_1,player_2);

        StartOperation();

    }
    public void UpdateFrames(){
        Graphics game_imageGraphics=game_image.getGraphics();
        game_imageGraphics.drawImage(board.GetBoardImage(),0,0,null);
        for (Piece piece:board.GetAllPieces()) {
            if (piece.IsAlive()&&piece!=board.GetNowSelect()) {
                int posX=(piece.GetPosition().x-1) * UNIT_SIZE+OFFSET_W;
                int posY=(piece.GetPosition().y-1) * UNIT_SIZE+OFFSET_H;
                game_imageGraphics.drawImage(piece.GetImage(),posX,posY,null);
            }
        }
    }
    public void StartOperation(){
        draw_board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int posX=(int)((double)(e.getX()-OFFSET_W)/UNIT_SIZE+1);
                int posY=(int)((double)(e.getY()-OFFSET_H)/UNIT_SIZE+1);
                if ( posX<=9 && posX>0 && posY<=10 && posY>0 ) {
                    System.out.printf("%d %d\n",(e.getX()-OFFSET_W)/UNIT_SIZE,(e.getY()-OFFSET_H)/UNIT_SIZE);
                    if(ChoiceJudge.Init().DoJudge()){

                    }
                    if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()==NullPiece.ID) {//如果当前未选择
                        board.SetNowSelect(posX,posY);
                        System.out.println(board.GetNowSelect().GetName());
                    }
                    else {
                        board.SetAimSelect(posX,posY);
                        Judge.GetNowPlayer().MakeChoice(Judge.C_GO);
                        if(ChoiceJudge.Init().DoJudge()){
                            board.MovePieces();
                        }
                    }
                }
            }
        });

        draw_board.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()!=NullPiece.ID) {
                    System.out.println(board.GetAimSelect().GetID());
                    UpdateFrames();
                    game_image.getGraphics().drawImage(board.GetNowSelect().GetImage(), e.getX()-UNIT_SIZE/2, e.getY()-UNIT_SIZE/2, null);
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
