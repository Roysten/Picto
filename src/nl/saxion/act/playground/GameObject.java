package nl.saxion.act.playground;

public class GameObject {
	/** @author Jan Stroet
	 *   Each game object has a tile visualization
	 *   
	 *   Every game object should inherit from this class
	 *   
	 */
	/* tileId: id of the bitmap, the object is associated with */
	
	private int tileId;
	
	/**
	 * Initializes GameObject the bitmap, the object is associated with
	 * @param itsTile id of 
	 */
	public GameObject(int itsTile){
		tileId = itsTile;
	}
	
	/**
	 * 
	 * @return id of the bitmap, the object is associated wit 
	 */
	public int getTileId(){
		return tileId;
	}
}
