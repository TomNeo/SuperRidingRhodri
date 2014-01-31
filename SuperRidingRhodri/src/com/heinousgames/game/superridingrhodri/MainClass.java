package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MainClass implements ApplicationListener {
	public static TiledMap map;
	private CustomTiledRenderer renderer;
	private OrthographicCamera camera;
	private double sin;
	private float r, g, b, time, delta, sr, sg, sb;
	private boolean forward, square, fiveSecs;
	private float songTime;

	private ShapeRenderer shapeRenderer;
	
	private LevelLoader levelLoader;
	private Texture playerTexture;
	private TextureRegion tRegion;
	private Player player;
	private Animation stand;
	private Animation walk;
	private Animation jump;

	private Music deerTickMusic;

	@Override
	public void create() {
		playerTexture = new Texture("gfx/rhondi.png");
		tRegion = new TextureRegion(playerTexture, 0, 0, 30, 62);

		deerTickMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/dishes2.ogg"));
		shapeRenderer = new ShapeRenderer();

		stand = new Animation(0, tRegion);
		jump = new Animation(0, tRegion);
		walk = new Animation(0, tRegion);

		// figure out the width and height of the player for collision
		// detection and rendering by converting a player frames pixel
		// size into world units (1 unit == 16 pixels)
		Player.WIDTH = 1 / 32f * tRegion.getRegionWidth();
		Player.HEIGHT = 1 / 32f * tRegion.getRegionHeight();

		sin = 0;
		time = 0;
		songTime = 0;
		r = 1;
		g = 0.5f;
		b = 0;
		forward = true;
		square = true;
		fiveSecs = false;
		sr = 0;
		sg = 1;
		sb = 0;

		/*first implementation of levelloader. On game create it will load with the current dummy level "LevelTemplate"
		 * this section of code will be redone and probably relocated, but I needed to start somewhere.
		 */
		levelLoader = new LevelLoader();
		levelLoader.add(0, new ExampleLevel1());
		levelLoader.setCurrentLevel(levelLoader.queue.get(0));
		
		// load the map, set the unit scale to 1/32 (1 unit == 32 pixels)
		map = levelLoader.getCurrentLevel().getMap();
		renderer = new CustomTiledRenderer(levelLoader.getCurrentLevel(), 1 / 32f);
		
		// create an orthographic camera, shows us 30x20 units of the world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		// create the rhodri we want to move around the world
		player = new Player(map);
		player.position.set(levelLoader.getCurrentLevel().getStartX(),levelLoader.getCurrentLevel().getStartY());

		// start the playback of the background music immediately
		//deerTickMusic.setLooping(true);
		//deerTickMusic.play();

	}

	@Override
	public void render() {

		delta = Gdx.graphics.getDeltaTime();

		if (time >= 1) {
			ColorCycle();
		}

		time += delta;
		songTime += delta;

		/*if (songTime == 3) {
			r=1;
			g = 0;
			b = 0;
		}

		if (songTime == 6) {
			g = 1;
			r = b = 0;
		}*/

		// update the player (process input, collision detection, position update)
		player.updatePlayer(delta);

	
		camera.position.x = player.position.x;
		camera.position.y = player.position.y;

		camera.update();
		
		// set the tile map renderer view based on what the
		// camera sees and render the map
		renderer.setView(camera);
		
		//THIS IS THE NORMAL METHOD FOR RENDERER
		renderer.render();
		levelLoader.tick(delta);
		
		//THIS IS THE RENDER METHOD FOR LEVELTEMPLATE1
		//renderer.renderMode1();
			
//		shapeRenderer.setProjectionMatrix(camera.combined);
//		shapeRenderer.begin(ShapeType.Filled);
//		shapeRenderer.setColor(sr, sg, sb, 1);
//		shapeRenderer.rect(12, 7, 4, 2);
		//shapeRenderer.circle(x, y, radius);
//		shapeRenderer.end();
		
		//System.out.println(songTime);
		
		if (songTime >= 1.9 && !fiveSecs){
			fiveSecs = true;
			songTime = 0;
		}
		
		if (songTime >= 1 && square && fiveSecs) {
			sr = 1;
			sg = 0;
			sb = 0;
			square = !square;
			songTime = 0;
			changeSquareColor();
		} else if (songTime >= 1 && !square && fiveSecs) {
			sr = 0;
			sg = 1;
			sb = 0;
			square= !square;
			songTime = 0;
			changeSquareColor();
		}
		

		// render the player
		renderPlayer(delta);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	/**
	 * Continuously clears the screen so the background is always changing colors.
	 * Taking out the additives to the sins will result in just black and white, no color.
	 * Decrease the sin to .1 when doing black and white to make the game incredibly challenging
	 */
	private void ColorCycle() {
		/*
		 * black to white
		 * take out the +2 and +4
		 * essentially make r, g, b all the same number
		 */
		/* 
		 * red to cyan
		 * add +2 to the r when rgb is already the same
		 */

		//turquoiseR = 64, 224, 208
		// teal = 0, 128, 128;
		// orange = 255, 127, 0;

		/*r = (float) ((Math.sin(sin+0)*127+128)/255);
		g = (float) ((Math.sin(sin+2)*127)/255);
		b = (float) ((Math.sin(sin+4)*127)/255);
		sin += .1053;*/
		if (forward) {
			r -= 0.03;
			b += 0.015;
		} else {
			r += 0.03;
			b -= 0.015;
		}

		if ((r <= 0 || b >= 0.5f) && forward) {
			forward = !forward;
		} else if (!forward && (r >= 1 || b <= 0)) {
			forward = !forward;
		}


		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	private void renderPlayer(float deltaTime) {
		// based on the player state, get the animation frame
		TextureRegion frame = null;
		switch(player.state) {
		case Standing: frame = stand.getKeyFrame(player.stateTime); break;
		case Walking: frame = walk.getKeyFrame(player.stateTime); break;
		case Jumping: frame = jump.getKeyFrame(player.stateTime); break; 
		}

		// draw the player, depending on the current velocity
		// on the x-axis, draw the player facing either right
		// or left
		SpriteBatch batch = renderer.getSpriteBatch();
		batch.begin();
		if(player.facesRight) {
			batch.draw(frame, player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);
		} else {
			batch.draw(frame, player.position.x + Player.WIDTH, player.position.y, -Player.WIDTH, Player.HEIGHT);
		}
		batch.end();
	}
	
	private void changeSquareColor() {
		//shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(sr, sg, sb, 1);
		shapeRenderer.rect(12, 7, 4, 2);
		//shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
	}
}
