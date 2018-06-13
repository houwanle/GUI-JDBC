package shixun2;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class SCSelect extends JFrame implements ActionListener {// 用于选课信息管理中查询时输入学号的界面

	JLabel ltitle = new JLabel("学号：");
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("确定");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String scxh = null;
	int i = 0;

	public SCSelect() {// 构造方法

		p.add(ltitle);

		p.add(tsno);

		p.add(btnOK);

		add(p);

		this.setBounds(300, 280, 200, 160);

		btnOK.addActionListener(this);

		this.setResizable(false);

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

	public void ii() {// 取得符合条件的记录数

		List al = new ArrayList();

		try {

			rs = stmt.executeQuery("select * from sc where sno='" + scxh + "'");

			while (rs.next()) {

				al.add(rs.getString("sno"));

				al.add(rs.getString("cno"));

				al.add(rs.getString("g"));

				i++; // 把符合条件的记录数赋给i

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	public void actionPerformed(ActionEvent e) {

		scxh = tsno.getText(); // 取得当前输入学号的值

		this.connDB();

		this.ii();

		if (scxh.equals("")) {// 判断是否输入了学号

			JOptionPane.showMessageDialog(null, "学号不能为空，请重新输入！");

		} else {

			this.dispose();

			new SCM(this, "选课信息管理").select();

		}

	}
}
