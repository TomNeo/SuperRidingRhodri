package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	public CustomTiledRenderer(TiledMap map) {
		super(map);
	}


	public CustomTiledRenderer(TiledMap map, float f) {
		super(map, f);
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
				spriteBatch.draw(texture.getTexture(), 28, 86, 1, 1);
				}else if(tiles.size() > 1){
					//TODO get keyframe from logic and select proper frame for rendering animations
				}
			}
		}
	}
}
