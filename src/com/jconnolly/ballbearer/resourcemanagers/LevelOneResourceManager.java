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

import com.jconnolly.ballbearer.activities.LevelOneActivity;
import com.jconnolly.ballbearer.tiles.TileManager;

public class LevelOneResourceManager {
	
	//====================================================
	// CONSTANTS
	//====================================================
		
	// AppResourceManager instance
	private static final LevelOneResourceManager LEVEL_ONE_MAN = new LevelOneResourceManager();
		
	//====================================================
	// VARIABLES
	//====================================================
		
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public LevelOneActivity levelOneAct;
		
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
		
	public void loadGameResources() {
		// Fonts
		final ITexture simpFontText = new BitmapTextureAtlas(levelOneAct.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		
		FontFactory.setAssetBasePath("font/");
		this.simpFont = FontFactory.createFromAsset(levelOneAct.getFontManager(), simpFontText, levelOneAct.getAssets(),
				"Simpsonfont.ttf", FONT_SIZE, true, Color.YELLOW);
		this.simpFont.load();
		
		// Graphics
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.gameTextureAtlas = new BuildableBitmapTextureAtlas(levelOneAct.getTextureManager(), 1024, 1024);
		levelBackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelOneAct,
				"level_background2.png");
		ballTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelOneAct, "redBall.png");
		wallTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelOneAct, "blackWall.png");
		trapHoleTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelOneAct, "trapHole.png");
		finishTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, levelOneAct, "orangeGoal.png");
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
		this.levelCompleteTextureAtlas = new BitmapTextureAtlas(levelOneAct.getTextureManager(), 1024, 1024);
		levelCompleteTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelCompleteTextureAtlas, levelOneAct,
				"levelComplete.png", 0, 0);
		levelCompleteTextureAtlas.load();
		// Sounds
	}
		
	public void unloadGameResources() {
		gameTextureAtlas.unload();
		gameTextureAtlas = null;
	}
		
	public void unloadLevelCompleteResources() {
		levelCompleteTextureAtlas.unload();
		levelCompleteTR = null;
	}
		
	public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, LevelOneActivity levelOneActivity) {
		getLvlOneResMan().engine = eng;
		getLvlOneResMan().camera = cam;
		getLvlOneResMan().vbom = vbom;
		getLvlOneResMan().levelOneAct = levelOneActivity;
	}
		
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

	public static LevelOneResourceManager getLvlOneResMan() {
		return LEVEL_ONE_MAN;
	}

}
