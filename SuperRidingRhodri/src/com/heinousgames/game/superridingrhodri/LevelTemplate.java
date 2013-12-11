package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class LevelTemplate implements Level{

	private TiledMap map;	
	
	@Override
	public void render(OrthogonalTiledMapRenderer renderer) {
		
//		map = new TmxMapLoader().load("tmxFileName.tmx");
		
		map = new TmxMapLoader().load("gfx/HelloRhodri.tmx");
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
	
	

}
