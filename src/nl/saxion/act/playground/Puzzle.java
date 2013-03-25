package nl.saxion.act.playground;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import android.util.Log;


public class Puzzle {

	private int dimension;
	private int [][] puzzleSolution;
	private int total = 0;
	private Pattern splitPattern = Pattern.compile(",");
	private int[][] columnHints, rowHints;
	private int[] columnTotal, rowTotal;
	
	public Puzzle(InputStream puzzle){
		parsePuzzel(puzzle);
	}
	
	private void parsePuzzel(InputStream puzzel) {
		long start = System.currentTimeMillis();
		Scanner lineScanner = new Scanner(puzzel);
		if(lineScanner.hasNextLine()){
			this.dimension = lineScanner.nextInt();
		}
		lineScanner.nextLine();
		lineScanner.nextLine();
		
		//puzzel opslaan in een 2-dimensionale array
		//Van string array naar int array, zodat we ermee kunnen rekenen
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
		
		//aantallen voor de rijen berekenen
		rowHints = new int[dimension][1];
		rowTotal = new int[dimension];
		for(int i = 0; i < dimension; i++){
			int[] row = puzzleSolution[i];
			int tempSum = 0;
			int numberOfGroups = 0;
			int[] rowCountArray = new int[(dimension / 2) + 1]; //veilige lengte, zo klein mogelijk
			for(int j = 0; j < dimension; j++){
				int rowNumber = row[j];
				if(j == dimension - 1 || rowNumber == 0 && tempSum != 0){
					rowCountArray[numberOfGroups] = tempSum + rowNumber;
					numberOfGroups++;
					tempSum = 0;
				}
				else if(rowNumber == 1){
					tempSum += rowNumber;
				}
				rowTotal[i] += rowNumber;
			}
			rowHints[i] = rowCountArray;
		}
		Log.d("puzzle", Arrays.toString(rowTotal));
		
		//aantallen voor de kolommen berekenen
		columnHints = new int[dimension][1];
		columnTotal = new int[dimension];//totale aantallen kolom ook meteen bijhouden
		for(int i = 0; i < dimension; i++){
			int[] column = new int[dimension];
			for(int j = 0; j < dimension; j++){
				column[j] = puzzleSolution[j][i];
			}
			int tempSum = 0;
			int numberOfGroups = 0;
			int[] columnCountArray = new int[(dimension / 2) + 1];//veilige lengte, zo klein mogelijk
			for(int j = 0; j < dimension; j++){
				int columnNumber = column[j];
				if(j == dimension - 1 || columnNumber == 0 && tempSum != 0){
					columnCountArray[numberOfGroups] = tempSum + columnNumber;
					numberOfGroups++;
					tempSum = 0;
				}
				else if(columnNumber == 1){
					tempSum += columnNumber;
				}
				columnTotal[i] += columnNumber;
			}
			columnHints[i] = columnCountArray;
		}
		Log.d("puzzle", Arrays.toString(columnTotal));
		
		long end = System.currentTimeMillis();
		Log.d("Puzzle", "Parsing took: " + (end - start));
	}
	
	public int[][] getRowHints(){
		return rowHints;
	}
	
	public int[][] getColumnHints(){
		return columnHints;
	}
	
	public int[] getColumnTotals(){
		return columnTotal;
	}
	
	public int[] getRowTotals(){
		return rowTotal;
	}
	
	/**
	 * @return the sizeX
	 */
	public int getDimension() {
		return dimension;
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
