package shixun2;

import javax.swing.*; 
import java.awt.*;  
import java.awt.event.*; 
import java.sql.*; 




class GAdd extends JFrame implements ActionListener {// 用于成绩信息管理中修改成绩的界面
			JLabel lcno = new JLabel("学号：");  
			JLabel lcname = new JLabel("课程名："); 
			JLabel lg = new JLabel("成绩："); 
			JTextField tcno = new JTextField(10); 
			JTextField tcname = new JTextField(10); 
			JTextField tpcno = new JTextField(10); 
			JButton btnOK = new JButton("确定"); 
			JButton btnCancel = new JButton("取消"); 
			JPanel p = new JPanel(); 
			Connection con = null; 
			Statement stmt = null; 
			ResultSet rs = null;  
			boolean isNewsm = true;// 用于判断是否显示成绩信息管理的界面

 

public GAdd() {// 构造方法
			this.setTitle("增加"); this.setBounds(200, 200, 146, 235); 
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

  

public void connDB() { // 连接数据库
			try { Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
				} catch (ClassNotFoundException e) { 
					e.printStackTrace(); 
				} 
			try { con = DriverManager.getConnection( "jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123"); 
					stmt = con.createStatement(); 
				} catch (SQLException e) { 
					e.printStackTrace(); } 
	} 

  

public void closeDB() // 关闭连接
 { try { stmt.close(); 
 		con.close(); 
 		} catch (SQLException e) { 
 			e.printStackTrace(); 
 		} 
 } 

  

public void insertst() { // 修改成绩
			String kch = null; 
			String kcm = null; 
			String xxkch = null; 
			kch = tcno.getText(); 
			kcm = tcname.getText(); 
			xxkch = tpcno.getText(); 
			String gcno = null; 

			if (this.getTitle() == "修改成绩") { 
				try { this.connDB(); 
						rs = stmt.executeQuery("select cno from c where cn='" + kcm + "'"); 
						while (rs.next()) { 
							gcno = rs.getString("cno");// 找出哪门课程的成绩要被修改
						} 
						System.out.println(gcno); 
						stmt.executeUpdate("update sc set g='" + xxkch  + "' where sno='" + kch + "' and cno='" + gcno + "'"); 
					} catch (SQLException e) { 
								e.printStackTrace(); 
					} 
			} 
	} 

  

public void actionPerformed(ActionEvent e) { 
			if (e.getActionCommand() == "确定") { 
					this.insertst(); 
					if (isNewsm) { 
						new GM("成绩信息管理").display(); 
						this.dispose(); 
					} 
					isNewsm = true; 
			} 
			if (e.getActionCommand() == "取消") { 
				this.setVisible(false); 
				new GM("成绩信息管理").display(); 
			} 
	} 
	} 
