package shixun2;

import javax.swing.*; 
import java.awt.*;  
import java.awt.event.*; 
import java.sql.*; 




class GAdd extends JFrame implements ActionListener {// ���ڳɼ���Ϣ�������޸ĳɼ��Ľ���
			JLabel lcno = new JLabel("ѧ�ţ�");  
			JLabel lcname = new JLabel("�γ�����"); 
			JLabel lg = new JLabel("�ɼ���"); 
			JTextField tcno = new JTextField(10); 
			JTextField tcname = new JTextField(10); 
			JTextField tpcno = new JTextField(10); 
			JButton btnOK = new JButton("ȷ��"); 
			JButton btnCancel = new JButton("ȡ��"); 
			JPanel p = new JPanel(); 
			Connection con = null; 
			Statement stmt = null; 
			ResultSet rs = null;  
			boolean isNewsm = true;// �����ж��Ƿ���ʾ�ɼ���Ϣ����Ľ���

 

public GAdd() {// ���췽��
			this.setTitle("����"); this.setBounds(200, 200, 146, 235); 
			p.setLayout(new FlowLayout(FlowLayout.LEFT)); 
			p.add(lcno); 
			p.add(tcno); 
			p.add(lcname); 
			p.add(tcname); 
			p.add(lg); 
			p.add(tpcno); 
			p.add(btnOK); 
			p.add(btnCancel); 

			this.add(p); 
			this.setResizable(false); 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			btnOK.addActionListener(this); 
			btnCancel.addActionListener(this); 
			this.show(); 
			setLocationRelativeTo(null);
	} 

  

public void connDB() { // �������ݿ�
			try { Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
				} catch (ClassNotFoundException e) { 
					e.printStackTrace(); 
				} 
			try { con = DriverManager.getConnection( "jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123"); 
					stmt = con.createStatement(); 
				} catch (SQLException e) { 
					e.printStackTrace(); } 
	} 

  

public void closeDB() // �ر�����
 { try { stmt.close(); 
 		con.close(); 
 		} catch (SQLException e) { 
 			e.printStackTrace(); 
 		} 
 } 

  

public void insertst() { // �޸ĳɼ�
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			kch = tcno.getText(); 
			kcm = tcname.getText(); 
			xxkch = tpcno.getText(); 
			String gcno = null; 

			if (this.getTitle() == "�޸ĳɼ�") { 
				try { this.connDB(); 
						rs = stmt.executeQuery("select cno from c where cn='" + kcm + "'"); 
						while (rs.next()) { 
							gcno = rs.getString("cno");// �ҳ����ſγ̵ĳɼ�Ҫ���޸�
						} 
						System.out.println(gcno); 
						stmt.executeUpdate("update sc set g='" + xxkch  + "' where sno='" + kch + "' and cno='" + gcno + "'"); 
					} catch (SQLException e) { 
								e.printStackTrace(); 
					} 
			} 
	} 

  

public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand() == "ȷ��") { 
					this.insertst(); 
					if (isNewsm) { 
						new GM("�ɼ���Ϣ����").display(); 
						this.dispose(); 
					} 
					isNewsm = true; 
			} 
			if (e.getActionCommand() == "ȡ��") { 
				this.setVisible(false); 
				new GM("�ɼ���Ϣ����").display(); 
			} 
	} 
	} 
