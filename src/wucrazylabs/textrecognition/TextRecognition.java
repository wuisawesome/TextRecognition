package wucrazylabs.textrecognition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import wucrazylabs.imageparsing.Letter;
import wucrazylabs.sql.MySQL;
import wucrazylabs.sql.SQL;
import wucrazylabs.sql.SQLParam;


public class TextRecognition {
	
	public static final String HOST = "192.168.2.99";
	public static final int PORT = 3306;
	public static final String DB_NAME = "TextRecognition";
	public static final String TABLE_NAME = "CharacterHashes";
	public static final String USER_NAME = "_TextRecognition";
	public static final String PASSWORD = "";
	public static final String LETTER_COL = "letter", 
			WIDTH_COL = "width",
			HEIGHT_COL = "length",
			RATIO_COL = "ratio",
			MATRIX_COL = "hash";
	
	private SQL sql;
	
	public TextRecognition() throws SQLException, ClassNotFoundException{
		sql = new MySQL(USER_NAME, PASSWORD, HOST, PORT, DB_NAME);
	}
	
	public char getBestGuessForLetter(Letter l) throws SQLException{
		List<Map<String,String>> results = sql.SELECT(new String[]{"*"}, TABLE_NAME, new SQLParam[]{new SQLParam(WIDTH_COL, l.getNumCols()+""), new SQLParam(HEIGHT_COL, l.getNumRows()+""),  new SQLParam(MATRIX_COL, l.getConcentrationMatrix())},"ORDER BY ABS(" + l.getSizeRatio() + " - " + RATIO_COL + ")");
		return results.get(0).get("letter").charAt(0);
	}

	
	
}
