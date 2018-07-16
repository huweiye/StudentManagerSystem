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
		String []columnNames={"ѧ�����","ѧ������","ѧ���Ա�","ѧ������","��������","ѧ��ϵ��","ѧ��רҵ","ѧ���༶"};
		String [][] tableValues =new String[length][8];//�������������������Ӧ�ر���С
		try {/**��ȡ���ݿ����ݽ����**/
			res.first();
			for(int i=0;i<length;) {
				for(int j=0;j<8;j++) {
					tableValues[i][j]=res.getString(j+1);//��ȡ�������
				}
				i++;
				res.next();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyTable jt=new MyTable(tableValues, columnNames);//ʹ���Զ���ı��
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
		jt.setRowHeight(20);//�����и�
		JScrollPane jsp=new JScrollPane(jt);
	Container cp=this.getContentPane();
	cp.setLayout(new BorderLayout());//��񲼾�
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
