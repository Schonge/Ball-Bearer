package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.activities.LevelListActivity;
import com.jconnolly.ballbearer.resourcemanagers.LevelListResourceManager;

/*
 * This is an Abstract class in which methods to be used by the scene
 * that will control the level list will have to implement.
 */
public abstract class BaseLevelScene extends Scene {
	
	//====================================================
	// VARIABLES
	//====================================================
		
	protected Engine engine;
	protected Camera camera;
	protected VertexBufferObjectManager vbom;
	protected LevelListActivity levelListActivity;
	
	//====================================================
	// CONSTRUCTOR
	//====================================================
	
	public BaseLevelScene() {
		this.engine = LevelListResourceManager.getLevelListResMan().engine;
		this.camera = LevelListResourceManager.getLevelListResMan().camera;
		this.vbom = LevelListResourceManager.getLevelListResMan().vbom;
		this.levelListActivity = LevelListResourceManager.getLevelListResMan().levelListActivity;
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
	
	public abstract LevelType getLevelType();
	
	public abstract void onBackPressed();
	
	public abstract void destroyScene();

}
