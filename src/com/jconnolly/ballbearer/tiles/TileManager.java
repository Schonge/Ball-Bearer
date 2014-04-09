package com.jconnolly.ballbearer.tiles;

import java.util.ArrayList;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.jconnolly.ballbearer.resourcemanagers.LevelTwoResourceManager;

/*
 * This class manages all the tiles that are created
 * and stores them in an array list
 */
public class TileManager {
	
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public TileManager(VertexBufferObjectManager vbom) {
		tiles.add(new Tile("Wall", 1, 0, 0, 0.0f, 0.0f, 0.0f, LevelTwoResourceManager.getLvlTwoResMan().wallTR, vbom));
		tiles.add(new Tile("Trap Hole", 2, 0, 0, 0.0f, 0.0f, 0.0f, LevelTwoResourceManager.getLvlTwoResMan().trapHoleTR, vbom));
		tiles.add(new Tile("Finish", 3, 0, 0, 0.0f, 0.0f, 0.0f, LevelTwoResourceManager.getLvlTwoResMan().finishTR, vbom));
		tiles.add(new Tile("Border", 4, 0, 0, 0.0f, 0.0f, 0.0f, LevelTwoResourceManager.getLvlTwoResMan().wallTR, vbom));
	}
	
	public Tile getTileByID(int id) {
		for(Tile t : tiles) {
			if(t.getId() == id) {
				return t;
			} 
		}
		return null;
	}

}
