package cgFinalProj;

/**
 * ProjectionType.java
 * 
 * To perform perspective or orthographic projection
 * 
 * ( Code partly taken from class assignment)
 * 
 */
import javax.media.opengl.*; 

import Jama.Matrix;

/**
 * Class to calculate perspective and orthographic projections
 *
 */
public class ProjectionType
{
	float lookAtX, lookAtY, lookAtZ;
	float eyepointX, eyepointY, eyepointZ;
	float upX, upY, upZ;
	float tx, ty, tz;
    
	/**
	 * Default constructor
	 * 
	 */
	public ProjectionType()
	{
		this.lookAtX	= 0.0f;
		this.lookAtY	= 1.0f;
		this.lookAtZ	= 0.0f;
		this.eyepointX	= 1.0f;
		this.eyepointY	= 0.5f;
		this.eyepointZ	= 2.5f;
		this.upX		= -0.2f;
		this.upY		= 1.0f;
		this.upZ		= 0.0f;
		this.tx			= 0.0f;
		this.ty			= 0.0f;
		this.tz			= 0.0f;
		
	}
	
	/**
	 * Parameterized constructor with values for x, y and z coordinates
	 * of look-At-vector, eye-point-vector and up-vector of camera
	 * respectively
	 * 
	 */
	public ProjectionType(	float lookAtX,float  lookAtY, float  lookAtZ,
							float  eyepointX,float  eyepointY,float  eyepointZ,
							float  upX,float  upY,float  upZ)
	{
		this.lookAtX	= lookAtX;
		this.lookAtY	= lookAtY;
		this.lookAtZ	= lookAtZ;
		this.eyepointX	= eyepointX;
		this.eyepointY	= eyepointY;
		this.eyepointZ	= eyepointZ;
		this.upX		= upX;
		this.upY		= upY;
		this.upZ		= upZ;							
		
										
	}
    
	/**
	 * This function returns the required transformation matrix
	 * 
	 * @return		The final transformation matrix required
	 * 
	 */
    public Matrix operate()
    {
    	Matrix temp = Matrix.identity(4, 4);
    	
    	
    	// Initialize the camera coordinates as given
    	float[] lookAt	 = {lookAtX, lookAtY, lookAtZ };
    	float[] eyepoint = {eyepointX, eyepointY, eyepointZ };
    	float[] up		 = {upX, upY, upZ };
    	
    	// Calculate u, v and n
    	float[] u = new float[3];
    	float[] v = new float[3];
    	float[] n = new float[3];
    	
    	n = normalize( subtract(eyepoint, lookAt) );
    	u = normalize( crossProduct(up,n) );
    	v = crossProduct(n,u);
    	
    	// Create the world coordinate to camera coordinate
    	// transformation matrix
    	double vals2[][] = { {u[0], u[1], u[2], (-1) * dot(u,eyepoint)},
    						{v[0], v[1], v[2], (-1) * dot(v,eyepoint)},
    						{n[0], n[1], n[2], (-1) * dot(n,eyepoint)},
    						{0,0,0,1}};
    	
    	Matrix worldToCam = new Matrix(vals2);
    	
    	temp = worldToCam.times(temp);
    	
    	// Return the matrix with all the transformations
    	return temp;
    }
    
    /**
     * This function performs dot product of two vectors
     * 
     * @param 	a		First vector (array)
     * @param 	b		Second vector (array)
     * 
     * @return			Return the dot product of a and b
     * 
     */
    public float dot(float[] a, float[] b)
    {
    	float temp;
    	
    	temp = (a[0] * b[0]) + (a[1] * b[1]) + (a[2] * b[2]) ;
    	
    	return temp;
    }
    
    /**
     * This function performs subtraction of two vectors
     * 
     * @param 	a		First vector (array)
     * @param 	b		Second vector (array)
     * 
     * @return			Return the subtraction of a by b
     * 
     */
    public float[] subtract(float[] a, float[] b)
    {
    	float[] temp = new float[3];
    	
    	temp[0] = a[0] - b[0];
    	temp[1] = a[1] - b[1];
    	temp[2] = a[2] - b[2];
    	
    	return temp;
    }
    
    /**
     * This function performs cross product of two vectors
     * 
     * @param 	a		First vector (array)
     * @param 	b		Second vector (array)
     * 
     * @return			Return the cross product of a and b
     * 
     */
    public float[] crossProduct(float[] a, float[] b)
    {
    	float[] temp = new float[3];
    	
    	
    	temp[0] = ( a[1] * b[2] ) - ( a[2] * b[1] );
    	temp[1] = ( a[2] * b[0] ) - ( a[0] * b[2] );
    	temp[2] = ( a[0] * b[1] ) - ( a[1] * b[0] );
    	
    	return temp;
    }
    
    /**
     * This function normalizes the input
     * 
     * @param 	a		First vector (array)
     * 
     * @return			Return normalized form of array a
     * 
     */
    public float[] normalize(float[] a)
    {
    	float normalizeValue = (float) Math.sqrt( ( (a[0]*a[0]) + (a[1]*a[1]) + (a[2]*a[2]) ) );
    	
    	float[] temp = new float[3];
    	
    	temp[0] = a[0] / normalizeValue;
    	temp[1] = a[1] / normalizeValue;
    	temp[2] = a[2] / normalizeValue;
    	
    	return temp;
    	
    }
    
    /**
     * This functions sets up the view and projection parameter for a frustrum
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpFrustrum (int program, GL2 gl2)
    {
    	Matrix frus = operate();
    	
    	double l = -1.5;	//left
    	double r = 1.0;		//right
    	double t = 1.5;		//top
    	double b = -1.5;	//bottom
    	double n = 1.0;		//near
    	double f = 8.5;		//far
    	
    	// Create the transform matrix for perspective view
    	double valsP[][] = { { (2*n)/(r-l), 0, (r+l)/(r-l), 0},
							 { 0, (2*n)/(t-b), (t+b)/(t-b), 0},
							 { 0, 0, (-1)*( (f+n)/(f-n) ), ( (-2)*f*n ) / (f-n)},
							 {0, 0, -1, 0}};
    	
    	Matrix P = new Matrix(valsP);
    	
    	frus = P.times(frus);
    	
    	float[] frusFloat = new float[16];
    	
    	// Convert the matrix into a single dimensional float array
    	int count = 0;
    	for(int i = 0; i < 4; i ++)
    		for(int j = 0; j < 4; j ++)
    		{
    			frusFloat[count] = (float)frus.get(i, j);
    			count++;
    		}
    	
    	// Send the array to vshader
    	int location=gl2.glGetUniformLocation(program, "inputVertices");
    	gl2.glUniform1fv(location, 16, frusFloat, 0);
    }
    
    /**
     * This functions sets up the view and projection parameter for an orthographic
     * projection of the scene. See the assignment description for the values
     * for the projection parameters.
     *
     * @param program - The ID of an OpenGL (GLSL) program to which parameter values
     *    are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpOrtho (int program, GL2 gl2)
    {
    	Matrix ortho = operate();
    	
    	double l = -1.5;	//left
    	double r = 1.0;		//right
    	double t = 1.5;		//top
    	double b = -1.5;	//bottom
    	double n = 1.0;		//near
    	double f = 8.5;		//far
    	
    	// Create the transform matrix for orthographic view
    	double valsO[][] = { { 2/(r-l), 0, 0, (-1) * ( (r+l)/(r-l) )},
							 { 0, 2/(t-b), 0, (-1) * ( (t+b)/(t-b) )},
							 { 0, 0, (-2)/(f-n), (-1) * ( (f+n)/(f-n) )},
							 { 0, 0, 0, 1}};
    
    	Matrix O = new Matrix(valsO);
    	
    	ortho = O.times(ortho);
    	
    	float[] orthoFloat = new float[16];
    	
    	// Convert the matrix into a single dimensional float array
    	int count = 0;
    	for(int i = 0; i < 4; i ++)
    		for(int j = 0; j < 4; j ++)
    		{
    			orthoFloat[count] = (float)ortho.get(i, j);
    			count++;
    		}
    	    	
    	// Send the array to vshader
    	int location=gl2.glGetUniformLocation(program, "inputVertices");
    	gl2.glUniform1fv(location, 16, orthoFloat, 0);
    }
}
