package com.wucrazylabs.experiment;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import wucrazylabs.utils.ImageUtils;

public class TextToGraphics {

	public static void main(String[] args) throws Exception {
		String text = "H";

		/*
		 * Because font metrics is based on a graphics context, we need to
		 * create a small, temporary image so we can ascertain the width and
		 * height of the final image
		 */
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("Arial", Font.PLAIN, 400);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		g2d.setFont(font);
		fm = g2d.getFontMetrics();
		g2d.setColor(Color.BLACK);
		g2d.drawString(text, 0, fm.getAscent());
		g2d.dispose();

		BufferedImage bimg = img;
		// ...
		ImageIcon icon = new ImageIcon();
		icon.setImage(bimg);
		JOptionPane.showConfirmDialog(null, icon);

		ImageUtils.printBinaryImage(img);

		ImageIO.write(img, "png", new File("/users/alex/desktop/Text.png"));
	}

}