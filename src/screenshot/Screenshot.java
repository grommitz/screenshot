package screenshot;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Screenshot {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");

	public void robo() throws Exception
	{
		Calendar now = Calendar.getInstance();
		Robot robot = new Robot();
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(screenShot, "JPG", new File("C:\\Users\\Martin\\screenshot1.jpg")); //"+formatter.format(now.getTime())+".jpg"));
		System.out.println(formatter.format(now.getTime()));
		screenShot = process(screenShot);
		ImageIO.write(screenShot, "JPG", new File("C:\\Users\\Martin\\screenshot2.jpg")); //"+formatter.format(now.getTime())+".jpg"));
	}

	private BufferedImage process(BufferedImage old) {
		int w = old.getWidth();
		int h = old.getHeight();
		BufferedImage img = new BufferedImage(
				w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(old, 0, 0, null);
		g2d.setPaint(Color.red);
		g2d.setFont(new Font("Serif", Font.BOLD, 20));
		String s = "Hello, world!";
		FontMetrics fm = g2d.getFontMetrics();
		int x = img.getWidth() - fm.stringWidth(s) - 5;
		int y = fm.getHeight();
		g2d.drawString(s, x, y);
		g2d.dispose();
		return img;
	}
	
	public static void main(String[] args) throws Exception
	{
		Screenshot s2i = new Screenshot();
		s2i.robo();
	}

}
