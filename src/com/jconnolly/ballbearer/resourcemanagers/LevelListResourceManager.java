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

import com.jconnolly.ballbearer.activities.LevelListActivity;

public class LevelListResourceManager {
	
	//====================================================
	// CONSTANTS
	//====================================================
		
	// LevelListResourceManager instance
	private static final LevelListResourceManager LEVEL_LIST_MAN = new LevelListResourceManager();
		
	//====================================================
	// VARIABLES
	//====================================================

	
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public LevelListActivity levelListActivity;
	
	// Level List graphics
	private BuildableBitmapTextureAtlas levelListTextureAtlas;
	public BitmapTextureAtlas levelListBackTexture;
	public ITextureRegion levelListBackTR;
	public ITextureRegion levelOneBtnTR;
	public ITextureRegion levelTwoBtnTR;
	public ITextureRegion levelThreeBtnTR;
	public ITextureRegion levelFourBtnTR;
	
	public void loadLevelListResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.levelListTextureAtlas = new BuildableBitmapTextureAtlas(levelListActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		levelOneBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelListTextureAtlas, levelListActivity, "levelOneBtn.png");
		levelTwoBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelListTextureAtlas, levelListActivity, "levelTwoBtn.png");
		levelThreeBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelListTextureAtlas, levelListActivity, "levelThreeBtn.png");
		levelFourBtnTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelListTextureAtlas, levelListActivity, "levelFourBtn.png");
		
		this.levelListBackTexture = new BitmapTextureAtlas(levelListActivity.getTextureManager(), 800, 480);
		this.levelListBackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelListTextureAtlas, levelListActivity, "levelListBack.png");
		
		// Builds an area for holding and rendering textures
		try {
			this.levelListTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.levelListTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
	}
	
	public void unloadLevelListResources() {
		levelListTextureAtlas.unload();
		levelListBackTexture = null;
		levelListBackTR = null;
		levelOneBtnTR = null;
		levelTwoBtnTR = null;
		levelThreeBtnTR = null;
		levelFourBtnTR = null;
	}
	
	public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, LevelListActivity levelListAct) {
		getLevelListResMan().engine = eng;
		getLevelListResMan().camera = cam;
		getLevelListResMan().vbom = vbom;
		getLevelListResMan().levelListActivity = levelListAct;
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

	public static LevelListResourceManager getLevelListResMan() {
		return LEVEL_LIST_MAN;
	}
}
