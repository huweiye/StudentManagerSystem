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
		student=new JFrame("ѧ��");
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
		cp.setLayout(new BorderLayout());//��񲼾�
		cp.add(jp,BorderLayout.NORTH);
		cp.add(jtabbedpane, BorderLayout.CENTER);
		student.addWindowListener(new Mywindow());
		student.setVisible(true);
		student.setBounds(300,200,800,700);
	}
	public void createjp() throws SQLException {//��ȡ��ǰѧ��������Ϣ����ʾ�ڽ����Ϸ�
		ResultSet res=jdbcoperate.getinformation("student_information", studentnumber);
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
		JLabel jl[]=new JLabel[] {new JLabel(res.getString("studentnumber")),new JLabel(res.getString("studentname")),new JLabel(res.getString("studentsex")),
				new JLabel(res.getString("studentclass"))		};//��ǰѧ��ѧ�ţ��������Ա�רҵ�༶�ĸ���ǩ��ʾ���Ϸ������
	for(int i=0;i<jl.length;i++) {
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jp.add(jl[i]);
	}
	}
	public void createjtabbedpane() {//����ѡ���壬��createjtab��������ȡ���ض�Ӧ�����
		JPanel jpx=new JPanel();
		jpx.setLayout(new BorderLayout(15, 15));
		JLabel jlx=new JLabel("�ڴ˴�������ñ�Ż���˫��Ԫ�����ѡ��");
		 jtx=new JTextField(15);
		JButton jbx=new JButton("�ύ");
		jbx.addActionListener(new MyJButtonAction());//�ⲿ��������������ť
		JPanel jpx1=new JPanel();
		jpx1.setLayout(new FlowLayout(15));
		jpx1.add(jlx);
		jpx1.add(jtx);
		jpx1.add(jbx);
		jpx.add(jpx1,BorderLayout.NORTH);
		jpx.add(createjtab("ѡ���γ�"), BorderLayout.CENTER);
		jtabbedpane.addTab("�γ̳ɼ�", createjtab("�γ̳ɼ�"));
		jtabbedpane.addTab("���Բ�ѯ", createjtab("���Բ�ѯ"));
		jtabbedpane.addTab("ѡ���γ�", jpx);
		jtabbedpane.addTab("�γ̱�", createjtab("�γ̱�"));
	}//����㿴������ע�ͣ�֤�����ڳ��ҵĴ��룬������
	public JComponent createjtab(String tab) { //����ѡ�����ֵ��ͬ������Ӧ�����
		JScrollPane jtabpanel_my=new JScrollPane();
		if(tab.equals("�γ̳ɼ�")) {
			try {
				String [] columnNames={"�γ̱��","�γ�����","ѧ��","�ɼ�"};//�ɼ�����Ӧ��
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("�γ̱�")) {
			try {
				String [] columnNames={"���ú�","�γ�����","�ον�ʦ","�Ͽ�ʱ��","�Ͽεص�"};
				//�γ̱��Ӧ�У��γ̱�ţ��γ����ƣ��ον�ʦ���Ͽ�ʱ�䣬�Ͽεص�
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("���Բ�ѯ")) {
			try {//���Բ�ѯ��Ӧ�ţ����Ա�ţ����Կγ����֣��࿼��ʦ������ʱ�䣬���Եص�,����ʱ��
				String [] columnNames={"���Ա��","���Կγ�����","�࿼��ʦ","����ʱ��","���Եص�","����ʱ��"};
				jtabpanel_my=createpanel_of_student(columnNames);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(tab.equals("ѡ���γ�")) {
			try {//ѡ���γ��õı��Ѹ������ñ�`teachcourse`�ĸ������õ�����ֵ���г������У��г����Ŀ��ö�����ѡ�ģ�
				//����ѡ�Ŀ��ö�Ӧ�Ŀγ�ֻ���ǵ�ǰѧ����û�гɼ��ģ��Ѿ��гɼ��Ŀ��ò���ѡ
				String [] columnNames={"���ñ��","�γ�����","�ο���ʦ","�Ͽ�ʱ��","�Ͽεص�"};
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
		if(columnNames[0].equals("�γ̱��")) {
			res=jdbcoperate.getgrade(studentnumber);
			}
		else if(columnNames[0].equals("���ú�")) {
			res=jdbcoperate.getclasstable(studentnumber);
		}
		else if(columnNames[0].equals("���Ա��")){
			res=jdbcoperate.getexamination(studentnumber);
		}else if(columnNames[0].equals("���ñ��")){
			res=jdbcoperate.getallteachcourse();
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
		if(columnNames[0].equals("���ñ��")) {
			jt.addMouseListener(new MouseAdapter(){//˫���鿴������Ϣ
			    public void mouseClicked(MouseEvent e) {
			       if(e.getClickCount()==2){//������Σ�������˫���¼�
			    	        int row=jt.getSelectedRow();	
			    	        String value_teachcourse=(String) jt.getValueAt(row, 0);
			       value_doubleclick=value_teachcourse;
			       System.out.println(value_doubleclick);
			       }
			    }
			   });
		}
		
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
		jt.setRowHeight(20);//�����и�
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
				JOptionPane.showMessageDialog(null,"����δ������ñ��","������ʾ",JOptionPane.ERROR_MESSAGE,null);
				return;
				}
				else {
					teachcourse=value_doubleclick;
				}
			}
			jdbcoperate.Selectcourse(studentnumber, teachcourse);
			jtabbedpane.remove(3);
			jtabbedpane.addTab("�γ̱�", createjtab("�γ̱�"));
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
