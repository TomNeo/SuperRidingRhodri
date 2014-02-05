package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CustomPlayer {
	
	TiledMap map; 
	LevelLoader home;
	
	public CustomPlayer (TiledMap Map, LevelLoader Home) {
		this.map = Map;
		this.home = Home;
	}
	
	

	public static float WIDTH;
	public static float HEIGHT;
	static float MAX_VELOCITY = 4f;
	static float JUMP_VELOCITY = 40f;
	static float DAMPING = 0.87f;

	public enum State {
		Standing,
		Walking,
		Jumping
	}

	public final Vector2 position = new Vector2();
	final Vector2 velocity = new Vector2();
	public State state = State.Walking;
	public float stateTime = 0;
	public boolean facesRight = true;
	boolean grounded = false;
	private static final float GRAVITY = -2.5f;
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject () {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();
	private Array<Rectangle> deadlyTiles = new Array<Rectangle>();
	private Array<Rectangle> doors = new Array<Rectangle>();

	public void changeMap(TiledMap newMap){
		this.map = newMap;
	}
	
	public void updatePlayer(float deltaTime) {
		if(deltaTime == 0) return;
		stateTime += deltaTime;
		
		if (position.x <= 0)
			position.x = 0;
		
		if (position.y <= 0)
			position.set(home.getCurrentLevel().getStartX(), home.getCurrentLevel().getStartY());

		// check input and apply to velocity & state
		if((Gdx.input.isKeyPressed(Keys.SPACE)) && grounded) {
			velocity.y += CustomPlayer.JUMP_VELOCITY;
			state = CustomPlayer.State.Jumping;
			grounded = false;
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || isTouched(0, 0.5f)) {
			velocity.x = -CustomPlayer.MAX_VELOCITY;
			if (grounded) state = CustomPlayer.State.Walking;
			facesRight = false;
		}

		if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || isTouched(0.5f, 1)) {
			velocity.x = CustomPlayer.MAX_VELOCITY;
			if(grounded) state = CustomPlayer.State.Walking;
			facesRight = true;
		}

		// apply gravity if we are falling
		velocity.add(0, GRAVITY);

		// clamp the velocity to the maximum, x-axis only
		if(Math.abs(velocity.x) > Player.MAX_VELOCITY) {
			velocity.x = Math.signum(velocity.x) * Player.MAX_VELOCITY;
		}

		// clamp the velocity to 0 if it's < 1, and set the state to standing
		if(Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if(grounded) state = CustomPlayer.State.Standing;
		}

		// multiply by delta time so we know how far we go
		// in this frame
		velocity.scl(deltaTime);

		// perform collision detection & response, on each axis, separately
		// if the player is moving right, check the tiles to the right of it's
		// right bounding box edge, otherwise check the ones to the left
		Rectangle playerRect = rectPool.obtain();
		playerRect.set(position.x, position.y, Player.WIDTH, Player.HEIGHT);
		int startX, startY, endX, endY;
		if (velocity.x > 0) {
			startX = endX = (int)(position.x + Player.WIDTH + velocity.x);
		} else {
			startX = endX = (int)(position.x + velocity.x);
		}
		startY = (int)(position.y);
		endY = (int)(position.y + Player.HEIGHT);
		getTiles(startX, startY, endX, endY, tiles);
		playerRect.x += velocity.x;
		
		for (Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				velocity.x = 0;
				break;
			}
		}
		
		getDoorTiles(startX, startY, endX, endY, doors);
		for (Rectangle tile : doors) {
			if (playerRect.overlaps(tile)) {
				position.x = 33;
			}
		}
		
		getDeadlyTiles(startX, startY, endX, endY, deadlyTiles);
		for (Rectangle tile: deadlyTiles) {
			if(playerRect.overlaps(tile)) {
				position.x = home.getCurrentLevel().getStartX();
				position.y = home.getCurrentLevel().getStartY();
				break;
			}
		}
		playerRect.x = position.x;

		// if the player is moving upwards, check the tiles to the top of it's
		// top bounding box edge, otherwise check the ones to the bottom
		if(velocity.y > 0) {
			startY = endY = (int)(position.y + Player.HEIGHT + velocity.y);
		} else {
			startY = endY = (int)(position.y + velocity.y);
		}
		startX = (int)(position.x);
		endX = (int)(position.x + Player.WIDTH);
		getTiles(startX, startY, endX, endY, tiles);
		playerRect.y += velocity.y;
		for(Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				// we actually reset the player's y-position here
				// so it is just below/above the tile we collided with
				// this removes bouncing :)
				if(velocity.y > 0) {
					position.y = tile.y - Player.HEIGHT;
					
					// we hit a block jumping upwards, let's destroy it!
					/*TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
					layer.setCell((int)tile.x, (int)tile.y, null);*/
				} else {
					position.y = tile.y + tile.height;
					// if we hit the ground, mark us as grounded so we can jump
					grounded = true;
				}
				velocity.y = 0;
				break;
			}
		}
		
		
		getDeadlyTiles(startX, startY, endX, endY, deadlyTiles);
		for (Rectangle tile: deadlyTiles) {
			if(playerRect.overlaps(tile)) {
				position.y = home.currentLevel.getStartY();
				break;
			}
		}
		rectPool.free(playerRect);

		// unscale the velocity by the inverse delta time and set 
		// the latest position
		position.add(velocity);
		velocity.scl(1/deltaTime);

		// Apply damping to the velocity on the x-axis so we don't
		// walk infinitely once a key was pressed
		velocity.x *= Player.DAMPING;

	}
	
	private boolean isTouched(float startX, float endX) {
		// check if any finge is touch the area between startX and endX
		// startX/endX are given between 0 (left edge of the screen) and 1 (right edge of the screen)
		for(int i = 0; i < 2; i++) {
			float x = Gdx.input.getX() / (float)Gdx.graphics.getWidth();
			if(Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
				return true;
			}
		}
		return false;
	}

	private void getTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		rectPool.freeAll(tiles);
		tiles.clear();
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

	private void getDeadlyTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
		rectPool.freeAll(tiles);
		tiles.clear();
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}
	
	private void getDoorTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
//		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(2);
//		rectPool.freeAll(tiles);
//		tiles.clear();
//		for(int y = startY; y <= endY; y++) {
//			for(int x = startX; x <= endX; x++) {
//				Cell cell = layer.getCell(x, y);
//				if(cell != null) {
//					Rectangle rect = rectPool.obtain();
//					rect.set(x, y, 1, 1);
//					tiles.add(rect);
//				}
//			}
//		}
	}
	
	public void moveTo(int newX, int newY){
		position.x = newX;
		position.y = newY;
	}
	
	//Will be called at the level logic if we use additional tiled map layers to do special collision things(this is wide and allows for custom code per level)
	public boolean collideSpecialTileset(TiledMapTileLayer special) {
		boolean test = false;
		rectPool.freeAll(tiles);
		tiles.clear();
		Rectangle playerRect = rectPool.obtain();
		playerRect.set(position.x, position.y, Player.WIDTH, Player.HEIGHT);
		int startX, startY, endX, endY;
		if (velocity.x > 0) {
				startX = endX = (int)(position.x + Player.WIDTH + velocity.x);
		} else {
				startX = endX = (int)(position.x + velocity.x);
		}
		startY = (int)(position.y);
		endY = (int)(position.y + Player.HEIGHT);
		playerRect.x += velocity.x;		
		///	getTiles(startX, startY, endX, endY, tiles);
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) {
				Cell cell = special.getCell(x, y);
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
		
		for (Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				test = true;
				break;
			}
		}
		
		rectPool.freeAll(tiles);
		tiles.clear();
		if(velocity.y > 0) {
			startY = endY = (int)(position.y + Player.HEIGHT + velocity.y);
		} else {
			startY = endY = (int)(position.y + velocity.y);
		}
		startX = (int)(position.x);
		endX = (int)(position.x + Player.WIDTH);
		
		for(int y = startY; y <= endY; y++) {
			for(int x = startX; x <= endX; x++) {
				Cell cell = special.getCell(x, y);
				if(cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
		
		for (Rectangle tile: tiles) {
			if(playerRect.overlaps(tile)) {
				test = true;
				break;
			}
		}
		rectPool.freeAll(tiles);
		rectPool.free(playerRect);
		tiles.clear();
		return test;
		
	}

}
