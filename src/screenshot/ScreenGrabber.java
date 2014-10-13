package screenshot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Grabs screenshot of the desktop & adds a timestamp.
 * 
 * @author Martin Charlesworth
 *
 */
@SuppressWarnings("serial")
public class ScreenGrabber extends JPanel {

	private static BufferedImage image;
	private static Calendar now = Calendar.getInstance();

	public BufferedImage grab() throws AWTException {
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(
				new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return screenShot;
	}

	public ScreenGrabber() {
		try {
			image = grab(); //ImageIO.read(new URL("http://sstatic.net/so/img/logo.png"));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(
				image.getWidth(), image.getHeight()));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm   ");
		String overlayText = formatter.format(now.getTime());
		
		image = addOverlayText(image, overlayText);
	}

	private BufferedImage addOverlayText(BufferedImage old, String overlayText) {
		int w = old.getWidth();
		int h = old.getHeight();
		BufferedImage img = new BufferedImage(w, h, old.getType());
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(old, 0, 0, null);
		g2d.setPaint(Color.blue);
		g2d.setFont(new Font("Verdana", Font.BOLD, 20));
		FontMetrics fm = g2d.getFontMetrics();
		int x = img.getWidth() - fm.stringWidth(overlayText) - 5;
		int y = fm.getHeight();
		g2d.drawString(overlayText, x, y);
		g2d.dispose();
		return img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	static class Saver implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
				String timestamp = formatter.format(now.getTime());
				String filename = "screenshot-"+timestamp+".jpg";
				ImageIO.write(image, "JPG", new File(filename));
				JOptionPane.showMessageDialog(null, "Saved " + filename);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private static JMenuBar menu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("Save");
		menuItem.addActionListener(new Saver());
		menu.add(menuItem);
		return menuBar;
	}
	
	private static void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new ScreenGrabber());
		f.setJMenuBar(menu());
		f.pack();
		f.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				create();
			}
		});
	}
}
