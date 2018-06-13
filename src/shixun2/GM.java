package shixun2;

import java.awt.Component; 
import java.awt.FlowLayout; 
import java.awt.event.*; 
import java.sql.*; 
import java.util.*; 
import javax.swing.*;  
import javax.swing.table.TableCellRenderer; 





class GM extends JFrame implements ActionListener {// �ɼ���Ϣ����
			JPanel p = new JPanel();  
			JButton btnAlter = new JButton("�޸ĳɼ�"); 
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
			GSelect gst; 
			int ii = 0;  
			String mxh = null; 
			boolean bstd = false; 

  

GM(String title) {// ���췽��
			super(title); 
			add("South", p); 
			this.add("Center", p1); 
			mb.add(btnAlter); 
			mb.add(btnSearch); 
			mb.add(btnDisplay); 
			this.connDB(); 
			this.setBounds(200, 200, 400, 260); 
			btnAlter.addActionListener(this); 
			btnSearch.addActionListener(this); 
			btnDisplay.addActionListener(this); 
			this.setJMenuBar(mb); 
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.setResizable(false); 
			show(); 
			setLocationRelativeTo(null);
	} 

GM(GSelect gst, String title) {// ���췽��
			super(title); 
			this.gst = gst; 
			bstd = true; 
			add("South", p); 
			this.add("Center", p1); 
			mb.add(btnAlter); 
			mb.add(btnSearch); 
			mb.add(btnDisplay); 

			this.connDB(); 
			this.setBounds(200, 200, 400, 260); 
			btnAlter.addActionListener(this);  
			btnSearch.addActionListener(this); 
			btnDisplay.addActionListener(this); 
			this.setJMenuBar(mb); 
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.setResizable(false); 
			show(); 
	} 

public void display() {// ��ʾ���еĳɼ���Ϣ
			int i = 0; 
			int j = 0; 
			int k = 0; 
			List al = new ArrayList(); 
			try { rs = stmt.executeQuery("select * from sc,c where c.cno=sc.cno"); 
			while (rs.next()) {// �ҳ����еļ�¼������i 
				al.add(rs.getString("sno")); 
				al.add(rs.getString("cn")); 
				al.add(rs.getString("g")); 
				i++; 
			} 
			} catch (SQLException e) { 
				e.printStackTrace(); 
				} 
			playerInfo = new Object[i][3]; 
			String[] columnNames = { "ѧ��", "�γ���", "�ɼ�" }; 
			try { 
				rs = stmt.executeQuery("select * from sc,c where c.cno=sc.cno"); 
				while (rs.next()) { 
					playerInfo[j][0] = rs.getString("sno"); 
					playerInfo[j][1] = rs.getString("cn"); 
					playerInfo[j][2] = rs.getString("g"); 
					j++; 
				} 
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
			sTable = new JTable(playerInfo, columnNames);// ��������
			p1.add(sTable); 
			scroll = new JScrollPane(sTable); 
			this.add(scroll); }  


public void connDB() { // �������ݿ�
			try { Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
				} catch (ClassNotFoundException e) { 
					e.printStackTrace(); 
				} 
			try { con = DriverManager.getConnection( "jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123"); 
					stmt = con.createStatement(); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
	} 


public void closeDB() // �ر�����
{ try { stmt.close(); 
		con.close(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
	} 



public void update() {// �޸�ĳ��ѧ���ĳɼ���Ϣ
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			int row = -1; 
			row = sTable.getSelectedRow(); 
			if (row == -1) {// �ж�Ҫ�޸ĵ���Ϣ�Ƿ�ѡ��
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵļ�¼��"); 
				} else { 
						int j1 = 0; 
						try { 
							if (!bstd) {//�ж�ѡ����ǲ��ǲ�ѯ��Ľ��
								rs = stmt .executeQuery("select * from sc,c where c.cno=sc.cno"); 


							} else { 
								rs = stmt .executeQuery("select * from sc,c where c.cno=sc.cno and sno='" + mxh + "'"); 
							} 
							while (rs.next() && j1 <= row) { 
								kch = rs.getString("sno"); 
								kcm = rs.getString("cn"); 
								xxkch = rs.getString("g"); 
								j1++; 
							} 
						} catch (SQLException e) { 
							e.printStackTrace(); 
						} 
							GAdd gadd = new GAdd(); 
							gadd.setTitle("�޸ĳɼ�"); 
							gadd.tcno.setText(kch); 
							gadd.tcname.setText(kcm); 
							gadd.tpcno.setText(xxkch); 
							gadd.tcno.setEnabled(false); 
							gadd.tcname.setEnabled(false); 

							this.dispose(); 
				} 
	} 



public void select() {// ��ʾĳ��ѧ���ĳɼ���ѯ���
			int j = 0; 
			ii = gst.i; 
			mxh = gst.gxh; 
			playerInfo = new Object[ii][3]; 
			String[] columnNames = { "ѧ��", "�γ���", "�ɼ�" }; 
			try { 
				rs = stmt .executeQuery("select * from sc,c where c.cno=sc.cno and sno='" + mxh + "'"); 
				while (rs.next()) { 
					playerInfo[j][0] = rs.getString("sno"); 
					playerInfo[j][1] = rs.getString("cn"); 
					playerInfo[j][2] = rs.getString("g"); 
					j++; 
				} 
			} catch (SQLException e) { 
				e.printStackTrace();  
			} 
			if (playerInfo[0][1] == null) { 
				this.dispose(); 
				JOptionPane.showMessageDialog(null, "ѧ�Ų����ڣ�"); 
				new GM("�ɼ���Ϣ����").display(); 
			} else { 
				sTable = new JTable(playerInfo, columnNames); 
				p1.add(sTable); 
				scroll = new JScrollPane(sTable); 
				this.add(scroll); 
			} 
	} 


public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand() == "�޸ĳɼ�") { 
				this.update(); 
				} 
			if (e.getActionCommand() == "��ѯ") { 
				gst = new GSelect(); 
				this.dispose(); 
				} 
			if (e.getActionCommand() == "��ʾ") { 
					this.dispose(); 
					new GM("�ɼ���Ϣ����").display(); 
			} 
	} 
} 
























