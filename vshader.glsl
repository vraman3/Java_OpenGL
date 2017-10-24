//# Vertex shader file for object

attribute vec4 vPosition;

uniform vec3 theta;

uniform float inputVertices[16];


attribute vec2 vTexCoord;
varying vec2 texCoord;



//attribute vec3 vNormal;
//uniform vec4 lightPosition;
//uniform vec4 lightColor;
//uniform vec4 diffuseColor;
//varying vec4 color;
//uniform vec4 specular;
//uniform float specular_exp;

void main()
{
    // Compute the sines and cosines of each rotation
    // about each axis
    vec3 angles = radians (theta);
    vec3 c = cos (angles);
    vec3 s = sin (angles);
   // vec3 sF = 0.5;
    // rotation matricies

    mat4 rx = mat4 (1.0,  0.0,  0.0,  0.0, 
                    0.0,  c.x,  s.x,  0.0,
                    0.0, -s.x,  c.x,  0.0,
                    0.0,  0.0,  0.0,  1.0);
                    
    mat4 ry = mat4 (c.y,  0.0, -s.y,  0.0, 
                    0.0,  1.0,  0.0,  0.0,
                    s.y,  0.0,  c.y,  0.0,
                    0.0,  0.0,  0.0,  1.0);

	mat4 rz;
	rz[0] = vec4(c.z, s.z,  0.0,  0.0);
	rz[1] = vec4(-s.z,  c.z,  0.0,  0.0);
	rz[2] = vec4(0.0,  0.0,  1.0,  0.0);
	rz[3] = vec4(0.0,  0.0,  0.0,  1.0);



   	mat4 rot = rx * ry * rz;

    mat4 scale = mat4 (0.5, 0.0,  0.0,  0.0, 
                    	  0.0,  0.5,  0.0,  0.0,
                	  0.0,  0.0,  0.5,  0.0,
           	       0.0,  0.0,  0.0,  1.0);

    mat4 translate = mat4 (1.0, 0.0,  0.0,  1, 
	                    0.0,  1.0,  0.0,  1.0,
	                    0.0,  0.0,  1.0,  1.0,
     		               0.0,  0.0,  0.0,  1.0);

 

// Construct the matrix, from the input vertices to the vhsader
	mat4 matrix = mat4 (	 inputVertices[0],  inputVertices[4],  inputVertices[8],  inputVertices[12], 
               			 inputVertices[1],  inputVertices[5],  inputVertices[9],  inputVertices[13],
					 inputVertices[2],  inputVertices[6],  inputVertices[10], inputVertices[14],
					 inputVertices[3],  inputVertices[7],  inputVertices[11], inputVertices[15]);

    texCoord = vTexCoord;
    vec4 temp = rot * scale * vPosition;
    gl_Position = matrix * temp;
    
}
