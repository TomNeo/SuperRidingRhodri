package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

/* This class will be responsible for buffering the next maps, keeping track of which level the player is in and 
 * access any level specific information for use by the game. It is a work in progress and will continually grow as more
 *  responsibilites are found. On the fence if this class will handle audio, or if an AudioLoader class should exist.
 */
public class LevelLoader {
	
	TextureRegion[] currentTextures;
	CustomTiledRenderer renderer;

	
	LevelLoader(){
		queue = new Array<Level>();
	}
	
	Array<Level> queue;
	Level currentLevel;

	public void getTextures(Level current){
		currentTextures = current.getTextures();
	}
	
	//The index field isn't really needed currently. 
	public int add(int index, Level newLevel){
		queue.add(newLevel);
		return queue.size;
	}
	
	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentLevel(Level set){
		currentLevel = set;
		currentTextures = set.getTextures();
	}
	
	public void logic(){
		//Place for level specific things and special effects and such. Really anything that might be needed for level specific
		//physics or whatever.
	}
	
	public void render(){
		currentLevel.render(renderer);
	}

}
