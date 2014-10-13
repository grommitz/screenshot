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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextOverlay extends JPanel {

	private BufferedImage image;

	public BufferedImage grab() throws AWTException {
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(
				new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return screenShot;
	}

	
	public TextOverlay() {
		try {
			image = grab(); //ImageIO.read(new URL("http://sstatic.net/so/img/logo.png"));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(
				image.getWidth(), image.getHeight()));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String overlayText = formatter.format(Calendar.getInstance().getTime());
		
		image = process(image, overlayText);
	}

	private BufferedImage process(BufferedImage old, String overlayText) {
		int w = old.getWidth();
		int h = old.getHeight();
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(old, 0, 0, null);
		g2d.setPaint(Color.red);
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

	private static void create() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new TextOverlay());
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
