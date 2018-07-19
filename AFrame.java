package com.company;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class AFrame {

    public static void main(String[] args) {
        Aframe myframe=new Aframe("布局");
        myframe.Enter();
        myframe.setVisible(true);
        myframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}

@SuppressWarnings("serial")
class Aframe extends JFrame implements ActionListener{

    TextField text1=new TextField();
    TextField text2=new TextField();
    TextField text3=new TextField();
    TextField text4=new TextField();

    public Aframe(String title) {
        super(title);
    }

    public void Enter(){
        this.setBounds(100,100,600,600);
        this.setLayout(null);
        //标签
        JLabel title=new JLabel("title");
        JLabel account=new JLabel("account");
        JLabel password=new JLabel("password");
        JLabel remark=new JLabel("remark");



        //按钮
        JButton enter= new JButton("Enter");
        JButton cancel= new JButton("Cancel");

        //标签位置
        title.setBounds(0, 0, 300, 100);
        account.setBounds(0, 100, 300, 100);
        password.setBounds(0, 200, 300, 100);
        remark.setBounds(0, 300, 300, 100);

        //文本框位置
        text1.setBounds(300, 0, 300, 99);
        text2.setBounds(300, 100, 300, 99);
        text3.setBounds(300, 200, 300, 99);
        text4.setBounds(300, 300, 300, 99);

        //按钮位置
        enter.setBounds(180, 500, 80, 50);
        cancel.setBounds(400, 500, 80, 50);

        //添加文本框
        this.add(text1);
        this.add(text2);
        this.add(text3);
        this.add(text4);
        //添加标签
        this.add(title);
        this.add(account);
        this.add(password);
        this.add(remark);
        //添加按钮
        this.add(enter);
        this.add(cancel);

        enter.addActionListener(this);
        cancel.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Enter")) {

            JOptionPane.showConfirmDialog(Aframe.this,text1.getText()+text2.getText()+text3.getText()+text4.getText(),"警告",JOptionPane.YES_NO_OPTION);
        }
    }

}



