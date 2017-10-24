package cgFinalProj;

/**
 * CreateShapes.java
 * 
 * Tesselate and create a cube, cylinder, cone or sphere
 * 
 */
import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;

/**
 * Generate shapes with fixed dimensions
 * 
 * @author Vishwanath
 *
 */
public class CreateShapes extends ShapeClass
{
	/*
	 * Inner class to store a point
	 * 
	 */
	public class Point
	{
		float x, y, z;
		
		// Default constructor
		Point()
		{
			
		}
		
		// Parameterized constructor
		Point(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		// Override toString()
		public String toString()
		{
			return "x:" + this.x + " " + this.y + " " + this.z;
		}
	}
	
    
    /**
	 * Default constructor
	 */
	public CreateShapes()
	{
	}
    	
    /**
     * makeCube - Create a unit cube, centered at the origin, with a given number
     * of subdivisions in each direction on each face.
     *
     * @param subdivision - number of equal subdivisons to be made in each 
     *        direction along each face
     *
     * Can only use calls to addTriangle()
     */
    public void makeCube (int subdivisions)
    {
    	float horz[] = new float[subdivisions + 1];
    	float vert[] = new float[subdivisions + 1];
    	
    	
    	// Front face
    	Point p1 = new Point(-0.5f, -0.5f, 0.5f);
    	Point p2 = new Point(0.5f, -0.5f, 0.5f);
    	Point p3 = new Point(0.5f, 0.5f, 0.5f);
    	Point p4 = new Point(-0.5f, 0.5f, 0.5f);
    	
    	float h = p1.x;
    	float v = p1.y;
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h + (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(horz[k], vert[j], 0.5f);
    			Point tempP2 = new Point(horz[k + 1], vert[j], 0.5f);
    			Point tempP3 = new Point(horz[k + 1], vert[j + 1], 0.5f);
    			Point tempP4 = new Point(horz[k], vert[j + 1], 0.5f);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    	// Back face
    	p1.y = p1.z = -0.5f;
    	p1.x = 0.5f;
    	p2.x = p2.y = p2.z = -0.5f;
    	p3.x = p3.z = -0.5f;
    	p3.y = 0.5f;
    	p4.x = p4.y = 0.5f;
    	p4.z = -0.5f;
    	
    	h = p1.x;
    	v = p1.y;
    	
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h - (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(horz[k], vert[j], -0.5f);
    			Point tempP2 = new Point(horz[k + 1], vert[j], -0.5f);
    			Point tempP3 = new Point(horz[k + 1], vert[j + 1], -0.5f);
    			Point tempP4 = new Point(horz[k], vert[j + 1], -0.5f);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    	// Left face
    	p1.x = p1.y = p1.z = -0.5f;
    	p2.x = p2.y = -0.5f;
    	p2.z = 0.5f;
    	p3.x = -0.5f;
    	p3.y = p3.z = 0.5f;
    	p4.y = 0.5f;
    	p4.x = p4.z = -0.5f;
    	
    	h = p1.z;
    	v = p1.y;
    	
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h + (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(-0.5f, vert[j], horz[k]);
    			Point tempP2 = new Point(-0.5f, vert[j], horz[k + 1]);
    			Point tempP3 = new Point(-0.5f, vert[j + 1], horz[k + 1]);
    			Point tempP4 = new Point(-0.5f, vert[j + 1], horz[k]);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    	// Right face
    	p1.x = p1.z = 0.5f;
    	p1.y = -0.5f;
    	p2.y = p2.z = -0.5f;
    	p2.x = 0.5f;
    	p3.x = p3.y = 0.5f;
    	p3.z = -0.5f;
    	p4.x = p4.y = p4.z = 0.5f;
    	
    	
    	h = p1.z;
    	v = p1.y;
    	
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h - (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(0.5f, vert[j], horz[k]);
    			Point tempP2 = new Point(0.5f, vert[j], horz[k + 1]);
    			Point tempP3 = new Point(0.5f, vert[j + 1], horz[k + 1]);
    			Point tempP4 = new Point(0.5f, vert[j + 1], horz[k]);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    	// Top face
    	p1.x = -0.5f;
    	p1.y = p1.z = 0.5f;
    	p2.x = p2.y = p2.z = 0.5f;
    	p3.x = p3.y = 0.5f;
    	p3.z = -0.5f;
    	p4.x = p4.z = -0.5f;
    	p4.y = 0.5f;
    	
    	
    	h = p1.x;
    	v = p1.z;
    	
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h + (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v - (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(horz[k], 0.5f, vert[j]);
    			Point tempP2 = new Point(horz[k + 1], 0.5f, vert[j]);
    			Point tempP3 = new Point(horz[k + 1], 0.5f, vert[j + 1]);
    			Point tempP4 = new Point(horz[k], 0.5f, vert[j + 1]);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    	// Bottom face
    	p1.x = p1.y = p1.z = -0.5f;
    	p2.y = p2.z = 0.5f;
    	p2.x = 0.5f;
    	p3.x = p3.z = 0.5f;
    	p3.y = -0.5f;
    	p4.x = p4.y = -0.5f;
    	p4.z = 0.5f;
    	
    	
    	h = p1.x;
    	v = p1.z;
    	
    	
    	for( int i = 0; i <= subdivisions; i++ )
    	{
    		horz[i] = h;
    		h = h + (float)(1/(float)subdivisions);
    	}
    	
    	for( int i = 0; i <= subdivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)subdivisions);
    	}
    	
    	for( int j = 0; j < vert.length - 1; j ++)
    		for( int k = 0; k < horz.length - 1; k ++)
    		{
    			Point tempP1 = new Point(horz[k], -0.5f, vert[j]);
    			Point tempP2 = new Point(horz[k + 1], -0.5f, vert[j]);
    			Point tempP3 = new Point(horz[k + 1], -0.5f, vert[j + 1]);
    			Point tempP4 = new Point(horz[k], -0.5f, vert[j + 1]);
    			
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP4.x, tempP4.y, tempP4.z);
    			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP2.x, tempP2.y, tempP2.z, tempP3.x, tempP3.y, tempP3.z);
    			
    		}
    	
    }
    
    /**
     * makeCylinder - Create polygons for a cylinder with unit height, centered at
     * the origin, with separate number of radial subdivisions and height 
     * subdivisions.
     *
     * @param radius - Radius of the base of the cylinder
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
    {
    	// Midpoint for top and bottom surfaces
    	Point midTop = new Point( 0f, 0.5f, 0f);
    	Point midBot = new Point( 0f, -0.5f, 0f );
    	
    	Point upper[] = new Point[radialDivisions + 1];
    	Point lower[] = new Point[radialDivisions + 1];
    	
    	// Calculate x and z coordinates using parametric equations
    	// Top circular surface
    	for(int i = 0; i <= radialDivisions; i ++)
    	{
    		upper[i] = new Point();
    		
    		upper[i].x = radius * (float)( Math.cos(  Math.toRadians((360.0/radialDivisions ) * i  )  ) );
    		upper[i].z = radius * (float)( Math.sin(  Math.toRadians((360.0/radialDivisions ) * i  )  ) );
    		upper[i].y = 0.5f;
    		
    	}
    	
    	// Bottom circle
    	for(int i = 0; i <= radialDivisions; i ++)
    	{
    		lower[i] = new Point();
    		
    		lower[i].x = radius * (float)( Math.cos( ( Math.toRadians(360.0/radialDivisions * ( i ) ) ) ) );
    		lower[i].z = radius * (float)( Math.sin( ( Math.toRadians(360.0/radialDivisions * ( i ) ) ) ) );
    		lower[i].y = -0.5f;
    		
    	}
    	
    	// Print both the top and bottom circular faces
    	for( int i = 0; i < radialDivisions; i ++ )
    	{
    		this.addTriangle(midTop.x, midTop.y, midTop.z, upper[i + 1].x, upper[i + 1].y, upper[i + 1].z, upper[i].x, upper[i].y, upper[i].z);
    	}
    	
    	for( int i = 0; i < radialDivisions; i ++ )
    	{
    		this.addTriangle(midBot.x, midBot.y, midBot.z, lower[i].x, lower[i].y, lower[i].z, lower[i + 1].x, lower[i + 1].y, lower[i + 1].z);
    	}
    	
    	
    	
    	// Curved Surface
    	float v = -0.5f;
    	
    	float vert[] = new float[heightDivisions + 1];
    	
    	for( int i = 0; i <= heightDivisions; i++)
    	{
    		vert[i] = v;
    		v = v + (float)(1/(float)heightDivisions);
    	}
    	
    	// Display the curved surface faces
    	for( int j = 0; j < heightDivisions; j ++ )
    		for(int k = 0; k < radialDivisions; k ++ )
    		{
    			if( k == radialDivisions - 1 )
    			{
    				Point tempP1 = new Point(lower[k].x, vert[j], lower[k].z);
    				Point tempP2 = new Point(lower[0].x, vert[j], lower[0].z);
    				Point tempP3 = new Point(lower[0].x, vert[j + 1], lower[0].z);
    				Point tempP4 = new Point(lower[k].x, vert[j + 1], lower[k].z);
    				
    				addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP4.x, tempP4.y, tempP4.z, tempP3.x, tempP3.y, tempP3.z );
        			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP2.x, tempP2.y, tempP2.z);
    			}
    			else
    			{
    				Point tempP1 = new Point(lower[k].x, vert[j], lower[k].z);
    				Point tempP2 = new Point(lower[k + 1].x, vert[j], lower[k + 1].z);
    				Point tempP3 = new Point(lower[k + 1].x, vert[j + 1], lower[k + 1].z);
    				Point tempP4 = new Point(lower[k].x, vert[j + 1], lower[k].z);
    				
    				addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP4.x, tempP4.y, tempP4.z, tempP3.x, tempP3.y, tempP3.z );
        			addTriangle(tempP1.x, tempP1.y, tempP1.z, tempP3.x, tempP3.y, tempP3.z, tempP2.x, tempP2.y, tempP2.z);
    			}
    		}
    }
    
    /**
     * makeCone - Create polygons for a cone with unit height, centered at the
     * origin, with separate number of radial subdivisions and height 
     * subdivisions.
     *
     * @param radius - Radius of the base of the cone
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCone (float radius, int radialDivisions, int heightDivisions)
    {
    	Point lower[][] = new Point[heightDivisions + 1][radialDivisions + 1];
    	
    	float rad[] = new float[heightDivisions + 1];
    	float vert = -0.5f;
    	
    	// Calculate radius for every subdivision along the height
    	for(int i = 0; i < heightDivisions; i ++)
    	{
    		rad[i] = radius * ( 1.0f - ( 1f /(float)heightDivisions) * i );
    	}
    	
    	// Calculate curved surface vertices for every subdivision along the height
    	for(int i = 0; i <= heightDivisions; i ++ )
    	{
    		for(int j = 0; j <= radialDivisions; j ++)
    		{
    			lower[i][j] = new Point();
    			lower[i][j].x = rad[i] * (float)Math.cos(Math.toRadians ( (360.0f / (float)radialDivisions) * j ) );
    			lower[i][j].z = rad[i] * (float)Math.sin(Math.toRadians ( (360.0f / (float)radialDivisions) * j ) );
    			lower[i][j].y = vert;
    		}
    		// Update the y coordinate for next iteration
    		vert += (1f / (float)heightDivisions);
    	}
    	
    	vert = -0.5f;
    	
    	// Print the bottom circular surface
    	for(int i = 0; i <= radialDivisions - 1; i ++)
    	{
    		this.addTriangle(lower[0][i].x, vert, lower[0][i].z, 0f, vert, 0f, lower[0][i+1].x, vert, lower[0][i+1].z );
    	}
    	
    	// Print the curved surface
    	for(int i = 0 ; i < heightDivisions; i++)
    		for(int j = 0 ; j < radialDivisions; j++)
    		{
    			this.addTriangle(lower[i][j].x, lower[i][j].y, lower[i][j].z, lower[i][j+1].x, lower[i][j+1].y, lower[i][j+1].z, lower[i+1][j+1].x, lower[i+1][j+1].y, lower[i+1][j+1].z);
    			this.addTriangle(lower[i][j].x, lower[i][j].y, lower[i][j].z, lower[i+1][j+1].x, lower[i+1][j+1].y, lower[i+1][j+1].z, lower[i+1][j].x, lower[i+1][j].y, lower[i+1][j].z);
    			
    		}
    }
    
    
    /**
     * makeSphere - Create sphere of a given radius, centered at the origin, 
     * using spherical coordinates with separate number of thetha and 
     * phi subdivisions.
     *
     * @param radius - Radius of the sphere
     * @param slides - number of subdivisions in the theta direction
     * @param stacks - Number of subdivisions in the phi direction.
     *
     * Can only use calls to addTriangle
     */
    public void makeSphere (float radius, int slices, int stacks)
    {
    	
    	Point lower[][] = new Point[stacks + 1][slices + 1];
    	float rad[] = new float[stacks + 1];
    	float vert = -0.5f;
    	
    	// Calculate radius for each stack
    	for(int i = 0; i <= stacks; i ++)
    	{
    		rad[i] = (float)Math.sqrt((0.5f * 0.5f) - (( 0.5f - ( ( 1.0f/(float)stacks) * i ) ) * ( 0.5f - ( ( 1.0f/(float)stacks) * i ) ) ));
		}
    	
    	vert = -0.5f;
    	
    	// Calculate the vertices for each stack
    	for(int i = 0; i <= stacks; i ++ )
    	{
    		for(int j = 0; j <= slices; j ++)
    		{
    			lower[i][j] = new Point();
    			lower[i][j].x = rad[i] * (float)Math.cos(Math.toRadians ( (360.0f / (float)slices) * j ) );
    			lower[i][j].z = rad[i] * (float)Math.sin(Math.toRadians ( (360.0f / (float)slices) * j ) );
    			lower[i][j].y = vert;
    		}
    		// Update the y coordinate for the next stack
    		vert += (1.0f / (float)stacks);
    	}
    	
    	
    	// Print all the triangles using the vertices
    	for(int i = 0 ; i < stacks; i++)
    		for(int j = 0 ; j < slices; j++)
    		{
    			this.addTriangle(lower[i][j].x, lower[i][j].y, lower[i][j].z, lower[i][j+1].x, lower[i][j+1].y, lower[i][j+1].z, lower[i+1][j+1].x, lower[i+1][j+1].y, lower[i+1][j+1].z);
    			this.addTriangle(lower[i][j].x, lower[i][j].y, lower[i][j].z, lower[i+1][j+1].x, lower[i+1][j+1].y, lower[i+1][j+1].z, lower[i+1][j].x, lower[i+1][j].y, lower[i+1][j].z);
    		}
    }
}
