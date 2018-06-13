package shixun2;

import java.awt.Component; 
import java.awt.FlowLayout; 
import java.awt.event.*; 
import java.sql.*; 
import java.util.*; 
import javax.swing.*;  
import javax.swing.table.TableCellRenderer; 





class GM extends JFrame implements ActionListener {// 成绩信息管理
			JPanel p = new JPanel();  
			JButton btnAlter = new JButton("修改成绩"); 
			JButton btnSearch = new JButton("查询");  
			JButton btnDisplay = new JButton("显示"); 

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

  

GM(String title) {// 构造方法
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

GM(GSelect gst, String title) {// 构造方法
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

public void display() {// 显示所有的成绩信息
			int i = 0; 
			int j = 0; 
			int k = 0; 
			List al = new ArrayList(); 
			try { rs = stmt.executeQuery("select * from sc,c where c.cno=sc.cno"); 
			while (rs.next()) {// 找出表中的记录数赋给i 
				al.add(rs.getString("sno")); 
				al.add(rs.getString("cn")); 
				al.add(rs.getString("g")); 
				i++; 
			} 
			} catch (SQLException e) { 
				e.printStackTrace(); 
				} 
			playerInfo = new Object[i][3]; 
			String[] columnNames = { "学号", "课程名", "成绩" }; 
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
			sTable = new JTable(playerInfo, columnNames);// 创建网格
			p1.add(sTable); 
			scroll = new JScrollPane(sTable); 
			this.add(scroll); }  


public void connDB() { // 连接数据库
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


public void closeDB() // 关闭连接
{ try { stmt.close(); 
		con.close(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
	} 



public void update() {// 修改某个学生的成绩信息
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			int row = -1; 
			row = sTable.getSelectedRow(); 
			if (row == -1) {// 判断要修改的信息是否被选中
					JOptionPane.showMessageDialog(null, "请选择要修改的记录！"); 
				} else { 
						int j1 = 0; 
						try { 
							if (!bstd) {//判断选择的是不是查询后的结果
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
							gadd.setTitle("修改成绩"); 
							gadd.tcno.setText(kch); 
							gadd.tcname.setText(kcm); 
							gadd.tpcno.setText(xxkch); 
							gadd.tcno.setEnabled(false); 
							gadd.tcname.setEnabled(false); 

							this.dispose(); 
				} 
	} 



public void select() {// 显示某个学生的成绩查询结果
			int j = 0; 
			ii = gst.i; 
			mxh = gst.gxh; 
			playerInfo = new Object[ii][3]; 
			String[] columnNames = { "学号", "课程名", "成绩" }; 
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
				JOptionPane.showMessageDialog(null, "学号不存在！"); 
				new GM("成绩信息管理").display(); 
			} else { 
				sTable = new JTable(playerInfo, columnNames); 
				p1.add(sTable); 
				scroll = new JScrollPane(sTable); 
				this.add(scroll); 
			} 
	} 


public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand() == "修改成绩") { 
				this.update(); 
				} 
			if (e.getActionCommand() == "查询") { 
				gst = new GSelect(); 
				this.dispose(); 
				} 
			if (e.getActionCommand() == "显示") { 
					this.dispose(); 
					new GM("成绩信息管理").display(); 
			} 
	} 
} 
























