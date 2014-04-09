package com.jconnolly.ballbearer.tiles;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/*
 * A Tile class for the creation of individual tiles to be generated and placed
 * on the game/map screen * 
 */
public class Tile extends Sprite {

	//=====================================================
	// CONSTANTS
	//=====================================================
	
	private final String name;
	private final int id;
	private final float density, elastic, friction;

	//=====================================================
	// CONSTRUCTOR
	//=====================================================
	
	public Tile(String name, int id, float x, float y, float density, float elastic,
			float friction, ITextureRegion texture, VertexBufferObjectManager vbom) {
		super(x, y, texture, vbom);
		this.name = name;
		this.id = id;
		this.density = density;
		this.elastic = elastic;
		this.friction = friction;
	}
	
	//=====================================================
	// METHODS
	//=====================================================
	
	/* This method creates the physical element of the sprite and attaches it
	 * to the map so that physical actions can be performed on each individual
	 * tile e.g. collisions
	 */
	public void createBodyAndAttach(Scene scene, PhysicsWorld physicsWorld) {
		final FixtureDef tileFixDef = PhysicsFactory.createFixtureDef(density, elastic, friction);
		tileFixDef.restitution = 0;
		Body body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.StaticBody, tileFixDef);
		scene.attachChild(this);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
	}
	
	//=====================================================
	// GETTERS AND SETTERS
	//=====================================================

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public Tile getTile(float x, float y) {
		return new Tile(name, id, x, y, density, elastic, friction, getTextureRegion(), getVertexBufferObjectManager());
	}
	
}
