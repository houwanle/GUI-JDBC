package shixun2;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

class PM extends JFrame implements ActionListener {// �û���Ϣ����
	JPanel p = new JPanel();
	JButton btnAlter = new JButton("�޸�");
	JButton btnSearch = new JButton("��ѯ");
	JButton btnDisplay = new JButton("��ʾ");
	JMenuBar mb = new JMenuBar();
	JPanel p1 = new JPanel();;
	JTable sTable;
	JScrollPane scroll;
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	Object[][] playerInfo;
	PSelect pst;

	String myh = null;
	boolean bstd = false;

	PM(String title) {// ���췽��
		super(title);
		add("South", p);
		this.add("Center", p1);
		mb.add(btnAlter);
		mb.add(btnSearch);
		mb.add(btnDisplay);
		this.connDB();
		this.setBounds(200, 200, 200, 260);
		btnAlter.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDisplay.addActionListener(this);
		this.setJMenuBar(mb);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		show();
	}

	PM(PSelect pst, String title) {// ���췽��
		super(title);
		this.pst = pst;
		bstd = true;
		add("South", p);
		this.add("Center", p1);
		mb.add(btnAlter);
		mb.add(btnSearch);
		mb.add(btnDisplay);
		this.connDB();
		this.setBounds(200, 200, 200, 260);
		btnAlter.addActionListener(this);
		btnSearch.addActionListener(this);
		btnDisplay.addActionListener(this);
		this.setJMenuBar(mb);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		show();
	}

	public void display() {// ��ʾ�����û��Ļ�����Ϣ
		int i = 0;
		int j = 0;
		int k = 0;
		List al = new ArrayList();
		try {
			rs = stmt.executeQuery("select * from unpw where qx=1");
			while (rs.next()) {
				al.add(rs.getString("un"));
				al.add(rs.getString("pw"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		playerInfo = new Object[i][2];
		String[] columnNames = { "�û���", "����" };
		try {
			rs = stmt.executeQuery("select * from unpw where qx=1 order by un");
			while (rs.next()) {
				playerInfo[j][0] = rs.getString("un");
				playerInfo[j][1] = rs.getString("pw");
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

	public void connDB() { // �������ݿ�
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

	public void closeDB() // �ر�����
	{
		try {
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}

	}

	public void update() {// �޸�ĳ���û�������

		String yh = null;

		String mm = null;

		int row = -1;

		row = sTable.getSelectedRow();

		if (row == -1) {

			JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵļ�¼��");

		} else {

			int j1 = 0;

			try {

				if (!bstd) {

					rs = stmt.executeQuery("select * from unpw where qx=1");

				} else {

					rs = stmt.executeQuery("select * from unpw where qx=1 and un='" + myh + "'");
				}

				while (rs.next() && j1 <= row) {

					yh = rs.getString("un");

					mm = rs.getString("pw");

					j1++;

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			UAdd uadd = new UAdd();

			uadd.setTitle("�޸�");

			uadd.tun.setText(yh);

			uadd.tpw.setText(mm);

			uadd.tun.setEnabled(false);

			this.dispose();

		}
	}

	public void select() {// ��ʾĳ����ѯ�Ľ��

		myh = pst.yh;

		playerInfo = new Object[1][2];

		String[] columnNames = { "�û���", "����" };

		try {

			rs = stmt.executeQuery("select * from unpw where qx=1 and un='" + myh + "'");

			while (rs.next()) {

				playerInfo[0][0] = rs.getString("un");

				playerInfo[0][1] = rs.getString("pw");

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		if (playerInfo[0][1] == null) {

			this.dispose();

			JOptionPane.showMessageDialog(null, "�û������ڣ�");

			new PM("�û���Ϣ����").display();

		} else {

			sTable = new JTable(playerInfo, columnNames);

			p1.add(sTable);

			scroll = new JScrollPane(sTable);

			this.add(scroll);

		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "�޸�") {

			this.update();

		}

		if (e.getActionCommand() == "��ѯ") {

			pst = new PSelect();

			this.dispose();

		}

		if (e.getActionCommand() == "��ʾ") {

			this.dispose();

			new PM("�û���Ϣ����").display();

		}
	}

}
