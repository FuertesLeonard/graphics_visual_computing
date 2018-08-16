/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.airhockey;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.airhockey.android.util.LoggerConfig;
import com.airhockey.android.util.ShaderHelper;
import com.airhockey.android.util.TextResourceReader;
import com.example.airhockey.R;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import static android.opengl.GLES20.*;


public class AirHockeyRenderer implements Renderer {
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	private int program;
	private final Context context;
	private static final int POSITION_COMPONENT_COUNT =2;
	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	
	
	public AirHockeyRenderer(Context context){
		this.context = context;
		float[] tableVerticesWithTriangles = {		
				//Line 1
				-0.5f,0.8f,
				0.5f,0.8f,
				//Line 1.0
				-0.51f,0.81f,
				0.51f,0.81f,
				
				//Line 1.2
				-0.5f,-0.8f,
				0.5f,-0.8f,
				//Line 1.2.1
				-0.51f,-0.81f,
				0.51f,-0.81f,
				
				//Line 1.3
				-0.5f,-0.8f,
				-0.5f,0.8f,
				//Line 1.3.1
				-0.51f,-0.81f,
				-0.51f,0.81f,
				
				//Line 1.4
				0.5f,0.8f,
				0.5f,-0.8f,
				//Line 1.4.1
				0.51f,0.81f,
				0.51f,-0.81f,


				//road 1
				-0.96f,-0.83f,
				0.96f,-0.83f,
				
				//road 1.1
				-0.96f,-0.96f,
				0.96f,-0.96f,
				
				//road 1.2
				0.89f,-0.89f,
				0.85f,-0.89f,
				
				//road 1.2.1
				0.80f,-0.89f,
				0.75f,-0.89f,
				
				//road 1.2.2
				0.70f,-0.89f,
				0.65f,-0.89f,
				
				//road 1.2.3
				0.60f,-0.89f,
				0.55f,-0.89f,
				
				//road 1.2.4
				0.50f,-0.89f,
				0.45f,-0.89f,
				
				//road 1.2.5
				0.40f,-0.89f,
				0.35f,-0.89f,
				
				//road 1.2.6
				0.30f,-0.89f,
				0.25f,-0.89f,
				
				//road 1.2.7
				0.20f,-0.89f,
				0.15f,-0.89f,
				
				//road 1.2.8
				0.10f,-0.89f,
				0.05f,-0.89f,
				
				//road 1.2.9
				0.0f,-0.89f,
				-0.05f,-0.89f,
				
				//road 1.2.10
				-0.10f,-0.89f,
				-0.15f,-0.89f,
				
				//road 1.2.11
				-0.20f,-0.89f,
				-0.25f,-0.89f,
				
				//road 1.2.12
				-0.30f,-0.89f,
				-0.35f,-0.89f,
				
				//road 1.2.13
				-0.40f,-0.89f,
				-0.45f,-0.89f,
				
				//road 1.2.14
				-0.50f,-0.89f,
				-0.55f,-0.89f,
		
				//road 1.2.15
				-0.60f,-0.89f,
				-0.65f,-0.89f,
				
				//road 1.2.16
				-0.70f,-0.89f,
				-0.75f,-0.89f,
				
				//road 1.2.17
				-0.80f,-0.89f,
				-0.85f,-0.89f,
				
				//road 1.2.18
				-0.90f,-0.89f,
				-0.95f,-0.89f,
				

				//Line 2
				-0.40f,-0.8f,
				-0.40f,0.8f,
				
				//Line 2.1
				-0.30f,-0.8f,
				-0.30f,0.8f,
				
				//Line 2.2
				-0.20f,-0.8f,
				-0.20f,0.8f,
				
				//Line 2.3
				-0.10f,-0.8f,
				-0.10f,0.8f,
				
				//Line 2.4
				0.0f,-0.8f,
				0.0f,0.8f,
				
				//Line 2.5
				0.10f,-0.8f,
				0.10f,0.8f,
				
				//Line 2.6
				0.20f,-0.8f,
				0.20f,0.8f,
				
				//Line 2.7
				0.30f,-0.8f,
				0.30f,0.8f,
				
				//Line 2.8
				0.40f,-0.8f,
				0.40f,0.8f,
				
				//Line 3
				-0.5f,0.60f,
				0.5f,0.60f,
				//Line 3.0
				-0.5f,0.59f,
				0.5f,0.59f,
				
				//Line 3.1
				-0.5f,0.40f,
				0.5f,0.40f,
				//Line 3.1.1
				-0.5f,0.39f,
				0.5f,0.39f,
				
				//Line 3.2
				-0.5f,0.20f,
				0.5f,0.20f,
				//Line 3.2.1
				-0.5f,0.19f,
				0.5f,0.19f,
				
				//Line 3.3
				-0.5f,0.0f,
				0.5f,0.0f,
				//Line 3.3.1
				-0.5f,-0.01f,
				0.5f,-0.01f,
				
				//Line 3.4
				-0.5f,-0.20f,
				0.5f,-0.20f,
				//Line 3.4.1
				-0.5f,-0.19f,
				0.5f,-0.19f,
				
				//Line 3.5
				-0.5f,-0.40f,
				0.5f,-0.40f,
				//Line 3.5.1
				-0.5f,-0.39f,
				0.5f,-0.39f,
				
				//Line 3.5
				-0.5f,-0.60f,
				0.5f,-0.60f,
				//Line 3.5.1
				-0.5f,-0.59f,
				0.5f,-0.59f,
				//////////////////////
				/////////////////////
				
				//line side
				0.92f,0.88f,
				0.92f,-0.75f,
				//line side 1
				0.91f,0.88f,
				0.91f,-0.75f,
				// line side horizontal 
				0.92f,-0.75f,
				0.51f,-0.81f,
				// line side horizontal  1
				0.91f,-0.74f,
				0.53f,-0.80f,
				// line side Vertical  1
				0.53f,0.80f,
				0.53f,-0.80f,
				// line side horizontal up  1
				0.53f,0.80f,
				0.91f,0.86f,
				
				// line side Vertical center
				0.70f,0.83f,
				0.70f,-0.77f,
				0.71f,0.83f,
				0.71f,-0.77f,
				
				//line side horizontal 1.0
				0.53f,0.60f,
				0.91f,0.66f,
				//line side horizontal 1
				0.53f,0.59f,
				0.91f,0.65f,
				
				//line side horizontal 2.0
				0.53f,0.40f,
				0.91f,0.46f,
				//line side horizontal 2.1
				0.53f,0.39f,
				0.91f,0.45f,
				
				//line side horizontal 3.0
				0.53f,0.20f,
				0.91f,0.26f,
				//line side horizontal 3.1
				0.53f,0.19f,
				0.91f,0.25f,
				
				//line side horizontal 4.0
				0.53f,0.00f,
				0.91f,0.06f,
				//line side horizontal 4.1
				0.53f,-0.01f,
				0.91f,0.05f,
				
				//line side horizontal 5.0
				0.53f,-0.20f,
				0.91f,-0.14f,
				//line side horizontal 5.1
				0.53f,-0.19f,
				0.91f,-0.13f,
				
				//line side horizontal 6.0
				0.53f,-0.40f,
				0.91f,-0.34f,
				//line side horizontal 6.1
				0.53f,-0.39f,
				0.91f,-0.33f,
				
				//line side horizontal 7.0
				0.53f,-0.60f,
				0.91f,-0.54f,
				//line side horizontal 7.1
				0.53f,-0.59f,
				0.91f,-0.53f,
				
				
				
				////////////////////////
				//triangle 1.1
				-0.48f,0.78f,
				-0.48f,0.73f,
				-0.43f,0.78f,
				//triangle 1.2
				-0.48f,0.73f,
				-0.43f,0.78f,
				-0.43f,0.73f,
				//triangle 1.1.1
				-0.38f,0.78f,
				-0.38f,0.73f,
				-0.33f,0.78f,
				//triangle 1.2.1
				-0.38f,0.73f,
				-0.33f,0.78f,
				-0.33f,0.73f,
				///triangle 1.1.2
				-0.28f,0.78f,
				-0.28f,0.73f,
				-0.23f,0.78f,
				///triangle 1.2.2
				-0.28f,0.73f,
				-0.23f,0.78f,
				-0.23f,0.73f,
				////triangle 1.1.3
				-0.18f,0.78f,
				-0.18f,0.73f,
				-0.13f,0.78f,
				////triangle 1.2.3
				-0.18f,0.73f,
				-0.13f,0.78f,
				-0.13f,0.73f,
							/////triangle 1.1.4
							-0.08f,0.78f,
							-0.08f,0.73f,
							-0.03f,0.78f,
							/////triangle 1.2.4
							-0.08f,0.73f,
							-0.03f,0.78f,
							-0.03f,0.73f,
								/////triangle 1.1.5
								0.02f,0.78f,
								0.02f,0.73f,
								0.07f,0.78f,
								/////triangle 1.2.5
								0.02f,0.73f,
								0.07f,0.78f,
								0.07f,0.73f,
							/////triangle 1.1.6
								0.12f,0.78f,
								0.12f,0.73f,
								0.17f,0.78f,
								/////triangle 1.2.6
								0.12f,0.73f,
								0.17f,0.78f,
								0.17f,0.73f,
									/////triangle 1.1.7
								0.22f,0.78f,
								0.22f,0.73f,
								0.27f,0.78f,
									/////triangle 1.2.7
								0.22f,0.73f,
								0.27f,0.78f,
								0.27f,0.73f,
							/////triangle 1.1.8
								0.32f,0.78f,
								0.32f,0.73f,
								0.37f,0.78f,
									/////triangle 1.2.8
								0.32f,0.73f,
								0.37f,0.78f,
								0.37f,0.73f,
							/////triangle 1.1.9
								0.42f,0.78f,
								0.42f,0.73f,
								0.47f,0.78f,
									/////triangle 1.2.9
								0.42f,0.73f,
								0.47f,0.78f,
								0.47f,0.73f,
				
				////////////////////
				//triangle 2.1
				-0.48f,0.69f,
				-0.48f,0.64f,
				-0.43f,0.69f,
				//triangle 2.2
				-0.48f,0.64f,
				-0.43f,0.69f,
				-0.43f,0.64f,
				//triangle 2.1.1
				-0.38f,0.69f,
				-0.38f,0.64f,
				-0.33f,0.69f,
				//triangle 2.2.1
				-0.38f,0.64f,
				-0.33f,0.69f,
				-0.33f,0.64f,
				//triangle 2.1.2
				-0.28f,0.69f,
				-0.28f,0.64f,
				-0.23f,0.69f,
				//triangle 2.2.2
				-0.28f,0.64f,
				-0.23f,0.69f,
				-0.23f,0.64f,
				//triangle 2.1.3
				-0.18f,0.69f,
				-0.18f,0.64f,
				-0.13f,0.69f,
				//triangle 2.2.3
				-0.18f,0.64f,
				-0.13f,0.69f,
				-0.13f,0.64f,
				//triangle 2.1.4
				-0.08f,0.69f,
				-0.08f,0.64f,
				-0.03f,0.69f,
				//triangle 2.2.4
				-0.08f,0.64f,
				-0.03f,0.69f,
				-0.03f,0.64f,
					//triangle 2.1.5
					0.02f,0.69f,
					0.02f,0.64f,
					0.07f,0.69f,
					//triangle 2.2.5
					0.02f,0.64f,
					0.07f,0.69f,
					0.07f,0.64f,
					//triangle 2.1.6
					0.12f,0.69f,
					0.12f,0.64f,
					0.17f,0.69f,
					//triangle 2.2.6
					0.12f,0.64f,
					0.17f,0.69f,
					0.17f,0.64f,
					//triangle 2.1.7
					0.22f,0.69f,
					0.22f,0.64f,
					0.27f,0.69f,
					//triangle 2.2.7
					0.22f,0.64f,
					0.27f,0.69f,
					0.27f,0.64f,
					//triangle 2.1.8
					0.32f,0.69f,
					0.32f,0.64f,
					0.37f,0.69f,
					//triangle 2.2.8
					0.32f,0.64f,
					0.37f,0.69f,
					0.37f,0.64f,
					//triangle 2.1.9
					0.42f,0.69f,
					0.42f,0.64f,
					0.47f,0.69f,
					//triangle 2.2.9
					0.42f,0.64f,
					0.47f,0.69f,
					0.47f,0.64f,
				
				
				///////////////////
				//triangle 3.1
				-0.48f,0.55f,
				-0.48f,0.50f,
				-0.43f,0.55f,
				//triangle 3.2
				-0.48f,0.50f,
				-0.43f,0.55f,
				-0.43f,0.50f,
				//triangle 3.1.1
				-0.38f,0.55f,
				-0.38f,0.50f,
				-0.33f,0.55f,
				//triangle 3.2.1
				-0.38f,0.50f,
				-0.33f,0.55f,
				-0.33f,0.50f,
				//triangle 3.1.2
				-0.28f,0.55f,
				-0.28f,0.50f,
				-0.23f,0.55f,
				//triangle 3.2.2
				-0.28f,0.50f,
				-0.23f,0.55f,
				-0.23f,0.50f,
				//triangle 3.1.3
				-0.18f,0.55f,
				-0.18f,0.50f,
				-0.13f,0.55f,
				//triangle 3.2.3
				-0.18f,0.50f,
				-0.13f,0.55f,
				-0.13f,0.50f,
				//triangle 3.1.4
				-0.08f,0.55f,
				-0.08f,0.50f,
				-0.03f,0.55f,
				//triangle 3.2.4
				-0.08f,0.50f,
				-0.03f,0.55f,
				-0.03f,0.50f,
							//triangle 3.1.5
							0.02f,0.55f,
							0.02f,0.50f,
							0.07f,0.55f,
							//triangle 3.2.5
							0.02f,0.50f,
							0.07f,0.55f,
							0.07f,0.50f,
							//triangle 3.1.6
							0.12f,0.55f,
							0.12f,0.50f,
							0.17f,0.55f,
							//triangle 3.2.6
							0.12f,0.50f,
							0.17f,0.55f,
							0.17f,0.50f,
							//triangle 3.1.7
							0.22f,0.55f,
							0.22f,0.50f,
							0.27f,0.55f,
							//triangle 3.2.7
							0.22f,0.50f,
							0.27f,0.55f,
							0.27f,0.50f,
							//triangle 3.1.8
							0.32f,0.55f,
							0.32f,0.50f,
							0.37f,0.55f,
							//triangle 3.2.8
							0.32f,0.50f,
							0.37f,0.55f,
							0.37f,0.50f,
							//triangle 3.1.9
							0.42f,0.55f,
							0.42f,0.50f,
							0.47f,0.55f,
							//triangle 3.2.9
							0.42f,0.50f,
							0.47f,0.55f,
							0.47f,0.50f,
							
							
				/////////
				//triangle 4.1
				-0.48f,0.47f,
				-0.48f,0.42f,
				-0.43f,0.47f,
				//triangle 4.2
				-0.48f,0.42f,
				-0.43f,0.47f,
				-0.43f,0.42f,
				//triangle 4.1.1
				-0.38f,0.47f,
				-0.38f,0.42f,
				-0.33f,0.47f,
				//triangle 4.2.1
				-0.38f,0.42f,
				-0.33f,0.47f,
				-0.33f,0.42f,
				//triangle 4.1.2
				-0.28f,0.47f,
				-0.28f,0.42f,
				-0.23f,0.47f,
				//triangle 4.2.2
				-0.28f,0.42f,
				-0.23f,0.47f,
				-0.23f,0.42f,
				//triangle 4.1.3
				-0.18f,0.47f,
				-0.18f,0.42f,
				-0.13f,0.47f,
				//triangle 4.2.3
				-0.18f,0.42f,
				-0.13f,0.47f,
				-0.13f,0.42f,
				//triangle 4.1.4
				-0.08f,0.47f,
				-0.08f,0.42f,
				-0.03f,0.47f,
				//triangle 4.2.4
				-0.08f,0.42f,
				-0.03f,0.47f,
				-0.03f,0.42f,					
								//triangle 4.1.5
								0.02f,0.47f,
								0.02f,0.42f,
								0.07f,0.47f,
								//triangle 4.2.5
								0.02f,0.42f,
								0.07f,0.47f,
								0.07f,0.42f,
								//triangle 4.1.6
								0.12f,0.47f,
								0.12f,0.42f,
								0.17f,0.47f,
								//triangle 4.2.6
								0.12f,0.42f,
								0.17f,0.47f,
								0.17f,0.42f,
								//triangle 4.1.7
								0.22f,0.47f,
								0.22f,0.42f,
								0.27f,0.47f,
								//triangle 4.2.7
								0.22f,0.42f,
								0.27f,0.47f,
								0.27f,0.42f,
								//triangle 4.1.8
								0.32f,0.47f,
								0.32f,0.42f,
								0.37f,0.47f,
								//triangle 4.2.8
								0.32f,0.42f,
								0.37f,0.47f,
								0.37f,0.42f,
								//triangle 4.1.9
								0.42f,0.47f,
								0.42f,0.42f,
								0.47f,0.47f,
								//triangle 4.2.9
								0.42f,0.42f,
								0.47f,0.47f,
								0.47f,0.42f,
				
				/////////
				//triangle 5.1
				-0.48f,0.35f,
				-0.48f,0.30f,
				-0.43f,0.35f,
				//triangle 5.2
				-0.48f,0.30f,
				-0.43f,0.35f,
				-0.43f,0.30f,
				//triangle 5.1.1
				-0.38f,0.35f,
				-0.38f,0.30f,
				-0.33f,0.35f,
				//triangle 5.2.1
				-0.38f,0.30f,
				-0.33f,0.35f,
				-0.33f,0.30f,
				//triangle 5.1.2
				-0.28f,0.35f,
				-0.28f,0.30f,
				-0.23f,0.35f,
				//triangle 5.2.2
				-0.28f,0.30f,
				-0.23f,0.35f,
				-0.23f,0.30f,
				//triangle 5.1.3
				-0.18f,0.35f,
				-0.18f,0.30f,
				-0.13f,0.35f,
				//triangle 5.2.3
				-0.18f,0.30f,
				-0.13f,0.35f,
				-0.13f,0.30f,
				//triangle 5.1.4
				-0.08f,0.35f,
				-0.08f,0.30f,
				-0.03f,0.35f,
				//triangle 5.2.4
				-0.08f,0.30f,
				-0.03f,0.35f,
				-0.03f,0.30f,
								//triangle 5.1.5
								0.02f,0.35f,
								0.02f,0.30f,
								0.07f,0.35f,
								//triangle 5.2.5
								0.02f,0.30f,
								0.07f,0.35f,
								0.07f,0.30f,
								//triangle 5.1.6
								0.12f,0.35f,
								0.12f,0.30f,
								0.17f,0.35f,
								//triangle 5.2.6
								0.12f,0.30f,
								0.17f,0.35f,
								0.17f,0.30f,
								//triangle 5.1.7
								0.22f,0.35f,
								0.22f,0.30f,
								0.27f,0.35f,
								//triangle 5.2.7
								0.22f,0.30f,
								0.27f,0.35f,
								0.27f,0.30f,
								//triangle 5.1.8
								0.32f,0.35f,
								0.32f,0.30f,
								0.37f,0.35f,
								//triangle 5.2.8
								0.32f,0.30f,
								0.37f,0.35f,
								0.37f,0.30f,
								//triangle 5.1.9
								0.42f,0.35f,
								0.42f,0.30f,
								0.47f,0.35f,
								//Triangle 5.2.9
								0.42f,0.30f,
								0.47f,0.35f,
								0.47f,0.30f,
				
				
				///////////
				//triangle 6.1
				-0.48f,0.27f,
				-0.48f,0.22f,
				-0.43f,0.27f,
				//triangle 6.2
				-0.48f,0.22f,
				-0.43f,0.27f,
				-0.43f,0.22f,
				//triangle 6.1.1
				-0.38f,0.27f,
				-0.38f,0.22f,
				-0.33f,0.27f,
				//triangle 6.2.1
				-0.38f,0.22f,
				-0.33f,0.27f,
				-0.33f,0.22f,
				//triangle 6.1.2
				-0.28f,0.27f,
				-0.28f,0.22f,
				-0.23f,0.27f,
				//triangle 6.2.2
				-0.28f,0.22f,
				-0.23f,0.27f,
				-0.23f,0.22f,
				//triangle 6.1.3
				-0.18f,0.27f,
				-0.18f,0.22f,
				-0.13f,0.27f,
				//triangle 6.2.3
				-0.18f,0.22f,
				-0.13f,0.27f,
				-0.13f,0.22f,
				//triangle 6.1.4
				-0.08f,0.27f,
				-0.08f,0.22f,
				-0.03f,0.27f,
				//triangle 6.2.4
				-0.08f,0.22f,
				-0.03f,0.27f,
				-0.03f,0.22f,
								//triangle 6.1.5
								0.02f,0.27f,
								0.02f,0.22f,
								0.07f,0.27f,
								//triangle 6.2.5
								0.02f,0.22f,
								0.07f,0.27f,
								0.07f,0.22f,
								//triangle 6.1.6
								0.12f,0.27f,
								0.12f,0.22f,
								0.17f,0.27f,
								//triangle 6.2.6
								0.12f,0.22f,
								0.17f,0.27f,
								0.17f,0.22f,
								//triangle 6.1.7
								0.22f,0.27f,
								0.22f,0.22f,
								0.27f,0.27f,
								//triangle 6.2.7
								0.22f,0.22f,
								0.27f,0.27f,
								0.27f,0.22f,
								//triangle 6.1.8
								0.32f,0.27f,
								0.32f,0.22f,
								0.37f,0.27f,
								//triangle 6.2.8
								0.32f,0.22f,
								0.37f,0.27f,
								0.37f,0.22f,
								//triangle 6.1.9
								0.42f,0.27f,
								0.42f,0.22f,
								0.47f,0.27f,
								//triangle 6.2.9
								0.42f,0.22f,
								0.47f,0.27f,
								0.47f,0.22f,
								
				///////
				//triangle 7.1
				-0.48f,0.15f,
				-0.48f,0.10f,
				-0.43f,0.15f,
				//triangle 7.2
				-0.48f,0.10f,
				-0.43f,0.15f,
				-0.43f,0.10f,
				//triangle 7.1.1
				-0.38f,0.15f,
				-0.38f,0.10f,
				-0.33f,0.15f,
				//triangle 7.2.1
				-0.38f,0.10f,
				-0.33f,0.15f,
				-0.33f,0.10f,
				//triangle 7.1.2
				-0.28f,0.15f,
				-0.28f,0.10f,
				-0.23f,0.15f,
				//triangle 7.2.2
				-0.28f,0.10f,
				-0.23f,0.15f,
				-0.23f,0.10f,
				//triangle 7.1.3
				-0.18f,0.15f,
				-0.18f,0.10f,
				-0.13f,0.15f,
				//triangle 7.2.3
				-0.18f,0.10f,
				-0.13f,0.15f,
				-0.13f,0.10f,
				//triangle 7.1.4
				-0.08f,0.15f,
				-0.08f,0.10f,
				-0.03f,0.15f,
				//triangle 7.2.4
				-0.08f,0.10f,
				-0.03f,0.15f,
				-0.03f,0.10f,
								//triangle 7.1.5
								0.02f,0.15f,
								0.02f,0.10f,
								0.07f,0.15f,
								//triangle 7.2.5
								0.02f,0.10f,
								0.07f,0.15f,
								0.07f,0.10f,
								//triangle 7.1.6
								0.12f,0.15f,
								0.12f,0.10f,
								0.17f,0.15f,
								//triangle 7.2.6
								0.12f,0.10f,
								0.17f,0.15f,
								0.17f,0.10f,
								//triangle 7.1.7
								0.22f,0.15f,
								0.22f,0.10f,
								0.27f,0.15f,
								//triangle 7.2.7
								0.22f,0.10f,
								0.27f,0.15f,
								0.27f,0.10f,
								//triangle 7.1.8
								0.32f,0.15f,
								0.32f,0.10f,
								0.37f,0.15f,
								//triangle 7.2.8
								0.32f,0.10f,
								0.37f,0.15f,
								0.37f,0.10f,
								//triangle 7.1.9
								0.42f,0.15f,
								0.42f,0.10f,
								0.47f,0.15f,
								//triangle 7.2.9
								0.42f,0.10f,
								0.47f,0.15f,
								0.47f,0.10f,
				
				
				
				/////////
				//triangle 8.1
				-0.48f,0.07f,
				-0.48f,0.02f,
				-0.43f,0.07f,
				//triangle 8.2
				-0.48f,0.02f,
				-0.43f,0.07f,
				-0.43f,0.02f,
				//triangle 8.1.1
				-0.38f,0.07f,
				-0.38f,0.02f,
				-0.33f,0.07f,
				//triangle 8.2.1
				-0.38f,0.02f,
				-0.33f,0.07f,
				-0.33f,0.02f,
				//triangle 8.1.2
				-0.28f,0.07f,
				-0.28f,0.02f,
				-0.23f,0.07f,
				//triangle 8.2.2
				-0.28f,0.02f,
				-0.23f,0.07f,
				-0.23f,0.02f,
				//triangle 8.1.3
				-0.18f,0.07f,
				-0.18f,0.02f,
				-0.13f,0.07f,
				//triangle 8.2.3
				-0.18f,0.02f,
				-0.13f,0.07f,
				-0.13f,0.02f,	
				//triangle 8.1.4
				-0.08f,0.07f,
				-0.08f,0.02f,
				-0.03f,0.07f,
				//triangle 8.2.4
				-0.08f,0.02f,
				-0.03f,0.07f,
				-0.03f,0.02f,
									//triangle 8.1.5
									0.02f,0.07f,
									0.02f,0.02f,
									0.07f,0.07f,
									//triangle 8.2.5
									0.02f,0.02f,
									0.07f,0.07f,
									0.07f,0.02f,
									//triangle 8.1.6
									0.12f,0.07f,
									0.12f,0.02f,
									0.17f,0.07f,
									//triangle 8.2.6
									0.12f,0.02f,
									0.17f,0.07f,
									0.17f,0.02f,
									//triangle 8.1.7
									0.22f,0.07f,
									0.22f,0.02f,
									0.27f,0.07f,
									//triangle 8.2.7
									0.22f,0.02f,
									0.27f,0.07f,
									0.27f,0.02f,
									//triangle 8.1.8
									0.32f,0.07f,
									0.32f,0.02f,
									0.37f,0.07f,
									//triangle 8.2.8
									0.32f,0.02f,
									0.37f,0.07f,
									0.37f,0.02f,
									//triangle 8.1.9
									0.42f,0.07f,
									0.42f,0.02f,
									0.47f,0.07f,
									//triangle 8.2.9
									0.42f,0.02f,
									0.47f,0.07f,
									0.47f,0.02f,
				
				//////////
				//triangle 9.1
				-0.48f,-0.05f,
				-0.48f,-0.10f,
				-0.43f,-0.05f,
				//triangle 9.2
				-0.48f,-0.10f,
				-0.43f,-0.05f,
				-0.43f,-0.10f,
				//triangle 9.1.1
				-0.38f,-0.05f,
				-0.38f,-0.10f,
				-0.33f,-0.05f,
				//triangle 9.2.1
				-0.38f,-0.10f,
				-0.33f,-0.05f,
				-0.33f,-0.10f,
				//triangle 9.1.2
				-0.28f,-0.05f,
				-0.28f,-0.10f,
				-0.23f,-0.05f,
				//triangle 9.2.2
				-0.28f,-0.10f,
				-0.23f,-0.05f,
				-0.23f,-0.10f,
				//triangle 9.1.3
				-0.18f,-0.05f,
				-0.18f,-0.10f,
				-0.13f,-0.05f,
				//triangle 9.2.3
				-0.18f,-0.10f,
				-0.13f,-0.05f,
				-0.13f,-0.10f,
				//triangle 9.1.4
				-0.08f,-0.05f,
				-0.08f,-0.10f,
				-0.03f,-0.05f,
				//triangle 9.2.4
				-0.08f,-0.10f,
				-0.03f,-0.05f,
				-0.03f,-0.10f,
								//triangle 9.1.5
								0.02f,-0.05f,
								0.02f,-0.10f,
								0.07f,-0.05f,
								//triangle 9.2.5
								0.02f,-0.10f,
								0.07f,-0.05f,
								0.07f,-0.10f,
								//triangle 9.1.6
								0.12f,-0.05f,
								0.12f,-0.10f,
								0.17f,-0.05f,
								//triangle 9.2.6
								0.12f,-0.10f,
								0.17f,-0.05f,
								0.17f,-0.10f,
								//triangle 9.1.7
								0.22f,-0.05f,
								0.22f,-0.10f,
								0.27f,-0.05f,
								//triangle 9.2.7
								0.22f,-0.10f,
								0.27f,-0.05f,
								0.27f,-0.10f,
								//triangle 9.1.8
								0.32f,-0.05f,
								0.32f,-0.10f,
								0.37f,-0.05f,
								//triangle 9.2.8
								0.32f,-0.10f,
								0.37f,-0.05f,
								0.37f,-0.10f,
								//triangle 9.1.9
								0.42f,-0.05f,
								0.42f,-0.10f,
								0.47f,-0.05f,
								//triangle 9.2.9
								0.42f,-0.10f,
								0.47f,-0.05f,
								0.47f,-0.10f,
				
								
				////////
				//triangle 10.1
				-0.48f,-0.12f,
				-0.48f,-0.17f,
				-0.43f,-0.12f,
				//triangle 10.2
				-0.48f,-0.17f,
				-0.43f,-0.12f,
				-0.43f,-0.17f,
				//triangle 10.1.1
				-0.38f,-0.12f,
				-0.38f,-0.17f,
				-0.33f,-0.12f,
				//triangle 10.2.1
				-0.38f,-0.17f,
				-0.33f,-0.12f,
				-0.33f,-0.17f,
				//triangle 10.1.2
				-0.28f,-0.12f,
				-0.28f,-0.17f,
				-0.23f,-0.12f,
				//triangle 10.2.2
				-0.28f,-0.17f,
				-0.23f,-0.12f,
				-0.23f,-0.17f,
				//triangle 10.1.3
				-0.18f,-0.12f,
				-0.18f,-0.17f,
				-0.13f,-0.12f,
				//triangle 10.2.3
				-0.18f,-0.17f,
				-0.13f,-0.12f,
				-0.13f,-0.17f,
				//triangle 10.1.4
				-0.08f,-0.12f,
				-0.08f,-0.17f,
				-0.03f,-0.12f,
				//triangle 10.2.4
				-0.08f,-0.17f,
				-0.03f,-0.12f,
				-0.03f,-0.17f,
								//triangle 10.1.5
								0.02f,-0.12f,
								0.02f,-0.17f,
								0.07f,-0.12f,
								//triangle 10.2.5
								0.02f,-0.17f,
								0.07f,-0.12f,
								0.07f,-0.17f,
								//triangle 10.1.6
								0.12f,-0.12f,
								0.12f,-0.17f,
								0.17f,-0.12f,
								//triangle 10.2.6
								0.12f,-0.17f,
								0.17f,-0.12f,
								0.17f,-0.17f,
								//triangle 10.1.7
								0.22f,-0.12f,
								0.22f,-0.17f,
								0.27f,-0.12f,
								//triangle 10.2.7
								0.22f,-0.17f,
								0.27f,-0.12f,
								0.27f,-0.17f,
								//triangle 10.1.8
								0.32f,-0.12f,
								0.32f,-0.17f,
								0.37f,-0.12f,
								//triangle 10.2.8
								0.32f,-0.17f,
								0.37f,-0.12f,
								0.37f,-0.17f,
								//triangle 10.1.9
								0.42f,-0.12f,
								0.42f,-0.17f,
								0.47f,-0.12f,
								//triangle 10.2.9
								0.42f,-0.17f,
								0.47f,-0.12f,
								0.47f,-0.17f,
								
								
				//////////////
				//triangle 11.1
				-0.48f,-0.24f,
				-0.48f,-0.29f,
				-0.43f,-0.24f,
				//triangle 11.2
				-0.48f,-0.29f,
				-0.43f,-0.24f,
				-0.43f,-0.29f,
				//triangle 11.1.1
				-0.38f,-0.24f,
				-0.38f,-0.29f,
				-0.33f,-0.24f,
				//triangle 11.2.1
				-0.38f,-0.29f,
				-0.33f,-0.24f,
				-0.33f,-0.29f,
				//triangle 11.1.2
				-0.28f,-0.24f,
				-0.28f,-0.29f,
				-0.23f,-0.24f,
				//triangle 11.2.2
				-0.28f,-0.29f,
				-0.23f,-0.24f,
				-0.23f,-0.29f,
				//triangle 11.1.3
				-0.18f,-0.24f,
				-0.18f,-0.29f,
				-0.13f,-0.24f,
				//triangle 11.2.3
				-0.18f,-0.29f,
				-0.13f,-0.24f,
				-0.13f,-0.29f,
				//triangle 11.1.4
				-0.08f,-0.24f,
				-0.08f,-0.29f,
				-0.03f,-0.24f,
				//triangle 11.2.4
				-0.08f,-0.29f,
				-0.03f,-0.24f,
				-0.03f,-0.29f,
								//triangle 11.1.5
								0.02f,-0.24f,
								0.02f,-0.29f,
								0.07f,-0.24f,
								//triangle 11.2.5
								0.02f,-0.29f,
								0.07f,-0.24f,
								0.07f,-0.29f,
								//triangle 11.1.6
								0.12f,-0.24f,
								0.12f,-0.29f,
								0.17f,-0.24f,
								//triangle 11.2.6
								0.12f,-0.29f,
								0.17f,-0.24f,
								0.17f,-0.29f,
								//triangle 11.1.7
								0.22f,-0.24f,
								0.22f,-0.29f,
								0.27f,-0.24f,
								//triangle 11.2.7
								0.22f,-0.29f,
								0.27f,-0.24f,
								0.27f,-0.29f,
								//triangle 11.1.8
								0.32f,-0.24f,
								0.32f,-0.29f,
								0.37f,-0.24f,
								//triangle 11.2.8
								0.32f,-0.29f,
								0.37f,-0.24f,
								0.37f,-0.29f,
								//triangle 11.1.9
								0.42f,-0.24f,
								0.42f,-0.29f,
								0.47f,-0.24f,
								//triangle 11.2.9
								0.42f,-0.29f,
								0.47f,-0.24f,
								0.47f,-0.29f,
				
				////////////
				//triangle 12.1
				-0.48f,-0.31f,
				-0.48f,-0.36f,
				-0.43f,-0.31f,
				//triangle 12.2
				-0.48f,-0.36f,
				-0.43f,-0.31f,
				-0.43f,-0.36f,
				//triangle 12.1.1
				-0.38f,-0.31f,
				-0.38f,-0.36f,
				-0.33f,-0.31f,
				//triangle 12.2.1
				-0.38f,-0.36f,
				-0.33f,-0.31f,
				-0.33f,-0.36f,
				//triangle 12.1.2
				-0.28f,-0.31f,
				-0.28f,-0.36f,
				-0.23f,-0.31f,
				//triangle 12.2.2
				-0.28f,-0.36f,
				-0.23f,-0.31f,
				-0.23f,-0.36f,
				//triangle 12.1.3
				-0.18f,-0.31f,
				-0.18f,-0.36f,
				-0.13f,-0.31f,
				//triangle 12.2.3
				-0.18f,-0.36f,
				-0.13f,-0.31f,
				-0.13f,-0.36f,
				//triangle 12.1.4
				-0.08f,-0.31f,
				-0.08f,-0.36f,
				-0.03f,-0.31f,
				//triangle 12.2.4
				-0.08f,-0.36f,
				-0.03f,-0.31f,
				-0.03f,-0.36f,
								//triangle 12.1.5
								0.02f,-0.31f,
								0.02f,-0.36f,
								0.07f,-0.31f,
								//triangle 12.2.5
								0.02f,-0.36f,
								0.07f,-0.31f,
								0.07f,-0.36f,
								//triangle 12.1.6
								0.12f,-0.31f,
								0.12f,-0.36f,
								0.17f,-0.31f,
								//triangle 12.2.6
								0.12f,-0.36f,
								0.17f,-0.31f,
								0.17f,-0.36f,
								//triangle 12.1.7
								0.22f,-0.31f,
								0.22f,-0.36f,
								0.27f,-0.31f,
								//triangle 12.2.7
								0.22f,-0.36f,
								0.27f,-0.31f,
								0.27f,-0.36f,
								//triangle 12.1.8
								0.32f,-0.31f,
								0.32f,-0.36f,
								0.37f,-0.31f,
								//triangle 12.2.8
								0.32f,-0.36f,
								0.37f,-0.31f,
								0.37f,-0.36f,
								//triangle 12.1.9
								0.42f,-0.31f,
								0.42f,-0.36f,
								0.47f,-0.31f,
								//triangle 12.2.9
								0.42f,-0.36f,
								0.47f,-0.31f,
								0.47f,-0.36f,
								
				////////
				//triangle 13.1
				-0.48f,-0.43f,
				-0.48f,-0.48f,
				-0.43f,-0.43f,
				//triangle 13.2
				-0.48f,-0.48f,
				-0.43f,-0.43f,
				-0.43f,-0.48f,
				//triangle 13.1.1
				-0.38f,-0.43f,
				-0.38f,-0.48f,
				-0.33f,-0.43f,
				//triangle 13.2.1
				-0.38f,-0.48f,
				-0.33f,-0.43f,
				-0.33f,-0.48f,
				//triangle 13.1.2
				-0.28f,-0.43f,
				-0.28f,-0.48f,
				-0.23f,-0.43f,
				//triangle 13.2.2
				-0.28f,-0.48f,
				-0.23f,-0.43f,
				-0.23f,-0.48f,
				//triangle 13.1.3
				-0.18f,-0.43f,
				-0.18f,-0.48f,
				-0.13f,-0.43f,
				//triangle 13.2.3
				-0.18f,-0.48f,
				-0.13f,-0.43f,
				-0.13f,-0.48f,
				//triangle 13.1.4
				-0.08f,-0.43f,
				-0.08f,-0.48f,
				-0.03f,-0.43f,
				//triangle 13.2.4
				-0.08f,-0.48f,
				-0.03f,-0.43f,
				-0.03f,-0.48f,
									//triangle 13.1.5
									0.02f,-0.43f,
									0.02f,-0.48f,
									0.07f,-0.43f,
									//triangle 13.2.5
									0.02f,-0.48f,
									0.07f,-0.43f,
									0.07f,-0.48f,
									//triangle 13.1.6
									0.12f,-0.43f,
									0.12f,-0.48f,
									0.17f,-0.43f,
									//triangle 13.2.6
									0.12f,-0.48f,
									0.17f,-0.43f,
									0.17f,-0.48f,
									//triangle 13.1.7
									0.22f,-0.43f,
									0.22f,-0.48f,
									0.27f,-0.43f,
									//triangle 13.2.7
									0.22f,-0.48f,
									0.27f,-0.43f,
									0.27f,-0.48f,
									//triangle 13.1.8
									0.32f,-0.43f,
									0.32f,-0.48f,
									0.37f,-0.43f,
									//triangle 13.2.8
									0.32f,-0.48f,
									0.37f,-0.43f,
									0.37f,-0.48f,
									//triangle 13.1.9
									0.42f,-0.43f,
									0.42f,-0.48f,
									0.47f,-0.43f,
									//triangle 13.2.9
									0.42f,-0.48f,
									0.47f,-0.43f,
									0.47f,-0.48f,
				
				
				////////
				//triangle 14.1
				-0.48f,-0.50f,
				-0.48f,-0.55f,
				-0.43f,-0.50f,
				//triangle 14.2
				-0.48f,-0.55f,
				-0.43f,-0.50f,
				-0.43f,-0.55f,
				//triangle 14.1.1
				-0.38f,-0.50f,
				-0.38f,-0.55f,
				-0.33f,-0.50f,
				//triangle 14.2.1
				-0.38f,-0.55f,
				-0.33f,-0.50f,
				-0.33f,-0.55f,
				//triangle 14.1.2
				-0.28f,-0.50f,
				-0.28f,-0.55f,
				-0.23f,-0.50f,
				//triangle 14.2.2
				-0.28f,-0.55f,
				-0.23f,-0.50f,
				-0.23f,-0.55f,
				//triangle 14.1.3
				-0.18f,-0.50f,
				-0.18f,-0.55f,
				-0.13f,-0.50f,
				//triangle 14.2.3
				-0.18f,-0.55f,
				-0.13f,-0.50f,
				-0.13f,-0.55f,
				//triangle 14.1.4
				-0.08f,-0.50f,
				-0.08f,-0.55f,
				-0.03f,-0.50f,
				//triangle 14.2.4
				-0.08f,-0.55f,
				-0.03f,-0.50f,
				-0.03f,-0.55f,
									//triangle 14.1.5
									0.02f,-0.50f,
									0.02f,-0.55f,
									0.07f,-0.50f,
									//triangle 14.2.5
									0.02f,-0.55f,
									0.07f,-0.50f,
									0.07f,-0.55f,
									//triangle 14.1.6
									0.12f,-0.50f,
									0.12f,-0.55f,
									0.17f,-0.50f,
									//triangle 14.2.6
									0.12f,-0.55f,
									0.17f,-0.50f,
									0.17f,-0.55f,
									//triangle 14.1.7
									0.22f,-0.50f,
									0.22f,-0.55f,
									0.27f,-0.50f,
									//triangle 14.2.7
									0.22f,-0.55f,
									0.27f,-0.50f,
									0.27f,-0.55f,
									//triangle 14.1.8
									0.32f,-0.50f,
									0.32f,-0.55f,
									0.37f,-0.50f,
									//triangle 14.2.8
									0.32f,-0.55f,
									0.37f,-0.50f,
									0.37f,-0.55f,
									//triangle 14.1.9
									0.42f,-0.50f,
									0.42f,-0.55f,
									0.47f,-0.50f,
									//triangle 14.2.9
									0.42f,-0.55f,
									0.47f,-0.50f,
									0.47f,-0.55f,
				///////////
									
			///Roof top1
			-0.51f,0.81f,
			0.0f,0.88f,
			0.0f,0.81f,
			
			///Roof top1.1
			0.51f,0.81f,
			0.0f,0.88f,
			0.0f,0.81f,
			///Roof top1.1.1
			0.92f,0.88f,
			0.0f,0.88f,
			0.51f,0.81f,
			
		//building sa kilid
			-1.0f,0.0f,
			-0.80f,0.05f,
			-0.70f,0.00f,
			
			-0.54f,0.05f,
			-0.80f,0.05f,
			-0.70f,0.00f,
			
			//lawas sa building sa kilid
			-1.0f,0.0f,
			-0.70f,-0.81f,
			-0.70f,0.00f,

			-1.0f,0.0f,
			-0.70f,-0.81f,
			-1.00f,-0.81f,
			
			///kilid
			-0.54f,0.0f,
			-0.70f,-0.81f,
			-0.70f,0.00f,
			
			-0.54f,0.0f,
			-0.70f,-0.81f,
			-0.54f,-0.79f,
			
			-0.54f,0.0f,
			-0.70f,0.00f,
			-0.54f,0.05f,
		};
		vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexData.put(tableVerticesWithTriangles);
	}
	
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to red. The first component is
        // red, the second is green, the third is blue, and the last
        // component is alpha, which we don't use in this lesson.
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        
        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        
        program = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        
        if(LoggerConfig.ON){
        	ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);        
    }

	/**
     * onSurfaceChanged is called whenever the surface has changed. This is
     * called at least once when the surface is initialized. Keep in mind that
     * Android normally restarts an Activity on rotation, and in that case, the
     * renderer will be destroyed and a new one created.
     * 
     * @param width
     *            The new width, in pixels.
     * @param height
     *            The new height, in pixels.
     */
    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);
    }

    /**
     * OnDrawFrame is called whenever a new frame needs to be drawn. Normally,
     * this is done at the refresh rate of the screen.
     */
    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);


		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
		glDrawArrays(GL_LINES, 0, 108);

		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
		glDrawArrays(GL_LINES, 108, 40);
		
		glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 0.0f);
		glDrawArrays(GL_TRIANGLES, 148, 988);
		
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
		glDrawArrays(GL_TRIANGLES, 988, 997);
		
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 0.0f);
		glDrawArrays(GL_TRIANGLES, 997, 6);
	
		glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 0.0f);
		glDrawArrays(GL_TRIANGLES, 1003, 6);
		
		glUniform4f(uColorLocation, 0.5f, 0.5f, 0.5f, 0.5f);
		glDrawArrays(GL_TRIANGLES, 1009, 9);
    }
}
