package com.heinousgames.game.superridingrhodri;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class LevelTemplate1 implements Level{

	int startX;
	int startY;
	private TiledMap map;
	ArrayList<MapObject> objects;
	GenericObject firstDoor;
	ArrayList<GenericObject> toRender;
	
	public LevelTemplate1(){
		map = new TmxMapLoader().load("gfx/HelloRhodri.tmx");
		startX = 14;
		startY = 98;
		toRender = new ArrayList<GenericObject>();
		objects = setObjects();
		onCreate();
	}
	
	private void onCreate() {
		firstDoor = new GenericObject("door", this);
		assignRef(firstDoor);
		firstDoor.constructFromRef();
		firstDoor.setWidth(1);
		firstDoor.setHeight(1);
		firstDoor.setPos(28, 87);
		toRender.add(firstDoor);
	}

	//Called by the render each frame to see what objects need to be rendered
	public ArrayList<GenericObject> toRender(){
		return toRender;
	}
	
	//Used in the constructor to initialize the ArrayList<GenericObjects> objects. This loops through
	//all the objects found in a map and puts them into a list.
	private ArrayList<MapObject> setObjects() {
		ArrayList<MapObject> value = new ArrayList<MapObject>();
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible()) {
				for (MapObject object : layer.getObjects()) {
						value.add(object);
					}
				}
			}
		return value;
	}

	/*No longer sure if we will need a render step here, but we should leave it for now.
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
	
	public void logic(){
		//Level specific logic (cutscene, special one time thing, graphical manipulations)
	}
	
	//I created this class to link objects we create to objects found in the objects arrayList.
	//if there is an object in the array with the same "type" value, then this method will change
	//the GenericObjects ref variable to be that object. This allows us to find the proper img to render.
	public void assignRef(GenericObject target){
		for(MapObject object : objects){
			MapProperties objProperties = object.getProperties();
			if(target.getType().equalsIgnoreCase((String)objProperties.get("type"))){
				for(TiledMapTileSet set : map.getTileSets()){
					if (set.getName().equals(object.getProperties().get("type"))){
						target.setRef(object);
					}
				}
			}
		}
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

	//This was meant to grab out all of the texture regions found inside the tmx, but I had 
	//a hard time figuring out how we would identify which one should render for which object
	//Could probably go through it, it did help me debug so stuff. might be usefull.
	@Override
	public TextureRegion[] getTextures(){
		ArrayList<TextureRegion> list = new ArrayList<TextureRegion>();
		if(objects.size() > 0){
			for(MapObject source : objects){
				MapProperties objectProperties = source.getProperties();
				for(TiledMapTileSet tiles : map.getTileSets()){
					if(tiles.getName().equals(objectProperties.get("type"))){
						int src_id = Integer.parseInt(objectProperties.get("src_id").toString());
						list.add(tiles.getTile(src_id).getTextureRegion());
						}
					}
			}
		}
		if(list.size() > 0){
			int index = 0;
			TextureRegion[] textures = new TextureRegion[list.size()];
			for(TextureRegion active : list){
				textures[index] = active;
				index++;
			}
			return textures;
		}
		return null;
	}
	
	public TextureRegion getTexture(String key){
		return null;
	}
	
	

}
