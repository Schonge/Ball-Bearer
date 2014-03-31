package com.jconnolly.ballbearer.resourcemanagers;

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
	
	// Menu graphics
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	public BitmapTextureAtlas menuBackTexture;
	public ITextureRegion menuBackTR;
	public ITextureRegion playBtnTR;
	public ITextureRegion scoreBtnTR;
	
	//====================================================
	// METHODS
	//====================================================
	
	public void loadMenuResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.menuTextureAtlas = new BuildableBitmapTextureAtlas(menuActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		playBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "playBtn.png");
		scoreBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, menuActivity, "scoreBtn.png");
		
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
	}
	
	public void unloadMenuResources() {
		menuTextureAtlas.unload();
		menuBackTR = null;
		playBtnTR = null;
		scoreBtnTR = null;
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
