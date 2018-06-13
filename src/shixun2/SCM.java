package shixun2;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

class SCM extends JFrame implements ActionListener {// 选课信息管理

	JPanel p = new JPanel();
	JButton btnSearch = new JButton("查询");
	JButton btnDisplay = new JButton("显示");
	JMenuBar mb = new JMenuBar();
	JPanel p1 = new JPanel();;
	JTable sTable;
	JScrollPane scroll;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	Object[][] playerInfo;
	SCSelect scst;
	String mscxh = null;
	int ii = 0;
	boolean bstd = false;

	SCM(String title) {// 构造方法

		super(title);

		add("South", p);

		this.add("Center", p1);

		mb.add(btnSearch);

		mb.add(btnDisplay);

		this.connDB();

		this.setBounds(200, 200, 400, 260);

		btnSearch.addActionListener(this);

		btnDisplay.addActionListener(this);

		this.setJMenuBar(mb);

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);

		show();

	}

	SCM(SCSelect scst, String title) {// 构造方法

		super(title);

		this.scst = scst;

		bstd = true;

		add("South", p);

		this.add("Center", p1);

		mb.add(btnSearch);

		mb.add(btnDisplay);

		this.connDB();

		this.setBounds(200, 200, 400, 260);

		btnSearch.addActionListener(this);

		btnDisplay.addActionListener(this);

		this.setJMenuBar(mb);

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);

		show();
	}

	public void display() {// 显示所有选课信息

		int i = 0;

		int j = 0;

		int k = 0;

		List al = new ArrayList();

		try {

			rs = stmt.executeQuery("select * from sc");

			while (rs.next()) {// 找出表中的记录数赋给i

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

		sTable = new JTable(playerInfo, columnNames);// 创建网格

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

	public void select() {// 显示某个查询的结果

		ii = scst.i;

		mscxh = scst.scxh;

		int j = 0;

		playerInfo = new Object[ii][3];

		String[] columnNames = { "学号", "课程号", "成绩" };

		try {

			rs = stmt

			.executeQuery("select * from sc where sno='" + mscxh + "'");

			while (rs.next()) {

				playerInfo[j][0] = rs.getString("sno");

				playerInfo[j][1] = rs.getString("cno");

				playerInfo[j][2] = rs.getString("g");

				j++;

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		if (playerInfo[0][1] == null) {

			this.dispose();

			JOptionPane.showMessageDialog(null, "课程号不存在！");

			new SCM("选课信息管理").display();

		} else {

			sTable = new JTable(playerInfo, columnNames);

			p1.add(sTable);

			scroll = new JScrollPane(sTable);

			this.add(scroll);

		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "查询") {

			scst = new SCSelect();

			this.dispose();
		}

		if (e.getActionCommand() == "显示") {

			this.dispose();

			new SCM("选课信息管理").display();

		}
	}

}
