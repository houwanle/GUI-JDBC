package shixun2;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class SSelect extends JFrame implements ActionListener {
	// ����ѧ��������Ϣ�����в�ѯʱ����ѧ�ŵĽ���

	JLabel ltitle = null;
	JTextField tsno = new JTextField(8);
	JButton btnOK = new JButton("ȷ��");
	JPanel p = new JPanel();
	String xh = null;

	public SSelect(String str) {// ���췽��

		ltitle = new JLabel(str);

		p.add(ltitle);

		p.add(tsno);

		p.add(btnOK);

		add(p);

		this.setBounds(300, 280, 200, 160);

		btnOK.addActionListener(this);

		this.setResizable(false);

		this.show();
	}

	public void actionPerformed(ActionEvent e) {

		xh = tsno.getText();// ȡ�õ�ǰ����ѧ�ŵ�ֵ

		if (xh.equals("")) {// �ж��Ƿ�������ѧ��

			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ����������룡");

		} else {

			this.dispose();

			new SM(this).select();

		}

	}
}
