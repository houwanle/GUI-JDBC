package shixun2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ManagerFrane extends JFrame implements ActionListener {// ����Ա����

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JButton btns = new JButton("ѧ����Ϣ����");
	JButton btnc = new JButton("�γ���Ϣ����");
	JButton btnsc = new JButton("ѡ����Ϣ����");
	JButton btng = new JButton("�ɼ���Ϣ����");
	JButton btnu = new JButton("�û���Ϣ����");
	JButton btnClose = new JButton("�˳�����ϵͳ");
	JLabel l = new JLabel("����Ա");

	@SuppressWarnings("deprecation")
	ManagerFrane() {// ���췽��
		super("ѧ����Ϣ����ϵͳ");
		setSize(350, 200);
		add("North", p1);
		add("Center", p2);
		p1.add(l);
		p2.add(btns);
		p2.add(btnc);
		p2.add(btnsc);
		p2.add(btng);
		p2.add(btnu);
		p2.add(btnClose);
		btns.addActionListener(this);
		btnc.addActionListener(this);
		btnsc.addActionListener(this);
		btng.addActionListener(this);
		btnu.addActionListener(this);
		btnClose.addActionListener(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show();
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "ѧ����Ϣ����")
			new SM().display();
		if (e.getActionCommand() == "�γ���Ϣ����") {

			new CM("�γ���Ϣ����").display();
		}
		if (e.getActionCommand() == "ѡ����Ϣ����") {

			new SCM("ѡ����Ϣ����").display();
		}

		if (e.getActionCommand() == "�ɼ���Ϣ����") {

			new GM("�ɼ���Ϣ����").display();
		}

		if (e.getActionCommand() == "�û���Ϣ����") {

			new PM("�û���Ϣ����").display();
		}

		if (e.getActionCommand() == "�˳�����ϵͳ") {
			System.exit(0);
		}
	}
}
