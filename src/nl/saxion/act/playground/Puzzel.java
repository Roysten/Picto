package nl.saxion.act.playground;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Puzzel {

	private String naam;
	private int sizeX;
	private int sizeY;
	private int difficulty;
	private int [][] oplossing;
	private String [] verticalHints;
	private String [] horizontalHints;
	
	public Puzzel(File puzzel){
		initPuzzel(puzzel);
	}
	
	private void initPuzzel(File puzzel) {
		try {
			Scanner lineScanner = new Scanner(puzzel);
			Scanner scanner;
			if(lineScanner.hasNextLine()){
				this.naam=lineScanner.nextLine();
			}
			if(lineScanner.hasNextLine()){
				this.sizeX=Integer.parseInt(lineScanner.nextLine());
			}
			if(lineScanner.hasNextLine()){
				this.sizeY=Integer.parseInt(lineScanner.nextLine());
			}
			if(lineScanner.hasNextLine()){
				this.difficulty=Integer.parseInt(lineScanner.nextLine());
			}
			lineScanner.nextLine();
			if(lineScanner.hasNextLine()){
				oplossing=new int[sizeX][sizeY];
				for(int i=0;i<sizeX;i++){
					scanner= new Scanner(lineScanner.nextLine());
					scanner.useDelimiter(",");
					for(int j=0;j<sizeY;j++){
						oplossing[i][j]=scanner.nextInt();
					}
				}
			}
			lineScanner.nextLine();
			verticalHints= new String[sizeY];
			for(int i=0;i<sizeY;i++){
				verticalHints[i]=lineScanner.next();
			}
			lineScanner.nextLine();
			horizontalHints= new String[sizeX];
			for(int i=0;i<sizeX;i++){
				horizontalHints[i]=lineScanner.next();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}


	public String[] getVerticalHints(){
		return verticalHints;
	}
	
	public String[] getHorizontalHints(){
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
		for(int i=0;i<sizeX;i++){
			for(int j=0;j<sizeY;j++){
				result=result+oplossing[i][j];
			}
			result=result+"\n";
		}
		result=result+"\n";
		for(int i=0;i<sizeY;i++){
			result=result+verticalHints[i];
			result=result+"\n";
		}
		result=result+"\n";
		for(int i=0;i<sizeX;i++){
			result=result+horizontalHints[i];
			result=result+"\n";
		}
		
		
		
		return result;
		
	}
}
