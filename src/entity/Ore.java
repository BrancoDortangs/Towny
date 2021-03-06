package entity;

import entity.item.Item;
import graphics.Sprite;
import map.Level;
import sound.Sound;

public class Ore extends Resource {
	private float mined = 100; //mined percentage (100 = unfinished / 0 = finished)
	private Sprite itemSprite; // sprite for the item the ore drops when mined
	private OreType type; // type of ore (iron, coal, gold,...)

	//basic constructor
	public Ore(int x, int y, OreType type) {
		super(x, y);
		this.type = type;
		setVisible(true);
		getSprite();
	}

	//decide the sprite for the ore
	private void getSprite() {
		switch (type) {
		case IRON:
			sprite = Sprite.ironOre;
			itemSprite = Sprite.ironChunk;
			break;
		case GOLD:
			sprite = Sprite.goldOre;
			itemSprite = Sprite.goldChunk;
			break;
		case COAL:
			sprite = Sprite.coalOre;
			break;
		default:
			sprite = null;
		}
	}

	//work method executed by the villager when mining
	public boolean work(Level level) {
		if (mined > 0){
			if (mined%20==0) Sound.speelGeluid(Sound.stoneMining);
			mined--;
			return false;
		} else {
			level.entities.remove(this);
			level.getTile(x>>4, y>>4).setSolid(false);
			level.addItem(new Item(type.name().toLowerCase() + " ore", this.x, this.y, itemSprite, true, 2));
			return true;
		}
	}
}
