package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class LevelTemplate2 implements Level{

	int startX;
	int startY;
	private TiledMap map;	
	
	public LevelTemplate2(){
		map = new TmxMapLoader().load("gfx/HelloRhodri.tmx");
		startX = 14;
		startY = 98;
	}
	
	/* Eventually, each level will be able to be made in tiled with any number of layers and we could specify 
	 * rendering order here. I want us to try and keep to a convention for our maps just so we can copy as much code 
	 * as possible for these level specific things
	 */
	@Override
	public void render(OrthogonalTiledMapRenderer renderer) {
	
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
	
	

}
