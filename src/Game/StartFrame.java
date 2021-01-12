package Game;

import javax.swing.*;
import java.awt.*;

public class StartFrame {
    JFrame frame=new JFrame("中国象棋");
    JPanel panel=new JPanel();
    JButton one=new JButton("双人游戏");
    JButton two=new JButton("本地联机");
    JLabel point=new JLabel("中国象棋");
    public StartFrame(){
        panel.setPreferredSize(new Dimension(300,400));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,60));
        point.setFont(new Font("楷体",Font.BOLD,50));
        Dimension size=new Dimension(150,30);
        one.setPreferredSize(size);
        two.setPreferredSize(size);
        panel.setBackground(new Color(238,187,85));
        panel.add(point);
        panel.add(one);
        panel.add(two);
        frame.setBounds(600,200,0,0);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void Init(final byte[] choose){
        frame.setVisible(true);
        one.addActionListener(e -> choose[0]=1);
        two.addActionListener(e -> choose[0]=2);
    }
    public void Close(){
        frame.dispose();
    }
}
