package shixun2;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class PPSelect extends JFrame implements ActionListener {// ���ڸ�����Ϣ�����ѯʱ�����û����Ľ���
	JLabel ltitle = new JLabel("�û�����");
	JTextField tun = new JTextField(8);
	JButton btnOK = new JButton("ȷ��");
	JPanel p = new JPanel();
	String yh = null;

	public PPSelect() { // ���췽��

		p.add(ltitle);

		p.add(tun);

		p.add(btnOK);

		add(p);

		this.setBounds(300, 280, 200, 160);

		btnOK.addActionListener(this);

		this.setResizable(false);

		this.show();
	}

	public void actionPerformed(ActionEvent e) {

		yh = tun.getText();// ȡ�õ�ǰ������û���

		if (yh.equals("")) {// �ж��Ƿ��������û���

			JOptionPane.showMessageDialog(null, "�û�������Ϊ�գ����������룡");

		} else {

			this.dispose();

			new PPM(this, "�û���Ϣ����").select();

		}

	}
}
