package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

/* This class will be responsible for buffering the next maps, keeping track of which level the player is in and 
 * access any level specific information for use by the game. It is a work in progress and will continually grow as more
 *  responsibilites are found. On the fence if this class will handle audio, or if an AudioLoader class should exist.
 */
public class LevelLoader {

	LevelLoader(){
		queue = new Array<Level>();
	}
	Array<Level> queue;
	Level currentLevel;

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
	}
	
	public void logic(){
		//Place for level specific things and special effects and such. Really anything that might be needed for level specific
		//physics or whatever.
	}
	
	public void render(){
//		//Runs the levels specific logic first then renders by layers (eventually!!! Doesn't use this method yet)
//		logic();
//		this.getCurrentLevel().render(renderer);
	}

}
