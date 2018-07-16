/**写在最前面的注释，Teacher类与Student类有很多代码重复的地方，本来可以封装继承减少代码量，但我最近太累了啊，还是直接复制粘贴吧**/
/**重复造轮子，虽然很不好，但是很舒坦，嘿嘿嘿**/
package teacher;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import jdbc.JDBCOperate;
import jdbc.JDBCOperate_Teacher;
import student.MyTable;
public class Teacher {
	JFrame teacher;
	JPanel jp;
	JTabbedPane jtabbedpane;
	String teachernumber;
	JFrame jf;
	JDBCOperate_Teacher jdbcoperate;
	public Teacher(String teacher_number,JFrame jf_login){
		jf=jf_login;
		teachernumber=teacher_number;
		jdbcoperate=new JDBCOperate_Teacher();
		teacher=new JFrame("教师");
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
		Container cp=teacher.getContentPane();
		cp.setLayout(new BorderLayout());//表格布局
		cp.add(jp,BorderLayout.NORTH);
		cp.add(jtabbedpane, BorderLayout.CENTER);
		teacher.addWindowListener(new Mywindow());
		teacher.setVisible(true);
		teacher.setBounds(300,200,800,700);
	}
	public void createjp() throws SQLException {//获取当前教师基本信息，显示在界面上方
		ResultSet res=jdbcoperate.getinformation("teacher_information", teachernumber);
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
		JLabel jl[]=new JLabel[] {new JLabel(res.getString("teachernumber")),new JLabel(res.getString("teachername")),new JLabel(res.getString("teachersex")),
				new JLabel(res.getString("teachersedpt"))		};//当前教师职工号，姓名，性别，院系四个标签显示在上方面板上
	for(int i=0;i<jl.length;i++) {
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jp.add(jl[i]);
	}
	}
	public void createjtabbedpane() {//创建选项卡面板，从createjtab（）处获取加载对应的面板
		JPanel jpx=new JPanel();
		jpx.setLayout(new BorderLayout(15, 15));
		JLabel jlx=new JLabel("双击查看上该课堂的学生信息");
		jlx.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		jpx.add(jlx,BorderLayout.NORTH);
		jpx.add(createjtab("授课任务查询"), BorderLayout.CENTER);
		jtabbedpane.addTab("授课任务查询", jpx);
		jtabbedpane.addTab("监考任务查询", createjtab("监考任务查询"));
		jtabbedpane.addTab("自然班学生信息", createjtab("自然班学生信息"));
	}
	public JComponent createjtab(String tab) { //根据选项卡索引值不同创建对应的面板
		JScrollPane jtabpanel_my=new JScrollPane();
		if(tab.equals("授课任务查询")) {
			try {
				String [] columnNames={"课堂编号","课程名称","授课时间","授课地点"};//授课信息
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("监考任务查询")) {
			try {//examination表中指定ex_teachernumber的信息
				String [] columnNames={"考试编号","监考课程名称","监考时间","监考地点","考试时长"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("自然班学生信息")) {
			try {//输出指定studentclass的全部学生信息
				String [] columnNames={"学生编号","学生姓名","学生性别","学生籍贯","出生日期","班级"};
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
		if(columnNames[0].equals("课堂编号")) {
			res=jdbcoperate.getteachcourse(teachernumber);
			}
		else if(columnNames[0].equals("考试编号")) {
			res=jdbcoperate.getteachexam(teachernumber);
		}
		else if(columnNames[0].equals("学生编号")){
			res=jdbcoperate.getmyclass(teachernumber);
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
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//自动调整列宽
		jt.setRowHeight(20);//设置行高
		jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		jsp=new JScrollPane(jt);
		if(columnNames[0].equals("课堂编号")) {
			jt.addMouseListener(new MouseAdapter(){//双击查看课堂信息
			    public void mouseClicked(MouseEvent e) {
			       if(e.getClickCount()==2){//点击几次，这里是双击事件
			    	        int row=jt.getSelectedRow();	
			    	        String value_teachcourse=(String) jt.getValueAt(row, 0);
			       System.out.println(value_teachcourse);
			       ResultSet r=jdbcoperate.getteach_course_student(teachernumber, value_teachcourse);
			       int l = 0;
				try {
					l = jdbcoperate.length_of_res(r);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			       new Teach_course_student(l, r);
			 
			       }
			    }
			   });
		}
		return jsp;
	}
 class Mywindow extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			jdbcoperate.closeJDBCOperate();
			teacher.dispose();
			jf.setVisible(true);
		}
		
	}
	
}

