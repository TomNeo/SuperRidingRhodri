package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public interface Level {
	
	void render(OrthogonalTiledMapRenderer renderer);
	TiledMap getMap();
	int getStartX();
	int getStartY();
	
}
