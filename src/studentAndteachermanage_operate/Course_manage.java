/**课程信息和授课安排`course_information``teachcourse`**/
package studentAndteachermanage_operate;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import studentAndteachermanage_operate.StuAndTea_operation.Mywindow;

public class Course_manage {
	JFrame course_manage;
	JTabbedPane jtabbedpane;
	JFrame jf;
	JPanel in_jtabedp1;
	JPanel in_jtabedp2;
	StuAndTea_operation course_man;
	StuAndTea_operation tea_cour_man;
	public Course_manage(JFrame jf_login) {
		course_manage=new JFrame("课程管理员");
		jf=jf_login;
		jtabbedpane=new JTabbedPane(JTabbedPane.LEFT);
		 course_man=new StuAndTea_operation("course_information",jf);
		 tea_cour_man=new StuAndTea_operation("teachcourse",jf);
		in_jtabedp1=(JPanel) course_man.getvisible().getContentPane();
		in_jtabedp2=(JPanel) tea_cour_man.getvisible().getContentPane();
		jtabbedpane.addTab("课程显示",in_jtabedp1 );
		jtabbedpane.addTab("授课安排", in_jtabedp2);
		course_man.getvisible().dispose();
		tea_cour_man.getvisible().dispose();
		jtabbedpane.setSelectedIndex(0);
		Container cp=course_manage.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(jtabbedpane,BorderLayout.CENTER);
		course_manage.addWindowListener(new Mywindow());
		course_manage.setVisible(true);
		course_manage.setBounds(300,200,1250,700);
	}
	class Mywindow extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			course_man.jdbcoperate.closeJDBCOperate();
			course_manage.dispose();
			jf.setVisible(true);
		}
		
	}

}
