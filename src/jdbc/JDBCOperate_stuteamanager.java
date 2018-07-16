package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;

public class JDBCOperate_stuteamanager extends JDBCOperate {
	public static Connection con;//数据库的连接
	public static Statement sta;//执行SQL语句
	public static PreparedStatement presta;//预处理语句
	public static ResultSet res;//存放数据库操作返回结果
	public JDBCOperate_stuteamanager() {
		this("education_manager");
	}
	public JDBCOperate_stuteamanager(String databasename){//连接数据库
		getConnection(databasename);
	}
	@Override
	protected void getConnection(String databasename) {
		// TODO Auto-generated method stub
		super.getConnection(databasename);
		con=super.con;
		sta=super.sta;
		presta=super.presta;
		res=super.res;
	}
	public ResultSet getwhoseinformation(String tablename) {//获取学生或教职工的个人信息
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
			sql="SELECT studentnumber, studentname, studentsex, studentbirthplace, studentbirthday, studentsdept, studentprofession, studentclass FROM student_information";
			}else if(tablename.equals("teacher_information")) {
				sql="SELECT teachernumber, teachername, teachersex, teachersedpt, teacherbirthplace, teacherbirthday FROM teacher_information ";
			}else if(tablename.equals("course_information")) {
				sql="SELECT * FROM course_information";
			}else if(tablename.equals("teachcourse")) {
				sql="SELECT teachcourse, teacher_information.teachernumber,teachername,course_information.coursenumber,coursename,teachtime,teachplace"
						+ " FROM teacher_information left join teachcourse on teacher_information.teachernumber=teachcourse.tc_teachernumber AND"
						+ " teachcourse right join course_information on teachcourse.tc_coursenumber=course_information.coursenumber"
						+ " ORDER BY teachplace DESC";
			}else if(tablename.equals("examination")) {
				sql="SELECT examinationnumber, ex_coursenumber, coursename, ex_teachernumber, teachername, examinationtime, examinationplace, examinationlength"
						+ " FROM examination,teacher_information,course_information"
						+ " WHERE course_information.coursenumber=ex_coursenumber AND ex_teachernumber=teacher_information.teachernumber";
			}else if(tablename.equals("account")) {
				sql="SELECT accountid, accountpassword, accounttype"
						+ " FROM account";
			}
			else {System.out.println("getwhoseinformation出错");}
			try {
				presta=(PreparedStatement) con.prepareStatement(sql);	
				res=presta.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
	}
	public ResultSet Select_studentorteacher(String tablename,String []search_sql,String [] search_where) {
		//对应文本框内文本search_sql和数据表对应地列名search_where
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
			if(tablename.equals("student_information")) {//查学生
				 sql="SELECT * FROM student_information WHERE ";
			}else if(tablename.equals("teacher_information")) {//查老师
				 sql="SELECT * FROM teacher_information WHERE ";
			}else if(tablename.equals("course_information")) {
				sql="SELECT * FROM course_information WHERE ";
			}else if(tablename.equals("teachcourse")) {
				sql="SELECT teachcourse.teachcourse, teacher_information.teachernumber,teachername,course_information.coursenumber,coursename,teachtime,teachplace"+ 
					" FROM teacher_information left join teachcourse on teacher_information.teachernumber=teachcourse.tc_teachernumber AND"+ 
						" teachcourse right join course_information on teachcourse.tc_coursenumber=course_information.coursenumber "
						+ " WHERE ";
			}else if(tablename.equals("examination")) {
				sql="SELECT examinationnumber, ex_coursenumber, coursename, ex_teachernumber, teachername, examinationtime, examinationplace, examinationlength"
						+ " FROM examination,teacher_information,course_information"
						+ " WHERE course_information.coursenumber=ex_coursenumber AND ex_teachernumber=teacher_information.teachernumber AND ";
			}else if(tablename.equals("account")) {
				sql="SELECT accountid, accountpassword, accounttype"
						+ " FROM account WHERE ";
			}
			else {
				System.out.println("Select_studentorteacher出错");
			}
			StringBuffer s=new StringBuffer();s.append(sql);
			for(int mm=0;mm<search_sql.length;mm++) {
				s.append(search_where[mm]+" LIKE");
				s.append(" ? AND ");
			}
			s.deleteCharAt(s.length()-1);
			s.deleteCharAt(s.length()-1);
			s.deleteCharAt(s.length()-1);
			s.deleteCharAt(s.length()-1);
			try {
				presta=(PreparedStatement) con.prepareStatement(s.toString());
			for(int mm=0;mm<search_where.length;mm++) {
					presta.setString(mm+1, "%"+search_sql[mm]+"%");
			}
				res=presta.executeQuery();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return res;
		}
	public void Increase_record(String []value,String tablename) {
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
		if(tablename.equals("student_information")) {//加学生
		sql="INSERT INTO student_information (studentnumber, studentname, studentsex, studentbirthplace, studentbirthday, studentsdept, studentprofession, studentclass)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		}else if(tablename.equals("teacher_information")) {//加老师
			sql="INSERT INTO teacher_information (teachernumber, teachername, teachersex, teachersedpt, teacherbirthplace, teacherbirthday)"
					+ "VALUES (?,?,?,?,?,?)";
		}else if(tablename.equals("course_information")) {//加课程
			sql="INSERT INTO course_information (coursenumber, coursename, coursecredit, precoursenumber)"
					+ "VALUES (?,?,?,?)";
		}else if(tablename.equals("teachcourse")) {
			sql="INSERT INTO teachcourse(teachcourse, tc_teachernumber, tc_coursenumber, teachtime, teachplace)"
					+ "VALUES (?,?,?,?,?)";
		}else if(tablename.equals("examination")) {
			sql="INSERT INTO examination(examinationnumber, ex_coursenumber, ex_teachernumber, examinationtime, examinationplace, examinationlength)"
					+ " VALUES (?,?,?,?,?,?)";
		}else if(tablename.equals("account")) {
			sql="INSERT INTO account(accountid, accountpassword, accounttype)"
					+ " VALUES (?,?,?)";
		}
		else {
			System.out.println("Increase_record出错");
		}
		try {
			presta=(PreparedStatement) con.prepareStatement(sql);
			if(tablename.equals("teachcourse")) {
				presta.setString(1, value[0]);
				presta.setString(2, value[1]);
				presta.setString(3, value[3]);
				presta.setString(4, value[5]);
				presta.setString(5, value[6]);
			}else if(tablename.equals("examination")){
				presta.setString(1, value[0]);
				presta.setString(2, value[1]);
				presta.setString(3, value[3]);
				presta.setString(4, value[5]);
				presta.setString(5, value[6]);
				presta.setString(6, value[7]);
			}
			else {
			for(int ii=0;ii<value.length;ii++) {
				presta.setString(ii+1, value[ii]);
			}}
			presta.execute();
			System.out.println("插入成功");
			JOptionPane.showMessageDialog(null,"插入信息成功","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"插入失败！您输入的教职工号或课程号有误！","操作提示",JOptionPane.ERROR_MESSAGE,null);
			System.out.println("插入失败，外键问题");
		}
	}
	public void Update(String []value,String tablename,String id) {
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
		if(tablename.equals("student_information")) {//改学生
			sql="UPDATE student_information SET studentnumber=?, studentname=?, studentsex=?, studentbirthplace=?, studentbirthday=?, studentsdept=?, studentprofession=?, studentclass=?"
					+ " WHERE studentnumber="+id;
					
			}else if(tablename.equals("teacher_information")) {//改老师
				sql="UPDATE teacher_information SET teachernumber=?, teachername=?, teachersex=?, teachersedpt=?, teacherbirthplace=?, teacherbirthday=?"
						+ "WHERE teachernumber="+id;
			}else if(tablename.equals("course_information")) {
				sql="UPDATE course_information SET coursenumber=?, coursename=?, coursecredit=?, precoursenumber=?"
						+ " WHERE coursenumber="+id;
			}else if(tablename.equals("teachcourse")) {
				sql="UPDATE teachcourse SET teachcourse=?, tc_teachernumber=?, tc_coursenumber=?, teachtime=?, teachplace=?"
						+ " WHERE teachcourse="+id;
			}else if(tablename.equals("examination")) {
				sql="UPDATE examination SET examinationnumber=?, ex_coursenumber=?, ex_teachernumber=?, examinationtime=?, examinationplace=?, examinationlength=?"
						+ " WHERE examinationnumber="+id;
			}else if(tablename.equals("account")) {
				sql="UPDATE account SET accountid=?, accountpassword=?, accounttype=?"
						+ " WHERE accountid="+id;
			}
		try {
			presta=(PreparedStatement) con.prepareStatement(sql);
			if(tablename.equals("teachcourse")) {
				presta.setString(1, value[0]);
				presta.setString(2, value[1]);
				presta.setString(3, value[3]);
				presta.setString(4, value[5]);
				presta.setString(5, value[6]);
			}else if(tablename.equals("examination")) {
				presta.setString(1, value[0]);
				presta.setString(2, value[1]);
				presta.setString(3, value[3]);
				presta.setString(4, value[5]);
				presta.setString(5, value[6]);
				presta.setString(6, value[7]);
			}
			
			else {
			for(int ii=0;ii<value.length;ii++) {
				presta.setString(ii+1, value[ii]);
			}}
			presta.execute();
			System.out.println("修改成功");
			JOptionPane.showMessageDialog(null,"修改信息成功","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"修改信息失败！您输入的教职工号或课程号有误！","操作提示",JOptionPane.ERROR_MESSAGE,null);
			System.out.println("修改失败");
		}
		
	}
	public void delete_record(String tablename,String id)  {
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
		if(tablename.equals("student_information")) {//删学生
			sql="DELETE FROM student_information "
					+ " WHERE studentnumber=?";
					
			}else if(tablename.equals("teacher_information")) {//删老师
				sql="DELETE FROM teacher_information "
						+ "WHERE teachernumber=?";
			}else if(tablename.equals("course_information")) {
				sql="DELETE FROM course_information WHERE coursenumber=?";
			}else if(tablename.equals("teachcourse")) {
				sql="DELETE FROM teachcourse WHERE teachcourse.teachcourse=?";
			}else if(tablename.equals("examination")) {
				sql="DELETE FROM examination WHERE examinationnumber=?";
			}else if(tablename.equals("account")) {
				sql="DELETE FROM account WHERE accountid=?";
			}
			else {System.out.println("delete_record出错");}
		try {
			presta=(PreparedStatement) con.prepareStatement(sql);
				presta.setString(1, id);
			presta.execute();
			System.out.println("删除成功");
			JOptionPane.showMessageDialog(null,"删除成功","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("删除失败");
		}
	}
	public ResultSet exmination_information() {
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
		sql="SELECT examinationnumber,examinationtime,examinationplace"
				+ " FROM examination";
		try {
			res=sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	public ResultSet getexmination_studentnumber(String id_of_exm) {
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
		sql="SELECT examination_student.exst_studentnumber,examination.ex_coursenumber"
				+ " FROM examination_student,examination"
				+ " WHERE examination_student.exst_examnumber=examination.examinationnumber AND examinationnumber="+id_of_exm;
		try {
			res=sta.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	public void set_grade(String [][]grade,int length) {
		int[] gra=new int[length];
		for(int i=0;i<length;i++) {
			gra[i]=Integer.valueOf(grade[i][2]).intValue();
	}try {
		if(con.isClosed())
		 {
			 getConnection("education_manager");
		 }
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	String sql=null;
	
	sql="INSERT INTO coursegrade(studentnumber, coursenumber, grade)"
			+ " VALUES (?,?,?)";
	int jj=0;
	for(;jj<length;jj++) {
	try {
		presta=(PreparedStatement) con.prepareStatement(sql);
		presta.setString(1, grade[jj][0]);
		presta.setString(2, grade[jj][1]);
		presta.setInt(3, gra[jj]);
		presta.execute();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
		JOptionPane.showMessageDialog(null,"信息重复","操作提示",JOptionPane.ERROR_MESSAGE,null);
	}
	System.out.println("插入成功");
	}
	JOptionPane.showMessageDialog(null,"录入成绩成功","操作提示",JOptionPane.INFORMATION_MESSAGE,null);
		
		
	}
		
	
	
	
	
	
	
	


}
