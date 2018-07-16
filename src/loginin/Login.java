
package loginin;
import loginin.Background;
import student.Student;
import studentAndteachermanage_operate.Course_manage;
import studentAndteachermanage_operate.Exmination_manage;
import studentAndteachermanage_operate.StuAndTea_operation;
import teacher.Teacher;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import jdbc.JDBCOperate;
public class Login{//登录界面
	JFrame jf_login;//登录窗口
	Background back;
	JLabel jlb1=new JLabel("教务管理系统",SwingConstants.CENTER),jlb2=new JLabel("用户名（U）"),
			jlb3=new JLabel("密 码（P）");
	JPanel jpl1,jpl2,jpl3,jpl4;
	ButtonGroup bg1;//登录方式单选钮按钮组
	JRadioButton jrb[]=new JRadioButton[]{new JRadioButton("学生登录"),new JRadioButton("教师登录",true),new JRadioButton("学籍信息管理员"),
			new JRadioButton("教职工信息管理员"),new JRadioButton("课程管理员"),new JRadioButton("考务管理员"),new JRadioButton("超级管理员")};
	/**学生登陆可查询已结课的（已经录入完成绩）课程成绩名次；正在上课的（选完课但尚未录入成绩）任课教师上课时间地点，考试地点，监考老师；还没有上的课程（也就是需要选课的）有哪些**/
	/**学生登陆可选课：选定课程和任课教师**/
	/**教师登陆可查询自己授课任务：即教哪些课，分别的上课地点，时间和课堂学生；查询监考任务，即监考时间和地点**/
	/**学籍信息和教职工信息管理员增删改学生和教师的基本信息，如改学生的专业出生年月，增删新入学学生和入职教师，删毕业学生和离职教师**/
	/**课程管理员安排教师授课任务，即安排每个老师上哪些课以及对应的上课时间地点；安排每个专业的教学计划，即应该上哪些课**/
	/**考务管理员安排考试信息，并录入每门考试的学生考试成绩**/
	JTextField jtf1=new JTextField(30);
	JPasswordField jpf1=new JPasswordField(30);//登录名，密码
	JButton jb2=new JButton("登录"),jb3=new JButton("退出");//登录按钮
	Login(){
		jf_login=new JFrame("登录界面/你伟哥专属程序");
		//设置背景图片
		back=new Background(jf_login);
		jf_login.setContentPane(back);
		Container cp=jf_login.getContentPane();
		cp.setLayout(new GridLayout(6,0));//表格布局
		jlb1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		cp.add(jlb1);
		jpl1=new JPanel();
		bg1=new ButtonGroup();
		for(int i=0;i<jrb.length;i++) {
			jrb[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			jrb[i].setOpaque(false);
			bg1.add(jrb[i]);
			jpl1.add(jrb[i]);
		}		
		cp.add(jpl1);
		jlb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jlb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jpf1.setEchoChar('*');
		jpl2=new JPanel();jpl2.add(jlb2);jpl2.add(jtf1);cp.add(jpl2);
		jpl3=new JPanel();jpl3.add(jlb3);jpl3.add(jpf1);cp.add(jpl3);
		jpl4=new JPanel();
		jpl4.setLayout(new FlowLayout(1,20,5));
		jb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jpl4.add(jb2);jpl4.add(jb3);cp.add(jpl4);
		jb2.addActionListener(new LoginListener());//登录按钮事件监听器
		jb3.addActionListener(new LoginListener());	
		jpl1.setOpaque(false);
		jpl2.setOpaque(false);		
		jpl3.setOpaque(false);		
		jpl4.setOpaque(false);
		jf_login.setBounds(500, 200, 700,600);
		jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf_login.setVisible(true);
	}
	
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JDBCOperate jdbcoperate=new JDBCOperate();
			String s=e.getActionCommand();
			switch(s) {
			case "登录":
				String s1=jtf1.getText(),s2=new String(jpf1.getPassword());
					for(int i=0;i<jrb.length;i++) {
						if(jrb[i].isSelected()) {//判断选择何种登录方式
							switch(i){
							case 0://学生登陆
								if(jdbcoperate.isloginin("student_information",s1,s2)) {
								new Student(s1,jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 1://教师登陆
								if(jdbcoperate.isloginin("teacher_information",s1,s2)){
									new Teacher(s1,jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 2://学籍管理员登陆
								if(jdbcoperate.is_loginaccount("sa",s1,s2)) {
								new StuAndTea_operation("student_information",jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 3://教师信息管理员登陆
								if(jdbcoperate.is_loginaccount("ta",s1,s2)) {
								new StuAndTea_operation("teacher_information",jf_login);								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 4://教学计划课程管理员登陆
								if(jdbcoperate.is_loginaccount("co",s1,s2)) {
									Course_manage course=new Course_manage(jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 5://考务管理员登陆
								if(jdbcoperate.is_loginaccount("ex",s1,s2)) {
									new Exmination_manage(jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 6://超级管理员登陆
								if(jdbcoperate.is_loginaccount("su",s1,s2)) {
									new StuAndTea_operation("account",jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//隐藏登录窗口
								}else {
									JOptionPane.showMessageDialog(null,"用户名或密码错误","错误提示",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							}
						}
					}break;
			
			case "退出":
				System.exit(0);
				break;
			}
		}
	}
	/**不应该按功能分包，
	应该是界面一个包，操作数据库一个包，这样的代码利用率更高，
	本projuct有很多代码重复的地方，很难受
	**/
	
	public static void main(String args[]) {
		new Login();
	}
}
