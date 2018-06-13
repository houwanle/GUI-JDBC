package shixun2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class UAdd extends JFrame implements ActionListener {// 用于用户信息管理中修改密码的界面

	JLabel lun = new JLabel("用户：");
	JLabel lpw = new JLabel("密码：");
	JTextField tun = new JTextField(10);
	JTextField tpw = new JTextField(10);
	JButton btnOK = new JButton("确定");
	JButton btnCancel = new JButton("取消");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	boolean isNewsm = true;// 用于判断是否显示用户信息管理的界面

	public UAdd() {// 构造方法

		this.setTitle("增加");

		this.setBounds(200, 200, 146, 200);

		p.setLayout(new FlowLayout(FlowLayout.LEFT));

		p.add(lun);

		p.add(tun);

		p.add(lpw);

		p.add(tpw);

		p.add(btnOK);

		p.add(btnCancel);

		this.add(p);

		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnOK.addActionListener(this);

		btnCancel.addActionListener(this);

		this.show();
	}

	public void connDB() { // 连接数据库

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		}

		try {

			con = DriverManager.getConnection(

			"jdbc:sqlserver://localhost:1433; DatabaseName=student",

			"sa", "123");

			stmt = con.createStatement();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public void closeDB() // 关闭连接

	{

		try {

			stmt.close();

			con.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public void insertst() { // 插入记录

		String yh = null;

		String mm = null;

		yh = tun.getText();

		mm = tpw.getText();

		if ((this.getTitle() == "修改") || (this.getTitle() == "修改密码")) {// 判断修改密码的角色（管理员、学生）

			try {// 如果是修改，先删除再增加

				this.connDB();

				int rs1 = stmt.executeUpdate("delete from unpw where un='" + yh + "'");

			} catch (SQLException e) {

				e.printStackTrace();

			}

		}

		String str = "insert into unpw values('" + yh + "','" + mm + "'," + 1 + ")";

		this.connDB();// 连接数据库

		try {

			stmt.executeUpdate(str);

			JOptionPane.showMessageDialog(null, this.getTitle() + "成功！");

			this.setVisible(false);

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "用户已存在！");

			tun.setText("");

		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "确定") {

			this.insertst();

			if (isNewsm) {

				if (this.getTitle() == "修改")

					new PM("用户信息管理").display();

			}

			isNewsm = true;

		}

		if (e.getActionCommand() == "取消") {

			this.setVisible(false);

			new PM("用户信息管理").display();

		}

	}

}
