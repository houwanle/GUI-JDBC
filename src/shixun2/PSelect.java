package shixun2;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class PSelect extends JFrame implements ActionListener {
	// 用于用户信息管理中查询时输入用户名的界面

	JLabel ltitle = new JLabel("用户名：");
	JTextField tun = new JTextField(8);
	JButton btnOK = new JButton("确定");
	JPanel p = new JPanel();
	String yh = null;

	public PSelect() { // 构造方法

		p.add(ltitle);

		p.add(tun);

		p.add(btnOK);

		add(p);

		this.setBounds(300, 280, 200, 160);

		btnOK.addActionListener(this);

		this.setResizable(false);

		this.show();
	}

	public void actionPerformed(ActionEvent e) {

		yh = tun.getText();// 取得当前输入用户名的值

		if (yh.equals("")) {// 判断是否输入了用户名

			JOptionPane.showMessageDialog(null, "用户名不能为空，请重新输入！");

		} else {

			this.dispose();

			new PM(this, "用户信息管理").select();

		}

	}
}
