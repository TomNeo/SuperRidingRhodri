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
	CustomPlayer player;
	Array<Level> queue;
	Level currentLevel;

	
	LevelLoader(){
		queue = new Array<Level>();
		setCurrentLevel(new ExampleLevel1());
		currentLevel.setHome(this);
		currentLevel.setQueue();
/*********A HERO IS BORN**********/
		setPlayer(new CustomPlayer(currentLevel.getMap(), this));
	}
	

	private void setPlayer(CustomPlayer set){
		player = set;
	}
	
	public CustomPlayer getPlayer(){
		return player;
	}

	//Not really being used for anything at this time. Was an initial way for me to see the textures brought in from the tmx file
	public void getTextures(Level current){
		currentTextures = current.getTextures();
	}
	
	//The index field isn't really needed currently. 
	public int add(Level newLevel){
		queue.add(newLevel);
		return queue.size - 1;
	}
	
	public void playerChangeMap(Level newLevel){
		setCurrentLevel(newLevel);
		queue.clear();
		currentLevel.setQueue();
		player.changeMap(currentLevel.getMap());
		player.position.x = currentLevel.getStartX();
		player.position.y = currentLevel.getStartY();
		player.changeMap(currentLevel.getMap());
		renderer.moveTo(currentLevel);
	}
	
	public void playerChangeMap(Level newLevel, int x, int y){
		setCurrentLevel(newLevel);
		queue.clear();
		currentLevel.setQueue();
		currentLevel.setStartX(x);
		currentLevel.setStartY(y);
		player.position.x = currentLevel.getStartX();
		player.position.y = currentLevel.getStartY();
		player.changeMap(currentLevel.getMap());
		renderer.moveTo(currentLevel);
	}
	
	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentLevel(Level set){
		currentLevel = set;
		currentLevel.setHome(this);
	//	currentTextures = set.getTextures();
	}
	
	public void tick(float deltaTime){
		this.logic(deltaTime);
		this.render();
	}
	
	public void logic(float deltaTime){
		currentLevel.logic(deltaTime);
		player.updatePlayer(deltaTime);
	}
	
	public void render(){
		currentLevel.render(renderer);
	}

}
