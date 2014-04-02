package com.jconnolly.ballbearer.resourcemanagers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Color;

import com.jconnolly.ballbearer.activities.LevelTwoActivity;
import com.jconnolly.ballbearer.tiles.TileManager;


public class GameResourceManager {
	
	//====================================================
	// CONSTANTS
	//====================================================
		
	// AppResourceManager instance
	private static final GameResourceManager GAME_RES_MAN = new GameResourceManager();
		
	//====================================================
	// VARIABLES
	//====================================================
		
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public LevelTwoActivity levelTwoAct;
		
	// Loading Screen graphics
	public BitmapTextureAtlas loadingTextureAtlas;
	public ITextureRegion loadingTR;
		
	// Game graphics
	public BuildableBitmapTextureAtlas gameTextureAtlas;
	public ITextureRegion levelBackTR;
	public ITextureRegion wallTR;
	public ITextureRegion trapHoleTR;
	public ITextureRegion ballTR;
	public ITextureRegion finishTR;
		
	// Fonts
	public Font simpFont;
	private static final int FONT_SIZE = 32;
	
	// Tile Manager
	public TileManager tileManager;
	
	// Level Complete Graphics
	public BitmapTextureAtlas levelCompleteTextureAtlas;
	public ITextureRegion levelCompleteTR;
	
	//====================================================
	// METHODS
	//====================================================
		
	public void loadLoadingResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.loadingTextureAtlas = new BitmapTextureAtlas(levelTwoAct.getTextureManager(), 1024, 1024);
		loadingTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadingTextureAtlas, levelTwoAct,
				"loadingText.png", 0, 0);
		loadingTextureAtlas.load();
		// Sounds
	}
		
	public void loadGameResources() {
		// Fonts
		final ITexture simpFontText = new BitmapTextureAtlas(levelTwoAct.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		
		FontFactory.setAssetBasePath("font/");
		this.simpFont = FontFactory.createFromAsset(levelTwoAct.getFontManager(), simpFontText, levelTwoAct.getAssets(),
				"Simpsonfont.ttf", FONT_SIZE, true, Color.YELLOW);
		this.simpFont.load();
		
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.gameTextureAtlas = new BuildableBitmapTextureAtlas(levelTwoAct.getTextureManager(), 1024, 1024);
		levelBackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelTwoAct,
				"level_background2.png");
		ballTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelTwoAct, "redBall.png");
		wallTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelTwoAct, "blackWall.png");
		trapHoleTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelTwoAct, "trapHole.png");
		finishTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelTwoAct, "redGoal.png");
		gameTextureAtlas.load();
		
		// Builds an area for holding and rendering textures
		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		
		// Sounds
	}
		
	public void loadTileManager() {
		loadGameResources();
		tileManager = new TileManager(vbom);
	}
	
	public void loadLevelCompleteResources() {
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.levelCompleteTextureAtlas = new BitmapTextureAtlas(levelTwoAct.getTextureManager(), 1024, 1024);
		levelCompleteTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompleteTextureAtlas, levelTwoAct,
				"levelComplete.png", 0, 0);
		levelCompleteTextureAtlas.load();
		// Sounds
	}
		
	public void unloadLoadingResources() {
		loadingTextureAtlas.unload();
		loadingTR = null;
	}
		
	public void unloadGameResources() {
		gameTextureAtlas.unload();
		gameTextureAtlas = null;
	}
		
	public void unloadLevelCompleteResources() {
		levelCompleteTextureAtlas.unload();
		levelCompleteTR = null;
	}
		
	public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, LevelTwoActivity levelTwoAct) {
		getGameResMan().engine = eng;
		getGameResMan().camera = cam;
		getGameResMan().vbom = vbom;
		getGameResMan().levelTwoAct = levelTwoAct;
	}
		
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

	public static GameResourceManager getGameResMan() {
		return GAME_RES_MAN;
	}

}