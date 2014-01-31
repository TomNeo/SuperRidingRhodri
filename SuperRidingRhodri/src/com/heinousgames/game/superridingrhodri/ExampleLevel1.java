package com.heinousgames.game.superridingrhodri;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class ExampleLevel1 implements Level{

	int startX;
	int startY;
	private TiledMap map;
	ArrayList<GenericObject> objects;
	private LevelLoader home;
	
	public ExampleLevel1(){
		map = new TmxMapLoader().load("gfx/Example.tmx");
		startX = 17;
		startY = 44;
		objects = assignObjects();
	}
	
	//Used in the constructor to initialize the ArrayList<GenericObjects> objects. This loops through
	//all the objects found in a map and puts them into a list.
	private ArrayList<GenericObject> assignObjects() {
		ArrayList<GenericObject> value = new ArrayList<GenericObject>();
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				for (MapObject object : layer.getObjects()) {
						value.add(new GenericObject(object, this));
					}
				}
			}
		return value;
	}
	/* Eventually, each level will be able to be made in tiled with any number of layers and we could specify 
	 * rendering order here. I want us to try and keep to a convention for our maps just so we can copy as much code 
	 * as possible for these level specific things
	 */
	@Override
	public void render(CustomTiledRenderer renderer) {
	
		/* This is just copied from player, using it as reference for extracting layer information 
		 * 
		 */
//		map = new TmxMapLoader().load("tmxFileName.tmx");
		
		// TODO Auto-generated method stub
//		private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
//			TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
//			rectPool.freeAll(tiles);
//			tiles.clear();
//			for(int y = startY; y <= endY; y++) {
//				for(int x = startX; x <= endX; x++) {
//					Cell cell = layer.getCell(x, y);
//					if(cell != null) {
//						Rectangle rect = rectPool.obtain();
//						rect.set(x, y, 1, 1);
//						tiles.add(rect);
//					}
//				}
//			}
//		}

	}
	
	private float timeInLevel = 0;
	
	public void logic(float deltaTime){
	timeInLevel = timeInLevel + deltaTime;	
	}

	//Needed so that each map has the tmx connected to its code. LevelLoader can get map easily.
	@Override
	public TiledMap getMap() {
		return map;
	}

	@Override
	public int getStartX() {
		return startX;
	}

	@Override
	public int getStartY() {
		return startY;
	}

	@Override
	public TextureRegion[] getTextures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GenericObject> toRender() {
		return objects;
	}
	
	

}
