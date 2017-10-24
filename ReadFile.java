package cgFinalProj;

/**
 * ReadFile.java
 * 
 * Read a .obj file to get object information
 * 
 * @Author - Vishwanath
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

/**
 * This class reads from a specified .obj file to get the
 * vertices, textures and normals
 * 
 * @author Vishwanath
 *
 */
public class ReadFile
{
	/**
     * The vertices
     */
    private Vector<Float> readVertices;
    
    /**
     * The array elements
     */
    private Vector<Short> readElements;
    private short nVerts;
    
    /**
     * The Textures
     */
    private Vector<Float> readTextures;
    
    /**
     *  The Normals
     */
    private Vector<Float> readNormals;
    
    // Initialize variables to store intermittent values
    
	Vector<Point> objVertices = new Vector<Point>();
	Vector<Point> objTextures = new Vector<Point>();
	Vector<Point> objNormals  = new Vector<Point>();
	Vector<Integer> triangles = new Vector<Integer>();
	Vector<Integer> textureIndex = new Vector<Integer>();
	Vector<Integer> vertexNormalIndex = new Vector<Integer>();
	
	BufferedReader reader;
	String fileName;
	
	/**
	 * Default constructor that reads .obj filename and 
	 * initializes all variables
	 * 
	 * @throws Exception	To handle file not found exception
	 * 
	 */
	ReadFile(String inputFileName) throws Exception
	{
		this.fileName = inputFileName;
		readVertices = new Vector<Float>();
		readElements = new Vector<Short>();
		readTextures = new Vector<Float>();
		readNormals = new Vector<Float>();
	}
	
	/**
	 * Stores all the vertices, textures, and normals used alongwith
	 * the vertex, texture and normal indexes for each triangle
	 * 
	 * @throws Exception		To handle File Not Found exception
	 * 
	 */
	public void getSingleObject()throws Exception
	{
		// Read from file
		reader = new BufferedReader(new FileReader(fileName));
				
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
			// Store all the vertices to be used
			if(line.length() != 0 && line.charAt(0) == 'v' && line.charAt(1) == ' ')
			{
				String[] temp = line.split(" ");
				objVertices.add(new Point(Float.parseFloat(temp[1]), Float.parseFloat(temp[2]), Float.parseFloat(temp[3])));
			}
			
			// Store all the textures to be used
			if(line.length() != 0 && line.charAt(0) == 'v' && line.charAt(1) == 't')
			{
				String[] temp = line.split(" ");
				objTextures.add(new Point(Float.parseFloat(temp[1]), Float.parseFloat(temp[2])));
			}
			
			// Store all the vertex normals to be used
			if(line.length() != 0 && line.charAt(0) == 'v' && line.charAt(1) == 'n')
			{
				String[] temp = line.split(" ");
				objNormals.add(new Point(Float.parseFloat(temp[1]), Float.parseFloat(temp[2]), Float.parseFloat(temp[3])));
			}


			// Store triangle info
			if(line.length() != 0 && line.charAt(0) == 'f')
			{
				String[] temp = line.split(" ");
				
				String[] first = temp[1].split("/");
				String[] second = temp[2].split("/");
				String[] third = temp[3].split("/");
				
				// Get vertex indexes for each triangle
				triangles.add(Integer.parseInt(first[0]) - 1);
				triangles.add(Integer.parseInt(second[0]) - 1);
				triangles.add(Integer.parseInt(third[0]) - 1);
				
				// Get texture indexes for each triangle
				textureIndex.add(Integer.parseInt(first[1]) - 1);
				textureIndex.add(Integer.parseInt(second[1]) - 1);
				textureIndex.add(Integer.parseInt(third[1]) - 1);
				
				// Get vertex normals indexes for each triangle
				vertexNormalIndex.add(Integer.parseInt(first[2]) - 1);
				vertexNormalIndex.add(Integer.parseInt(second[2]) - 1);
				vertexNormalIndex.add(Integer.parseInt(third[2]) - 1);
			}
		}
		
		SendVertices();
		SendTextures();
		SendNormals();
	}
	
	/**
	 * Store the vertex info for all triangles in the object
	 * 
	 * @param x0		x coord of first vertex
     * @param y0		y coord of first vertex
     * @param z0		z coord of first vertex
     * @param x1		x coord of second vertex
     * @param y1		y coord of second vertex
     * @param z1		z coord of second vertex
     * @param x2		x coord of third vertex
     * @param y2		y coord of third vertex
     * @param z2		z coord of third vertex
     * 
     */
    public void addTriangleForObject (float x0, float y0, float z0,
                                float x1, float y1, float z1,
                                float x2, float y2, float z2)
    {
    	
        readVertices.add (new Float(x0));
        readVertices.add (new Float(y0));
        readVertices.add (new Float(z0));
        readVertices.add (new Float(1.0f));
        readElements.add (new Short(nVerts));
        nVerts++;
        
        readVertices.add (new Float(x1));
        readVertices.add (new Float(y1));
        readVertices.add (new Float(z1));
        readVertices.add (new Float(1.0f));
        readElements.add (new Short(nVerts));
        nVerts++;
        
        readVertices.add (new Float(x2));
        readVertices.add (new Float(y2));
        readVertices.add (new Float(z2));
        readVertices.add (new Float(1.0f));
        readElements.add (new Short(nVerts));
        nVerts++;
        
    }
    
    /**
     * Store the texture info for all triangles in the object
     * 
     * @param x0		tx coord of first vertex texture
     * @param y0		ty coord of first vertex texture
     * @param x1		tx coord of second vertex texture
     * @param y1		ty coord of second vertex texture
     * @param x2		tx coord of third vertex texture
     * @param y2		ty coord of third vertex texture
     * 
     */
    public void addTexturesForObjects(float x0, float y0, float x1, float y1, float x2, float y2)
    {
    	readTextures.add(new Float(x0));
    	readTextures.add(new Float(y0));
    	
    	readTextures.add(new Float(x1));
    	readTextures.add(new Float(y1));
    	
    	readTextures.add(new Float(x2));
    	readTextures.add(new Float(y2));
    	
    }
    
    
    /**
     * Store the vertex normal info for all triangles in the object
     * 
     * @param x0		x coord of first vertex normal
     * @param y0		y coord of first vertex normal
     * @param z0		z coord of first vertex normal
     * @param x1		x coord of second vertex normal
     * @param y1		y coord of second vertex normal
     * @param z1		z coord of second vertex normal
     * @param x2		x coord of third vertex normal
     * @param y2		y coord of third vertex normal
     * @param z2		z coord of third vertex normal
     * 
     */
    public void addNormalsForObject (float x0, float y0, float z0,
                                float x1, float y1, float z1,
                                float x2, float y2, float z2)
    {
    	
        readNormals.add (new Float(x0));
        readNormals.add (new Float(y0));
        readNormals.add (new Float(z0));
                
        readNormals.add (new Float(x1));
        readNormals.add (new Float(y1));
        readNormals.add (new Float(z1));
        
        readNormals.add (new Float(x2));
        readNormals.add (new Float(y2));
        readNormals.add (new Float(z2));
        
    }
    
    
    
    /**
     *  Use vertex indexes to store vertex information for
     *  each triangle for all objects in file
     *  
     */
	public void SendVertices()
	{
		for(int i = 0; i < triangles.size(); i += 3)
		{
			this.addTriangleForObject(objVertices.get(triangles.get(i)).x, objVertices.get(triangles.get(i)).y, objVertices.get(triangles.get(i)).z,
					objVertices.get(triangles.get(i+1)).x, objVertices.get(triangles.get(i+1)).y, objVertices.get(triangles.get(i+1)).z,
					objVertices.get(triangles.get(i+2)).x, objVertices.get(triangles.get(i+2)).y, objVertices.get(triangles.get(i+2)).z);
		}
	}
	
	/**
     *  Use texture indexes to store texture information for
     *  each triangle for all objects in file
     *  
     */
	public void SendTextures()
	{
		for(int i = 0; i < textureIndex.size(); i += 3)
		{
			this.addTexturesForObjects(	objTextures.get(textureIndex.get(i)).tx, objTextures.get(textureIndex.get(i)).ty,
										objTextures.get(textureIndex.get(i+1)).tx, objTextures.get(textureIndex.get(i+1)).ty,
										objTextures.get(textureIndex.get(i+2)).tx, objTextures.get(textureIndex.get(i+2)).ty);
		}
	}
	
	/**
     *  Use vertex normal indexes to store vertex normal information for
     *  each triangle for all objects in file
     *  
     */
	public void SendNormals()
	{
		for(int i = 0; i < vertexNormalIndex.size(); i += 3)
		{
			this.addNormalsForObject(objNormals.get(vertexNormalIndex.get(i)).x, objNormals.get(vertexNormalIndex.get(i)).y, objNormals.get(vertexNormalIndex.get(i)).z,
					objNormals.get(vertexNormalIndex.get(i+1)).x, objNormals.get(vertexNormalIndex.get(i+1)).y, objNormals.get(vertexNormalIndex.get(i+1)).z,
					objNormals.get(vertexNormalIndex.get(i+2)).x, objNormals.get(vertexNormalIndex.get(i+2)).y, objNormals.get(vertexNormalIndex.get(i+2)).z);
		}
	}
	
	/**
	 * Return all the vertices for all objects
	 * 
	 * @return		Return Vector of vertices
	 * 
	 */
	public Vector<Float> returnPoints()
    {
    	return readVertices;
    }
    
	/**
	 * Return all the elements for all objects
	 * 
	 * @return		Return Vector of elements
	 * 
	 */
    public Vector<Short> returnElements()
    {
    	return readElements;
    }
    
    /**
	 * Return all the textures for all objects
	 * 
	 * @return		Return Vector of textures
	 * 
	 */
    public Vector<Float> returnTextures()
    {
    	return readTextures;
    }
    
    /**
	 * Return number of vertices for all objects
	 * 
	 * @return		Return the number of vertices
	 * 
	 */
    public short returnNumberofVertices()
    {
    	return nVerts;
    }
    
    /**
	 * Return all the vertex normals for all objects
	 * 
	 * @return		Return Vector of vertex normals
	 * 
	 */
    public Vector<Float> returnNormals()
    {
    	return readNormals;
    }
    
	
	/**
     * Print the vertices of each triangle for
     * all objects in file
     * 
     */
	public void printTriangles()
	{	
		System.out.println("Triangles:");
		
		for(int i = 0; i < triangles.size(); i ++)
		{
			if(i % 3 == 0 && i != 0)
				System.out.println();
			System.out.print(triangles.get(i) + " ");
		}
		System.out.println("\n");
	}
	
	/**
     * Print all the vertices used for
     * all objects in the file
     * 
     */
	public void printVertices()
	{
		System.out.println("Vertices:");
		System.out.println(objVertices.size());
		for(int i = 0; i < objVertices.size(); i ++)
		{
			System.out.println(objVertices.get(i));
		}
		System.out.println();
	}
	
	/**
     * Print all the vertex normals used for
     * all objects in file
     * 
     */
	public void printVertexNormals()
	{
		System.out.println("Vertex Normals:");
		System.out.println(objNormals.size());
		for(int i = 0; i < objNormals.size(); i ++)
		{
			System.out.println(objNormals.get(i));
		}
		System.out.println();
	}
	
	/**
     * Print all the textures used for
     * all objects in file
     * 
     */
	public void printTextures()
	{
		System.out.println("Textures:");
		System.out.println(objTextures.size());
		for(int i = 0; i < objTextures.size(); i ++)
		{
			System.out.println(objTextures.get(i).tx + " " + objTextures.get(i).ty);
		}
		System.out.println();
	}
	
	
	
	/**
     * Print the textures indexes of each triangle for
     * all objects in file
     * 
     */
	public void printTextureIndex()
	{	
		System.out.println("Texture Index:");
		
		for(int i = 0; i < textureIndex.size(); i ++)
		{
			if(i % 3 == 0 && i != 0)
				System.out.println();
			System.out.print(textureIndex.get(i) + " ");
		}
		System.out.println("\n");
	}
	
	/**
     * Print the vertex normal indexes of each triangle
     * for all objects in file
     * 
     */
	public void printVertexNormalIndex()
	{	
		System.out.println("Vertex Normal Index:");
		
		for(int i = 0; i < vertexNormalIndex.size(); i ++)
		{
			if(i % 3 == 0 && i != 0)
				System.out.println();
			System.out.print(vertexNormalIndex.get(i) + " ");
		}
		System.out.println("\n");
	}
	
	
}