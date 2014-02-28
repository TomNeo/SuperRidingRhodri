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
	 * 
	 * @see
	 * com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer#renderObject
	 * (com.badlogic.gdx.maps.MapObject) Still needs a little work. The numbers
	 * are hard coded in as the position on the map i know they are supposed to
	 * be at. Still figuring out if these numbers will come from the tmx or the
	 * level logic.
	 */
	
	public void moveTo(Level newLevel){
		current = newLevel;
		map = current.getMap();
	}
	
	@Override
	public void renderObject(MapObject object) {
		renderMode2(object);
		/* Temporary for purposes of using the libgdx renderer
		MapProperties objectProperties = object.getProperties();
		for (TiledMapTileSet tiles : map.getTileSets()) {
			if (tiles.getName().equals(objectProperties.get("type"))) {
				if (tiles.size() == 1) {
					texture = tiles.getTile(33).getTextureRegion();
					spriteBatch.draw(texture.getTexture(), 28, 87, 1, 1);
				} else if (tiles.size() > 1) {
					// TODO decide how animation will happen. here, or in the
					// object. get keyframe from logic and select proper frame
					// for rendering animations
				}
			}
		}
		*/
	}
	
	public GenericObject returnSame(MapObject test){
		for(GenericObject maybe : current.toRender()){
			if(test == maybe.ref){
				return maybe;
			}
		}
		return new GenericObject("DUMMY", current);
	}

	/*
	 * This method is called for the LevelTemplate1's way off arranging
	 * graphical info to be rendered.
	 */
	public void renderMode1() {
		beginRender();
		// This loop is taken from the parent class's original render loop. I
		// just commented out the normal render object instructions.
		for (MapLayer layer : current.getMap().getLayers()) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer) layer);
				}
				/*
				 * else { for (MapObject object : layer.getObjects()) {
				 * renderObject(object); } }
				 */
			}
		}
		// This loop checks the current Levels array of GenericObjects to be
		// rendered, then draws them on screen on top of everything, (except
		// rhodri, he renders last)
		// If we go with this style, we will just have to include a property to
		// the GenericObjects class to determine when/where it should actually
		// render.
		for (GenericObject item : current.toRender()) {
			spriteBatch.draw(item.getImg(), item.xPos, item.yPos,
					item.width, item.height);
		}

		endRender();
	}

	// This will be how i section off the other way we could render objects
	public void renderMode2(MapObject object) {
		GenericObject sameObject = returnSame(object);
		if(!"DUMMY".equals(sameObject.getType())){
			//Width and height are not coming in correctly
			spriteBatch.draw(sameObject.getImg(), sameObject.xPos, sameObject.yPos, sameObject.width, sameObject.height);
//			spriteBatch.draw(sameObject.img.getTexture(), sameObject.xPos, sameObject.yPos, sameObject.width, sameObject.height);
		}
	}
}
