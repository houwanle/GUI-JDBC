package shixun2;
import javax.swing.*; 
import java.awt.*;  
import java.awt.event.*; 
import java.sql.*; 

  

class CAdd extends JFrame implements ActionListener{// ���ڿγ���Ϣ���������ӻ��޸�ĳ����¼�Ľ���
		JLabel lcno = new JLabel("�γ̺ţ�"); 
		JLabel lcname = new JLabel("�γ�����"); 
		JLabel lpcno = new JLabel("���пγ̺ţ�"); 
		
		JTextField tcno = new JTextField(10); 
		JTextField tcname = new JTextField(10); 
		JTextField tpcno = new JTextField(10); 

		JButton btnOK = new JButton("ȷ��"); 
		JButton btnCancel = new JButton("ȡ��"); 

		JPanel p = new JPanel(); 

		Connection con = null; 

		Statement stmt = null; 

		ResultSet rs = null;  

		boolean isNewsm = true;// �����ж��Ƿ���ʾ�γ���Ϣ����Ľ���

 public CAdd() {// ���췽��
	 this.setTitle("����"); 
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

  
public void connDB() { // �������ݿ�
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

  

 public void insertst() { // �����¼
	 	String kch = null; 
	 	String kcm = null; 
	 	String xxkch=null; 
	 	kch = tcno.getText(); 
	 	kcm = tcname.getText(); 
	 	xxkch=tpcno.getText(); 

  
	 	if (this.getTitle() == "�޸�") {// ������޸ļ�¼����ɾ��������
	 		try { 
	 				this.connDB(); 
	 				int rs1 = stmt.executeUpdate("delete from c where cno='" + kch + "'"); 
	 			} catch (SQLException e) { 
	 				e.printStackTrace(); 
	 			} 
	 	}  
 
 
	 		String str = "insert into c values('" + kch + "','" + kcm + "','" + xxkch + "')"; 
	 			this.connDB();//�������ݿ�
	 			try { 
	 					stmt.executeUpdate(str); 
	 					JOptionPane.showMessageDialog(null, this.getTitle() + "�ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE, new  ImageIcon( "menu4.gif")); 

	 					this.setVisible(false); 
	 				} catch (SQLException e) { 
	 					JOptionPane.showMessageDialog(null, "�γ̺��Ѵ��ڣ�"); 
	 					tcno.setText(""); 
	 					} 
 			} 

  

public void actionPerformed(ActionEvent e) { 
		if (e.getActionCommand() == "ȷ��") { 
			this.insertst(); 
			if (isNewsm) { 
				new CM("�γ���Ϣ����").display(); 
			} 
			isNewsm = true; 
		} 
		if (e.getActionCommand() == "ȡ��") { 
			this.setVisible(false); 
			new CM("�γ���Ϣ����").display(); 
		} 
	} 
} 

