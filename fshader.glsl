//# Fragment shader for object file

varying vec2 texCoord;
varying vec4 color;
uniform sampler2D texture;

void main()
{
    gl_FragColor = gl_FragColor + texture2D (texture, texCoord);

	// Default fragment shader
	// gl_FragColor = vec4 (0.0, 0.0, 0.0, 1.0);

}
