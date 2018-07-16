package loginin;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Background extends JPanel{
	private static final long serialVersionUID = 1L;
	JFrame mainFrame;
	ImageIcon imageicon;
	Image image;
	String url;
	public Background(JFrame mainFrame) {
		this(mainFrame,"D:\\source\\学生列表\\学生1\\Wallpapers\\system\\42.jpg");
	}
	public Background(JFrame mainFrame,String url){ 
		this.mainFrame=mainFrame;
		imageicon=new ImageIcon(url);
		image=imageicon.getImage();
	} 
	@Override
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);
		int sizex=mainFrame.getWidth();
		int sizey=mainFrame.getHeight();
		g.drawImage(image,0,0,sizex,sizey,null );
	} 
}
