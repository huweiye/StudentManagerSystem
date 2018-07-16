package teacher;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import jdbc.JDBCOperate_Teacher;
import student.MyTable;
import teacher.Teacher.Mywindow;

public class Teach_course_student extends JFrame {
	int length;
	
	Teach_course_student(int l,ResultSet res){
		length=l;
		String []columnNames={"学生编号","学生姓名","学生性别","学生籍贯","出生日期","学生系别","学生专业","学生班级"};
		String [][] tableValues =new String[length][8];//根据行数和列数定义对应地表格大小
		try {/**读取数据库数据进表格**/
			res.first();
			for(int i=0;i<length;) {
				for(int j=0;j<8;j++) {
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
		JScrollPane jsp=new JScrollPane(jt);
	Container cp=this.getContentPane();
	cp.setLayout(new BorderLayout());//表格布局
	cp.add(jsp, BorderLayout.CENTER);
	this.addWindowListener(new Mywindow());
	this.setVisible(true);
	this.setBounds(300,200,800,200);
	}
	
	
	
	class Mywindow extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			dispose();
		}
		
	}
}
