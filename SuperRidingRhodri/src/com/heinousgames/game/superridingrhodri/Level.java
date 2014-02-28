package com.heinousgames.game.superridingrhodri;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public interface Level {
	
	void render(CustomTiledRenderer renderer);
	void logic(float deltaTime);
	TiledMap getMap();
	int getStartX();
	int getStartY();
	void setStartX(int x);
	void setStartY(int y);
	void setQueue();
	TextureRegion[] getTextures();
	ArrayList<GenericObject> toRender();
	void setHome(LevelLoader levelLoader);
}