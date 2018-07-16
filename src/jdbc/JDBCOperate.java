package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

public class JDBCOperate {
	    public static Connection con;//���ݿ������
		public static Statement sta;//ִ��SQL���
		public static PreparedStatement presta;//Ԥ�������
		public static ResultSet res;//������ݿ�������ؽ��
		public JDBCOperate(){
			this("education_manager");
		}
		public JDBCOperate(String databasename){//�������ݿ�
			getConnection(databasename);
			
		}
		protected void getConnection(String databasename) {//�������ݿ⺯��
			try {
				Class.forName("com.mysql.jdbc.Driver");//����mysql���ݿ�����
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con=DriverManager.getConnection
						("jdbc:mysql://localhost:3306/"+databasename+"?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","hrwy134617");
				System.out.println("���ݿ����ӳɹ�");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				sta=con.createStatement();
				System.out.println("sql���ִ�ж���stamentʵ�����ɹ�");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public boolean isloginin(String tablename,String id,String password) {//���˺�����
			try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String sql=null;
			if(tablename.equals("student_information")) {
			sql="SELECT * FROM student_information WHERE studentnumber=? AND studentpassword=?";
			}else if(tablename.equals("teacher_information")) {
				sql="SELECT * FROM teacher_information WHERE teachernumber=? AND teacherpassword=?";
			}else if(tablename.equals("account")) {
				sql="SELECT * FROM account WHERE accountid=? AND accountpassword=?";
			}
			try {
				presta=(PreparedStatement) con.prepareStatement(sql);
				presta.setString(1, id);
				presta.setString(2, password);
				res=presta.executeQuery();
				if(res.next()) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}public boolean is_loginaccount(String type,String id,String password) {
			try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String sql=null;
			
				sql="SELECT * FROM account WHERE accountid=? AND accountpassword=? AND accounttype=?";
			
			try {
				presta=(PreparedStatement) con.prepareStatement(sql);
				presta.setString(1, id);
				presta.setString(2, password);
				presta.setString(3, type);
				res=presta.executeQuery();
				if(res.next()) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
		}
		public ResultSet getinformation(String tablename,String id) {//��ȡѧ�����ְ���ĸ�����Ϣ
			try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String sql=null;
			if(tablename.equals("student_information")) {
				sql="SELECT studentnumber, studentname, studentsex, studentbirthplace, studentbirthday, studentsdept, studentprofession, studentclass FROM student_information WHERE studentnumber=?";
				}else if(tablename.equals("teacher_information")) {
					sql="SELECT teachernumber, teachername, teachersex, teachersedpt, teacherbirthplace, teacherbirthday FROM teacher_information WHERE teachernumber=?";
				}
				else if(tablename.equals("course_information")) {
					sql="SELECT coursenumber, coursename, coursecredit, precoursenumber FROM course_information WHERE coursenumber=?";
				}else if(tablename.equals("teachcourse")) {
					sql="SELECT teachcourse, tc_teachernumber, tc_coursenumber, teachtime, teachplace  FROM teachcourse WHERE teachcourse=?";
				}
				else {}
				try {
					presta=(PreparedStatement) con.prepareStatement(sql);	
					presta.setString(1, id);
					res=presta.executeQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return res;
		}
		public ResultSet getgrade(String id) {//��ȡѧ��Ϊid��ѧ���ɼ�������ȡ`coursegrade`��
			try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String sql=null;
			sql="SELECT course_information.coursenumber,coursename,coursecredit,grade"
					+ " FROM course_information,coursegrade"
					+ " WHERE course_information.coursenumber=coursegrade.coursenumber AND coursegrade.studentnumber="+id
							+ " ORDER BY grade DESC";
			try {
				res=sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}
	 public ResultSet getclasstable(String id) {//��ȡѧ��Ϊid��ѧ���Ŀγ̱�(��ν�γ̱�Ҳ���ǵ�ǰ��û�п��ԣ�Ҳ����gradeΪ�յ���Щ�γ�)
		/**�ڱ�ʾѧ���Ͽι�ϵ��`student_course`���У��еĿ��ö�Ӧ�Ŀγ�����grade�ģ���Щ����εĿγ̲��ܳ����ڿγ̱��ϣ�ֻ��gradeΪnull�Ĳ����ڿγ̱���**/
		 try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 String sql=null;
		sql="SELECT student_course.teachcourse,course_information.coursename,teacher_information.teachername,teachcourse.teachtime,teachcourse.teachplace"
				+ " FROM student_course,teachcourse,course_information,teacher_information"
				+ " WHERE teacher_information.teachernumber=teachcourse.tc_teachernumber AND teachcourse.teachcourse=student_course.teachcourse AND"
				+ " teachcourse.tc_coursenumber=course_information.coursenumber AND student_course.sc_studentnumber="+id+" AND"
						+ " teachcourse.tc_coursenumber NOT IN "
						+ "(SELECT coursegrade.coursenumber"
						+ " FROM coursegrade"//�ϵĶ��ǻ�û�гɼ��Ŀ�
						+ " WHERE studentnumber="+id+")";
		try {
			res=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		//����㿴�������д��룬˵��������д���ע�͵�ʱ������������˾�������
	 }
	 public ResultSet getexamination(String id) {
		 /**��ȡ����ѧ��Ϊid��ѧ���Ŀ��԰��ţ��ڿ��Ա��еĿγ̶�����coursegrade���л�û�гɼ��Ŀγ̣�
		  * һ����coursegrade���е���ѧ��id�γ�coursenumber�ĳɼ�grade������examination_student���ϾͶ�Ӧ�Ŀ��ԾͿ�����
		  * ��Ӧ������ʾ�ڿ��԰�����**/
		 try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 String sql=null;
		 sql="SELECT examination.examinationnumber,course_information.coursename,teacher_information.teachername,examinationtime,examinationplace,examinationlength"
		 		+ " FROM examination,course_information,teacher_information,examination_student"
		 		+ " WHERE examination_student.exst_examnumber=examination.examinationnumber AND"
		 		+ " course_information.coursenumber=examination.ex_coursenumber AND"
		 		+ " examination.ex_teachernumber=teacher_information.teachernumber AND"
		 		+ " examination_student.exst_studentnumber="+id+" AND"
		 		+ " examination.ex_coursenumber NOT IN"
		 				+ "(SELECT coursegrade.coursenumber"
					+ " FROM coursegrade"//���Ķ��ǻ�û�гɼ��Ŀ�
					+ " WHERE studentnumber="+id+")";
		 try {
				res=sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		 
	 }
	 public ResultSet getallteachcourse() {//��ȡ������Ϣ,����ѧ��ѡ��
		 try {
				if(con.isClosed())
				 {
					 getConnection("education_manager");
				 }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 String sql=null;
		 sql="SELECT teachcourse.teachcourse,course_information.coursename,teacher_information.teachername,teachtime,teachplace"
		 		+ " FROM teachcourse,course_information,teacher_information"
		 		+ " WHERE course_information.coursenumber=tc_coursenumber"
		 		+ " AND tc_teachernumber=teacher_information.teachernumber";
		 try {
				res=sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;	
	 }
	 public void Selectcourse(String id,String teachcourse) {//ѡ��û��ʵ���жϳɼ��İ취��������ʾ�α�İ취��ʵ����
		 
		 try {
			if(con.isClosed())
			 {
				 getConnection("education_manager");
			 }
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 String sql=null;
		 /**��`student_course`��Ӽ�¼**/
		 String s="SELECT tc_coursenumber FROM teachcourse WHERE teachcourse.teachcourse="+teachcourse;
		 try {
			ResultSet r=sta.executeQuery(s);
			int l=length_of_res(r);
			if(l==0) {
				JOptionPane.showMessageDialog(null,"������Ŀ��ñ�����󣬱��������ָ���Ŀ��ñ����ѡ��","������ʾ",JOptionPane.ERROR_MESSAGE,null);
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		 sql="INSERT"
		 		+ " INTO student_course(teachcourse,sc_studentnumber)"
		 		+ " VALUES(?,?)";
		 try {
			presta=(PreparedStatement) con.prepareStatement(sql);
			presta.setString(1, teachcourse);
			presta.setString(2, id);
			presta.executeUpdate();
			JOptionPane.showMessageDialog(null,"ѡ�γɹ�","�ɹ���ʾ",JOptionPane.INFORMATION_MESSAGE,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"����ѡ���ÿΣ������ظ�ѡ��","������ʾ",JOptionPane.ERROR_MESSAGE,null);
			
		}
	 }
	 
	 
		public int length_of_res(ResultSet res) throws SQLException {//ͳ�ƽ��������
			int i=0;
			while(res.next()) {
				i++;
			}
			return i;
		}
		
		
		
		
		public void closeJDBCOperate() {//��ִ�����Ӧ��sql���֮��Ҫ�����ͷŶ�Ӧ��connection��statementʵ��
			try {
				con.close();
				sta.close();
				presta.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
