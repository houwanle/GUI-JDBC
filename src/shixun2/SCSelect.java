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

public class SCSelect extends JFrame implements ActionListener {// ����ѡ����Ϣ�����в�ѯʱ����ѧ�ŵĽ���

	JLabel ltitle = new JLabel("ѧ�ţ�");
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("ȷ��");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String scxh = null;
	int i = 0;

	public SCSelect() {// ���췽��

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

			con = DriverManager.getConnection(

			"jdbc:sqlserver://localhost:1433; DatabaseName=student",

			"sa", "123");

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

			rs = stmt.executeQuery("select * from sc where sno='" + scxh + "'");

			while (rs.next()) {

				al.add(rs.getString("sno"));

				al.add(rs.getString("cno"));

				al.add(rs.getString("g"));

				i++; // �ѷ��������ļ�¼������i

			}

		} catch (SQLException e) {

			e.printStackTrace();

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

			new SCM(this, "ѡ����Ϣ����").select();

		}

	}
}
