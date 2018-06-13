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

public class StudentS extends JFrame implements ActionListener {
	// ����ѧ����Ϣ�鿴ʱ����ѧ�ŵĽ���

	JLabel ltitle = new JLabel("ѧ�ţ�");
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("ȷ��");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String scxh = null;
	int ii = 0;

	public StudentS() {// ���췽��
		
		p.add(ltitle);
		p.add(tsno);
		p.add(btnOK);
		add(p);
		this.setBounds(300, 280, 200, 160);
		btnOK.addActionListener(this);
		this.setResizable(false);
		this.show();
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

	public void ii() {// ȡ�÷��������ļ�¼��

		List al = new ArrayList();
		try {

			rs = stmt.executeQuery("select * from s where sno='" + scxh + "'");
			while (rs.next()) {
				al.add(rs.getString("sno"));
				al.add(rs.getString("sn"));
				al.add(rs.getString("ss"));
				al.add(rs.getInt("sa"));
				al.add(rs.getString("sd"));
				ii++;// �ѷ��������ļ�¼������ii

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	class StudentMS extends JFrame {// ѧ����Ϣ�鿴

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
		StudentMS() {// ���췽��

			super("ѧ����Ϣ�鿴");
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

		public void display() {// ��ʾ����ѧ���Ļ�����Ϣ

			int i = 0;
			int j = 0;
			int k = 0;
			List al = new ArrayList();
			try {
				rs = stmt.executeQuery("select * from s");

				while (rs.next()) {
					al.add(rs.getString("sno"));
					al.add(rs.getString("sn"));
					al.add(rs.getString("ss"));
					al.add(rs.getInt("sa"));
					al.add(rs.getString("sd"));
					i++;
				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			playerInfo = new Object[i][5];
			String[] columnNames = { "ѧ��", "����", "����", "�Ա�", "Ժϵ" };
			try {
				rs = stmt.executeQuery("select * from s order by sno");
				while (rs.next()) {
					playerInfo[j][0] = rs.getString("sno");
					playerInfo[j][1] = rs.getString("sn");
					playerInfo[j][2] = rs.getInt("sa");
					playerInfo[j][3] = rs.getString("ss");
					playerInfo[j][4] = rs.getString("sd");
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
				con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=student","sa", "123");
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

		public void select() {// ��ѯĳ��ѧ���Ļ�����Ϣ

			int j1 = 0;
			playerInfo = new Object[ii][5];
			String[] columnNames = { "ѧ��", "����", "����", "�Ա�", "Ժϵ" };
			try {
				rs = stmt.executeQuery("select * from s where sno='" + scxh + "'");
				while (rs.next()) {

					playerInfo[j1][0] = rs.getString("sno");
					playerInfo[j1][1] = rs.getString("sn");
					playerInfo[j1][2] = rs.getInt("sa");
					playerInfo[j1][3] = rs.getString("ss");
					playerInfo[j1][4] = rs.getString("sd");
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
				JOptionPane.showMessageDialog(null, "ѧ�Ų����ڣ�");
				this.dispose();
			}

		}
	}

	public void actionPerformed(ActionEvent e) {
		scxh = tsno.getText(); // ȡ�õ�ǰ����ѧ�ŵ�ֵ
		this.connDB();
		this.ii();
		if (scxh.equals("")) {// �ж��Ƿ�������ѧ��
			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ����������룡");
		} else {
			this.dispose();
			new StudentMS().select();

		}

	}
}
