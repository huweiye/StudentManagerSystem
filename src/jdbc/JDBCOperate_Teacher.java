/**写在整个类最前面的注释，这是继承了JDBCOperate类的，只针对Teacher身份给他提供相应的操作数据库方法**/
/**我在原JDBCOperate类中写了针对学生身份操作数据库的方法后，发现所有方法都挤在一个类中代码太混乱，因此从Teacher开始后面几个身份的方法都写在上一个身份的子类中**/
/**至于Student操作方法还就让它在JDBCOperate类中吧，懒得把它拿出来了**/
package jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.PreparedStatement;
public class JDBCOperate_Teacher extends JDBCOperate {
	@Override
	protected void getConnection(String databasename) {
		// TODO Auto-generated method stub
		super.getConnection(databasename);
		con=super.con;
		sta=super.sta;
		presta=super.presta;
		res=super.res;
	}
	public static Connection con;//数据库的连接
	public static Statement sta;//执行SQL语句
	public static PreparedStatement presta;//预处理语句
	public static ResultSet res;//存放数据库操作返回结果
	public JDBCOperate_Teacher() {
		this("education_manager");
	}
	public JDBCOperate_Teacher(String databasename){//连接数据库
		getConnection(databasename);
	}
	
	public ResultSet getteachcourse(String id) {//{"课堂编号","课程名称","授课时间","授课地点"};
		String sql=null;
		sql="SELECT teachcourse.teachcourse,course_information.coursename,teachtime,teachplace"
				+ " FROM teachcourse,course_information"
				+ " WHERE teachcourse.tc_coursenumber=coursenumber AND tc_teachernumber="+id;
		try {
			res=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public ResultSet getteachexam(String id) {//{"考试编号","监考课程名称","监考时间","监考地点","考试时长"};
		String sql;
		sql="SELECT examinationnumber,course_information.coursename,examinationtime,examinationplace,examinationlength"
				+ " FROM examination , course_information"
				+ " WHERE ex_coursenumber=course_information.coursenumber AND ex_teachernumber="+id;
		try {
			res=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public ResultSet getmyclass(String id) {//{"学生编号","学生姓名","学生性别","学生籍贯","出生日期"};
		String sql;
		sql="SELECT student_information.studentnumber,student_information.studentname,studentsex,studentbirthplace,studentbirthday,student_information.studentclass"
				+ " FROM student_information"
				+ " WHERE student_information.studentclass IN"
				+ "(SELECT teacher_class.studentclass"
				+ " FROM teacher_class"
				+ " WHERE teacher_class.teachernumber="+id+")";
		try {
			res=sta.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public ResultSet getteach_course_student(String id,String teachcourse) {//上老师id教的课堂teachcourse里的学生信息
		try {
			if(con.isClosed())
			 {
				 getConnection("education_manager");
			 }
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String sql;
		sql="SELECT student_information.studentnumber, studentname, studentsex, studentbirthplace, studentbirthday, studentsdept, studentprofession, studentclass"
				+ " FROM student_information,student_course,teachcourse"
				+ " WHERE teachcourse.teachcourse=student_course.teachcourse AND student_course.sc_studentnumber=student_information.studentnumber AND teachcourse.tc_teachernumber="+id
				+ " AND teachcourse.teachcourse="+teachcourse;
		try {
			res=sta.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
}
