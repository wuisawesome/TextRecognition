package com.wucrazylabs.experiment;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import wucrazylabs.hashing.HashMaker;
import wucrazylabs.imageparsing.Letter;
import wucrazylabs.imageparsing.TextLine;
import wucrazylabs.textrecognition.TextRecognition;
import wucrazylabs.utils.ImageUtils;

public class Tester {

	public static void main (String args[]) throws IOException, SQLException, ClassNotFoundException {

		// BufferedImage img = ImageIO.read(new
		// URL("http://wucrazylabs.ml/TextRecognition/SeedImages/alphabetLower.png"));
		BufferedImage img = ImageIO.read(new File("/users/alex/pictures/imagerecognition/B.jpg"));
//		BufferedImage img = HashMaker.imageForString("Hello World!", new Font("Times New Roman",Font.PLAIN, 300));
		TextRecognition tr = new TextRecognition();

		ArrayList<BufferedImage> lines = TextLine.parseMultiLineImage(img);
		for (BufferedImage line : lines) {
			ArrayList<BufferedImage> letters = TextLine.getSubImages(line);
			for (BufferedImage letter : letters) {
				System.out.println();
				// String matrix =
				// Letter.matrixToString(Letter.getConcentrationMatrix(letter));
//				 ImageUtils.printBinaryImage(letter);
				// System.out.println(matrix);
				Character guess = null;
				Letter l = new Letter(letter);
				try {
					guess = tr.getBestGuessForLetter(l);
				} catch (Exception e) {
					System.err.println("no match found for letter:" + l);
//					e.printStackTrace();
					ImageUtils.printBinaryImage(letter);
				}
				if (guess != null)
					System.out.print(guess);
				else
					System.out.print("?");
			}
			System.out.println();
		}

	}
}
