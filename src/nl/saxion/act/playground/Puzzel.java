package nl.saxion.act.playground;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import android.util.Log;


public class Puzzel {

	private String naam;
	private int sizeX;
	private int sizeY;
	private int difficulty;
	private int [][] oplossing;
	private String [][] verticalHints;
	private String [][] horizontalHints;
	
	public Puzzel(InputStream puzzel){
		initPuzzel(puzzel);
	}
	
	private void initPuzzel(InputStream puzzel) {
		long start = System.currentTimeMillis();
		Scanner lineScanner = new Scanner(puzzel);
		if(lineScanner.hasNextLine()){
			this.naam=lineScanner.nextLine();
		}
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
			oplossing=new int[sizeX][sizeY];
			for(int i = 0; i < sizeX; i++){
				String[] strArray = lineScanner.nextLine().split(",");
				int[] intArray = new int[strArray.length];
				for(int j = 0; j < strArray.length; j++){
					intArray[j] = Integer.parseInt(strArray[j]);
					oplossing[i] = intArray;
				}
			}
		}
		lineScanner.nextLine();
		verticalHints = new String[sizeY][1];
		for(int i = 0; i < sizeY; i++){
			verticalHints[i] = lineScanner.nextLine().split(",");
		}
		lineScanner.nextLine();
		horizontalHints = new String[sizeX][1];
		for(int i = 0; i < sizeX; i++){
			horizontalHints[i] = lineScanner.nextLine().split(",");
		}
		long end = System.currentTimeMillis();
		Log.d("Puzzel", "Parse time puzzle: " + (end - start) + "ms");
	}


	public String[][] getVerticalHints(){
		return verticalHints;
	}
	
	public String[][] getHorizontalHints(){
		return horizontalHints;
	}
	

	/**
	 * @return the naam
	 */
	public String getNaam() {
		return naam;
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
	 * @return the oplossing
	 */
	public int[][] getOplossing() {
		return oplossing;
	}

	public String toString(){
		String result ="";
		result=result+"puzzel naam: "+naam+"\n";
		result=result+"sizeX: "+sizeX+"\n";
		result=result+"sizeY: "+sizeY+"\n";
		result=result+"difficulty: "+difficulty+"\n";
		result=result+"\n";
		
		for(int i = 0; i < sizeX; i++){
			result += Arrays.toString(oplossing[i]) + "\n";
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
