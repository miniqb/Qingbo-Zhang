package Game;

import Board.ChessBoard;
import Internet.Client;
import Internet.Internet;
import Internet.Server;
import Judge.*;
import Piece.*;
import Player.*;
import Sound.WavPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class ChessGame {
    /**
     * 用于管理游戏进行的类
     */

    public static final byte HERE=1;
    public static final byte THERE=2;
    //鼠标点击事件的事件适配器
    private final MouseAdapter mouse_click_play=new MyMouseAdapter();//下棋时的监听器

    //鼠标移动事件的事件适配器，在选中棋子时让棋子跟随鼠标移动
    private final MouseMotionAdapter mouse_move_play=new MyMouseMotionAdapter();//下棋时的监听器

    //键盘按下时的事件适配器，用于实现悔棋操作
    private final KeyAdapter key_pressed_play=new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(mod==THERE)
                return;
            Judge.GetNowPlayer().MakeChoice(Judge.C_RETRACT);//当前棋手做出“悔棋”选择
            if(ChoiceJudge.Init().DoJudge()) {  //判断选择是否合法
                //执行悔棋操作并重绘棋盘
                board.Retract();
                UpdateFrames();
                draw_board.repaint();
                board.UpdatePiecesCanGo();
            }
        }
    };

    private final int UNIT_SIZE = 67;   //棋盘上格子的边长
    private final int WIDTH_SIZE;   //棋盘宽格子数
    private final int HIGH_SIZE;    //棋盘高格子数
    private final int OFFSET_W;     //棋盘左边缘距离棋盘的距离
    private final int OFFSET_H;     //棋盘上边缘距离棋盘的距离

    private BufferedImage game_image;   //整个棋盘画面的图像

    JFrame game_frame = new JFrame("象棋");   //游戏窗口
    DrawBoard draw_board = new DrawBoard(); //棋盘画板，用于在上面绘图

    private final ChessPlayer player_1; //玩家1
    private final ChessPlayer player_2; //玩家2
    private final ChessBoard board; //棋盘

    private Internet internet=null;

    public static byte mod=THERE;

    public ProcessControl control=new ProcessControl();


    /**
     * 初始化各种数据
     */
    public ChessGame(byte choose){
        Scanner scanner=new Scanner(System.in);
        mod=choose;
        System.out.println("请选择先走还是后走：先：2;后：1");
        byte c_g=scanner.nextByte();
        //初始化玩家
        player_1=new ChessPlayer();
        player_2=new ChessPlayer();

        //为玩家指定阵营
        player_1.SetGroup(c_g);
        player_2.SetGroup(c_g==Judge.G_CHU?Judge.G_HAN:Judge.G_CHU);

        if(mod==THERE){
            if(c_g==2) {
                CreateRoom creator=new CreateRoom();
                creator.Init();
                internet = new Server();
                creator.Close();
            }
            else {
                JoinRoom joiner=new JoinRoom();
                StringBuffer add=new StringBuffer();
                //String add ="null";
                joiner.Init(add);
                while (add.length()==0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                internet = new Client(add.toString(),1234);

                joiner.Close();
            }
        }

        //向裁判提供玩家信息
        Judge.SetPlayers(player_1,player_2);

        //初始化棋盘
        board=ChessBoard.Init();

        //初始化一些数据
        WIDTH_SIZE=board.GetBoardImage().getWidth();
        HIGH_SIZE=board.GetBoardImage().getHeight();
        OFFSET_W=(WIDTH_SIZE-ChessBoard.WIDTH*UNIT_SIZE)/2;
        OFFSET_H=(HIGH_SIZE-ChessBoard.HIGH*UNIT_SIZE)/2;

        //初始化游戏画面图像
        game_image=new BufferedImage(WIDTH_SIZE,HIGH_SIZE,BufferedImage.TYPE_INT_RGB);

        //设置画板大小
        draw_board.setPreferredSize(new Dimension(WIDTH_SIZE,HIGH_SIZE));
        //更新游戏画面图像
        UpdateFrames();
        //向窗口载入画板
        game_frame.add(draw_board);
        game_frame.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

        //窗口自适应初始大小
        game_frame.pack();
        //禁止手动调整窗口大小
        game_frame.setResizable(false);
        //设置点击右上角”X“关闭程序
        game_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println(Judge.GetHome());
    }

    /**
     * 开始游戏
     */
    public void StartGame(){

        //显示画面
        game_frame.setVisible(true);
        //添加事件适配器
        StartOperation();
        control.StartControl();
    }

    private void Receive(){
        Point[] points=internet.Receive();
        byte type=WavPlayer.GO;
        if(board.GetPiece(points[1].x,points[1].y).GetID()!=NullPiece.ID)
            type=WavPlayer.EAT;
        board.MovePiece(points[0],points[1]);
        UpdateFrames();
        draw_board.repaint();
        board.UpdatePiecesCanGo();
        new WavPlayer(type).start();
    }
    /**
     * 根据棋盘信息更新游戏画面
     */
    public void UpdateFrames(){
        Graphics game_imageGraphics=game_image.getGraphics();
        game_imageGraphics.drawImage(board.GetBoardImage(),0,0,null);//画棋盘
        for (Piece piece:board.GetAllPieces()) {//画上所有存活棋子
            if (piece.IsAlive()) {
                int posX=(piece.GetPosition().x-1) * UNIT_SIZE+OFFSET_W;
                int posY=(piece.GetPosition().y-1) * UNIT_SIZE+OFFSET_H;
                if(piece!=board.GetNowSelect())//该棋子是否被选中
                    game_imageGraphics.drawImage(piece.GetImage()[0],posX,posY,null);
                else
                    game_imageGraphics.drawImage(piece.GetImage()[1],posX,posY,null);
            }
        }
    }

    /**
     * 添加事件适配器
     */
    public void StartOperation(){
        draw_board.setFocusable(true);

        draw_board.addMouseListener(mouse_click_play);

        draw_board.addMouseMotionListener(mouse_move_play);

        draw_board.addKeyListener(key_pressed_play);
    }

    class DrawBoard extends JPanel{
        @Override
        public void paint(Graphics g) { //重写JPanel的paint方法，实现绘制游戏画面
            //画画板
            g.drawImage(game_image,0,0,null);
        }
    }

    class MyMouseAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {    //响应鼠标“按下”事件的方法
            if(!Judge.other_end)
                return;
            //获取鼠标按下时在棋盘上的单位位置
            int posX=(int)((double)(e.getX()-OFFSET_W)/UNIT_SIZE+1);
            int posY=(int)((double)(e.getY()-OFFSET_H)/UNIT_SIZE+1);
            //如果位置在棋盘上（不在边缘处）
            if ( posX<=9 && posX>0 && posY<=10 && posY>0 && e.getButton()==MouseEvent.BUTTON1) {
                if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()==NullPiece.ID) {//如果当前未选择任何有效棋子则选中该棋子
                    Piece piece=board.GetPiece(posX,posY);
                    if(piece.GetGroup()!=Judge.GetNowPlayer().GetGroup())
                        return;
                    board.SetNowSelect(piece);
                    new WavPlayer(WavPlayer.PICK).start();
                    //点击时将棋子移动到合适位置，增强观感
                    UpdateFrames();
                    game_image.getGraphics().drawImage(board.GetNowSelect().GetImage()[0], e.getX()-UNIT_SIZE/2, e.getY()-UNIT_SIZE/2, null);
                    draw_board.repaint();
                }
                else if(board.GetAimSelect().GetID()==NullPiece.ID && board.GetNowSelect().GetID()!=NullPiece.ID){//如果已选择有效棋子且未选择目标位置
                    board.SetAimSelect(posX,posY);  //设置目标位置
                    Judge.GetNowPlayer().MakeChoice(Judge.C_GO);    //当前棋手做出“走子”选择
                    if(ChoiceJudge.Init().DoJudge()){   //如果当前选择合法
                        if(board.GetAimSelect().GetID()==NullPiece.ID)  //播放走子或吃子音效
                            new WavPlayer(WavPlayer.GO).start();
                        else
                            new WavPlayer(WavPlayer.EAT).start();
                        control.MoveEnd();
                    }
                    else {   //如果当前选择不合法
                        new WavPlayer(WavPlayer.BACK).start();  //播放放弃执子音效
                        //重置选择并重绘
                        board.ResetSelect();
                        UpdateFrames();
                        draw_board.repaint();
                    }
                }
            }
            //如果位置在边缘处且按下的鼠标键不为滚轮
            else if(e.getButton()!=MouseEvent.BUTTON2){
                if(board.GetNowSelect().GetID()!=NullPiece.ID){//如果当前选中
                    new WavPlayer(WavPlayer.BACK).start();//播放放弃执子音效
                    //重置选择并重绘
                    board.ResetSelect();
                    UpdateFrames();
                    draw_board.repaint();
                }
            }
        }
    }

    class MyMouseMotionAdapter extends MouseMotionAdapter{
        private void MoveAndDrag(MouseEvent e) {    //响应鼠标移动或按下鼠标时移动的方法，用于实现棋子跟随鼠标移动的效果
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
    }

    class ProcessControl {
        private boolean move_end=false;

        public void StartControl(){
            if(mod==THERE&&Judge.GetNowPlayer().GetGroup()==Judge.G_CHU) {
                ChoiceJudge.Init().OtherEnd(false);
                Receive();
                ChoiceJudge.Init().OtherEnd(true);
            }
            while (true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if(move_end){
                    if(mod==THERE){
                        ChoiceJudge.Init().OtherEnd(false);
                        internet.Send(board.GetNowSelect().GetPosition(),board.GetAimSelect().GetPosition());//发送数据
                    }


                    board.MovePiece();  //移动棋子

                    //重置选择并重绘
                    ThinkingJudge.Resetting();
                    board.ResetSelect();
                    UpdateFrames();
                    draw_board.repaint();
                    board.UpdatePiecesCanGo();
                    move_end=false;

                    if(mod==THERE){
                        if(ResultJudge.Init().DoJudge()){
                            String name=Judge.getWinner().GetGroup()==Judge.G_HAN?"汉":"楚";
                            System.out.println(name+"获胜");
                            System.exit(0);
                        }
                        ChoiceJudge.Init().OtherEnd(false);
                        Receive();
                        ChoiceJudge.Init().OtherEnd(true);
                    }
                    if(ResultJudge.Init().DoJudge()){
                        String name=Judge.getWinner().GetGroup()==Judge.G_HAN?"汉":"楚";
                        System.out.println(name+"获胜");
                        System.exit(0);
                    }

                }
            }
        }

        public void MoveEnd(){
            move_end=true;
        }
    }
}
