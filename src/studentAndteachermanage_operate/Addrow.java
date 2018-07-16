package studentAndteachermanage_operate;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jdbc.JDBCOperate_stuteamanager;

public class Addrow {
	JFrame addjframe;
	JLabel[] jl;
	JTextField[] jtf;
	int num_of_cloum=0;
	String [] value_youset;
	JButton jb_doit;
	String ss;
	JDBCOperate_stuteamanager jdbc_add;
	String whosee_table;
	String id;
	Addrow(String Attributes[],String ss,JDBCOperate_stuteamanager jdbc_add,String whosee_table,String [] value_from_datbase,String number) {
		this( Attributes, ss, jdbc_add,whosee_table);
		for(int jj=0;jj<value_from_datbase.length;jj++) {
			jtf[jj].setText(value_from_datbase[jj]);
			jtf[jj].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		}
		id=number;
	}
	public Addrow(String Attributes[],String ss,JDBCOperate_stuteamanager jdbc_add,String whosee_table) {
		this.whosee_table=whosee_table;
		this.jdbc_add=jdbc_add;
		addjframe=new JFrame(ss);
		this.ss=ss;
		Container cp=addjframe.getContentPane();
		cp.setLayout(new GridLayout(Attributes.length+1, 2));//表格布局
		num_of_cloum=Attributes.length;
		jl=new JLabel[num_of_cloum];
		jtf=new JTextField[num_of_cloum];
		value_youset=new String[num_of_cloum];
		jb_doit=new JButton("确定");
		for(int i=0;i<num_of_cloum;i++) {
			jl[i]=new JLabel(Attributes[i]);
			jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jtf[i]=new JTextField(15);
			cp.add(jl[i]);
			cp.add(jtf[i]);
		}
		if(whosee_table.equals("teachcourse")||whosee_table.equals("examination")) {
			jtf[2].setEditable(false);
			jtf[4].setEditable(false);
		}
		cp.add(jb_doit);
		jb_doit.addActionListener(new Myaction());
		addjframe.addWindowListener(new Mywindow());
		addjframe.setVisible(true);
		addjframe.setBounds(300,200,250,400);
		// TODO Auto-generated constructor stub
	}
	class Myaction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch(ss) {
			case "增加记录":
				if(whosee_table.equals("student_information")||whosee_table.equals("teacher_information")){
				if(!(jtf[2].getText().equals("男")||jtf[2].getText().equals("女")||jtf[2].getText().equals(""))) {
					JOptionPane.showMessageDialog(null,"性别只能是男或女","错误提示",JOptionPane.ERROR_MESSAGE,null);
					return;
				}}else {if(whosee_table.equals("course_information")){
					if(jtf[2].getText().equals("")) {
						JOptionPane.showMessageDialog(null,"必填项","错误提示",JOptionPane.ERROR_MESSAGE,null);
						return;
					}}
				}
				if(jtf[0].getText().equals("")) {
					JOptionPane.showMessageDialog(null,"编号为必填项","错误提示",JOptionPane.ERROR_MESSAGE,null);
					return;
				}
				
				for(int jj=0;jj<num_of_cloum;jj++) {
					value_youset[jj]=jtf[jj].getText();
				}

				jdbc_add.Increase_record(value_youset,whosee_table);
				
				break;
				
			case "修改记录":
				System.out.println("到这了，可以修改了");
				if(whosee_table.equals("student_information")||whosee_table.equals("teacher_information")){
				if(!(jtf[2].getText().equals("男")||jtf[2].getText().equals("女")||jtf[2].getText().equals(""))) {
					JOptionPane.showMessageDialog(null,"性别只能是男或女","错误提示",JOptionPane.ERROR_MESSAGE,null);
					return;
				}}else {if(whosee_table.equals("course_information")){
					if(jtf[2].getText().equals("")) {
						JOptionPane.showMessageDialog(null,"必填项","错误提示",JOptionPane.ERROR_MESSAGE,null);
						return;
					}}
				}
				
				if(jtf[0].getText().equals("")) {
					JOptionPane.showMessageDialog(null,"编号为必填项","错误提示",JOptionPane.ERROR_MESSAGE,null);
					return;
				}
				for(int jj=0;jj<num_of_cloum;jj++) {
					value_youset[jj]=jtf[jj].getText();
				}
//				for(int j=0;j<num_of_cloum;j++) {
//					if(value_youset[j].equals("")) {
//						value_youset[j]=null;
//					}
//				}
				jdbc_add.Update(value_youset,whosee_table,id);
				break;
			
			}
		}
		
	}

	class Mywindow extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
			addjframe.dispose();
			
		}
		
	}
}
