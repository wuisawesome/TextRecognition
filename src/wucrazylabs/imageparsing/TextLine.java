package wucrazylabs.imageparsing;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import wucrazylabs.utils.ImageUtils;

public class TextLine {

	private static int indexOfSpace(BufferedImage img, int num, int start) {
		if (num < 0)
			return num;
		int space = 0;
		for (int i = start; i < img.getWidth(); i++) {
			if (ImageUtils.colIsWhite(img, i))
				if (space++ >= num)
					return i;
		}
		return -1;
	}

	private static int indexOfNewLine(BufferedImage img, int num, int start) {
		if (num < 0)
			return num;
		int space = 0;
		for (int i = start; i < img.getHeight(); i++) {
			if (ImageUtils.rowIsWhite(img, i))
				if (space++ >= num)
					return i;
		}
		return -1;
	}

	public static ArrayList<BufferedImage> parseMultiLineImage(BufferedImage img) {
		ArrayList<BufferedImage> lines = new ArrayList<BufferedImage>();
		while (true) {
			img = ImageUtils.parseImage(img);
			int end = indexOfNewLine(img, 0, 0);
			if (end <= -1)
				break;
			BufferedImage l = ImageUtils.cropImage(img, 0, img.getWidth(), 0,
					end);
			l = ImageUtils.parseImage(l);
			lines.add(l);
			img = ImageUtils.cropImage(img, 0, img.getWidth(), end,
					img.getHeight());
		}
		lines.add(img);
		return lines;
	}

	public static ArrayList<BufferedImage> getSubImages(BufferedImage img) {
		img = ImageUtils.parseImage(img);
		ArrayList<BufferedImage> letters = new ArrayList<BufferedImage>();
		while (true) {
			int end = indexOfSpace(img, 0, 0);
			if (end <= -1)
				break;
			BufferedImage l = ImageUtils.cropImage(img, 0, end, 0,
					img.getHeight());
			l = ImageUtils.parseImage(l);
			letters.add(l);
			img = ImageUtils.cropImage(img, end, img.getWidth(), 0,
					img.getHeight());
			img = ImageUtils.parseImage(img);
		}
		letters.add(img);
		return letters;
	}

}
