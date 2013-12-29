package com.heinousgames.game.superridingrhodri;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class CustomTiledRenderer extends OrthogonalTiledMapRenderer {

	private TextureRegion texture;
	private Level current;
	
	public CustomTiledRenderer(Level map) {
		super(map.getMap());
		current = map;
	}


	public CustomTiledRenderer(Level map, float f) {
		super(map.getMap(), f);
		current = map;
	}

/*
 * (non-Javadoc)
 * @see com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer#renderObject(com.badlogic.gdx.maps.MapObject)
 * Still needs a little work. The numbers are hard coded in as the position on the map i know they are supposd to be
 * at. Still figuring out if these numbers will come from the tmx or the level logic.
 */
	@Override
	public void renderObject(MapObject object) {
		MapProperties objectProperties = object.getProperties();
		for(TiledMapTileSet tiles : map.getTileSets()){
			if(tiles.getName().equals(objectProperties.get("type"))){
				if(tiles.size() == 1){
					texture = tiles.getTile(33).getTextureRegion();
				spriteBatch.draw(texture.getTexture(), 28, 87, 1, 1);
				}else if(tiles.size() > 1){
					//TODO decide how aniamtion will happen. here, or in the object. get keyframe from logic and select proper frame for rendering animations
				}
			}
		}
	}
	
	//Needs work. Wherever this is used needs to order player(map), levelLoader(level) and Renderer(map, level) at the same time.
	public void changeLevel(Level map){
		current = map;
	}
	
	/*
	 * This method is called for the LevelTemplate1's way off arranging graphical info to be rendered.
	 */
	public void renderMode1(){
		beginRender();
		//This loop is taken from the parent class's original render loop. I just commented out the normal render object instructions.
		for (MapLayer layer : current.getMap().getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer)layer);
				} /*else {
					for (MapObject object : layer.getObjects()) {
						renderObject(object);
					}
				}*/
			}
		}
		//This loop checks the current Levels array of GenericObjects to be rendered, then draws them on screen on top of everything, (except rhodri, he renders last)
		//If we go with this style, we will just have to include a property to the GenericObjects class to determine when/where it should actually render.
		for (GenericObject item : current.toRender()){
			spriteBatch.draw(item.img.getTexture(), item.xPos, item.yPos, item.width, item.height);
		}

		endRender();
	}
	
	//THis will be how i section off the other way we could render objects
	public void renderMode2(){
		
	}
}
