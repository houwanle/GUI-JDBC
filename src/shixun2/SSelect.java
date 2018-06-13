package shixun2;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class SSelect extends JFrame implements ActionListener {
	// 用于学生基本信息管理中查询时输入学号的界面

	JLabel ltitle = null;
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("确定");
	JPanel p = new JPanel();
	String xh = null;

	public SSelect(String str) {// 构造方法

		ltitle = new JLabel(str);

		p.add(ltitle);

		p.add(tsno);

		p.add(btnOK);

		add(p);

		this.setBounds(300, 280, 200, 160);

		btnOK.addActionListener(this);

		this.setResizable(false);

		this.show();
	}

	public void actionPerformed(ActionEvent e) {

		xh = tsno.getText();// 取得当前输入学号的值

		if (xh.equals("")) {// 判断是否输入了学号

			JOptionPane.showMessageDialog(null, "学号不能为空，请重新输入！");

		} else {

			this.dispose();

			new SM(this).select();

		}

	}
}
