package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

public class JDBCOperate {
	    public static Connection con;//数据库的连接
		public static Statement sta;//执行SQL语句
		public static PreparedStatement presta;//预处理语句
		public static ResultSet res;//存放数据库操作返回结果
		public JDBCOperate(){
			this("education_manager");
		}
		public JDBCOperate(String databasename){//连接数据库
			getConnection(databasename);
			
		}
		protected void getConnection(String databasename) {//连接数据库函数
			try {
				Class.forName("com.mysql.jdbc.Driver");//加载mysql数据库驱动
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				con=DriverManager.getConnection
						("jdbc:mysql://localhost:3306/"+databasename+"?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","hrwy134617");
				System.out.println("数据库连接成功");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				sta=con.createStatement();
				System.out.println("sql语句执行对象stament实例化成功");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public boolean isloginin(String tablename,String id,String password) {//查账号密码
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
		public ResultSet getinformation(String tablename,String id) {//获取学生或教职工的个人信息
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
		public ResultSet getgrade(String id) {//获取学号为id的学生成绩单，读取`coursegrade`表
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
	 public ResultSet getclasstable(String id) {//获取学号为id的学生的课程表(所谓课程表也就是当前还没有考试，也就是grade为空的那些课程)
		/**在表示学生上课关系的`student_course`表中，有的课堂对应的课程是有grade的，这些上完课的课程不能出现在课程表上，只有grade为null的才能在课程表上**/
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
						+ " FROM coursegrade"//上的都是还没有成绩的课
						+ " WHERE studentnumber="+id+")";
		try {
			res=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		//如果你看到了这行代码，说不定我在写这句注释的时候脑子里想的人就是你呢
	 }
	 public ResultSet getexamination(String id) {
		 /**获取的是学号为id的学生的考试安排，在考试表中的课程都是在coursegrade表中还没有成绩的课程，
		  * 一旦向coursegrade表中登入学生id课程coursenumber的成绩grade，则在examination_student表上就对应的考试就考完了
		  * 不应该再显示在考试安排上**/
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
					+ " FROM coursegrade"//考的都是还没有成绩的课
					+ " WHERE studentnumber="+id+")";
		 try {
				res=sta.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		 
	 }
	 public ResultSet getallteachcourse() {//获取课堂信息,帮助学生选课
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
	 public void Selectcourse(String id,String teachcourse) {//选课没有实现判断成绩的办法，但是显示课表的办法里实现了
		 
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
		 /**往`student_course`里加记录**/
		 String s="SELECT tc_coursenumber FROM teachcourse WHERE teachcourse.teachcourse="+teachcourse;
		 try {
			ResultSet r=sta.executeQuery(s);
			int l=length_of_res(r);
			if(l==0) {
				JOptionPane.showMessageDialog(null,"您输入的课堂编号有误，必须从下列指定的课堂编号中选择","错误提示",JOptionPane.ERROR_MESSAGE,null);
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
			JOptionPane.showMessageDialog(null,"选课成功","成功提示",JOptionPane.INFORMATION_MESSAGE,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"您已选过该课，不能重复选课","错误提示",JOptionPane.ERROR_MESSAGE,null);
			
		}
	 }
	 
	 
		public int length_of_res(ResultSet res) throws SQLException {//统计结果集行数
			int i=0;
			while(res.next()) {
				i++;
			}
			return i;
		}
		
		
		
		
		public void closeJDBCOperate() {//在执行完对应的sql语句之后都要自行释放对应的connection和statement实例
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
