package graphics.ui.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import input.Mouse;

public class MenuItem {
	private String text; //text on the menuitem
	public int x, y; // x and y of top left corner
	private int width; // width of the menuitem
	private boolean hover; // is the mouse hovering over the item
	private static final Font font = new Font("Dialog", Font.LAYOUT_LEFT_TO_RIGHT, 15); // font
	//some static strings to use as menuitem texts
	public static final String CANCEL = "Cancel";
	public static final String MOVE = "Move";
	public static final String CHOP = "Chop";
	public static final String MINE = "Mine";
	public static final String BUILD = "Build";

	//constructor
	public MenuItem(String type, Menu menu) {
		this.text = type;
		x = menu.getX();
		width = menu.getWidth();
		y = menu.getYLocForMenuItem();

	}

	//rendering the menuitem's text
	public void render(Graphics g) {
		if (hover) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		g.setFont(font);
		g.drawString(text, x, y + 15);
	}

	//updateing the mouse hover
	public void update(Mouse mouse) {
		hover = ((((mouse.getTrueXPixels()) >= x) && ((mouse.getTrueXPixels()) <= x + width)
				&& ((mouse.getTrueYPixels()) >= y) && ((mouse.getTrueYPixels()) <= y + 10)));
	}

	//getter
	public boolean clicked(Mouse mouse) {
		return hover && mouse.getButton() == 1;
	}

	//the menuitem is equal to other menuitems with the same text
	public boolean equals(String i) {
		return text.equals(i);
	}

}
