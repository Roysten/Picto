package nl.saxion.act.playground;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import android.util.Log;


public class Puzzle {

	private int dimension, difficulty;
	private int [][] puzzleSolution;
	private int total = 0;
	private Pattern splitPattern = Pattern.compile(",");
	private int[][] columnHints, rowHints;
	
	public Puzzle(InputStream puzzle){
		parsePuzzel(puzzle);
	}
	
	private void parsePuzzel(InputStream puzzel) {
		long start = System.currentTimeMillis();
		Scanner lineScanner = new Scanner(puzzel);
		if(lineScanner.hasNextLine()){
			this.dimension = lineScanner.nextInt();
		}
		if(lineScanner.hasNextLine()){
			this.difficulty = lineScanner.nextInt();
		}
		lineScanner.nextLine();
		lineScanner.nextLine();
		if(lineScanner.hasNextLine()){
			puzzleSolution = new int[dimension][dimension];
			
			for(int i = 0; i < dimension; i++){
				String[] strArray = splitPattern.split(lineScanner.nextLine());
				int[] intArray = new int[strArray.length];
				for(int j = 0; j < strArray.length; j++){
					intArray[j] = Integer.parseInt(strArray[j]);
					total += intArray[j];
					puzzleSolution[i] = intArray;
				}
			}
		}
		long end = System.currentTimeMillis();
		Log.d("Puzzle", "Parsing took: " + (end - start));
	}
	
	public int[][] getRowHints(){
		rowHints = new int[dimension][1];
		for(int i = 0; i < dimension; i++){
			int[] row = puzzleSolution[i];
			int tempSum = 0;
			int numberOfGroups = 0;
			int[] rowCountArray = new int[(dimension / 2) + 1];
			for(int j = 0; j < dimension; j++){
				int rowNumber = row[j];
				if(j == dimension - 1 || rowNumber == 0 && tempSum != 0){
					tempSum += rowNumber;
					rowCountArray[numberOfGroups] = tempSum;
					numberOfGroups++;
					tempSum = 0;
				}
				else if(rowNumber == 1){
					tempSum += rowNumber;
				}
			}
			rowHints[i] = rowCountArray;
		}
		return rowHints;
	}
	
	public int[][] getColumnHints(){
		columnHints = new int[dimension][1];
		for(int i = 0; i < dimension; i++){
			int[] column = new int[dimension];
			for(int j = 0; j < dimension; j++){
				column[j] = puzzleSolution[j][i];
			}
			int tempSum = 0;
			int numberOfGroups = 0;
			int[] columnCountArray = new int[(dimension / 2) + 1];
			for(int j = 0; j < dimension; j++){
				int columnNumber = column[j];
				if(j == dimension - 1 || columnNumber == 0 && tempSum != 0){
					tempSum += columnNumber;
					columnCountArray[numberOfGroups] = tempSum;
					numberOfGroups++;
					tempSum = 0;
				}
				else if(columnNumber == 1){
					tempSum += columnNumber;
				}
			}
			columnHints[i] = columnCountArray;
		}
		return columnHints;
	}
	
	public int[] getColumnTotals(){
		int[] columnTotals = new int[dimension];
		for(int i = 0; i < dimension; i++){
			int sum = 0;
			int[] column = columnHints[i];
			for(int j : column){
				sum += j;
			}
			columnTotals[i] = sum;
		}
		return columnTotals;
	}
	
	public int[] getRowTotals(){
		int[] rowTotals = new int[dimension];
		for(int i = 0; i < dimension; i++){
			int sum = 0;
			int[] row = rowHints[i];
			for(int j : row){
				sum += j;
			}
			rowTotals[i] = sum;
		}
		return rowTotals;
	}
	
	/**
	 * @return the sizeX
	 */
	public int getDimension() {
		return dimension;
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
		return puzzleSolution;
	}
}
