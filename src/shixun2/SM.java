package shixun2;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

class SM extends JFrame implements ActionListener {// ѧ����Ϣ����

	JPanel p = new JPanel();
	JButton btnAdd = new JButton("����");
	JButton btnDelete = new JButton("ɾ��");
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
	SSelect sst;
	String mxh = null;
	boolean bstd = false;

	SM() {// ���췽��

		super("ѧ����Ϣ����");

		add("South", p);

		this.add("Center", p1);

		mb.add(btnAdd);

		mb.add(btnDelete);

		mb.add(btnAlter);

		mb.add(btnSearch);

		mb.add(btnDisplay);

		this.connDB(); // �������ݿ�

		// this.display();

		this.setBounds(200, 200, 400, 260);

		btnAdd.addActionListener(this);

		btnDelete.addActionListener(this);

		btnAlter.addActionListener(this);

		btnSearch.addActionListener(this);

		btnDisplay.addActionListener(this);

		this.setJMenuBar(mb);

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setResizable(false);

		show();
	}

	SM(SSelect sst) {// ���췽��
		super("ѧ����Ϣ����");

		this.sst = sst;

		bstd = true;

		add("South", p);

		this.add("Center", p1);

		mb.add(btnAdd);

		mb.add(btnDelete);

		mb.add(btnAlter);

		mb.add(btnSearch);

		mb.add(btnDisplay);

		this.connDB();

		this.setBounds(200, 200, 400, 260);

		btnAdd.addActionListener(this);

		btnDelete.addActionListener(this);

		btnAlter.addActionListener(this);

		btnSearch.addActionListener(this);

		btnDisplay.addActionListener(this);

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

			while (rs.next()) { // �ҳ����еļ�¼������i

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

		sTable = new JTable(playerInfo, columnNames);// ��������

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

	public void closeDB() // �ر����ݿ�����

	{

		try {

			stmt.close();

			con.close();

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	public void delete() {// ɾ��ĳ��ѧ���Ļ�����Ϣ

		String xh = null;

		String xm = null;

		int nl = 0;

		String xb = null;

		String yx = null;

		int row = -1;

		row = sTable.getSelectedRow();

		if (row == -1) {// �ж�Ҫɾ������Ϣ�Ƿ�ѡ��

			JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���ļ�¼��");

		} else {

			if (!bstd) {// �ж�ѡ����ǲ��ǲ�ѯ��Ľ��

				int j1 = 0;

				try {

					rs = stmt.executeQuery("select * from s");

					while (rs.next() && j1 <= row) {// �ҳ���ǰ��ѡ�еļ�¼�����ݿ��еĶ�Ӧ

						xh = rs.getString("sno");

						xm = rs.getString("sn");

						nl = rs.getInt("sa");

						xb = rs.getString("ss");

						yx = rs.getString("sd");

						j1++;

					}

				} catch (SQLException e) {

					e.printStackTrace();

				}

				int i1 = 0;

				try {

					int rs1 = stmt.executeUpdate("delete from s where sno='" + xh + "'"); // ɾ�����ݿ��е�ǰ��ѡ�еļ�¼

					stmt.executeUpdate("delete from unpw where un='" + xh + "'");// ɾ����Ӧ���û����еļ�¼

					JOptionPane.showMessageDialog(null, "��¼ɾ���ɹ���");

					this.dispose();

					new SM().display();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			} else {

				try {

					int rs1 = stmt.executeUpdate("delete from s where sno='" + mxh + "'");

					stmt.executeUpdate("delete from unpw where un='" + mxh + "'");

					JOptionPane.showMessageDialog(null, "��¼ɾ���ɹ���");

					this.dispose();

					new SM().display();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		}

	}

	public void update() {// �޸�ĳ��ѧ���Ļ�����Ϣ

		String xh = null;

		String xm = null;

		int nl = 0;

		String xb = null;

		String yx = null;

		int row = -1;

		row = sTable.getSelectedRow();

		if (row == -1) {

			JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵļ�¼��");

		} else {

			int j1 = 0;

			try {

				if (!bstd) {// �ж�ѡ����ǲ��ǲ�ѯ��Ľ��

					rs = stmt.executeQuery("select * from s");

				} else {

					rs = stmt.executeQuery("select * from s where sno='" + mxh + "'");

				}

				while (rs.next() && j1 <= row) {// �ҳ���ǰ��ѡ�еļ�¼�����ݿ��еĶ�Ӧ

					xh = rs.getString("sno");

					xm = rs.getString("sn");

					nl = rs.getInt("sa");

					xb = rs.getString("ss");

					yx = rs.getString("sd");

					j1++;

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			SAdd sadd = new SAdd(xb, yx);

			sadd.setTitle("�޸�");

			sadd.tsno.setText(xh);

			sadd.tsname.setText(xm);

			sadd.tsage.setText("" + nl);

			sadd.tsno.setEnabled(false);

			this.dispose();

		}
	}

	public void select() {// ��ʾĳ����ѯ�Ľ��

		mxh = sst.xh;

		playerInfo = new Object[1][5];

		String[] columnNames = { "ѧ��", "����", "����", "�Ա�", "Ժϵ" };

		try {

			rs = stmt.executeQuery("select * from s where sno='" + mxh + "'");

			while (rs.next()) {

				playerInfo[0][0] = rs.getString("sno");

				playerInfo[0][1] = rs.getString("sn");

				playerInfo[0][2] = rs.getInt("sa");

				playerInfo[0][3] = rs.getString("ss");

				playerInfo[0][4] = rs.getString("sd");

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		if (playerInfo[0][1] == null) {

			this.dispose();

			JOptionPane.showMessageDialog(null, "ѧ�Ų����ڣ�");

			new SM().display();

		} else {

			sTable = new JTable(playerInfo, columnNames);// ��������

			p1.add(sTable);

			scroll = new JScrollPane(sTable);

			this.add(scroll);

		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "����") {

			new SAdd("��", "�ƿ�ϵ");
			this.dispose();

		}

		if (e.getActionCommand() == "ɾ��") {

			this.delete();

		}

		if (e.getActionCommand() == "�޸�") {

			this.update();

		}

		if (e.getActionCommand() == "��ѯ") {

			sst = new SSelect("ѧ�ţ�");

			this.dispose();

		}

		if (e.getActionCommand() == "��ʾ") {

			this.dispose();

			new SM().display();

		}
	}

}
