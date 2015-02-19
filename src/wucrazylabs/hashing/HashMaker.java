package wucrazylabs.hashing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import wucrazylabs.imageparsing.Letter;
import wucrazylabs.sql.MySQL;
import wucrazylabs.sql.SQL;

public class HashMaker {

	/*
	 * hashes += current++ + ":" + Letter.GRID_SIZE + "x" + Letter.GRID_SIZE +
	 * "\n" + matrix + "\n";
	 */

	public static final String[] fontNames = new String[] { "arial", "times new roman", "calibri", "helvetica", "times", "ariel", "courier" };
	// public static final Font[] fonts = new Font[fontNames.length];
	public static final ArrayList<Font> fonts = new ArrayList<Font>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts().length);
	public static final String[] letters = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "-", "=", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", ":", ";" };

	static {
//		Font[] arr = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//		for (Font f : arr)
//			if (isEnglish(f) && f.toString()!="[family=Al Bayan,name=AlBayan,style=plain,size=400]")
//				fonts.add(f);
		for (String f : fontNames){
			fonts.add(new Font(f, Font.PLAIN, 300));
			fonts.add(new Font(f, Font.ITALIC, 300));
			fonts.add(new Font(f, Font.BOLD, 300));
			fonts.add(new Font(f, Font.PLAIN, 12));
			fonts.add(new Font(f, Font.ITALIC, 12));
			fonts.add(new Font(f, Font.BOLD, 12));
		}
	}
	
	public static boolean isEnglish(Font f){
		String str = "";
		for (String s : letters)
			str += s;
		return f.canDisplayUpTo(str) == -1;
	}

	public static BufferedImage imageForString (String str, Font font) {

		font = new Font(font.getName(), font.getStyle(), 400); //Can't trust people to size their own font -_-
		
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int width = fm.stringWidth(str);
		int height = fm.getHeight();
		g2d.dispose();

		try {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} catch (Exception e) {
			System.err.println("Problem printing: " + str + " " + "with font: " + font);
			e.printStackTrace();
		}
		g2d = img.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
		g2d.setFont(font);
		fm = g2d.getFontMetrics();
		g2d.setColor(Color.BLACK);
		g2d.drawString(str, 0, fm.getAscent());
		g2d.dispose();
		return img;

	}

	public static ArrayList<BufferedImage> getImgs (Font f) {

		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>(letters.length);
		for (String letter : letters)
			imgs.add(imageForString(letter, f));
		return imgs;
	}

	public static void main (String args[]) throws Exception {

		// String hashes = "";
		SQL sql = new MySQL("_TextRecognition", "", "192.168.2.99", 3306, "TextRecognition");

		String[] columns = new String[] { "letter", "length", "width", "ratio", "hash", "maxval"};
		String[][] params = new String[letters.length][];

		for (int j = 0; j < fonts.size(); j++) {
			long start = System.currentTimeMillis();
			ArrayList<BufferedImage> upper = getImgs(fonts.get(j));
			int i = 0;
			try{
			for (BufferedImage img : upper) {
				Letter l = new Letter(img);
				params[i] = new String[] { letters[i % letters.length], l.getNumRows() + "", l.getNumCols() + "", l.getSizeRatio() + "", l.getConcentrationMatrix(), Letter.MAX_CONCENTRATION + ""};
				++i;
			}
			} catch (Exception e){
				System.err.println(i);
				e.printStackTrace();
			}
			sql.INSERT("CharacterHashes", columns, params);
			System.out.println((System.currentTimeMillis() - start) / 1000.f);
		}

	}

}
