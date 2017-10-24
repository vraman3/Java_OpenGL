package cgFinalProj;

/**
 * MainFunc.java
 * 
 * Read object info from an object file and render it using
 * OpenGL
 * 
 * (Code partly taken from class assignments)
 * 
 */
import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.*;


public class MainFunc implements GLEventListener, KeyListener
{
	BufferedImage bi;
	IntBuffer	  B;
	
	/**
	 * rotation angles
	 */
    public int theta;
    public float angles[];
    private float angleInc = 5.0f;
    
	/**
	 * buffer info 
	 */
    private boolean bufferInit = false;
    private int vBuffer;
    private int eBuffer;
    int viewMode;
    //private int ebuffer;
    
    /**
	 * shader info
	 */
	private TheShaderClass myShaders;
	private int shaderProgID = 0;
	private int shaderProgIDTexture = 0;
    private boolean updateNeeded = true;
    
    /**
     * Object of ShapeClass to parse an object from
     * a .obj file
     * 
     */
    ShapeClass myShape;
    
    /**
     * Canvas
     */
    GLCanvas myCanvas;

    /**
     * Object ProjectionType to calculate
     * projection matrix
     * 
     */
    ProjectionType proj;
    
    /**
	 * Constructor
	 * 
	 */
	public MainFunc(GLCanvas G)
	{
		// Set up rotation angle for viewing
		angles = new float[3];
        angles[0] = 30.0f;
        angles[1] = 30.0f;
        angles[2] = 0.0f;
		
        // View mode - Default is perspective
        viewMode = 1;
        
        // Initialization
		myShaders = new TheShaderClass();
        myShape = new ShapeClass();
        myCanvas = G;
        proj = new ProjectionType();
        
        G.addGLEventListener (this);
        G.addKeyListener (this);
	}
	
	/**
	 * Called by the drawable to initiate OpenGL rendering by the client.
	 * 
	 */
	public void display(GLAutoDrawable drawable)
	{
		// get GL
		GL2 gl2 = (drawable.getGL()).getGL2();

		//gl2.glClearColor(0.0f, 0.5f, 1.0f, 1.0f);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		
		/**
		 * Code to display background texture
		 * 
		 * Could not get it to work
		 * 
		 */
		
		/*
		 * 
		 *	// Bind vertex buffer
         *	gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vBuffer);
         *   
         *	// Bind element array buffer
         *	gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffer);
         *   
         *	// Set up attribute variables
         *	gl2.glUseProgram (shaderProgIDTexture);
         *	long dataSize = 6 * 4l * 4l;
         *	int  vPosition = gl2.glGetAttribLocation (shaderProgIDTexture, "vPosition");
         *	gl2.glEnableVertexAttribArray ( vPosition );
         *	gl2.glVertexAttribPointer (vPosition, 4, GL.GL_FLOAT, false, 0, 0l);
         *
         *	int  vTex = gl2.glGetAttribLocation (shaderProgIDTexture, "vTexCoord");
         *	gl2.glEnableVertexAttribArray ( vTex );
         *	gl2.glVertexAttribPointer (vTex, 2, GL.GL_FLOAT, false, 0, dataSize);
         *  
         *	// Setup uniform variables for texture
         * 	setUpTextures (shaderProgIDTexture, gl2);
		 *
		 *  // Draw background shape and texture
         *	gl2.glDrawElements ( GL.GL_TRIANGLES, 6,  GL.GL_UNSIGNED_SHORT, 0l);
         *
         */
		
        gl2.glUseProgram(shaderProgID);
        
		// Read the object file
		try 
		{
			myShape.GetObjects("stickfigure.obj");
			
		} catch (Exception e) 
		{
			System.out.println("Error reading object file (.obj) ");
			e.printStackTrace();
		}
		
		// Get all required buffers
		
		Buffer texCoords = myShape.getTextures();
		Buffer normals	 = myShape.getNormals();
		Buffer vertexBuffer = myShape.getVerticies();
//		//Buffer elementBuffer = myShape.getElements();
		
		int verts = myShape.getNVerts() * 3;
		
		long vertexBufferSize  = verts * 4l *4l;
		long textureBufferSize = verts *2l *4l;
		
		int bufferIndices[] = new int[1];
		
		
		// Generate buffer for vertices
		gl2.glGenBuffers(1, bufferIndices, 0);
		
		
		// Bind the vertices to Array Buffer
		vBuffer = bufferIndices[0];
		gl2.glBindBuffer(GL.GL_ARRAY_BUFFER, bufferIndices[0]);
		gl2.glBufferData(GL.GL_ARRAY_BUFFER, vertexBufferSize, vertexBuffer, GL.GL_STATIC_DRAW);
		
		// Get shader program id
		int bla = gl2.glGetAttribLocation(shaderProgID, "vPosition");
		
		// Enable the vertex array and assign what kind of values it is dealing with (Float here)
		gl2.glEnableVertexAttribArray(bla);
		
		gl2.glVertexAttribPointer(bla, 4, GL.GL_FLOAT, false, 0, 0l);
		
		// Pass in rotations as a uniform variable
        theta = gl2.glGetUniformLocation (shaderProgID, "theta");
		gl2.glUniform3fv (theta, 1, angles, 0);
		   	
		// Setup viewing and projection.
        if (viewMode == 1) 
        	proj.setUpFrustrum (shaderProgID, gl2);
        else if (viewMode == 2) 
        	proj.setUpOrtho (shaderProgID, gl2);
        
        
		// Draw the elements
		gl2.glDrawArrays(GL.GL_TRIANGLES, 0, verts);
                
    }
	
	/**
	 * Setup textures for the background
	 * 
	 * @param program		Shader program ID
	 * @param gl2			GL object
	 * 
	 */
	public void setUpTextures (int program, GL2 gl2)
    {
    	gl2.glUseProgram(program);
    	int baseImageLoc = gl2.glGetUniformLocation(program, "B");
        	    	
    	gl2.glUniform1i(baseImageLoc, 0); //Texture unit 0
    
    	//When rendering an object with this program.
    	gl2.glGenTextures(1, B);
    	gl2.glBindTexture(gl2.GL_TEXTURE_2D, baseImageLoc);
    	
    	gl2.glTexParameteri(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MAG_FILTER, gl2.GL_LINEAR);
    	gl2.glTexParameteri(gl2.GL_TEXTURE_2D, gl2.GL_TEXTURE_MIN_FILTER, gl2.GL_LINEAR);
    	
    	gl2.glTexImage2D(gl2.GL_TEXTURE_2D, 0, gl2.GL_RGBA, bi.getWidth(), bi.getHeight(), 0, gl2.GL_BGRA, gl2.GL_UNSIGNED_BYTE, B);
    	gl2.glActiveTexture(gl2.GL_TEXTURE0 + 0);
    	
    }
	
	/**
	 * Load background texture details from a file
	 * 
	 * @param filename		Name of texture file
	 * 
	 */
	public void loadTexture (String filename)
    {
    	File f = new File(filename);
    	try
    	{
    		
    		bi = ImageIO.read(f);
    	
    		int pixels[] = bi.getRGB (0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
    		B = IntBuffer.wrap (pixels);
    	}catch(Exception e)
    	{
    		System.out.println("loadTexture Error:");
    	}
    }
	
	
	/**
	 * Create a shape for displaying the background texture
	 * 
	 * @param	gl2		GL object
	 * 
	 */
	public void createBackground(GL2 gl2)
	{
		float[] tempPoints = {	-0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
								-0.5f, -0.5f, 0.5f,	0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
		
		float[] tempTextures =  { 1f, 1f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f};
		
		short[] tempElements = {0, 1, 2, 3, 4, 5};
		
        // Getvertices and elements
        Buffer points = FloatBuffer.wrap(tempPoints);
        Buffer elements = ShortBuffer.wrap(tempElements);
        Buffer texCoords = FloatBuffer.wrap(tempTextures);
            
        // Set up the vertex buffer
        int bf[] = new int[1];
        gl2.glGenBuffers (1, bf, 0);
        vBuffer = bf[0];
        long vertBsize = 6 * 4l * 4l;
        long tdataSize = 6 * 2l * 4l;
        gl2.glBindBuffer ( GL.GL_ARRAY_BUFFER, vBuffer);
        gl2.glBufferData ( GL.GL_ARRAY_BUFFER, vertBsize + tdataSize , null, GL.GL_STATIC_DRAW);
        gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, 0, vertBsize, points);
        gl2.glBufferSubData ( GL.GL_ARRAY_BUFFER, vertBsize, tdataSize, texCoords);
            
        gl2.glGenBuffers (1, bf, 0);
        eBuffer = bf[0];
        long eBuffSize = 6 * 2l;
        gl2.glBindBuffer ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffer);
        gl2.glBufferData ( GL.GL_ELEMENT_ARRAY_BUFFER, eBuffSize, elements, 
                              GL.GL_STATIC_DRAW);
    }
	
	/**
	 * Called by the drawable immediately after the OpenGL context is
	 * initialized. 
	 */
	public void init(GLAutoDrawable drawable)
	{
		// Get GL object
		GL2 gl2 = drawable.getGL().getGL2();
		
		
		shaderProgID = myShaders.readAndCompile (gl2, "vshader.glsl", "fshader.glsl");
		shaderProgIDTexture = myShaders.readAndCompile (gl2, "vshaderText.glsl", "fshaderText.glsl");
		if (shaderProgID == 0) 
		{
			System.err.println ("Error setting up shaders");
			System.exit (1);
		}
		if (shaderProgIDTexture == 0) 
		{
			System.err.println ("Error setting up background texture shaders");
			System.exit (1);
		}
			
		// Other GL initialization
		gl2.glEnable (GL.GL_DEPTH_TEST);
		gl2.glEnable (GL.GL_CULL_FACE);
        gl2.glCullFace ( GL.GL_BACK );
        gl2.glPolygonMode ( GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE );
        gl2.glFrontFace(GL.GL_CCW);
		gl2.glClearColor (1.0f, 1.0f, 1.0f, 1.0f);
        gl2.glDepthFunc (GL.GL_LEQUAL);
        gl2.glClearDepth (1.0f);
			
		// Create the background shape
        createBackground(gl2);

    	// Load the background texture
        loadTexture ("scenery.jpg");
			
	}
	
	/**
     * Because I am a Key Listener...we'll only respond to key presses
     */
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}

    /** 
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e)
    {
    	// Flag to check if a change was made
    	boolean flag = false;
    	
    	// Get the key that was pressed
    	char key = e.getKeyChar();
    	
    	// Respond appropriately
    	switch( key ) {
    	case 'x': angles[0] -= angleInc; flag = true; break;
    	case 'y': angles[1] -= angleInc; flag = true; break;
    	case 'z': angles[2] -= angleInc; flag = true; break;  
    	case 'X': angles[0] += angleInc; flag = true; break;
    	case 'Y': angles[1] += angleInc; flag = true; break;
    	case 'Z': angles[2] += angleInc; flag = true; break;
    	case '1': viewMode = 1; flag = true; break;
    	case '2': viewMode = 2; flag = true; break;
    	
    	// Change eye-point-vector
    	case 'e': proj.eyepointX+=0.5; flag = true; break;
    	case 'E': proj.eyepointX-=0.5; flag = true; break;
    	case 'r': proj.eyepointX+=0.5; flag = true; break;
    	case 'R': proj.eyepointX-=0.5; flag = true; break;
    	case 't': proj.eyepointX+=0.5; flag = true; break;
    	case 'T': proj.eyepointX-=0.5; flag = true; break;
    	
    	// Change look-at-vector
    	case 'd': proj.lookAtX+=0.5; flag = true; break;
    	case 'D': proj.lookAtX-=0.5; flag = true; break;
    	case 'f': proj.lookAtY+=0.5; flag = true; break;
    	case 'F': proj.lookAtY-=0.5; flag = true; break;
    	case 'g': proj.lookAtZ+=0.5; flag = true; break;
    	case 'G': proj.lookAtZ-=0.5; flag = true; break;
    	
    	// Change up-vector
    	case 'c': proj.upX+=0.5; flag = true; break;
    	case 'C': proj.upX-=0.5; flag = true; break;
    	case 'v': proj.upY+=0.5; flag = true; break;
    	case 'V': proj.upY-=0.5; flag = true; break;
    	case 'b': proj.upZ+=0.5; flag = true; break;
    	case 'B': proj.upZ-=0.5; flag = true; break;
    	
    	// Translate
    	case 'j': proj.tx++; flag = true; break;
    	case 'J': proj.tx--; flag = true; break;
    	case 'k': proj.ty++; flag = true; break;
    	case 'K': proj.ty--; flag = true; break;
    	case 'l': proj.tz++; flag = true; break;
    	case 'L': proj.tz--; flag = true; break;
    	
    	// Reset to original position
    	case '5': proj = new ProjectionType();
    				flag = true; 
    				break;
    	
    	// Exit
    	case 'q': case 'Q':
    		System.exit( 0 );
    		break;
    	}
    	
    	// Do a redraw if any value was changed
    	if(flag)
    	{
    		myCanvas.display();
    		
    		
    		/**
    		 *  Display the camera eye point, look At and up vectors
    		 * 
    		 *	System.out.print(proj.eyepointX + " " + proj.eyepointY + " " + proj.eyepointZ);
    		 *	System.out.println();
    		 *	System.out.print(proj.lookAtX + " " + proj.lookAtY + " " + proj.lookAtZ);
    		 *	System.out.println();
    		 *	System.out.print(proj.upX + " " + proj.upY + " " + proj.upZ);
    		 *	System.out.println("\n.....");
    		 */
    		
    	}
    }
    
    /**
     * Program execution beings here
     * 
     * @param  args		Command line argument
     * 
     */
    public static void main(String [] args)
    {
        // GL setup
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
		
		// Create MainFunc object
		MainFunc myMain = new MainFunc(canvas);
        
        
        Frame frame = new Frame("CG Final Project");
        frame.setSize(512, 512);
        frame.add(canvas);
        frame.setVisible(true);
        
        // By default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
    }

	@Override
	public void dispose(GLAutoDrawable arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) 
	{
		// TODO Auto-generated method stub
		
	}

}
