package student;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import jdbc.JDBCOperate;
import teacher.Teach_course_student;

public class Student {
	JFrame student;
	JPanel jp;
	JTabbedPane jtabbedpane;
	String studentnumber;
	JFrame jf;
	JDBCOperate jdbcoperate;
 String value_doubleclick=null;
	JTextField jtx;
	public Student(String student_number,JFrame jf_login){
		jf=jf_login;
		studentnumber=student_number;
		jdbcoperate=new JDBCOperate();
		student=new JFrame("学生");
		jp=new JPanel();
		jp.setLayout(new FlowLayout(15));
		try {
			createjp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jtabbedpane=new JTabbedPane(JTabbedPane.LEFT);
		createjtabbedpane();
		jtabbedpane.setSelectedIndex(0);
		Container cp=student.getContentPane();
		cp.setLayout(new BorderLayout());//表格布局
		cp.add(jp,BorderLayout.NORTH);
		cp.add(jtabbedpane, BorderLayout.CENTER);
		student.addWindowListener(new Mywindow());
		student.setVisible(true);
		student.setBounds(300,200,800,700);
	}
	public void createjp() throws SQLException {//获取当前学生基本信息，显示在界面上方
		ResultSet res=jdbcoperate.getinformation("student_information", studentnumber);
		try {
			res.first();
			if(res.isLast()) {
				
			}else {
				System.out.println("查询出错");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel jl[]=new JLabel[] {new JLabel(res.getString("studentnumber")),new JLabel(res.getString("studentname")),new JLabel(res.getString("studentsex")),
				new JLabel(res.getString("studentclass"))		};//当前学生学号，姓名，性别，专业班级四个标签显示在上方面板上
	for(int i=0;i<jl.length;i++) {
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jp.add(jl[i]);
	}
	}
	public void createjtabbedpane() {//创建选项卡面板，从createjtab（）处获取加载对应的面板
		JPanel jpx=new JPanel();
		jpx.setLayout(new BorderLayout(15, 15));
		JLabel jlx=new JLabel("在此处输入课堂编号或者双击元组进行选课");
		 jtx=new JTextField(15);
		JButton jbx=new JButton("提交");
		jbx.addActionListener(new MyJButtonAction());//外部命名类来监听按钮
		JPanel jpx1=new JPanel();
		jpx1.setLayout(new FlowLayout(15));
		jpx1.add(jlx);
		jpx1.add(jtx);
		jpx1.add(jbx);
		jpx.add(jpx1,BorderLayout.NORTH);
		jpx.add(createjtab("选定课程"), BorderLayout.CENTER);
		jtabbedpane.addTab("课程成绩", createjtab("课程成绩"));
		jtabbedpane.addTab("考试查询", createjtab("考试查询"));
		jtabbedpane.addTab("选定课程", jpx);
		jtabbedpane.addTab("课程表", createjtab("课程表"));
	}//如果你看到这条注释，证明你在抄我的代码，略略略
	public JComponent createjtab(String tab) { //根据选项卡索引值不同创建对应的面板
		JScrollPane jtabpanel_my=new JScrollPane();
		if(tab.equals("课程成绩")) {
			try {
				String [] columnNames={"课程编号","课程名称","学分","成绩"};//成绩单对应列
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("课程表")) {
			try {
				String [] columnNames={"课堂号","课程名称","任课教师","上课时间","上课地点"};
				//课程表对应列：课程编号，课程名称，任课教师，上课时间，上课地点
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("考试查询")) {
			try {//考试查询对应着：考试编号，考试课程名字，监考老师，考试时间，考试地点,考试时长
				String [] columnNames={"考试编号","考试课程名字","监考老师","考试时间","考试地点","考试时长"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("选定课程")) {
			try {//选定课程用的表格把各个课堂表`teachcourse`的各个课堂的属性值都列出来就行，列出来的课堂都是能选的，
				//但是选的课堂对应的课程只能是当前学生还没有成绩的，已经有成绩的课堂不能选
				String [] columnNames={"课堂编号","课程名字","任课老师","上课时间","上课地点"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("出错");
		}
		
		return jtabpanel_my;//把面板返回过去让标签项加载即可
	}
	/**抽象的构造表格的方法，只需要传进去有关列的信息即可**/
	public JScrollPane createpanel_of_student(String [] columnNames) throws SQLException {//创造当前学生表格面板
		JScrollPane jsp;
		ResultSet res = null;
		if(columnNames[0].equals("课程编号")) {
			res=jdbcoperate.getgrade(studentnumber);
			}
		else if(columnNames[0].equals("课堂号")) {
			res=jdbcoperate.getclasstable(studentnumber);
		}
		else if(columnNames[0].equals("考试编号")){
			res=jdbcoperate.getexamination(studentnumber);
		}else if(columnNames[0].equals("课堂编号")){
			res=jdbcoperate.getallteachcourse();
		}
		else {
			System.out.println("调用jdbc出错");
		}
		
		int length = jdbcoperate.length_of_res(res);//获取表格数据行数
		String [][] tableValues =new String[length][columnNames.length];//根据行数和列数定义对应地表格大小
		try {/**读取数据库数据进表格**/
			res.first();
			for(int i=0;i<length;) {
				for(int j=0;j<columnNames.length;j++) {
					tableValues[i][j]=res.getString(j+1);//读取表格数据
				}
				i++;
				res.next();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyTable jt=new MyTable(tableValues, columnNames);//使用自定义的表格
		if(columnNames[0].equals("课堂编号")) {
			jt.addMouseListener(new MouseAdapter(){//双击查看课堂信息
			    public void mouseClicked(MouseEvent e) {
			       if(e.getClickCount()==2){//点击几次，这里是双击事件
			    	        int row=jt.getSelectedRow();	
			    	        String value_teachcourse=(String) jt.getValueAt(row, 0);
			       value_doubleclick=value_teachcourse;
			       System.out.println(value_doubleclick);
			       }
			    }
			   });
		}
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//自动调整列宽
		jt.setRowHeight(20);//设置行高
		jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jsp=new JScrollPane(jt);
		return jsp;
	}
	class MyJButtonAction implements ActionListener{
		
		public MyJButtonAction(){
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String teachcourse=jtx.getText();
			if(teachcourse.equals("")) {
				if(value_doubleclick==null) {
				JOptionPane.showMessageDialog(null,"您还未输入课堂编号","错误提示",JOptionPane.ERROR_MESSAGE,null);
				return;
				}
				else {
					teachcourse=value_doubleclick;
				}
			}
			jdbcoperate.Selectcourse(studentnumber, teachcourse);
			jtabbedpane.remove(3);
			jtabbedpane.addTab("课程表", createjtab("课程表"));
			jtx.setText("");
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
 class Mywindow extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			jdbcoperate.closeJDBCOperate();
			student.dispose();
			jf.setVisible(true);
		}
		
	}
	
}
