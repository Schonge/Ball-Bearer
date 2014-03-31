package com.jconnolly.ballbearer.resourcemanagers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.activities.GameActivity;

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
		public GameActivity gameActivity;
		
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
		
		//====================================================
		// METHODS
		//====================================================
		
		public void loadLoadingResources() {
			// Graphics
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			this.loadingTextureAtlas = new BitmapTextureAtlas(gameActivity.getTextureManager(), 480, 800);
			loadingTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(loadingTextureAtlas, gameActivity,
					"loadingText.png", 0, 0);
			loadingTextureAtlas.load();
			// Sounds
		}
		
		public void loadGameResources() {
			// Graphics
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			this.gameTextureAtlas = new BuildableBitmapTextureAtlas(gameActivity.getTextureManager(), 800, 480);
			levelBackTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, gameActivity,
					"level_background2.png");
			ballTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, gameActivity, "redBall.png");
			wallTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, gameActivity, "blackWall.png");
			trapHoleTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, gameActivity, "trapHole.png");
			finishTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, gameActivity, "redGoal.png");
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
		
		public void unloadLoadingResources() {
			loadingTextureAtlas.unload();
			loadingTR = null;
		}
		
		public static void prepareManager(Engine eng, Camera cam, VertexBufferObjectManager vbom, GameActivity gAct) {
			getGameResMan().engine = eng;
			getGameResMan().camera = cam;
			getGameResMan().vbom = vbom;
			getGameResMan().gameActivity = gAct;
		}
		
		//===================================================
		// GETTERS AND SETTERS
		//===================================================

		public static GameResourceManager getGameResMan() {
			return GAME_RES_MAN;
		}

}