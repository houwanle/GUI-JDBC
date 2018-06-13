package shixun2;

import javax.swing.*; 
import java.awt.*;  
import java.awt.event.*; 
import java.sql.*; 

class DLFrame extends JFrame implements ActionListener, ItemListener {// 登录界面
			JPanel p1 = null; 
			JPanel p2 = null; 
			JPanel p3 = null;  
			JLabel userName = new JLabel("用户："); 
			JTextField txtUser = new JTextField(); 
			JLabel password = new JLabel("密码：");  
			JPasswordField txtPwd = new JPasswordField(6); 
			JLabel role = new JLabel("角色："); 
			JComboBox cbrole = new JComboBox(); 
			JButton btnLogin = new JButton("登录"); 
			JButton btncz = new JButton("重置"); 
			JButton btnCancel = new JButton("取消"); 
			JLabel imageLabel; 
			Icon image;  
			static int OK = 1;  
			static int CANCEL = 0;  
			int actionCode = 0; 
			Connection con = null; 
			Statement stmt = null; 
			ResultSet rs = null; 
			int qxian = 0; 
			
			
@SuppressWarnings("deprecation")
public DLFrame() {// 构造方法
			super("登录界面"); 
			p1 = new JPanel(); 
			p2 = new JPanel(); 
			p3 = new JPanel(); 
			cbrole.addItem("管理员"); 
			cbrole.addItem("学生"); 
			image = new ImageIcon("1.jpg"); 
			imageLabel = new JLabel(image); 
			p1.add(imageLabel); 
			this.setLayout(new FlowLayout()); 
			this.setBounds(100, 100, 246, 200); 
			p2.setLayout(new GridLayout(4, 2)); 
			p2.add(userName); 
			p2.add(txtUser); 
			p2.add(password); 
			p2.add(txtPwd); 
			p2.add(role); 
			p2.add(cbrole); 
			p3.add(btnLogin); 
			p3.add(btncz); 
			p3.add(btnCancel); 
			this.add(p1); 
			this.add(p2); 
			this.add(p3); 
			this.setResizable(false); 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			this.show(); 
			btnLogin.addActionListener(this); 
			cbrole.addItemListener(this); 
			btncz.addActionListener(this); 
			btnCancel.addActionListener(this); 
			setLocationRelativeTo(null);
	} 


public void connDB() { // 连接数据库
	try { 
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		} catch (ClassNotFoundException e) {  
			e.printStackTrace(); 
		} 
	try { 
		con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=student", "sa", "123"); 
		stmt = con.createStatement(); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
	} 

  
 public void closeDB() // 关闭连接
 { 			try { 
	 			stmt.close(); 
	 			con.close(); 
 			} catch (SQLException e) { 
 				e.printStackTrace(); 
 				} 

 } 

  

public void itemStateChanged(ItemEvent e) { 
			if (e.getStateChange() == ItemEvent.SELECTED) { 
				JComboBox jcb = (JComboBox) e.getSource(); 
				qxian = jcb.getSelectedIndex(); 
			} 
	} 

  

@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e) { 
			Object source = e.getSource(); 
			String un = null; 
			String pw = null; 
			boolean success = false;// 用于判断是否登录成功
			if (source == btnLogin) { 
				if (txtUser.getText().equals("") || txtPwd.getText().equals("")) {// 判断是否输入了用户名和密码
					JOptionPane.showMessageDialog(null, "登录名和密码不能为空！"); 
				} else { 
					this.connDB(); 
					try { 
						rs = stmt.executeQuery("select * from unpw where qx=" + qxian); 
						while (rs.next()) {  
							un = rs.getString("un").trim(); 
							pw = rs.getString("pw").trim(); 
							if (txtUser.getText().equals(un)) { 
								if (txtPwd.getText().equals(pw)) { 
									actionCode = OK; 
									this.setVisible(false); 
									if (qxian == 0) { 
										new ManagerFrane();//进入管理员界面
									} 
									if (qxian == 1) { 
										new StudentFrame();// 进入学生界面
									} success = true; 
									break; 
								} else { 
									JOptionPane.showMessageDialog(null, "密码错误！"); 
									txtPwd.setText(""); 
									success = true; 
								} 
							} 
						} if (!success) { 
							JOptionPane.showMessageDialog(null, "登录名错误！"); 
							txtUser.setText(""); 
							txtPwd.setText(""); 
						}  
					} catch (SQLException e1) { 
						e1.printStackTrace(); 
					} 
				}  
			} else if (source == btncz) { 
				txtUser.setText(""); 
				txtPwd.setText("");  
			} else if (source == btnCancel) { 
				System.exit(0); 
			}  
			} 
	} 

 



