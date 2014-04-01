package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.GameScreenManager.GameSceneType;
import com.jconnolly.ballbearer.activities.GameActivity;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;

public abstract class BaseGameScene extends Scene {
	
	//====================================================
	// CONSTANTS
	//====================================================
	
	//====================================================
	// VARIABLES
	//====================================================
		
	protected Engine engine;
	protected Camera camera;
	protected VertexBufferObjectManager vbom;
	protected GameActivity gameActivity;
	
	//====================================================
	// CONSTRUCTOR
	//====================================================
	
	public BaseGameScene() {
		this.engine = GameResourceManager.getGameResMan().engine;
		this.camera = GameResourceManager.getGameResMan().camera;
		this.vbom = GameResourceManager.getGameResMan().vbom;
		this.gameActivity = GameResourceManager.getGameResMan().gameActivity;
		createScene();
	}
	
	//====================================================
	// METHODS
	//====================================================
	
	// This method can be called to create sprites stopping me from having to duplicate
	// lost of code to create each new sprite
	protected Sprite createSprite(float x, float y, ITextureRegion textureRegion, VertexBufferObjectManager vbom) {
		Sprite sprite = new Sprite(x, y, textureRegion, vbom);
		return sprite;
	}
	
	public abstract void createScene();
	
	public abstract GameSceneType getSceneType();
	
	public abstract void onBackPressed();
	
	public abstract void destroyScene();

}
