package wucrazylabs.imageparsing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Scanner;
import wucrazylabs.utils.HTTPTools;
import wucrazylabs.utils.ImageUtils;

/**
 * 
 * @author alex
 * 
 *         As a static class this will get a letter for a matrix As a class this
 *         is a container for a letter matrix and other necessary metadata
 */
public class Letter {

	protected BufferedImage img;
	protected String matrix;

	public Letter (BufferedImage img) {
		this.img = ImageUtils.parseImage(img);
		calculateMatrix();
	}

	public void calculateMatrix () {
		matrix = Letter.matrixToString(Letter.getConcentrationMatrix(img));
	}

	public int getNumRows () {

		return Letter.GRID_SIZE;
	}

	public int getNumCols () {

		return Letter.GRID_SIZE;
	}

	public String getConcentrationMatrix(){
		return matrix;
	}
	
	public String toString () {
		return getSizeRatio() + ":" + Letter.GRID_SIZE + "x" + Letter.GRID_SIZE + "\n" + getConcentrationMatrix();
	}

	/**
	 * @return
	 * 
	 *         returns width/height of the letter
	 */
	public float getSizeRatio () {

		return img.getWidth() / ((float) img.getHeight());
	}

	protected static HashMap<String, Character> letters;
	public static final int MAX_CONCENTRATION = 2;
	public static int GRID_SIZE = 4; // THIS SHOULD BE TREATED AS IF IT USES THE
										// FINAL MODIFIER. IT SHOULD NOT BE
										// MODIFIED. It doesn't use the final
										// modifier because the static
										// initializer needs to dynamically size
										// it based on hash sizes which it pulls
										// from online.
	public static final boolean GOD_MODE = true; // For on the 7th day god said
													// let there by letters and
													// so there was
													// But really, this is useless and no longer used

	static {
		if (!GOD_MODE) {
			letters = new HashMap<String, Character>();

			try {
				String str = HTTPTools.stringFromURL("http://localhost/TextRecognition/SeedImages/hashes.txt");
				Scanner scanner = new Scanner(str);
				while (scanner.hasNextLine()) {
					String t = scanner.nextLine();
					Character c = new Character(t.charAt(0));
					int matrixSize = Integer.parseInt(t.substring(t.lastIndexOf('x') + 1));
					GRID_SIZE = matrixSize;
					String matrix = "";
					for (int i = 0; i < matrixSize; i++)
						matrix += scanner.nextLine() + "\n";
					matrix = matrix.trim();
					letters.put(new String(matrix), c);
				}
			} catch (Exception exc) {
				System.err.println("Could not find hashes");
				exc.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public static Character getBestGuess (Letter l) {

		return null;
	}

	public static String matrixToString (byte[][] matrix) {

		String str = "";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				str += matrix[j][i];
			str += "\n";
		}
		return str.trim();
	}

	public static byte getConcentration (BufferedImage img, int xMin, int xMax, int yMin, int yMax) {

		int black = 0;
		for (int i = yMin; i < yMax; i++) {
			for (int j = xMin; j < xMax; j++) {
				Color color = new Color(img.getRGB(j, i));
				if (!ImageUtils.isWhite(color))
					black++;
			}
		}
		float total = ((xMax - xMin) * (yMax - yMin));
		float percent = black / total;
		percent *= MAX_CONCENTRATION;
		byte rounded = (byte) Math.round(percent);
		return rounded;
	}

	public static byte[][] getConcentrationMatrix (BufferedImage img) {

		byte[][] matrix = new byte[GRID_SIZE][GRID_SIZE];
		int x, y = x = 0;
		img = ImageUtils.parseImage(img);
		for (double i = 0; ((i + ((double) img.getHeight()) / GRID_SIZE)) <= img.getHeight(); i += ((double) img.getHeight() / GRID_SIZE)) {
			for (double j = 0; ((j + ((double) img.getWidth()) / GRID_SIZE)) <= img.getWidth(); j += ((double) img.getWidth()) / GRID_SIZE) {
				int xMin, xMax, yMin, yMax;
				xMin = (int) j;
				xMax = (int) (j + (img.getWidth()) / GRID_SIZE);
				yMin = (int) i;
				yMax = (int) (i + (img.getHeight()) / GRID_SIZE);
				matrix[x++][y] = getConcentration(img, xMin, xMax, yMin, yMax);
			}
			++y;
			x = 0;
		}
		return matrix;
	}

}
