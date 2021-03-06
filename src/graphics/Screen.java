package graphics;

import entity.mob.Villager;
import map.Tile;

//the game screen (the game world and the items in it, NOT INCLUDING THE UI)
public class Screen {
	public int width; //screen width
	public int height; //screen height
	public int[] pixels; // array of pixels (is 1d but is actually 2d)
	public int xOffset, yOffset; // the offset of the screen

	//constructor
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	//empties the pixels array
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	//render a tile on the screen
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < Sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < Sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -Sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = tile.sprite.pixels[x + y * Sprite.SIZE];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}

	//render the red square around selected entities
	public void renderSelection(int xp, int yp, Villager e) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < Sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < Sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -Sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				if (e.isSelected() && (x == 0 || x == Sprite.SIZE - 1 || y == 0 || y == Sprite.SIZE - 1)) {
					pixels[xa + ya * width] = 0xf44242;

				}

			}
		}
	}

	//render entities
	public void renderEntity(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < Sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < Sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -Sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * Sprite.SIZE];
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}

	}

	//sets the offset for the screen
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
