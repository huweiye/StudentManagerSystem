/**������`examination`�Ϳ��Գɼ�¼��`coursegrade`**/
package studentAndteachermanage_operate;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jdbc.JDBCOperate_stuteamanager;
import studentAndteachermanage_operate.Course_manage.Mywindow;
import teacher.Teach_course_student;

public class Exmination_manage {
	JFrame examina_manage;
	JTabbedPane jtabbedpane;
	JScrollPane jp_table;
	JFrame jf;
	JPanel in_jtabedp1;
	JPanel in_jtabedp2;
	StuAndTea_operation exmina_man;
	StuAndTea_operation exminagrade_man;
	JLabel jl_tip;
	JPanel jpx;
	String [] columnNames={"���Ա��","����ʱ��","���Եص�"};//�ڿ���Ϣ
	JDBCOperate_stuteamanager jdbcoperate;
	String [][] tableValues;
	DefaultTableModel jt_def;//���ģ��
	JTable jt;
	int l;
	ResultSet r;
	public Exmination_manage(JFrame jf_login) {
		examina_manage=new JFrame("�������Ա");
		jf=jf_login;
		jdbcoperate=new JDBCOperate_stuteamanager();
		jtabbedpane=new JTabbedPane(JTabbedPane.LEFT);
		exmina_man=new StuAndTea_operation("examination",jf);
		in_jtabedp1=(JPanel) exmina_man.getvisible().getContentPane();
		jl_tip=new JLabel("˫��¼��ѡ�п���ѧ�����Գɼ�");
		jl_tip.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		 jpx=new JPanel();
		jpx.setLayout(new BorderLayout(15, 15));
		jpx.add(jl_tip,BorderLayout.NORTH);
		try {
			createtable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jp_table=new JScrollPane(jt);
		jpx.add(jp_table, BorderLayout.CENTER);
		jt.addMouseListener(new MouseAdapter(){//˫���鿴������Ϣ
		    public void mouseClicked(MouseEvent e) {
			       if(e.getClickCount()==2){//������Σ�������˫���¼�
			    	        int row=jt.getSelectedRow();	
			    	        String value_exmina=(String) jt.getValueAt(row, 0);
			       System.out.println(value_exmina);
			       r=jdbcoperate.getexmination_studentnumber(value_exmina);
				try {
					l = jdbcoperate.length_of_res(r);
					System.out.println(l);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 new Grade(l,r,jdbcoperate);
				
			       //new Teach_course_student(l, r);
			 
			       }
			    }
			   });
		
		jtabbedpane.addTab("���԰���",in_jtabedp1 );
		jtabbedpane.addTab("�ɼ�¼��", jpx);
		exmina_man.getvisible().dispose();
		jtabbedpane.setSelectedIndex(0);
		Container cp=examina_manage.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(jtabbedpane,BorderLayout.CENTER);
		examina_manage.addWindowListener(new Mywindow());
		examina_manage.setVisible(true);
		examina_manage.setBounds(300,200,1250,700);
	}
	public  void createtable() throws SQLException {
		ResultSet res = null;
		res=jdbcoperate.exmination_information();
		int length = jdbcoperate.length_of_res(res);//��ȡ�����������
		 tableValues =new String[length][columnNames.length];//�������������������Ӧ�ر���С
			try {/**��ȡ���ݿ����ݽ����**/
				res.first();
				for(int i=0;i<length;) {
					for(int j=0;j<columnNames.length;j++) {
						tableValues[i][j]=res.getString(j+1);//��ȡ�������
					}
					i++;
					res.next();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Exmination_manage.createtable()����");
			}
			jt_def=new DefaultTableModel(tableValues, columnNames) {
				@Override
	            public boolean isCellEditable(int row,int column){
            return false;
        }
		};//ʹ�ñ�����
			 jt=new JTable(jt_def);
				jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
				jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
				jt.setRowHeight(20);//�����и�
	}
	
	
	
	
	
	class Mywindow extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			exmina_man.jdbcoperate.closeJDBCOperate();
			jdbcoperate.closeJDBCOperate();
			examina_manage.dispose();
			jf.setVisible(true);
		}
		
	}

}
