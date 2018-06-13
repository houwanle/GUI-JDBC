package shixun2;

import java.awt.Component; 
import java.awt.FlowLayout; 
import java.awt.event.*; 
import java.sql.*; 
import java.util.*; 
import javax.swing.*;  
import javax.swing.table.TableCellRenderer; 

class CM extends JFrame implements ActionListener {// 课程信息管理
		JPanel p = new JPanel();  
		JButton btnAdd = new JButton("增加"); 
		JButton btnDelete = new JButton("删除"); 
		JButton btnAlter = new JButton("修改"); 
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
		CSelect cst;  

		String mkch = null; 
		boolean bstd = false; 
		
		
CM(String title) {// 构造方法
			super(title); 
			add("South", p); 
			this.add("Center", p1); 
			mb.add(btnAdd); 
			mb.add(btnDelete); 
			mb.add(btnAlter); 
			mb.add(btnSearch); 
			mb.add(btnDisplay); 

			this.connDB();// 连接数据库
			this.setBounds(200, 200, 400, 260); 
			btnAdd.addActionListener(this); 
			btnDelete.addActionListener(this); 
			btnAlter.addActionListener(this); 
			btnSearch.addActionListener(this); 
			btnDisplay.addActionListener(this); 
			this.setJMenuBar(mb);  
			// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.setResizable(false); 

			show(); 
} 


CM(CSelect cst, String title) {// 构造方法
			super(title); 
			this.cst = cst; 
			bstd = true; 
			add("South", p); 
			this.add("Center", p1); 
			mb.add(btnAdd); 
			mb.add(btnDelete); 
			mb.add(btnAlter); 
			mb.add(btnSearch); 
			mb.add(btnDisplay); 
			
			this.connDB(); 
			this.setBounds(200, 200, 400, 260); 

			btnAdd.addActionListener(this); 
			btnDelete.addActionListener(this); 
			btnAlter.addActionListener(this); 
			btnSearch.addActionListener(this); 
			btnDisplay.addActionListener(this); 

			this.setJMenuBar(mb); 
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.setResizable(false); 
			show(); 
} 


public void display() {// 显示所有的课程信息
			int i = 0; 
			int j = 0; 
			int k = 0; 
			List al = new ArrayList(); 
			try { rs = stmt.executeQuery("select * from c"); 
			while (rs.next()) {// 找出表中的记录数赋给i 
				al.add(rs.getString("cno")); 
				al.add(rs.getString("cn")); 
				al.add(rs.getString("pcno")); 
				i++; 
				}  
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
			playerInfo = new Object[i][3]; 
			String[] columnNames = { "课程号", "课程名", "先行课程号" }; 
			try { rs = stmt.executeQuery("select * from c order by cno"); 
				while (rs.next()) { 
					playerInfo[j][0] = rs.getString("cno"); 
					playerInfo[j][1] = rs.getString("cn"); 
					playerInfo[j][2] = rs.getString("pcno"); 
					j++; 
				} 
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
			sTable = new JTable(playerInfo, columnNames);// 创建网格
			p1.add(sTable); 
			scroll = new JScrollPane(sTable); 
			this.add(scroll); 
			} 

 
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


public void closeDB() { 
			try { 
				stmt.close();  
				con.close(); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
} 




public void delete() {// 删除某个课程信息
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			int row = -1; 
			row = sTable.getSelectedRow(); 
			if (row == -1) {// 判断要删除的信息是否被选中
				JOptionPane.showMessageDialog(null, "请选择要删除的记录！"); 
							} else { 
								if (!bstd) {// 判断选择的是不是查询后的结果
									int j1 = 0; 
									try { 
											rs = stmt.executeQuery("select * from c"); 
											while (rs.next() && j1 <= row) {// 找出当前被选中的记录在数据库中的对应
												kch = rs.getString("cno"); 
												kcm = rs.getString("cn"); 
												xxkch = rs.getString("pcno"); 
												j1++; 
												} 
										} catch (SQLException e) { 
											e.printStackTrace(); 
										} 
									int i1 = 0; 
									try { 
											int rs1 = stmt.executeUpdate("delete from c where cno='" + kch + "'");// 删除数据库中当前被选中的记录
											JOptionPane.showMessageDialog(null, "记录删除成功！"); 
											this.dispose(); 
											new CM("课程信息管理").display(); 
										} catch (SQLException e) { 
											e.printStackTrace(); 
										} 
								} else { 
									try { 
											int rs1 = stmt.executeUpdate("delete from c where cno='"  + mkch + "'");// 删除数据库中当前被选中的记录
											JOptionPane.showMessageDialog(null, "记录删除成功！"); 
											this.dispose(); 
											new CM("课程信息管理").display(); 
									} catch (SQLException e) { 
										e.printStackTrace(); 
									} 
								} 
							} 
	} 


public void update() {// 修改某个课程记录
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			int row = -1; 
			row = sTable.getSelectedRow(); 
			if (row == -1) { 
				JOptionPane.showMessageDialog(null, "请选择要修改的记录！"); 
			} else { 
				int j1 = 0; 
				try { if (!bstd) {// 判断选择的是不是查询后的结果
					rs = stmt.executeQuery("select * from c"); 
				} else { 
					rs = stmt.executeQuery("select * from c where cno='" + mkch + "'"); 
				} 
				while (rs.next() && j1 <= row) {// 找出当前被选中的记录在数据库中的对应
					kch = rs.getString("cno"); 
					kcm = rs.getString("cn"); 
					xxkch = rs.getString("pcno"); 
					j1++; 
				} 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
				CAdd cadd = new CAdd(); 
				cadd.setTitle("修改"); 
				cadd.tcno.setText(kch); 
				cadd.tcname.setText(kcm);  
				cadd.tpcno.setText(xxkch); 
				cadd.tcno.setEnabled(false); 
				this.dispose(); } 
	} 



public void select() {// 显示某个查询的结果
			mkch = cst.kch; 
			playerInfo = new Object[1][3]; 
			String[] columnNames = { "课程号", "课程名", "先行课程号" }; 
			try { 
				rs = stmt.executeQuery("select * from c where cno='" + mkch + "'"); 
				while (rs.next()) { 
					playerInfo[0][0] = rs.getString("cno"); 
					playerInfo[0][1] = rs.getString("cn"); 
					playerInfo[0][2] = rs.getString("pcno"); 
				} 
			} catch (SQLException e) { 
				e.printStackTrace(); 
			} 
			if (playerInfo[0][1] == null) { 
				this.dispose(); 
				JOptionPane.showMessageDialog(null, "课程号不存在！"); 
				new CM("课程信息管理").display(); 
			} else { 
					sTable = new JTable(playerInfo, columnNames);// 创建网格
					p1.add(sTable); 
					scroll = new JScrollPane(sTable); 
					this.add(scroll); 
			} 
	} 

 

public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand() == "增加") { new CAdd(); 
				this.dispose(); 
			} 
			if (e.getActionCommand() == "删除") { 
				this.delete(); 
			} 
			if (e.getActionCommand() == "修改") { 
				this.update();  
			} 
			if (e.getActionCommand() == "查询") { 
				cst = new CSelect(); 
				this.dispose(); 
			} 
			if (e.getActionCommand() == "显示") { 
				this.dispose(); 
				new CM("课程信息管理").display(); } } 
} 












