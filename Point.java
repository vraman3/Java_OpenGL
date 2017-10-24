package cgFinalProj;

/**
 * Point.java
 * 
 * User-defined class to store Points
 * 
 */


/**
 * A class to hold x, y and z values for a vertex
 * or a vertex normal. And to hold tx and ty values 
 * for texture info of a vertex
 * 
 * @author Vishwanath
 *
 */
public class Point 
{
	float x, y, z, tx, ty;
	
	/**
	 * Constructor for a vertex and vertex normals
	 * 
	 * @param x		x coord of vertex
	 * @param y		y coord of vertex
	 * @param z		z coord of vertex
	 * 
	 */
	public Point(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructor for texture info of a vertex
	 * 
	 * @param tx		tx coord of vertex
	 * @param ty		ty coord of vertex
	 * 
	 */
	public Point(float tx, float ty) 
	{
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 * Display a vertex or vertex normal element of Point class
	 * 
	 */
	public String toString()
	{
		return this.x + " " + this.y + " " + this.z;
	}

}
