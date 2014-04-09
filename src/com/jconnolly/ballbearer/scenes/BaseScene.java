package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.activities.MenuActivity;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

/*
 * An Abstract class for a base scene that will be used
 * by all scenes associated with the application's menu.
 * These scenes will have to implement the methods provided.
 */
public abstract class BaseScene extends Scene {
	
	//====================================================
	// CONSTANTS
	//====================================================
	
	//====================================================
	// VARIABLES
	//====================================================
		
	protected Engine engine;
	protected Camera camera;
	protected VertexBufferObjectManager vbom;
	protected MenuActivity menuActivity;
	
	//====================================================
	// CONSTRUCTOR
	//====================================================
	
	public BaseScene() {
		this.engine = MenuResourceManager.getMenuResMan().engine;
		this.camera = MenuResourceManager.getMenuResMan().camera;
		this.vbom = MenuResourceManager.getMenuResMan().vbom;
		this.menuActivity = MenuResourceManager.getMenuResMan().menuActivity;
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
	
	public abstract SceneType getSceneType();
	
	public abstract void onBackPressed();
	
	public abstract void destroyScene();

}
