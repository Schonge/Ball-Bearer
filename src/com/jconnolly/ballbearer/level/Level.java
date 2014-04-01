package com.jconnolly.ballbearer.level;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.jconnolly.ballbearer.tiles.Tile;


public class Level {
	
	private final int id;
	private int width, height;
	private final ArrayList<Tile> levelTiles = new ArrayList<Tile>();
	
	public Level(int id) {
		this.id = id;
	}
	
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
	
	public void addTiles(Tile t) {
		levelTiles.add(t);
	}
	
	public void load(Scene scene, PhysicsWorld physicsWorld) {
		for(Tile t : levelTiles) {
			t.createBodyAndAttach(scene, physicsWorld);
		}
	}

}
