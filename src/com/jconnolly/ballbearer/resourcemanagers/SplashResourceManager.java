package com.jconnolly.ballbearer.resourcemanagers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.activities.SplashActivity;

/*
 * This class will manage all the engine and graphic resources
 * for the application's splash screen.
 * This will allow me to create a single instance of SplashResourceManager
 * to access all the details instead of having to repeatedly initialize
 * them in other classes
 */

public class SplashResourceManager {
	
	//====================================================
	// CONSTANTS
	//====================================================
		
	// AppResourceManager instance
	private static final SplashResourceManager SPLASH_RES_MAN = new SplashResourceManager();
	
	//====================================================
	// VARIABLES
	//====================================================

	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public SplashActivity splashActivity;
	
	// Splash graphics
	public BitmapTextureAtlas splashTextureAtlas;
	public ITextureRegion splashTR;
	
	//====================================================
	// METHODS
	//====================================================
		
	public void loadSplashResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.splashTextureAtlas = new BitmapTextureAtlas(splashActivity.getTextureManager(), 480, 800);
		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, splashActivity,
				"splash_background.png", 0, 0);
		splashTextureAtlas.load();
		// Sounds
	}
	
	public void unloadSplashResources() {
		splashTextureAtlas.unload();
		splashTR = null;
	}
	
	public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, SplashActivity splashAct) {
		getSplashResMan().engine = eng;
		getSplashResMan().camera = cam;
		getSplashResMan().vbom = vbom;
		getSplashResMan().splashActivity = splashAct;
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

	public static SplashResourceManager getSplashResMan() {
		return SPLASH_RES_MAN;
	}
	
}
