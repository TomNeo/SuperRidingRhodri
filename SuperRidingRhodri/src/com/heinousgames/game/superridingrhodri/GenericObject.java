package com.heinousgames.game.superridingrhodri;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;



//This is going to be a generic handle for objects that interact in the game. We can extend this object to define special actions (doors, have exits, enemies hurt you, ect...)
//Most things (everything concerning rendering) will use this class type as the argument.
public class GenericObject extends MapObject{
	
	//This texture region comes from this.ref we will have things with animation control their own animation from inside this class
	//of of this texture region. In essence the img here will hold all the spriteframes, but when they render this will return a 
	//texture that is only the proper frame to render.
	TextureRegion img;
	public float xPos, yPos, width, height;
	public String type;
	MapObject ref;
	private Level owner;
	
	public GenericObject(String type, Level parent){
		this.type = type;
		owner = parent;
		ref = new MapObject();
		img = new TextureRegion();
	}
	
	public void logic(){
	}
	
	public TextureRegion getImg(){
		return img;
	}
	
	public void setWidth(float w){
		width = w;
	}
	
	public void setHeight(float h){
		height = h;
	}
	
	public String getType(){
		return type;
	}
	
	public void setRef(MapObject arg){
		ref = arg;
	}
	
	public void setPos(float x, float y){
		this.xPos = x;
		this.yPos = y;
	}
	
	//Possibly how we bring in info from the tmx. Used in the Level that creates this object to link the this to a MapObject found from the tmx.
	//This method then goes in and finds the tile found at the ref objects "src_id" property (we enter that in Tiled)
	/**IF SOMETHING DOESN"T MATCH HERE (type or src_id) IT WON"T RENDER**/
	public void constructFromRef(){
			for(TiledMapTileSet tiles : owner.getMap().getTileSets()){
				if(tiles.getName().equals(ref.getProperties().get("type"))){
						int src_id = Integer.parseInt(ref.getProperties().get("src_id").toString());
						img = tiles.getTile(src_id).getTextureRegion();
				}
			}
	}

	
	public void setImg(TextureRegion set){
		img = set;
	}

}