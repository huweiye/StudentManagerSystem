package studentAndteachermanage_operate;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBCOperate_stuteamanager;


public class Grade extends JFrame {
	int length;
	String [][] tableValues ;
	String []columnNames;
	DefaultTableModel jt_def;//���ģ��
	JTable jt;
	JScrollPane jp_table;
	JButton jb;
	ResultSet resss;
	JDBCOperate_stuteamanager jdbccc;
	Grade(int l,ResultSet res,JDBCOperate_stuteamanager jdbc){
		length=l;
		resss=res;
		jdbccc=jdbc;
		  columnNames=new String []{"ѧ�����","�γ̱��","�ɼ�¼��"};
		 tableValues =new String[length][3];//�������������������Ӧ�ر���С
		try {/**��ȡ���ݿ����ݽ����**/
			resss.first();
			for(int i=0;i<length;i++) {
					tableValues[i][0]=res.getString(1);//��ȡ�������
					resss.next();
			}
			resss.first();
			for(int i=0;i<length;i++) {
				tableValues[i][1]=res.getString(2);//��ȡ�������
				System.out.println(res.getString(2));
				resss.next();
		}
			resss.first();
			for(int i=0;i<length;i++) {
				tableValues[i][2]=null;
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jt_def=new DefaultTableModel(tableValues, columnNames);
		 jt=new JTable(jt_def);
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
			jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jt.setRowHeight(20);//�����и�
			jp_table=new JScrollPane(jt);
			jb=new JButton("�ύ");
			Container cp=this.getContentPane();
			cp.setLayout(new BorderLayout());//��񲼾�
			cp.add(jp_table, BorderLayout.CENTER);
			JPanel jppp=new JPanel();
			jppp.add(jb);
			cp.add(jppp, BorderLayout.SOUTH);
			jb.addActionListener(new MyActionListener());
			this.addWindowListener(new Mywindow());
			this.setVisible(true);
			this.setBounds(300,200,800,500);
}
	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<length;i++) {
				tableValues[i][2]=(String) jt_def.getValueAt(i, 2);
				System.out.println(tableValues[i][2]+"jjjj");
		}
			jdbccc.set_grade(tableValues,length);
			
			
			
			// TODO Auto-generated method stub
			
		}
		
	}
	class Mywindow extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			dispose();
		}
		
	}
	
}
