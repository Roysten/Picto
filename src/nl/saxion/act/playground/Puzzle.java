package nl.saxion.act.playground;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Puzzle {

	private int sizeX;
	private int sizeY;
	private int difficulty;
	private int [][] solution;
	private int total = 0;
	private String [][] verticalHints;
	private String [][] horizontalHints;
	private Pattern splitPattern = Pattern.compile(",");
	
	public Puzzle(InputStream puzzle){
		parsePuzzel(puzzle);
	}
	
	private void parsePuzzel(InputStream puzzel) {
		Scanner lineScanner = new Scanner(puzzel);
		if(lineScanner.hasNextLine()){
			this.sizeX = lineScanner.nextInt();
		}
		if(lineScanner.hasNextLine()){
			this.sizeY = lineScanner.nextInt();
		}
		if(lineScanner.hasNextLine()){
			this.difficulty = lineScanner.nextInt();
		}
		lineScanner.nextLine();
		lineScanner.nextLine();
		if(lineScanner.hasNextLine()){
			solution = new int[sizeX][sizeY];
			
			for(int i = 0; i < sizeX; i++){
				String[] strArray = splitPattern.split(lineScanner.nextLine());
				int[] intArray = new int[strArray.length];
				
				for(int j = 0; j < strArray.length; j++){
					intArray[j] = Integer.parseInt(strArray[j]);
					total += intArray[j];
					solution[i] = intArray;
				}
			}
		}
		
		lineScanner.nextLine();
		verticalHints = new String[sizeY][1];
		for(int i = 0; i < sizeY; i++){
			verticalHints[i] = splitPattern.split(lineScanner.nextLine());
		}
		
		lineScanner.nextLine();
		horizontalHints = new String[sizeX][1];
		for(int i = 0; i < sizeX; i++){
			horizontalHints[i] = splitPattern.split(lineScanner.nextLine());
		}
	}

	public String[][] getVerticalHints(){
		return verticalHints;
	}
	
	public String[][] getHorizontalHints(){
		return horizontalHints;
	}
	
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}
	
	/**
	 * @return het totaal van zwarte vakjes
	 */
	public int getTotal(){
		return total;
	}

	/**
	 * @return the oplossing
	 */
	public int[][] getSolution() {
		return solution;
	}

	public String toString(){
		String result ="";
		result=result+"sizeX: "+sizeX+"\n";
		result=result+"sizeY: "+sizeY+"\n";
		result=result+"difficulty: "+difficulty+"\n";
		result=result+"\n";
		
		for(int i = 0; i < sizeX; i++){
			result += Arrays.toString(solution[i]) + "\n";
		}
		result += "\n";
		for(int i = 0; i < sizeY; i++){
			result += Arrays.toString(verticalHints[i]);
			result += "\n";
		}
		result += "\n";
		for(int i = 0; i < sizeX; i++){
			result += Arrays.toString(horizontalHints[i]);
			result += "\n";
		}
		return result;
	}
}
