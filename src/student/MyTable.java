package student;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MyTable extends JTable{/**自定义表格**/
	@Override
	public JTableHeader getTableHeader() {
		// TODO Auto-generated method stub
		JTableHeader tableheader=super.getTableHeader();
		DefaultTableCellRenderer hr=(DefaultTableCellRenderer) tableheader.getDefaultRenderer();
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);//列名居中显示
		return tableheader;
	}

	@Override
	public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {//单元格内容居中显示
		// TODO Auto-generated method stub
		DefaultTableCellRenderer cr=(DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
		cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		return cr;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;//不可编辑
	}

	public MyTable(String [][] rowdatas,String [] columns) {
		super(rowdatas,columns);
	}
	
	
	
}
