package com.jconnolly.ballbearer.level;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.SAXUtils;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.xml.sax.Attributes;

import android.content.res.AssetManager;

import com.jconnolly.ballbearer.resourcemanagers.LevelTwoResourceManager;
import com.jconnolly.ballbearer.tiles.Tile;

public class LevelManager {
	
	private final LevelLoader lvlLoader;
	private final AssetManager assetMan;
	private final ArrayList<Level> levels = new ArrayList<Level>();
	
	// XML Tags for level loading
	private static final String TAG_TILE = "tile";
	private static final String TAG_TILE_ATTR_X = "x";
	private static final String TAG_TILE_ATTR_Y = "y";
	private static final String TAG_TILE_ATTR_TILE = "tile";
	//private static final String TAG_TILE = "tile";
	
	
	public LevelManager(AssetManager assetMan) {
		lvlLoader = new LevelLoader();
		lvlLoader.setAssetBasePath("levels/");
		this.assetMan = assetMan;
		addNewLevel(1, "example.lvl");
		addNewLevel(2, "example2.lvl");
	}
	
	private void addNewLevel(int id, String name) {
		final Level lvl = new Level(id);
		
		// Whenever new line is called it finds its tag and performs this function on it
		lvlLoader.registerEntityLoader(LevelConstants.TAG_LEVEL, new IEntityLoader() {
			
			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				final int width = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
				lvl.setWidth(width);
				lvl.setHeight(height);
				return null;
			}
		});
		
		// Whenever it calls a new tile line/entity it implements this function
		lvlLoader.registerEntityLoader(TAG_TILE, new IEntityLoader() {
			
			@Override
			public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
				final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILE_ATTR_X);
				final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILE_ATTR_Y);
				final int id = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILE_ATTR_TILE);
				Tile t = LevelTwoResourceManager.getLvlTwoResMan().tileManager.getTileByID(id);
				
				lvl.addTiles(t.getTile(x, y));
				return null;
			}
		});
		
		try {
			lvlLoader.loadLevelFromAsset(assetMan, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		levels.add(lvl);
	}
	
	public void loadLevel(int id, Scene scene, PhysicsWorld physicsWorld) {
		for(Level level : levels) {
			level.load(scene, physicsWorld);
		}
	}

}
