//# Vertex shader file for background texture

attribute vec4 vPosition;
attribute vec2 vTexCoord;

varying vec2 texCoord;

void main()
{
	texCoord	  = vTexCoord;
	gl_Position  = vPosition;
}