package shixun2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class SAdd extends JFrame implements ActionListener, ItemListener {
	// ����ѧ����Ϣ���������ӻ��޸�ĳ����¼�Ľ���

	JLabel lsno = new JLabel("ѧ�ţ�");
	JLabel lsname = new JLabel("������");
	JLabel lssex = new JLabel("�Ա�");
	JLabel lsage = new JLabel("���䣺");
	JLabel lsdept = new JLabel("Ժϵ��");
	JTextField tsno = new JTextField(14);
	JTextField tsname = new JTextField(14);
	JComboBox cbssex = new JComboBox();
	JTextField tsage = new JTextField(14);
	JComboBox cbsdept = new JComboBox();
	JButton btnOK = new JButton(" ȷ��");
	JButton btnCancel = new JButton(" ȡ��");
	JPanel p = new JPanel();
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	String xb = "��";
	String yx = "�ƿ�ϵ";
	boolean isNewsm = true;// �����ж��Ƿ���ʾѧ������Ľ���

	public SAdd(String xb, String yx) {// ���췽��
		this.xb = xb;

		this.yx = yx;

		this.setTitle("����");

		this.setBounds(200, 200, 220, 210);

		if (xb.trim().equals("��")) {

			cbssex.addItem("��");

			cbssex.addItem("Ů");

		} else {

			cbssex.addItem("Ů");

			cbssex.addItem("��");

		}
		if (yx.trim().equals("�ƿ�ϵ")) {
			cbsdept.addItem("�ƿ�ϵ");
			cbsdept.addItem("����ϵ");
			cbsdept.addItem("��ѧϵ");
			cbsdept.addItem("����ϵ");

		}

		if (yx.trim().equals("����ϵ")) {

			cbsdept.addItem("����ϵ");

			cbsdept.addItem("�ƿ�ϵ");

			cbsdept.addItem("��ѧϵ");

			cbsdept.addItem("����ϵ");

		}

		if (yx.trim().equals("����ϵ")) {

			cbsdept.addItem("����ϵ");

			cbsdept.addItem("�ƿ�ϵ");

			cbsdept.addItem("����ϵ");

			cbsdept.addItem("��ѧϵ");

		}

		if (yx.trim().equals("��ѧϵ")) {

			cbsdept.addItem("��ѧϵ");

			cbsdept.addItem("�ƿ�ϵ");

			cbsdept.addItem("����ϵ");

			cbsdept.addItem("����ϵ");
		}

		p.setLayout(new FlowLayout(FlowLayout.LEFT));

		p.add(lsno);

		p.add(tsno);

		p.add(lsname);

		p.add(tsname);

		p.add(lsage);

		p.add(tsage);

		p.add(lssex);

		p.add(cbssex);

		p.add(lsdept);

		p.add(cbsdept);

		p.add(btnOK);

		p.add(btnCancel);

		this.add(p);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cbssex.addItemListener(this);

		cbsdept.addItemListener(this);

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

	public void insertst() { // �����¼

		String xh = null;

		String xm = null;

		int nl = 0;

		xh = tsno.getText();

		xm = tsname.getText();

		try {

			nl = Integer.parseInt((String) tsage.getText());

			if (this.getTitle() == "�޸�") {// ������޸ļ�¼����ɾ��������

				try {

					this.connDB();

					int rs1 = stmt.executeUpdate("delete from s where sno='" + xh + "'");

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

			String str = "insert into s values('" + xh + "','" + xm + "'," + nl + ",'" + xb + "','" + yx + "')";

			this.connDB();// �������ݿ�

			try {
				stmt.executeUpdate(str);
				JOptionPane.showMessageDialog(null, this.getTitle() + "�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("menu4.gif"));

				this.setVisible(false);

			} catch (SQLException e) {

				JOptionPane.showMessageDialog(null, "ѧ���Ѵ��ڣ�");

				tsno.setText("");

			}

		} catch (NumberFormatException e) {// �ж������Ƿ�Ϊ����

			JOptionPane.showMessageDialog(null, "���������������");

			tsage.setText("");

			isNewsm = false;

		}

		if (this.getTitle() == "����") {// ��������Ӽ�¼����Ӧ���û�����Ҳ����һ����¼

			try {

				stmt.executeUpdate("insert into unpw values('" + xh + "','" + xh + "'," + 1 + ")");

			} catch (NullPointerException e) {

			} catch (SQLException e) {
				// e.printStackTrace();

			}

		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == " ȷ��") {

			this.insertst();

			if (isNewsm) {

				new SM().display();

			}

			isNewsm = true;

		}

		if (e.getActionCommand() == " ȡ��") {

			this.setVisible(false);

			new SM().display();

		}

	}

	public void itemStateChanged(ItemEvent e) { // ������ļ���

		if (e.getStateChange() == ItemEvent.SELECTED) {

			JComboBox jcb = (JComboBox) e.getSource();

			if ((jcb.getSelectedItem() == "��") || (jcb.getSelectedItem() == "Ů")) {

				xb = (String) jcb.getSelectedItem();

			} else {

				yx = (String) jcb.getSelectedItem();

			}

		}
	}
}
