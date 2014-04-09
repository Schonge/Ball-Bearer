package com.jconnolly.ballbearer.level;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.jconnolly.ballbearer.tiles.Tile;

/*
 * This class creates a level for the game from an ArrayList of Tiles
 */
public class Level {

	//=====================================================
	// VARIABLES
	//=====================================================

	private final int id;
	private int width, height;
	private final ArrayList<Tile> levelTiles = new ArrayList<Tile>();
	
	//=====================================================
	// METHODS
	//=====================================================
	
	public Level(int id) {
		this.id = id;
	}
	
	// Adds a tile to the ArrayList of tiles for the level
	public void addTiles(Tile t) {
		levelTiles.add(t);
	}
	
	// Loads the level by going through the tiles, creating them and attaching them
	// to the level.
	public void load(Scene scene, PhysicsWorld physicsWorld) {
		for(Tile t : levelTiles) {
			t.createBodyAndAttach(scene, physicsWorld);
		}
	}
	
	//=====================================================
	// GETTERS AND SETTERS
	//=====================================================
	
	public int getID() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
