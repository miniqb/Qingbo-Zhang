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

    private final MouseAdapter mouse_click_play=new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int posX=(int)((double)(e.getX()-OFFSET_W)/UNIT_SIZE+1);
            int posY=(int)((double)(e.getY()-OFFSET_H)/UNIT_SIZE+1);
            if ( posX<=9 && posX>0 && posY<=10 && posY>0 ) {
                if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()==NullPiece.ID) {//如果当前未选择任何有效棋子则选中该棋子
                    board.SetNowSelect(posX,posY);
                    if(board.GetNowSelect().GetGroup()!=Judge.GetNowPlayer().GetGroup()) {//如果选择了对方棋子则重置选择
                        board.ResetSelect();
                    }
                    //点击时将棋子移动到合适位置，增强观感
                    UpdateFrames();
                    game_image.getGraphics().drawImage(board.GetNowSelect().GetImage()[0], e.getX()-UNIT_SIZE/2, e.getY()-UNIT_SIZE/2, null);
                    draw_board.repaint();
                }
                else if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()!=NullPiece.ID){//如果已选择有效棋子且未选择目标位置
                    board.SetAimSelect(posX,posY);
                    Judge.GetNowPlayer().MakeChoice(Judge.C_GO);
                    if(ChoiceJudge.Init().DoJudge()){
                        board.MovePiece();
                    }
                    board.ResetSelect();
                    UpdateFrames();
                    draw_board.repaint();
                }
            }
            else{
                board.ResetSelect();
                UpdateFrames();
                draw_board.repaint();
            }
            if(ResultJudge.Init().DoJudge()){
                String name=Judge.getWinner().GetGroup()==Judge.G_HAN?"汉":"楚";
                System.out.println(name+"获胜");
                draw_board.removeMouseListener(this);
                //System.exit(0);
            }
        }

    };//下棋时的监听器

    private final MouseMotionAdapter mouse_move_play=new MouseMotionAdapter() {

        private void MoveAndDrag(MouseEvent e) {
            int posX=(int)((double)(e.getX()-OFFSET_W)/UNIT_SIZE+1);
            int posY=(int)((double)(e.getY()-OFFSET_H)/UNIT_SIZE+1);
            if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()!=NullPiece.ID) {
                board.moving.setLocation(posX,posY);
                UpdateFrames();
                if(( posX<=9 && posX>0 && posY<=10 && posY>0 )&&ThinkingJudge.Init().DoJudge())
                    game_image.getGraphics().drawImage(board.GetNowSelect().GetImage()[2],(posX-1)*UNIT_SIZE+OFFSET_W,(posY-1)*UNIT_SIZE+OFFSET_H,null );
                game_image.getGraphics().drawImage(board.GetNowSelect().GetImage()[0], e.getX()-UNIT_SIZE/2, e.getY()-UNIT_SIZE/2, null);
                draw_board.repaint();
            }
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            MoveAndDrag(e);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            MoveAndDrag(e);
        }
    };//下棋时的监听器

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

    public void StartGame(){
        player_1.SetGroup(Judge.G_HAN);
        player_2.SetGroup(Judge.G_CHU);
        Judge.SetPlayers(player_1,player_2);
        StartOperation();

    }

    public void UpdateFrames(){
        Graphics game_imageGraphics=game_image.getGraphics();
        game_imageGraphics.drawImage(board.GetBoardImage(),0,0,null);
        for (Piece piece:board.GetAllPieces()) {
            if (piece.IsAlive()) {
                int posX=(piece.GetPosition().x-1) * UNIT_SIZE+OFFSET_W;
                int posY=(piece.GetPosition().y-1) * UNIT_SIZE+OFFSET_H;
                if(piece!=board.GetNowSelect())
                    game_imageGraphics.drawImage(piece.GetImage()[0],posX,posY,null);
                else
                    game_imageGraphics.drawImage(piece.GetImage()[1],posX,posY,null);
            }
        }
    }

    public void StartOperation(){
        draw_board.addMouseListener(mouse_click_play);

        draw_board.addMouseMotionListener(mouse_move_play);


    }

    class DrawBoard extends JPanel{
        @Override
        public void paint(Graphics g) {
            //画画板
            g.drawImage(game_image,0,0,null);
        }
    }
}
