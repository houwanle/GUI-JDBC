package shixun2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StudentFrame extends JFrame implements ActionListener {
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JButton btns = new JButton("ѧ����Ϣ�鿴");
	JButton btng = new JButton("�ɼ���Ϣ�鿴");
	JButton btnu = new JButton("������Ϣ����");
	JButton btnClose = new JButton("�˳�����ϵͳ");
	JLabel l = new JLabel("ѧ��");

	StudentFrame() {

		super("ѧ����Ϣ����ϵͳ");
		setSize(350, 200);
		add("North", p1);
		add("Center", p2);
		p1.add(l);
		p2.add(btns);
		p2.add(btng);
		p2.add(btnu);
		p2.add(btnClose);
		btns.addActionListener(this);
		btng.addActionListener(this);
		btnu.addActionListener(this);
		btnClose.addActionListener(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show();
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "ѧ����Ϣ�鿴") {
			new StudentS();

		}

		if (e.getActionCommand() == "�ɼ���Ϣ�鿴") {
			new StudentSelect();

		}

		if (e.getActionCommand() == "������Ϣ����") {
			PPSelect ppst = new PPSelect();

		}

		if (e.getActionCommand() == "�˳�����ϵͳ") {
			System.exit(0);

		}
	}
}
