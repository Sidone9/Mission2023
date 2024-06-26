package quiz.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener{
    
    JButton rules, back;
    JTextField tfname;
    Login(){
        getContentPane().setBackground(Color.white);
        setLayout(null);
        
      ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
      JLabel image = new JLabel(i1);
      image.setBounds(0,0,600,500);
      add(image);
      
      
      JLabel heading = new JLabel("Simple Minds");
      heading.setBounds(750,60,300,45);
      heading.setFont(new Font("Viner Hand ITC",Font.BOLD,40));
      heading.setForeground(new Color(30,144,254));
      add(heading);
      
      JLabel name = new JLabel("Enter your Name");
      name.setBounds(820,150,300,20);
      name.setFont(new Font("Mongolian Baiti",Font.BOLD,18));
      name.setForeground(new Color(30,144,254));
      add(name);
      
      tfname = new JTextField();
      tfname.setBounds(735,200,300,25);
      tfname.setFont(new Font("Times new Roman",Font.BOLD,20));
      add(tfname);
      
      rules = new JButton("Rules");
      rules.setBounds(735,270,110,25);
      rules.setBackground(new Color(30,144,254));
      rules.setForeground(Color.WHITE);
      rules.addActionListener(this);
      add(rules);
      
      back = new JButton("Back");
      back.setBounds(925,270,110,25);
      back.setBackground(new Color(30,144,254));
      back.setForeground(Color.WHITE);
      back.addActionListener(this);
      add(back);
        
      
        setSize(1200,500);
        setLocation(50,100);
        setVisible(true);      
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == rules){
            String name = tfname.getText();
            setVisible(false);
            new Rules(name);
        }else if(ae.getSource() == back){
            System.exit(0);
        }
    }
    
    public static void main(String [] args){
        new Login();
    }
}
