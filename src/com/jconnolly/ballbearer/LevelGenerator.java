package com.jconnolly.ballbearer;

import java.util.Random;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

/*
 * This class uses the Recursive division method to generate the maze levels.
 */
public class LevelGenerator {
	
	//================================================
	// MAZE VARIABLES
	//================================================
	
	// Maze size will remain the same for all levels
	protected static final int mazeRows = 10;
	protected static final int mazeCols = 10;
	
	// Indicates which number will represent what part of the mazes
	protected static final int MAZE_START = 4;
	protected static final int MAZE_FINISH = 3;
	protected static final int MAZE_HOLE = 2;
	protected static final int MAZE_WALL = 1;
	protected static final int MAZE_PATH = 0;
	
	// 2D array to hold maze
	protected int[][] maze;
	
	// Graphics
	BitmapTextureAtlas wallTexture;
	ITextureRegion wallTR;
	
	//===============================================
	// METHODS FOR CREATING MAZE
	//===============================================
	
	/*public static void main(String[] args) {
		LevelGenerator maze1 = new LevelGenerator();
		maze1.createEasyMaze();
	}*/
	
	public int[][] createEasyMaze() {
		maze = new int[mazeRows][mazeCols];
		
		// Set the wall boundaries
		for(int i = 0; i < maze.length; i++) {
			maze[i][0] = MAZE_WALL;
			maze[i][maze.length - 1] = MAZE_WALL;
		}
		
		for(int j = 0; j < maze[0].length; j++) {
			maze[0][j] = MAZE_WALL;
			maze[maze[0].length - 1][j] = MAZE_WALL;
		}
		
		// Set randomly generated START and FINISH points
		// As I don't want them being too close together I will
		// limit the points to top left and bottom right hand corners
		// Which one will be the start and which will be the finish will be randomised
		Random rand = new Random();
		int start = rand.nextInt(2);
		if(start == 0) {
			maze[1][1] = MAZE_START;
			maze[mazeRows - 2][mazeCols - 2] = MAZE_FINISH;
		} else {
			maze[1][1] = MAZE_FINISH;
			maze[mazeRows - 2][mazeCols - 2] = MAZE_START;
		}
		
		// Set walls in maze
		
		// Set trap holes in maze
		
		return maze;
		//drawMaze(maze);
		
	}
	
	public void drawMaze(int[][] maze) {
		for(int i = 0 ; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
	}
	
	

}
