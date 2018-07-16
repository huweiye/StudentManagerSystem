/**写在本类前面，学生信息管理员和教职工信息管理员公用一个类用来产生界面和操作数据库，只是传参不同**/
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
		//增删改查四个按钮,用外部命名类监听
		DefaultTableModel jt_def;//表格模型
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
			stutea_manager=new JFrame("管理员");
			jp_search=new JPanel();
			jb_search=new JButton("查询");
			jb_delete=new JButton("删除选中记录");
			jb_change=new JButton("更改选中记录");
			jb_add=new JButton("增加记录");
			jp_search.setLayout(new GridLayout(3,1));
		jp_table=new JScrollPane();
			createjp();//jp_search初始化
			try {
				createjtab();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("createjtab()出错");
				e.printStackTrace();
			}
			jp_table.setViewportView(jt);
			Container cp=stutea_manager.getContentPane();
			cp.setLayout(new BorderLayout());//表格布局
			cp.add(jp_search,BorderLayout.NORTH);//放查询框用的
			cp.add(jp_table, BorderLayout.CENTER);//放表用的
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
		public void createjp()  {//这是安置查询窗口的
			if(whose_student_or_teacher.equals("student_information")) {
				JCheckBox jl[]={new JCheckBox("按学号查询"),new JCheckBox("按姓名查询查询"),new JCheckBox("按性别查询"),
						new JCheckBox("按籍贯查询"),new JCheckBox("按出生日期"),new JCheckBox("按系别查询"),new JCheckBox("按专业查询"),new JCheckBox("按班级查询")	};
			    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
			   JLabel jlabel= new JLabel("支持模糊查询",JLabel.CENTER);
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
			JCheckBox jl[]={new JCheckBox("按教职工号查询"),new JCheckBox("按姓名查询查询"),new JCheckBox("按性别查询"),
					new JCheckBox("按系别查询"),new JCheckBox("按籍贯查询"),new JCheckBox("按出生日期查询")	};
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
		JCheckBox jl[]={new JCheckBox("按课程号查询"),new JCheckBox("按课程名查询"),new JCheckBox("按学分查询"),
				new JCheckBox("按先修课号查询")};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
	   JLabel jlabel= new JLabel("支持模糊查询",JLabel.CENTER);
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
		JCheckBox jl[]={new JCheckBox("按课堂号查询"),new JCheckBox("按授课教师编号查询"),new JCheckBox("按授课老师姓名查询"),
				new JCheckBox("按课程编号查询"),new JCheckBox("按课程名查询"),new JCheckBox("按上课时间查询"),new JCheckBox("按上课地点查询")};
		JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
		JLabel jlabel= new JLabel("支持模糊查询",JLabel.CENTER);
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
		JCheckBox jl[]={new JCheckBox("按考试号查询"),new JCheckBox("按课程号查询"),new JCheckBox("按课程名查询"),
				new JCheckBox("按监考教师编号查询"),new JCheckBox("按监考教师姓名查询"),new JCheckBox("按考试时间查询"),new JCheckBox("按考试地点查询"),new JCheckBox("按考试时长查询")	};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10),new JTextField(10)};
	    JLabel jlabel= new JLabel("支持模糊查询",JLabel.CENTER);
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
		JCheckBox jl[]={new JCheckBox("按账户查询"),new JCheckBox("按账户密码查询"),new JCheckBox("按账户类型查询")};
	    JTextField []jt= {new JTextField(10),new JTextField(10),new JTextField(10)};
	   JLabel jlabel= new JLabel("支持模糊查询",JLabel.CENTER);
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
		System.out.println("createjp()  {//这是安置查询窗口的出错");
	}
			
		}
			
		public void createjtab() throws SQLException { //根据管理员身份创建滚动面板
			if(whose_student_or_teacher.equals("student_information")) {
				
				 columnNames_stu=new String[]{"学号","学生姓名","学生性别","学生籍贯","出生日期","学生系别","学生专业","学生班级"};
				createpanel(columnNames_stu);
			}else if(whose_student_or_teacher.equals("teacher_information")) {
				
				 columnNames_tea=new String[]{"教职工号","教师姓名","教师性别","教师系别","教师籍贯","出生日期"};
				createpanel(columnNames_tea);
				
			}else if(whose_student_or_teacher.equals("course_information")) {
				course_Names=new String[] {"课程号","课程名","课程学分","先修课号"};
				createpanel(course_Names);
			}else if(whose_student_or_teacher.equals("teachcourse")) {
				teacourse_Names=new String[] {"课堂号","授课老师编号","授课老师姓名","授课课程编号","授课课程名","上课时间","上课地点"};
				createpanel(teacourse_Names);
			}else if(whose_student_or_teacher.equals("examination")) {
				exmination_Names=new String[] {"考试编号","考试课程号","考试课程名","监考老师编号","监考老师姓名","考试时间","考试地点","考试时长"};
				createpanel(exmination_Names);
			}else if(whose_student_or_teacher.equals("account")) {
				account=new String[] {"账号","登陆密码","账户类型"};
				createpanel(account);
			}
			else {
				System.out.println("createjtab() throws SQLException { //根据管理员身份创建滚动面板出错");
			}
		}
		/**抽象的构造表格的方法，只需要传进去有关列的信息即可
		 * @throws SQLException **/
		public void createpanel(String [] columnNames) throws SQLException  {//创造当前学生表格面板
			ResultSet res = null;
			res=jdbcoperate.getwhoseinformation(whose_student_or_teacher);
			int length = jdbcoperate.length_of_res(res);//获取表格数据行数
		 tableValues =new String[length][columnNames.length];//根据行数和列数定义对应地表格大小
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
		 jt_def=new DefaultTableModel(tableValues, columnNames);//使用表格面板
		 jt=new JTable(jt_def);
			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//自动调整列宽
			jt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
			jt.setRowHeight(20);//设置行高
		}
		/**做完表格了，下面该实现增删改查四个功能了**/
		//查，同时读取全部个被选中文本框的文本内容，未被选中的文本框设置为不可编辑
		//改和删，必须选中表格中某一行才能修改该行或者删除
		//增，跳出内窗口填写信息
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
			public void actionPerformed(ActionEvent e) {//始终让复选框与对应的文本框的可编辑性对应
		
				for(int i=0;i<jl.length;i++) {
					if(jl[i].isSelected()!=jfx[i].isEnabled()) {
						jfx[i].setEnabled(jl[i].isSelected());
						return;
					}
				}
				/**接下来实现学生或老师或课程信息的增删改查**/
				String s=e.getActionCommand();
				switch(s) {
				case "查询":/**妈呀这里实现地太冗杂了，太麻烦了，反正就是获取哪个JTextFiled能用，然后找对应的列名写在SQL语句里就行了**/
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
						}//不可能出界的吧
					}
					//现在得到了是哪些文本框可用，即search_which的元素值,也就是数据表里的列，也就是表格的列下标
					//下面分别是对应文本框内文本search_sql和数据表对应地列名search_where
					for(int ii=0;ii<length;ii++) {
						search_sql[ii]=jfx[search_which[ii]].getText();
      				System.out.println(search_sql[ii]);
					}
					if(whose_student_or_teacher.equals("student_information")) {
					for(int ii=0;ii<length;ii++) {
						search_where[ii]=col_student[search_which[ii]];//获取数据表列明
						System.out.println(search_where[ii]);
						
					}
					s_cloum=columnNames_stu;
					length_cloum=8;
					}else if(whose_student_or_teacher.equals("teacher_information")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=col_teacher[search_which[ii]];//获取数据表列明
							System.out.println(search_where[ii]);
						}
						s_cloum=columnNames_tea;
						length_cloum=6;
					}
					else if(whose_student_or_teacher.equals("course_information")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=course_liuyongxin[search_which[ii]];//获取数据表列明
							System.out.println(search_where[ii]);
						}
						s_cloum=course_Names;
						length_cloum=4;
					}else if(whose_student_or_teacher.equals("teachcourse")) {
						for(int ii=0;ii<length;ii++) {
							search_where[ii]=teacourse_xiaoliu[search_which[ii]];//获取数据表列明
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
						System.out.println("对应文本框内文本search_sql和数据表对应地列名search_where出错");
					}
					if(s_cloum==null) {
						System.out.println("获取列明出错");
						return;
					}
					//开始调用jdbc方法进行查询
					ResultSet res=jdbcoperate.Select_studentorteacher(whose_student_or_teacher,search_sql,search_where);
					//刷新表格面板
					try {/**读取数据库数据进表格**/
						length_row = jdbcoperate.length_of_res(res);
						tableValues =new String[length_row][length_cloum];
						res.first();
						for(int i=0;i<length_row;) {
							for(int j=0;j<length_cloum;j++) {
								tableValues[i][j]=res.getString(j+1);//读取表格数据
								System.out.println(tableValues[i][j]);
							}
							i++;
							res.next();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//获取表格数据行数
					DefaultTableModel dtm2=(DefaultTableModel)jt.getModel();//获取表格模型
					dtm2.setDataVector(tableValues,s_cloum);//设置新内容
					jt.updateUI();//更新显示
					break;
					
				case "增加记录":
					String [] name_cloum = null;//列明，学生信息或者教师信息
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
						System.out.println("增加记录的tea_cloum为null了");
					}
					Addrow addrow=new Addrow(name_cloum, "增加记录",jdbcoperate,whose_student_or_teacher);
					break;
					
				case "更改选中记录":
					if(jt.getSelectedRowCount()!=1) {
						JOptionPane.showMessageDialog(null,"请选择更改行","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
						return;
					}
					String number_of_update=null;//由选中的表格获取的编号
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
						System.out.println("修改记录的value_to_update和列明name_of_colum为null了");
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
					Addrow changerecord=new Addrow(name_of_colum, "修改记录",jdbcoperate,whose_student_or_teacher,value_to_update,number_of_update);
					break;
				case "删除选中记录":
					if(jt.getSelectedRowCount()==0) {
						JOptionPane.showMessageDialog(null,"请选择删除行","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
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
		


