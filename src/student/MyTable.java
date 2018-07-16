package student;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MyTable extends JTable{/**�Զ�����**/
	@Override
	public JTableHeader getTableHeader() {
		// TODO Auto-generated method stub
		JTableHeader tableheader=super.getTableHeader();
		DefaultTableCellRenderer hr=(DefaultTableCellRenderer) tableheader.getDefaultRenderer();
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);//����������ʾ
		return tableheader;
	}

	@Override
	public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {//��Ԫ�����ݾ�����ʾ
		// TODO Auto-generated method stub
		DefaultTableCellRenderer cr=(DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		return cr;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;//���ɱ༭
	}

	public MyTable(String [][] rowdatas,String [] columns) {
		super(rowdatas,columns);
	}
	
	
	
}
