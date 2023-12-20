package view;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {

    private final MainFrame mainFrame;

    private String userType;

    public LoginFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userType = "学生";
        createFrom();
    }


    private void createFrom() {
        this.setTitle("登录界面");// 设置标题
        this.setSize(400, 200);// 界面大小
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//3关闭窗体退出程序
        this.setLocationRelativeTo(null);// 界面相对位置，
        this.setResizable(false);// 界面大小不变
        this.setAlwaysOnTop(true);


        this.setLayout(null);


        JLabel labName = new JLabel("账号：");   //设置标签
        JTextField textName = new JTextField();    //设置文本框
        labName.setBounds(10, 10, 60, 30);
        textName.setBounds(60, 10, 300, 30);
        this.add(labName);
        this.add(textName);

        JLabel labpwd = new JLabel("密码：");
        JPasswordField pwdtext = new JPasswordField();  //设置密码框
        labpwd.setBounds(10, 60, 60, 30);
        pwdtext.setBounds(60, 60, 300, 30);
        this.add(labpwd);
        this.add(pwdtext);

        JButton button1 = new JButton();   //按钮
        JButton button2 = new JButton();   //按钮
        button1.setBounds(110, 110, 60, 30);
        button2.setBounds(190, 110, 180, 30);
        button1.setText("登录");
        button2.setText("切换登陆类型：" + userType);
        this.add(button1);
        this.add(button2);

        createMouseclick(button1, button2, textName, pwdtext);

        //
        textName.addKeyListener(new KeyAdapter() {    //文本框的回车响应事件
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginButton(textName, pwdtext);
                }
            }
        });
        pwdtext.addKeyListener(new KeyAdapter() {  //文本框的回车响应事件
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginButton(textName, pwdtext);
                }
            }
        });
    }

    private void createMouseclick(JButton button1, JButton button2,   //鼠标点击响应事件
                                  JTextField textName,
                                  JPasswordField pwdtext
    ) {
        button1.addActionListener(e -> loginButton(textName, pwdtext));
        button2.addActionListener(e -> {
            if (userType.equals("学生")) {
                userType = "餐厅";
                button2.setText("切换登陆类型：" + userType);
            } else {
                userType = "学生";
                button2.setText("切换登陆类型：" + userType);
            }
        });
    }

    private void loginButton(JTextField textName, JPasswordField pwdtext) {
        String username = textName.getText();
        String password = "";
        if (userType.equals("学生"))
            password = mainFrame.getStuService().getPassword(username);
        else
            password = mainFrame.getRestService().getPassword(username);
        if (isNullOrEmpty(password)) {
            JOptionPane.showMessageDialog(this, "用户不存在");
            return;
        }
        if (pwdtext.getText().equals(password)) {      //登录判断
            JOptionPane.showMessageDialog(this, "登录成功");
            this.setVisible(false);
            mainFrame.showMain(username, userType);
        } else {
            textName.requestFocus();
            JOptionPane.showMessageDialog(this, "账号或密码错误");
            textName.setText("");
            pwdtext.setText("");
        }
    }

    private boolean isNullOrEmpty(String target) {
        return target == null || target.isEmpty();
    }
}