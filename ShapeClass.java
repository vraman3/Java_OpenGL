package cgFinalProj;

/**
 * ShapeClass.java
 * 
 * Read objects from a file to get corresponding
 * information regarding vertex, vertex normals and 
 * textures used for each triangle in all objects
 * 
 * (Code partly taken from class assignments)
 * 
 */
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.*;
import java.awt.event.*;
import java.io.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import java.util.*;

/**
 * Use to get information for each triangle from
 * a .obj file
 * 
 * @author Vishwanath
 *
 */
public class ShapeClass 
{
	/**
     * The vertices
     * 
     */
    private Vector<Float> points;
    
    /**
     * The array elements
     * 
     */
    private Vector<Short> elements;
    private short nVerts;
    
    /**
     * Textures
     */
    private Vector<Float> textures;
    
    /**
     *  Normals
     */
    private Vector<Float> normals;
    {
    	
    }
	/**
	 * Default Constructor for class
	 * 
	 */
	public ShapeClass()
	{
		//Initialize
		
        points = new Vector<Float>();
        elements = new Vector<Short>();
        textures = new Vector<Float>();
        normals = new Vector<Float>();
        nVerts = 0;
	}
	
	/**
	 * Get all info for all objects from a .obj file
	 * 
	 * @param  objFileName		Name of file to be parse
	 * 
	 * @throws Exception		Handle exception thrown by file
	 * 
	 */
	public void GetObjects(String objFileName) throws Exception
	{
		ReadFile readF = new ReadFile(objFileName);
		readF.getSingleObject();
		this.points = readF.returnPoints();
		this.elements = readF.returnElements();
		this.textures = readF.returnTextures();
		this.normals  = readF.returnNormals();
		this.nVerts	  = readF.returnNumberofVertices();
	
	}
	    
    /**
     * Add a triangle specified from the object file
     * 
     * @param x0	x coord of first vertex of triangle
     * @param y0	y coord of first vertex of triangle
     * @param z0	z coord of first vertex of triangle
     * @param x1	x coord of second vertex of triangle
     * @param y1	y coord of second vertex of triangle
     * @param z1	z coord of second vertex of triangle
     * @param x2	x coord of third vertex of triangle
     * @param y2	y coord of third vertex of triangle
     * @param z2	z coord of third vertex of triangle
     * 
     */
    protected void addTriangle (float x0, float y0, float z0,
                                float x1, float y1, float z1,
                                float x2, float y2, float z2)
    {
    	
        points.add (new Float(x0));
        points.add (new Float(y0));
        points.add (new Float(z0));
        points.add (new Float(1.0f));
        elements.add (new Short(nVerts));
        nVerts++;
        
        points.add (new Float(x1));
        points.add (new Float(y1));
        points.add (new Float(z1));
        points.add (new Float(1.0f));
        elements.add (new Short(nVerts));
        nVerts++;
        
        points.add (new Float(x2));
        points.add (new Float(y2));
        points.add (new Float(z2));
        points.add (new Float(1.0f));
        elements.add (new Short(nVerts));
        nVerts++;
        
    }
    
    /**
     * Clear the shape
     * 
     */
    public void clear()
    {
        points= new Vector<Float>(); 
        elements = new Vector<Short>();
        nVerts = 0;
    }
    
    /**
     * Send all vertices as a buffer
     * 
     * @return		Return vertices as a buffer array
     * 
     */
    public Buffer getVerticies ()
    {
        float v[] = new float[points.size()];
        for (int i=0; i < points.size(); i++) {
            v[i] = (points.elementAt(i)).floatValue();
        }
        
        return FloatBuffer.wrap (v);
    }
    
    /**
     * Send all elements as a buffer
     * 
     * @return		Return array elements as a buffer array
     * 
     */
    public Buffer getElements ()
    {
        short e[] = new short[elements.size()];
        for (int i=0; i < elements.size(); i++) {
            e[i] = (elements.elementAt(i)).shortValue();
        }

        return ShortBuffer.wrap (e);
    }
    
    /**
     * Send all textures as a buffer
     * 
     * @return		Return textures as a buffer array
     * 
     */
    public Buffer getTextures()
    {
        float t[] = new float[textures.size()];
        for (int i=0; i < textures.size(); i++) {
            t[i] = (textures.elementAt(i)).floatValue();
        }

        return FloatBuffer.wrap(t);
    }
    
    /**
     * Send all vertex normals as a buffer
     * 
     * @return		Return vertex normals as a buffer array
     * 
     */
    public Buffer getNormals()
    {
        float n[] = new float[normals.size()];
        for (int i=0; i < normals.size(); i++) {
            n[i] = (normals.elementAt(i)).floatValue();
        }

        return FloatBuffer.wrap(n);
    }
    
    /**
     * Return the number of vertices of all objects in file
     * 
     * @return		Return the number of vertices
     * 
     */
    public short getNVerts()
    {
        return nVerts;
    }

}
