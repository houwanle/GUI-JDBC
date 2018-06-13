package shixun2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class UAdd extends JFrame implements ActionListener {// �����û���Ϣ�������޸�����Ľ���

	JLabel lun = new JLabel("�û���");
	JLabel lpw = new JLabel("���룺");
	JTextField tun = new JTextField(10);
	JTextField tpw = new JTextField(10);
	JButton btnOK = new JButton("ȷ��");
	JButton btnCancel = new JButton("ȡ��");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	boolean isNewsm = true;// �����ж��Ƿ���ʾ�û���Ϣ����Ľ���

	public UAdd() {// ���췽��

		this.setTitle("����");

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

	public void insertst() { // �����¼

		String yh = null;

		String mm = null;

		yh = tun.getText();

		mm = tpw.getText();

		if ((this.getTitle() == "�޸�") || (this.getTitle() == "�޸�����")) {// �ж��޸�����Ľ�ɫ������Ա��ѧ����

			try {// ������޸ģ���ɾ��������

				this.connDB();

				int rs1 = stmt.executeUpdate("delete from unpw where un='" + yh + "'");

			} catch (SQLException e) {

				e.printStackTrace();

			}

		}

		String str = "insert into unpw values('" + yh + "','" + mm + "'," + 1 + ")";

		this.connDB();// �������ݿ�

		try {

			stmt.executeUpdate(str);

			JOptionPane.showMessageDialog(null, this.getTitle() + "�ɹ���");

			this.setVisible(false);

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "�û��Ѵ��ڣ�");

			tun.setText("");

		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "ȷ��") {

			this.insertst();

			if (isNewsm) {

				if (this.getTitle() == "�޸�")

					new PM("�û���Ϣ����").display();

			}

			isNewsm = true;

		}

		if (e.getActionCommand() == "ȡ��") {

			this.setVisible(false);

			new PM("�û���Ϣ����").display();

		}

	}

}
