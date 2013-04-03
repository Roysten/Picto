package nl.saxion.act.playground;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 	Class waarin puzzelfiles worden geparsed naar PuzzleObjecten
 *
 */
public class Puzzle {

	private int dimension;
	private int [][] puzzleSolution;
	private int total = 0;
	private Pattern splitPattern = Pattern.compile(",");
	private int[][] columnHints, rowHints;
	private int[] columnTotal, rowTotal;
	
	/**
	 * Puzzel parsen meteen aanroepen bij aanmaken puzzel object
	 * @param puzzle
	 */
	public Puzzle(InputStream puzzle){
		parsePuzzel(puzzle);
	}
	
	/**
	 * Puzzel parsen uit de tekstfile mbv scanner en split
	 * @param puzzel de puzzelfile om te parsen
	 */
	private void parsePuzzel(InputStream puzzel) {
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
	}
	
	/**
	 * @return de hints van de rijen (dubbele int array)
	 */
	public int[][] getRowHints(){
		return rowHints;
	}
	
	/**
	 * @return de hints van de kolommen (dubbele int array)
	 */
	public int[][] getColumnHints(){
		return columnHints;
	}
	
	/**
	 * @return het totaal aantal ingevulde vakjes voor elke kolom
	 */
	public int[] getColumnTotals(){
		return columnTotal;
	}
	
	/**
	 * @return het totaal aantal ingevulde vakjes voor elke rij
	 */
	public int[] getRowTotals(){
		return rowTotal;
	}
	
	/**
	 * @return De grootte van het spelbord
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * @return het totaal van gevulde vakjes (mbv fill, niet cross)
	 */
	public int getTotal(){
		return total;
	}

	/**
	 * @return de oplossing
	 */
	public int[][] getSolution() {
		return puzzleSolution;
	}
}
