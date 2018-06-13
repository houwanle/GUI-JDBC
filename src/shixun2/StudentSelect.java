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

public class StudentSelect extends JFrame implements ActionListener {
	// 用于成绩信息查看时输入学号的界面

	JLabel ltitle = new JLabel("学号：");
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("确定");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String scxh = null;
	int ii = 0;

	public StudentSelect() {// 构造方法

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

			con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123");

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

				ii++;// 把符合条件的记录数赋给ii

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	class StudentM extends JFrame {// 成绩信息查看

		JPanel p = new JPanel();

		JMenuBar mb = new JMenuBar();

		JPanel p1 = new JPanel();;

		JTable sTable;

		JScrollPane scroll;

		Connection con = null;

		Statement stmt = null;

		ResultSet rs = null;

		Object[][] playerInfo;

		CSelect cst;

		String mkch = null;

		boolean bstd = false;

		StudentM() {// 构造方法

			super("成绩信息查看");

			bstd = true;

			add("South", p);

			this.add("Center", p1);

			this.connDB();

			this.setBounds(200, 200, 400, 260);

			this.setJMenuBar(mb);

			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			this.setResizable(false);

			show();

		}

		public void display() {// 显示所有学生的成绩

			int i = 0;

			int j = 0;

			int k = 0;

			List al = new ArrayList();

			try {

				rs = stmt.executeQuery("select * from sc");

				while (rs.next()) {

					al.add(rs.getString("sno"));

					al.add(rs.getString("cno"));

					al.add(rs.getString("g"));

					i++;

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			playerInfo = new Object[i][3];

			String[] columnNames = { "学号", "课程号", "成绩" };

			try {

				rs = stmt.executeQuery("select * from sc order by sno");

				while (rs.next()) {

					playerInfo[j][0] = rs.getString("sno");

					playerInfo[j][1] = rs.getString("cno");

					playerInfo[j][2] = rs.getString("g");

					j++;

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			sTable = new JTable(playerInfo, columnNames);

			p1.add(sTable);

			scroll = new JScrollPane(sTable);

			this.add(scroll);

		}

		public void connDB() { // 连接数据库

			try {

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			}

			try {

				con = DriverManager

				.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123");

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

		public void select() {// 查询某个学生的成绩

			int j1 = 0;

			playerInfo = new Object[ii][3];

			String[] columnNames = { "学号", "课程号", "成绩" };

			try {
				rs = stmt.executeQuery("select * from sc where sno='" + scxh + "'");

				while (rs.next()) {

					playerInfo[j1][0] = rs.getString("sno");

					playerInfo[j1][1] = rs.getString("cno");

					playerInfo[j1][2] = rs.getString("g");

					j1++;

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			try {

				if (playerInfo[0][1] == null) {

					this.dispose();

				} else {

					sTable = new JTable(playerInfo, columnNames);

					p1.add(sTable);

					scroll = new JScrollPane(sTable);

					this.add(scroll);

				}

			} catch (ArrayIndexOutOfBoundsException e) {

				JOptionPane.showMessageDialog(null, "学号不存在！");

				this.dispose();

			}

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

			new StudentM().select();

		}

	}
}
