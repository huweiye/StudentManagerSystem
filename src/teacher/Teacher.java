/**д����ǰ���ע�ͣ�Teacher����Student���кܶ�����ظ��ĵط����������Է�װ�̳м��ٴ��������������̫���˰�������ֱ�Ӹ���ճ����**/
/**�ظ������ӣ���Ȼ�ܲ��ã����Ǻ���̹���ٺٺ�**/
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
		teacher=new JFrame("��ʦ");
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
		cp.setLayout(new BorderLayout());//��񲼾�
		cp.add(jp,BorderLayout.NORTH);
		cp.add(jtabbedpane, BorderLayout.CENTER);
		teacher.addWindowListener(new Mywindow());
		teacher.setVisible(true);
		teacher.setBounds(300,200,800,700);
	}
	public void createjp() throws SQLException {//��ȡ��ǰ��ʦ������Ϣ����ʾ�ڽ����Ϸ�
		ResultSet res=jdbcoperate.getinformation("teacher_information", teachernumber);
		try {
			res.first();
			if(res.isLast()) {
				
			}else {
				System.out.println("��ѯ����");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel jl[]=new JLabel[] {new JLabel(res.getString("teachernumber")),new JLabel(res.getString("teachername")),new JLabel(res.getString("teachersex")),
				new JLabel(res.getString("teachersedpt"))		};//��ǰ��ʦְ���ţ��������Ա�Ժϵ�ĸ���ǩ��ʾ���Ϸ������
	for(int i=0;i<jl.length;i++) {
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jp.add(jl[i]);
	}
	}
	public void createjtabbedpane() {//����ѡ���壬��createjtab��������ȡ���ض�Ӧ�����
		JPanel jpx=new JPanel();
		jpx.setLayout(new BorderLayout(15, 15));
		JLabel jlx=new JLabel("˫���鿴�ϸÿ��õ�ѧ����Ϣ");
		jlx.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		jpx.add(jlx,BorderLayout.NORTH);
		jpx.add(createjtab("�ڿ������ѯ"), BorderLayout.CENTER);
		jtabbedpane.addTab("�ڿ������ѯ", jpx);
		jtabbedpane.addTab("�࿼�����ѯ", createjtab("�࿼�����ѯ"));
		jtabbedpane.addTab("��Ȼ��ѧ����Ϣ", createjtab("��Ȼ��ѧ����Ϣ"));
	}
	public JComponent createjtab(String tab) { //����ѡ�����ֵ��ͬ������Ӧ�����
		JScrollPane jtabpanel_my=new JScrollPane();
		if(tab.equals("�ڿ������ѯ")) {
			try {
				String [] columnNames={"���ñ��","�γ�����","�ڿ�ʱ��","�ڿεص�"};//�ڿ���Ϣ
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("�࿼�����ѯ")) {
			try {//examination����ָ��ex_teachernumber����Ϣ
				String [] columnNames={"���Ա��","�࿼�γ�����","�࿼ʱ��","�࿼�ص�","����ʱ��"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("��Ȼ��ѧ����Ϣ")) {
			try {//���ָ��studentclass��ȫ��ѧ����Ϣ
				String [] columnNames={"ѧ�����","ѧ������","ѧ���Ա�","ѧ������","��������","�༶"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("����");
		}
		
		return jtabpanel_my;//����巵�ع�ȥ�ñ�ǩ����ؼ���
	}
	/**����Ĺ�����ķ�����ֻ��Ҫ����ȥ�й��е���Ϣ����**/
	public JScrollPane createpanel_of_student(String [] columnNames) throws SQLException {//���쵱ǰѧ��������
		JScrollPane jsp;
		ResultSet res = null;
		if(columnNames[0].equals("���ñ��")) {
			res=jdbcoperate.getteachcourse(teachernumber);
			}
		else if(columnNames[0].equals("���Ա��")) {
			res=jdbcoperate.getteachexam(teachernumber);
		}
		else if(columnNames[0].equals("ѧ�����")){
			res=jdbcoperate.getmyclass(teachernumber);
		}
		else {
			System.out.println("����jdbc����");
		}
		
		int length = jdbcoperate.length_of_res(res);//��ȡ�����������
		String [][] tableValues =new String[length][columnNames.length];//�������������������Ӧ�ر���С
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
			e.printStackTrace();
		}
		MyTable jt=new MyTable(tableValues, columnNames);//ʹ���Զ���ı��
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
		jt.setRowHeight(20);//�����и�
		jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		jsp=new JScrollPane(jt);
		if(columnNames[0].equals("���ñ��")) {
			jt.addMouseListener(new MouseAdapter(){//˫���鿴������Ϣ
			    public void mouseClicked(MouseEvent e) {
			       if(e.getClickCount()==2){//������Σ�������˫���¼�
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

