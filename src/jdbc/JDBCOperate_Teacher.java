/**д����������ǰ���ע�ͣ����Ǽ̳���JDBCOperate��ģ�ֻ���Teacher��ݸ����ṩ��Ӧ�Ĳ������ݿⷽ��**/
/**����ԭJDBCOperate����д�����ѧ����ݲ������ݿ�ķ����󣬷������з���������һ�����д���̫���ң���˴�Teacher��ʼ���漸����ݵķ�����д����һ����ݵ�������**/
/**����Student������������������JDBCOperate���аɣ����ð����ó�����**/
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
	public static Connection con;//���ݿ������
	public static Statement sta;//ִ��SQL���
	public static PreparedStatement presta;//Ԥ�������
	public static ResultSet res;//������ݿ�������ؽ��
	public JDBCOperate_Teacher() {
		this("education_manager");
	}
	public JDBCOperate_Teacher(String databasename){//�������ݿ�
		getConnection(databasename);
	}
	
	public ResultSet getteachcourse(String id) {//{"���ñ��","�γ�����","�ڿ�ʱ��","�ڿεص�"};
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
	public ResultSet getteachexam(String id) {//{"���Ա��","�࿼�γ�����","�࿼ʱ��","�࿼�ص�","����ʱ��"};
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
	public ResultSet getmyclass(String id) {//{"ѧ�����","ѧ������","ѧ���Ա�","ѧ������","��������"};
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
	public ResultSet getteach_course_student(String id,String teachcourse) {//����ʦid�̵Ŀ���teachcourse���ѧ����Ϣ
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
