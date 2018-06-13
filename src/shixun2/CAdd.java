package shixun2;
import javax.swing.*; 
import java.awt.*;  
import java.awt.event.*; 
import java.sql.*; 

  

class CAdd extends JFrame implements ActionListener{// 用于课程信息管理中增加或修改某条记录的界面
		JLabel lcno = new JLabel("课程号："); 
		JLabel lcname = new JLabel("课程名："); 
		JLabel lpcno = new JLabel("先行课程号："); 
		
		JTextField tcno = new JTextField(10); 
		JTextField tcname = new JTextField(10); 
		JTextField tpcno = new JTextField(10); 

		JButton btnOK = new JButton("确定"); 
		JButton btnCancel = new JButton("取消"); 

		JPanel p = new JPanel(); 

		Connection con = null; 

		Statement stmt = null; 

		ResultSet rs = null;  

		boolean isNewsm = true;// 用于判断是否显示课程信息管理的界面

 public CAdd() {// 构造方法
	 this.setTitle("增加"); 
	 this.setBounds(200, 200, 146, 235); 

	 p.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	 p.add(lcno); 
	 p.add(tcno); 
	 p.add(lcname); 
	 p.add(tcname); 
	 p.add(lpcno); 
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
	try { 
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		} catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		} 
	try { 
		con = DriverManager.getConnection( "jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123"); 
		stmt = con.createStatement(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		} 

  

public void closeDB(){ 
	try { 
			stmt.close(); 
			con.close(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
} 

  

 public void insertst() { // 插入记录
	 	String kch = null; 
	 	String kcm = null; 
	 	String xxkch=null; 
	 	kch = tcno.getText(); 
	 	kcm = tcname.getText(); 
	 	xxkch=tpcno.getText(); 

  
	 	if (this.getTitle() == "修改") {// 如果是修改记录，先删除再增加
	 		try { 
	 				this.connDB(); 
	 				int rs1 = stmt.executeUpdate("delete from c where cno='" + kch + "'"); 
	 			} catch (SQLException e) { 
	 				e.printStackTrace(); 
	 			} 
	 	}  
 
 
	 		String str = "insert into c values('" + kch + "','" + kcm + "','" + xxkch + "')"; 
	 			this.connDB();//连接数据库
	 			try { 
	 					stmt.executeUpdate(str); 
	 					JOptionPane.showMessageDialog(null, this.getTitle() + "成功！", "提示", JOptionPane.INFORMATION_MESSAGE, new  ImageIcon( "menu4.gif")); 

	 					this.setVisible(false); 
	 				} catch (SQLException e) { 
	 					JOptionPane.showMessageDialog(null, "课程号已存在！"); 
	 					tcno.setText(""); 
	 					} 
 			} 

  

public void actionPerformed(ActionEvent e) { 
		if (e.getActionCommand() == "确定") { 
			this.insertst(); 
			if (isNewsm) { 
				new CM("课程信息管理").display(); 
			} 
			isNewsm = true; 
		} 
		if (e.getActionCommand() == "取消") { 
			this.setVisible(false); 
			new CM("课程信息管理").display(); 
		} 
	} 
} 

