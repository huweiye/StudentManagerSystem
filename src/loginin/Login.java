
package loginin;
import loginin.Background;
import student.Student;
import studentAndteachermanage_operate.Course_manage;
import studentAndteachermanage_operate.Exmination_manage;
import studentAndteachermanage_operate.StuAndTea_operation;
import teacher.Teacher;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import jdbc.JDBCOperate;
public class Login{//��¼����
	JFrame jf_login;//��¼����
	Background back;
	JLabel jlb1=new JLabel("�������ϵͳ",SwingConstants.CENTER),jlb2=new JLabel("�û�����U��"),
			jlb3=new JLabel("�� �루P��");
	JPanel jpl1,jpl2,jpl3,jpl4;
	ButtonGroup bg1;//��¼��ʽ��ѡť��ť��
	JRadioButton jrb[]=new JRadioButton[]{new JRadioButton("ѧ����¼"),new JRadioButton("��ʦ��¼",true),new JRadioButton("ѧ����Ϣ����Ա"),
			new JRadioButton("��ְ����Ϣ����Ա"),new JRadioButton("�γ̹���Ա"),new JRadioButton("�������Ա"),new JRadioButton("��������Ա")};
	/**ѧ����½�ɲ�ѯ�ѽ�εģ��Ѿ�¼����ɼ����γ̳ɼ����Σ������Ͽεģ�ѡ��ε���δ¼��ɼ����ον�ʦ�Ͽ�ʱ��ص㣬���Եص㣬�࿼��ʦ����û���ϵĿγ̣�Ҳ������Ҫѡ�εģ�����Щ**/
	/**ѧ����½��ѡ�Σ�ѡ���γ̺��ον�ʦ**/
	/**��ʦ��½�ɲ�ѯ�Լ��ڿ����񣺼�����Щ�Σ��ֱ���Ͽεص㣬ʱ��Ϳ���ѧ������ѯ�࿼���񣬼��࿼ʱ��͵ص�**/
	/**ѧ����Ϣ�ͽ�ְ����Ϣ����Ա��ɾ��ѧ���ͽ�ʦ�Ļ�����Ϣ�����ѧ����רҵ�������£���ɾ����ѧѧ������ְ��ʦ��ɾ��ҵѧ������ְ��ʦ**/
	/**�γ̹���Ա���Ž�ʦ�ڿ����񣬼�����ÿ����ʦ����Щ���Լ���Ӧ���Ͽ�ʱ��ص㣻����ÿ��רҵ�Ľ�ѧ�ƻ�����Ӧ������Щ��**/
	/**�������Ա���ſ�����Ϣ����¼��ÿ�ſ��Ե�ѧ�����Գɼ�**/
	JTextField jtf1=new JTextField(30);
	JPasswordField jpf1=new JPasswordField(30);//��¼��������
	JButton jb2=new JButton("��¼"),jb3=new JButton("�˳�");//��¼��ť
	Login(){
		jf_login=new JFrame("��¼����/��ΰ��ר������");
		//���ñ���ͼƬ
		back=new Background(jf_login);
		jf_login.setContentPane(back);
		Container cp=jf_login.getContentPane();
		cp.setLayout(new GridLayout(6,0));//��񲼾�
		jlb1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		cp.add(jlb1);
		jpl1=new JPanel();
		bg1=new ButtonGroup();
		for(int i=0;i<jrb.length;i++) {
			jrb[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
			jrb[i].setOpaque(false);
			bg1.add(jrb[i]);
			jpl1.add(jrb[i]);
		}		
		cp.add(jpl1);
		jlb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jlb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jpf1.setEchoChar('*');
		jpl2=new JPanel();jpl2.add(jlb2);jpl2.add(jtf1);cp.add(jpl2);
		jpl3=new JPanel();jpl3.add(jlb3);jpl3.add(jpf1);cp.add(jpl3);
		jpl4=new JPanel();
		jpl4.setLayout(new FlowLayout(1,20,5));
		jb2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jb3.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		jpl4.add(jb2);jpl4.add(jb3);cp.add(jpl4);
		jb2.addActionListener(new LoginListener());//��¼��ť�¼�������
		jb3.addActionListener(new LoginListener());	
		jpl1.setOpaque(false);
		jpl2.setOpaque(false);		
		jpl3.setOpaque(false);		
		jpl4.setOpaque(false);
		jf_login.setBounds(500, 200, 700,600);
		jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf_login.setVisible(true);
	}
	
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JDBCOperate jdbcoperate=new JDBCOperate();
			String s=e.getActionCommand();
			switch(s) {
			case "��¼":
				String s1=jtf1.getText(),s2=new String(jpf1.getPassword());
					for(int i=0;i<jrb.length;i++) {
						if(jrb[i].isSelected()) {//�ж�ѡ����ֵ�¼��ʽ
							switch(i){
							case 0://ѧ����½
								if(jdbcoperate.isloginin("student_information",s1,s2)) {
								new Student(s1,jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 1://��ʦ��½
								if(jdbcoperate.isloginin("teacher_information",s1,s2)){
									new Teacher(s1,jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 2://ѧ������Ա��½
								if(jdbcoperate.is_loginaccount("sa",s1,s2)) {
								new StuAndTea_operation("student_information",jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 3://��ʦ��Ϣ����Ա��½
								if(jdbcoperate.is_loginaccount("ta",s1,s2)) {
								new StuAndTea_operation("teacher_information",jf_login);								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 4://��ѧ�ƻ��γ̹���Ա��½
								if(jdbcoperate.is_loginaccount("co",s1,s2)) {
									Course_manage course=new Course_manage(jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 5://�������Ա��½
								if(jdbcoperate.is_loginaccount("ex",s1,s2)) {
									new Exmination_manage(jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							case 6://��������Ա��½
								if(jdbcoperate.is_loginaccount("su",s1,s2)) {
									new StuAndTea_operation("account",jf_login);
								jtf1.setText(null);
								jpf1.setText(null);
								jf_login.setVisible(false);//���ص�¼����
								}else {
									JOptionPane.showMessageDialog(null,"�û������������","������ʾ",JOptionPane.ERROR_MESSAGE,null);
								}
								jdbcoperate.closeJDBCOperate();
								break;
							}
						}
					}break;
			
			case "�˳�":
				System.exit(0);
				break;
			}
		}
	}
	/**��Ӧ�ð����ְܷ���
	Ӧ���ǽ���һ�������������ݿ�һ�����������Ĵ��������ʸ��ߣ�
	��projuct�кܶ�����ظ��ĵط���������
	**/
	
	public static void main(String args[]) {
		new Login();
	}
}
