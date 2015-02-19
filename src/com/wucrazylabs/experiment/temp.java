package com.wucrazylabs.experiment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import wucrazylabs.imageparsing.Letter;
import wucrazylabs.textrecognition.TextRecognition;
import wucrazylabs.utils.ImageUtils;


public class temp {

	public static void main (String[] args) throws ClassNotFoundException, SQLException, IOException {

		BufferedImage img = ImageIO.read(new File("/users/alex/desktop/2410.png"));
		Letter l = new Letter(img);
		TextRecognition tr = new TextRecognition();
		ImageUtils.printBinaryImage(img);
		System.out.println(l.getConcentrationMatrix());
	}
}
