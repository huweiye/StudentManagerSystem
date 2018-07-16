/**д�ڱ���ǰ�棬ѧ����Ϣ����Ա�ͽ�ְ����Ϣ����Ա����һ����������������Ͳ������ݿ⣬ֻ�Ǵ��β�ͬ**/
package studentAndteachermanage_operate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import jdbc.JDBCOperate;
import jdbc.JDBCOperate_stuteamanager;
import student.MyTable;
public class StuAndTea_operation {
		JFrame stutea_manager;
		JPanel jp_search;
		 JScrollPane jp_table;
		String whose_student_or_teacher;
		JFrame jf;
		JDBCOperate_stuteamanager jdbcoperate;
		JButton jb_search;
		JButton jb_delete;
		JButton jb_change;
		JButton jb_add;
		String [][] tableValues;
		//��ɾ�Ĳ��ĸ���ť,���ⲿ���������
		DefaultTableModel jt_def;//���ģ��
		JTable jt;
		String []col_student;
		String []col_teacher;
		String [] course_liuyongxin;
		String [] teacourse_xiaoliu;
		String [] exmination_yongxin;
		String [] account_xinxin;
		String[] columnNames_stu=new String[8];
		String[] columnNames_tea=new String[6];
		String [] course_Names=new String [4];
		String [] teacourse_Names=new String[7];
		String [] exmination_Names=new String[8];
		String [] account=new String[3];
		public StuAndTea_operation(String whose,JFrame jf_login){
			jf=jf_login;
			whose_student_or_teacher=whose;
			col_student=new String[]{"studentnumber", "studentname", "studentsex", "studentbirthplace", "studentbirthday", "studentsdept", "studentprofession","studentclass"};
			col_teacher=new String[] {"teachernumber","teachername","teachersex","teacherbirthplace","teacherbirthplace","teacherbirthday"};
			course_liuyongxin=new String[] {"coursenumber", "coursename", "coursecredit", "precoursenumber"};
			teacourse_xiaoliu=new String[] {"teachcourse","tc_teachernumber","teachername","tc_coursenumber","coursename","teachtime","teachplace"};
			exmination_yongxin=new String[] {"examinationnumber", "ex_coursenumber","coursename", "ex_teachernumber", "teachername","examinationtime", "examinationplace", "examinationlength"};
			account_xinxin=new String[] {"accountid", "accountpassword", "accounttype"};
			jdbcoperate=new JDBCOperate_stuteamanager();
			stutea_manager=new JFrame("����Ա");
			jp_search=new JPanel();
			jb_search=new JButton("��ѯ");
			jb_delete=new JButton("ɾ��ѡ�м�¼");
			jb_change=new JButton("����ѡ�м�¼");
			jb_add=new JButton("���Ӽ�¼");
			jp_search.setLayout(new GridLayout(3,1));
		jp_table=new JScrollPane();
			createjp();//jp_search��ʼ��
			try {
				createjtab();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("createjtab()����");
				e.printStackTrace();
			}
			jp_table.setViewportView(jt);
			Container cp=stutea_manager.getContentPane();
			cp.setLayout(new BorderLayout());//��񲼾�
			cp.add(jp_search,BorderLayout.NORTH);//�Ų�ѯ���õ�
			cp.add(jp_table, BorderLayout.CENTER);//�ű��õ�
		JPanel jpp=	new JPanel();
		jpp.setLayout(new GridLayout(10, 1,10,10));
		jpp.add(jb_search);
		jpp.add(jb_delete);
		jpp.add(jb_change);
		jpp.add(jb_add);
			cp.add(jpp,BorderLayout.EAST);
			stutea_manager.addWindowListener(new Mywindow());
			stutea_manager.setVisible(true);
			stutea_manager.setBounds(300,200,1250,700);
		}
		public void createjp()  {//���ǰ��ò�ѯ���ڵ�
			if(whose_student_or_teacher.equals("student_information")) {
				JCheckBox jl[]={new JCheckBox("��ѧ�Ų�ѯ"),new JCheckBox("��������ѯ��ѯ"),new JCheckBox("���Ա��ѯ"),
						new JCheckBox("�������ѯ"),new JCheckBox("����������"),new JCheckBox("��ϵ���ѯ"),new JCheckBox("��רҵ��ѯ"),new JCheckBox("���༶��ѯ")	};
			    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
			   JLabel jlabel= new JLabel("֧��ģ����ѯ",JLabel.CENTER);
			   jlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			    jp_search.add(jlabel);
			   int i=0;
				for(;i<jl.length;i++) {
					JPanel jooo=new JPanel();
				jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
				jooo.add(jl[i]);
				jooo.add(jt[i]);
				jp_search.add(jooo);
				jl[i].setSelected(true);
				jl[i].addActionListener(new MyJAction(jt,jl));
		}
				jb_search.addActionListener(new MyJAction(jt,jl));
				jb_delete.addActionListener(new MyJAction(jt,jl));
				jb_change.addActionListener(new MyJAction(jt,jl));
				jb_add.addActionListener(new MyJAction(jt,jl));
				
		}else if(whose_student_or_teacher.equals("teacher_information")) {
			jp_search.setLayout(new GridLayout(2,1));
			JCheckBox jl[]={new JCheckBox("����ְ���Ų�ѯ"),new JCheckBox("��������ѯ��ѯ"),new JCheckBox("���Ա��ѯ"),
					new JCheckBox("��ϵ���ѯ"),new JCheckBox("�������ѯ"),new JCheckBox("���������ڲ�ѯ")	};
		    JTextField []jt={new JTextField(15),new JTextField(15),new JTextField(15),new JTextField(15),new JTextField(10),new JTextField(15)};
		    
			for(int i=0;i<jl.length;i++) {
				JPanel jooo=new JPanel();
			jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jooo.add(jl[i]);
			jooo.add(jt[i]);
			jp_search.add(jooo);
			jl[i].setSelected(true);
			jl[i].addActionListener(new MyJAction(jt,jl));
	}
			jb_search.addActionListener(new MyJAction(jt,jl));
			jb_delete.addActionListener(new MyJAction(jt,jl));
			jb_change.addActionListener(new MyJAction(jt,jl));
			jb_add.addActionListener(new MyJAction(jt,jl));

	}else if(whose_student_or_teacher.equals("course_information")) {
		JCheckBox jl[]={new JCheckBox("���γ̺Ų�ѯ"),new JCheckBox("���γ�����ѯ"),new JCheckBox("��ѧ�ֲ�ѯ"),
				new JCheckBox("�����޿κŲ�ѯ")};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
	   JLabel jlabel= new JLabel("֧��ģ����ѯ",JLabel.CENTER);
	   jlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
	    jp_search.add(jlabel);
	   int i=0;
		for(;i<jl.length;i++) {
			JPanel jooo=new JPanel();
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jooo.add(jl[i]);
		jooo.add(jt[i]);
		jp_search.add(jooo);
		jl[i].setSelected(true);
		jl[i].addActionListener(new MyJAction(jt,jl));
}
		jb_search.addActionListener(new MyJAction(jt,jl));
		jb_delete.addActionListener(new MyJAction(jt,jl));
		jb_change.addActionListener(new MyJAction(jt,jl));
		jb_add.addActionListener(new MyJAction(jt,jl));
		
	}else if(whose_student_or_teacher.equals("teachcourse")) {
		JCheckBox jl[]={new JCheckBox("�����úŲ�ѯ"),new JCheckBox("���ڿν�ʦ��Ų�ѯ"),new JCheckBox("���ڿ���ʦ������ѯ"),
				new JCheckBox("���γ̱�Ų�ѯ"),new JCheckBox("���γ�����ѯ"),new JCheckBox("���Ͽ�ʱ���ѯ"),new JCheckBox("���Ͽεص��ѯ")};
		JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
		JLabel jlabel= new JLabel("֧��ģ����ѯ",JLabel.CENTER);
		   jlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		    jp_search.add(jlabel);
		   int i=0;
			for(;i<jl.length;i++) {
				JPanel jooo=new JPanel();
			jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jooo.add(jl[i]);
			jooo.add(jt[i]);
			jp_search.add(jooo);
			jl[i].setSelected(true);
			jl[i].addActionListener(new MyJAction(jt,jl));
			}
			jb_search.addActionListener(new MyJAction(jt,jl));
			jb_delete.addActionListener(new MyJAction(jt,jl));
			jb_change.addActionListener(new MyJAction(jt,jl));
			jb_add.addActionListener(new MyJAction(jt,jl));
	}else if(whose_student_or_teacher.equals("examination")) {
		JCheckBox jl[]={new JCheckBox("�����ԺŲ�ѯ"),new JCheckBox("���γ̺Ų�ѯ"),new JCheckBox("���γ�����ѯ"),
				new JCheckBox("���࿼��ʦ��Ų�ѯ"),new JCheckBox("���࿼��ʦ������ѯ"),new JCheckBox("������ʱ���ѯ"),new JCheckBox("�����Եص��ѯ"),new JCheckBox("������ʱ����ѯ")	};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
	    JLabel jlabel= new JLabel("֧��ģ����ѯ",JLabel.CENTER);
		   jlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		    jp_search.add(jlabel);
		   int i=0;
			for(;i<jl.length;i++) {
				JPanel jooo=new JPanel();
			jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jooo.add(jl[i]);
			jooo.add(jt[i]);
			jp_search.add(jooo);
			jl[i].setSelected(true);
			jl[i].addActionListener(new MyJAction(jt,jl));
	}
			jb_search.addActionListener(new MyJAction(jt,jl));
			jb_delete.addActionListener(new MyJAction(jt,jl));
			jb_change.addActionListener(new MyJAction(jt,jl));
			jb_add.addActionListener(new MyJAction(jt,jl));
	}else if(whose_student_or_teacher.equals("account")) {
		JCheckBox jl[]={new JCheckBox("���˻���ѯ"),new JCheckBox("���˻������ѯ"),new JCheckBox("���˻����Ͳ�ѯ")};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10)};
	   JLabel jlabel= new JLabel("֧��ģ����ѯ",JLabel.CENTER);
	   jlabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
	    jp_search.add(jlabel);
	   int i=0;
		for(;i<jl.length;i++) {
			JPanel jooo=new JPanel();
		jl[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
		jooo.add(jl[i]);
		jooo.add(jt[i]);
		jp_search.add(jooo);
		jl[i].setSelected(true);
		jl[i].addActionListener(new MyJAction(jt,jl));
}
		jb_search.addActionListener(new MyJAction(jt,jl));
		jb_delete.addActionListener(new MyJAction(jt,jl));
		jb_change.addActionListener(new MyJAction(jt,jl));
		jb_add.addActionListener(new MyJAction(jt,jl));
	}
	else {
		System.out.println("createjp()  {//���ǰ��ò�ѯ���ڵĳ���");
	}
			
		}
			
		public void createjtab() throws SQLException { //���ݹ���Ա��ݴ����������
			if(whose_student_or_teacher.equals("student_information")) {
				
				 columnNames_stu=new String[]{"ѧ��","ѧ������","ѧ���Ա�","ѧ������","��������","ѧ��ϵ��","ѧ��רҵ","ѧ���༶"};
				createpanel(columnNames_stu);
			}else if(whose_student_or_teacher.equals("teacher_information")) {
				
				 columnNames_tea=new String[]{"��ְ����","��ʦ����","��ʦ�Ա�","��ʦϵ��","��ʦ����","��������"};
				createpanel(columnNames_tea);
				
			}else if(whose_student_or_teacher.equals("course_information")) {
				course_Names=new String[] {"�γ̺�","�γ���","�γ�ѧ��","���޿κ�"};
				createpanel(course_Names);
			}else if(whose_student_or_teacher.equals("teachcourse")) {
				teacourse_Names=new String[] {"���ú�","�ڿ���ʦ���","�ڿ���ʦ����","�ڿογ̱��","�ڿογ���","�Ͽ�ʱ��","�Ͽεص�"};
				createpanel(teacourse_Names);
			}else if(whose_student_or_teacher.equals("examination")) {
				exmination_Names=new String[] {"���Ա��","���Կγ̺�","���Կγ���","�࿼��ʦ���","�࿼��ʦ����","����ʱ��","���Եص�","����ʱ��"};
				createpanel(exmination_Names);
			}else if(whose_student_or_teacher.equals("account")) {
				account=new String[] {"�˺�","��½����","�˻�����"};
				createpanel(account);
			}
			else {
				System.out.println("createjtab() throws SQLException { //���ݹ���Ա��ݴ�������������");
			}
		}
		/**����Ĺ�����ķ�����ֻ��Ҫ����ȥ�й��е���Ϣ����
		 * @throws SQLException **/
		public void createpanel(String [] columnNames) throws SQLException  {//���쵱ǰѧ��������
			ResultSet res = null;
			res=jdbcoperate.getwhoseinformation(whose_student_or_teacher);
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
				e.printStackTrace();
			}
		 jt_def=new DefaultTableModel(tableValues, columnNames);//ʹ�ñ�����
		 jt=new JTable(jt_def);
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//�Զ������п�
			jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jt.setRowHeight(20);//�����и�
		}
		/**�������ˣ������ʵ����ɾ�Ĳ��ĸ�������**/
		//�飬ͬʱ��ȡȫ������ѡ���ı�����ı����ݣ�δ��ѡ�е��ı�������Ϊ���ɱ༭
		//�ĺ�ɾ������ѡ�б����ĳһ�в����޸ĸ��л���ɾ��
		//���������ڴ�����д��Ϣ
		public JFrame getvisible() {
			stutea_manager.setVisible(false);
			return stutea_manager;
		}
	
		
		
		
		
		class MyJAction implements ActionListener{
			JTextField[] jfx;
			JCheckBox []jl;
			public MyJAction(JTextField[] jfx1,JCheckBox []jl1){
				this.jfx=jfx1;
				jl=jl1;
			}

			@Override
			public void actionPerformed(ActionEvent e) {//ʼ���ø�ѡ�����Ӧ���ı���Ŀɱ༭�Զ�Ӧ
		
				for(int i=0;i<jl.length;i++) {
					if(jl[i].isSelected()!=jfx[i].isEnabled()) {
						jfx[i].setEnabled(jl[i].isSelected());
						return;
					}
				}
				/**������ʵ��ѧ������ʦ��γ���Ϣ����ɾ�Ĳ�**/
				String s=e.getActionCommand();
				switch(s) {
				case "��ѯ":/**��ѽ����ʵ�ֵ�̫�����ˣ�̫�鷳�ˣ��������ǻ�ȡ�ĸ�JTextFiled���ã�Ȼ���Ҷ�Ӧ������д��SQL����������**/
					int length=0;
					int length_cloum=0;
					int length_row=0;
					for(int i=0;i<jfx.length;i++) {
						if(jfx[i].isEnabled()==true) {
							length++;
						}
					}
					int []search_which=new int [length];
					String []search_sql=new String[length];
					String [] search_where=new String[length];
					String [] s_cloum = null;
					for(int i=0,j=0;i<jfx.length&&j<length;i++) {
						if(jfx[i].isEnabled()==true) {
							search_which[j]=i;
							j++;
						}//�����ܳ���İ�
					}
					//���ڵõ�������Щ�ı�����ã���search_which��Ԫ��ֵ,Ҳ�������ݱ�����У�Ҳ���Ǳ������±�
					//����ֱ��Ƕ�Ӧ�ı������ı�search_sql�����ݱ��Ӧ������search_where
					for(int ii=0;ii<length;ii++) {
						search_sql[ii]=jfx[search_which[ii]].getText();
      				System.out.println(search_sql[ii]);
					}
					if(whose_student_or_teacher.equals("student_information")) {
					for(int ii=0;ii<length;ii++) {
						search_where[ii]=col_student[search_which[ii]];//��ȡ���ݱ�����
						System.out.println(search_where[ii]);
						
					}
					s_cloum=columnNames_stu;
					length_cloum=8;
					}else if(whose_student_or_teacher.equals("teacher_information")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=col_teacher[search_which[ii]];//��ȡ���ݱ�����
							System.out.println(search_where[ii]);
						}
						s_cloum=columnNames_tea;
						length_cloum=6;
					}
					else if(whose_student_or_teacher.equals("course_information")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=course_liuyongxin[search_which[ii]];//��ȡ���ݱ�����
							System.out.println(search_where[ii]);
						}
						s_cloum=course_Names;
						length_cloum=4;
					}else if(whose_student_or_teacher.equals("teachcourse")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=teacourse_xiaoliu[search_which[ii]];//��ȡ���ݱ�����
							System.out.println(search_where[ii]);
						}
						s_cloum=teacourse_Names;
						length_cloum=7;
					}else if(whose_student_or_teacher.equals("examination")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=exmination_yongxin[search_which[ii]];
							System.out.println(search_where[ii]);
						}
						s_cloum=exmination_Names;
						length_cloum=8;
					}else if(whose_student_or_teacher.equals("account")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=account_xinxin[search_which[ii]];
							System.out.println(search_where[ii]);
						}
						s_cloum=account;
						length_cloum=3;
					}
					else {
						System.out.println("��Ӧ�ı������ı�search_sql�����ݱ��Ӧ������search_where����");
					}
					if(s_cloum==null) {
						System.out.println("��ȡ��������");
						return;
					}
					//��ʼ����jdbc�������в�ѯ
					ResultSet res=jdbcoperate.Select_studentorteacher(whose_student_or_teacher,search_sql,search_where);
					//ˢ�±�����
					try {/**��ȡ���ݿ����ݽ����**/
						length_row = jdbcoperate.length_of_res(res);
						tableValues =new String[length_row][length_cloum];
						res.first();
						for(int i=0;i<length_row;) {
							for(int j=0;j<length_cloum;j++) {
								tableValues[i][j]=res.getString(j+1);//��ȡ�������
								System.out.println(tableValues[i][j]);
							}
							i++;
							res.next();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//��ȡ�����������
					DefaultTableModel dtm2=(DefaultTableModel)jt.getModel();//��ȡ���ģ��
					dtm2.setDataVector(tableValues,s_cloum);//����������
					jt.updateUI();//������ʾ
					break;
					
				case "���Ӽ�¼":
					String [] name_cloum = null;//������ѧ����Ϣ���߽�ʦ��Ϣ
					if(whose_student_or_teacher.equals("student_information")) {
						name_cloum=columnNames_stu;
					}else if(whose_student_or_teacher.equals("teacher_information")) {
						name_cloum=columnNames_tea;
					}else if(whose_student_or_teacher.equals("course_information")) {
						name_cloum=course_Names;
					}
					else if(whose_student_or_teacher.equals("teachcourse")) {
						name_cloum=teacourse_Names;
					}else if(whose_student_or_teacher.equals("examination")) {
						name_cloum=exmination_Names;
					}else if(whose_student_or_teacher.equals("account")) {
						name_cloum=account;
					}
					else {
						System.out.println("���Ӽ�¼��tea_cloumΪnull��");
					}
					Addrow addrow=new Addrow(name_cloum, "���Ӽ�¼",jdbcoperate,whose_student_or_teacher);
					break;
					
				case "����ѡ�м�¼":
					if(jt.getSelectedRowCount()!=1) {
						JOptionPane.showMessageDialog(null,"��ѡ�������","������ʾ",JOptionPane.INFORMATION_MESSAGE,null);
						return;
					}
					String number_of_update=null;//��ѡ�еı���ȡ�ı��
					String []name_of_colum=null;
					String []value_to_update = null;
					number_of_update=(String) jt.getValueAt(jt.getSelectedRow(), 0);
					System.out.println("number_of_update:"+number_of_update);
					if(whose_student_or_teacher.equals("student_information")) {
						name_of_colum=columnNames_stu;
						value_to_update=new String[name_of_colum.length];
					}else if(whose_student_or_teacher.equals("teacher_information")) {
						name_of_colum=columnNames_tea;
						value_to_update=new String[name_of_colum.length];
					}else if(whose_student_or_teacher.equals("course_information")) {
						name_of_colum=course_Names;
						value_to_update=new String[name_of_colum.length];
					}else if(whose_student_or_teacher.equals("teachcourse")) {
						name_of_colum=teacourse_Names;
						value_to_update=new String[name_of_colum.length];
					}else if(whose_student_or_teacher.equals("examination")) {
						name_of_colum=exmination_Names;
						value_to_update=new String [name_of_colum.length];
					}else if(whose_student_or_teacher.equals("account")) {
						name_of_colum=account;
						value_to_update=new String [name_of_colum.length];
					}
					else {
						System.out.println("�޸ļ�¼��value_to_update������name_of_columΪnull��");
					}
				//	ResultSet res_update=jdbcoperate.getinformation(whose_student_or_teacher, number_of_update);
//					try {
//						res_update.first();
//					} catch (SQLException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
					for(int ii=0;ii<value_to_update.length;ii++) {
							value_to_update[ii]=(String) jt.getValueAt(jt.getSelectedRow(), ii);
					}
					Addrow changerecord=new Addrow(name_of_colum, "�޸ļ�¼",jdbcoperate,whose_student_or_teacher,value_to_update,number_of_update);
					break;
				case "ɾ��ѡ�м�¼":
					if(jt.getSelectedRowCount()==0) {
						JOptionPane.showMessageDialog(null,"��ѡ��ɾ����","������ʾ",JOptionPane.INFORMATION_MESSAGE,null);
						return;
					}
					String number_of_delete=null;
					number_of_delete=(String) jt.getValueAt(jt.getSelectedRow(), 0);
					System.out.println(number_of_delete+"number_of_delete");
					jdbcoperate.delete_record(whose_student_or_teacher,number_of_delete);
				// TODO Auto-generated method stub
				
			}
			
		}
		
		}
		
		class Mywindow extends WindowAdapter{

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				jdbcoperate.closeJDBCOperate();
				stutea_manager.dispose();
				jf.setVisible(true);
			}
			
		}

	 
		
		}
		


