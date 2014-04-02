package com.jconnolly.ballbearer.resourcemanagers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.activities.MenuActivity;

/*
 * This class will manage all the engine and graphic resources
 * for the application's menu.
 * This will allow me to create a single instance of MenuResourceManager
 * to access all the details instead of having to repeatedly initialize
 * them in other classes
 */

public class MenuResourceManager {
	
	//====================================================
	// CONSTANTS
	//====================================================
	
	// AppResourceManager instance
	private static final MenuResourceManager MENU_RES_MAN = new MenuResourceManager();
	
	//====================================================
	// VARIABLES
	//====================================================
	
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public MenuActivity menuActivity;
	
	// Splash graphics
	public BitmapTextureAtlas splashTextureAtlas;
	public ITextureRegion splashTR;
	
	// Menu graphics
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	public BitmapTextureAtlas menuBackTexture;
	public ITextureRegion menuBackTR;
	public ITextureRegion playBtnTR;
	public ITextureRegion scoreBtnTR;
	public ITextureRegion helpBtnTR;
	
	// Sounds
	public Music music;
	
	//====================================================
	// METHODS
	//====================================================
	
	public void loadSplashResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.splashTextureAtlas = new BitmapTextureAtlas(menuActivity.getTextureManager(), 480, 800);
		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, menuActivity,
				"splash_background.png", 0, 0);
		splashTextureAtlas.load();
		// Sounds
	}
	
	public void unloadSplashResources() {
		splashTextureAtlas.unload();
		splashTR = null;
	}
	
	public void loadMenuResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.menuTextureAtlas = new BuildableBitmapTextureAtlas(menuActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		playBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "playBtn.png");
		scoreBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "scoreBtn.png");
		helpBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "helpBtn.png");
		
		this.menuBackTexture = new BitmapTextureAtlas(menuActivity.getTextureManager(), 800, 480);
		this.menuBackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "menuBackground.png");
		
		// Builds an area for holding and rendering textures
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
		// Sounds
		try {
			music = MusicFactory.createMusicFromAsset(menuActivity.getMusicManager(), menuActivity, "mfx/DSTAircord.mp3");
			music.play();
			music.setLooping(true);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void unloadMenuResources() {
		menuTextureAtlas.unload();
		menuBackTR = null;
		playBtnTR = null;
		scoreBtnTR = null;
		helpBtnTR = null;
		menuBackTexture = null;
	}
	
	public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, MenuActivity menuAct) {
		getMenuResMan().engine = eng;
		getMenuResMan().camera = cam;
		getMenuResMan().vbom = vbom;
		getMenuResMan().menuActivity = menuAct;
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

	public static MenuResourceManager getMenuResMan() {
		return MENU_RES_MAN;
	}

}
