package wucrazylabs.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageUtils {
	
	public static boolean isWhite(Color color){
		return !((color.getRed() + color.getBlue() + color.getGreen()) < 200);
	}

	public static boolean rowIsWhite(BufferedImage img, int row) {
		for (int i = 0; i < img.getWidth(); i++) {
			Color color = new Color(img.getRGB(i, row));
			if (!(isWhite(color)))
				return false;
		}
		return true;
	}
	
	public static boolean colIsWhite(BufferedImage img, int col) {
		for (int i = 0; i < img.getHeight(); i++) {
			Color color = new Color(img.getRGB(col,i));
			if (!(isWhite(color)))
				return false;
		}
		return true;
	}
	
	public static BufferedImage cropImage(BufferedImage img, int xMin, int xMax, int yMin, int yMax){
		BufferedImage parsed = new BufferedImage(xMax-xMin, yMax-yMin, BufferedImage.TYPE_INT_ARGB);
		for (int i = xMin; i < xMax; i++)
			for (int j = yMin; j < yMax; j++)
				parsed.setRGB(i-xMin, j-yMin, img.getRGB(i, j));
		return parsed;
	}
	
	public static BufferedImage parseImage(BufferedImage img){
		int xMin,xMax,yMin,yMax;
		for (xMin=0;colIsWhite(img, xMin);xMin++);
		for (xMax=img.getWidth()-1;colIsWhite(img, xMax);xMax--);
		for (yMin=0;rowIsWhite(img, yMin);yMin++);
		for (yMax=img.getHeight()-1;rowIsWhite(img, yMax);yMax--);
		return cropImage(img, xMin, ++xMax, yMin, ++yMax);
	}

	public static void printMatrix(byte[][] print){
		for (int i = 0; i < print.length; i++){
			for (int j = 0; j < print[i].length; j++)
				System.out.print(print[j][i] + " ");
			System.out.println();
		}
	}
	
	public static void printBinaryImage(BufferedImage img){
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				if (isWhite(color))
					System.out.print("@");
				else
					System.out.print("#");
			}
			System.out.println();
		}
	}

}
