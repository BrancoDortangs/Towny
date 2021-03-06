package entity.item;

import entity.Entity;
import entity.mob.Villager;
import graphics.Sprite;


public class Item extends Entity {
	private String name; // the item's name
	private String tooltip; // the item's tooltip
	private Villager reservedVil; // the villager that plans to pick the item up, or is already holding it
	public int quantity; // the amount of the item (5 logs in a stack for instance)

	//basic constructors
	public Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible, int quantity) {
		super(x, y);
		this.sprite = sprite;
		setVisible(visible);
		this.name = name;
		this.quantity = quantity;
		this.tooltip = tooltip;
	}

	public Item(String name, int x, int y, Sprite sprite, boolean visible, int quantity) {
		this(name, x, y, sprite, "", visible, quantity);
	}

	public Item(Item o) {
		this(o.getName(), o.x, o.y, o.sprite, o.getToolTip(), o.isVisible(), o.quantity);
	}

	//getters and setters
	public String getToolTip() {
		return tooltip;
	}

	public String getName() {
		return name;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isReservedVil(Villager vil) {
		return reservedVil == null || reservedVil.equals(vil);
	}

	public void setReservedVil(Villager vil) {
		reservedVil = vil;
	}
}
