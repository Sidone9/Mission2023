package quiz.application;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
  
public class score extends JFrame implements ActionListener{
    
    score(String name, int score){
        setBounds(330,150,750,550);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
                
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 250,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,200,300,250);
        add(image);
        
        JLabel Heading = new JLabel("Thankyou " + name + " for playing Simple Minds");
        Heading.setBounds(60,30,700,30);
        Heading.setForeground(new Color(30,144,254));
        Heading.setFont(new Font("Viner Hand ITC",Font.BOLD,27));
        add(Heading);
        
        JLabel lblscore = new JLabel("your score is " + score);
        lblscore.setBounds(390,200,300,30);
        lblscore.setFont(new Font("Tahoma",Font.BOLD,20));
        add(lblscore);
        
        JButton submit = new JButton("Play Again");
        submit.setBounds(400,270,120,30);
        submit.setFont(new Font("Tahoma",Font.PLAIN,16));
        submit.setBackground(new Color(30,144,255));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        add(submit);
        
        
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
        new Login();
    }
    
    public static void main(String[] args){
        new score("user",0);
    }
}
