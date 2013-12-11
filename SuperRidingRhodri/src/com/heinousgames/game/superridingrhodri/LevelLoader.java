package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class LevelLoader {

	Array<Level> queue;
	Level currentLevel;
	int n;
	
	public int add(int index, Level newLevel){
		n = index;
		queue.set(n, newLevel);
		return  n;
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
	
	public void renderForeground(){
	logic();
	this.getCurrentLevel().render(renderer);
	}

}
