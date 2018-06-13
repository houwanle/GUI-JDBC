package shixun2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StudentFrame extends JFrame implements ActionListener {
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JButton btns = new JButton("学生信息查看");
	JButton btng = new JButton("成绩信息查看");
	JButton btnu = new JButton("个人信息管理");
	JButton btnClose = new JButton("退出管理系统");
	JLabel l = new JLabel("学生");

	StudentFrame() {

		super("学生信息管理系统");
		setSize(350, 200);
		add("North", p1);
		add("Center", p2);
		p1.add(l);
		p2.add(btns);
		p2.add(btng);
		p2.add(btnu);
		p2.add(btnClose);
		btns.addActionListener(this);
		btng.addActionListener(this);
		btnu.addActionListener(this);
		btnClose.addActionListener(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show();
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "学生信息查看") {
			new StudentS();

		}

		if (e.getActionCommand() == "成绩信息查看") {
			new StudentSelect();

		}

		if (e.getActionCommand() == "个人信息管理") {
			PPSelect ppst = new PPSelect();

		}

		if (e.getActionCommand() == "退出管理系统") {
			System.exit(0);

		}
	}
}
