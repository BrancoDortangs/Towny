package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import main.Game;

public class Mouse extends MouseAdapter implements MouseInputListener {
	//used to register the mouse on the screen
	private Game game;
	private int mouseX = -1; // x and y coord on the screen, in ingame pixels (/3), WITH OFFSET
	private int mouseY = -1;
	private int mouseB = -1; // the mouse button pressed
	private int mouseTileX = -1; // the x and y of the tiles in the game that the mouse is on
	private int mouseTileY = -1;
	private int trueX = -1; // x and y coord on the screen, in ingame pixels (/3), WITHOUT OFFSET
	private int trueY = -1;
	private int trueXpixels = -1; // x and y coord on the screen, in acutal pixels, WITHOUT OFFSET
	private int trueYpixels = -1;
	private boolean clicked; // has the mouse been clicked
	private boolean drag; // has the mouse been dragged
	private boolean released; // has the mouse been released

	//constructor
	public Mouse(Game game) {
		this.game = game;
	}

	//getters
	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY;
	}

	public int getButton() {
		return mouseB;
	}

	public int getTileY() {
		return mouseTileY;
	}

	public int getTileX() {
		return mouseTileX;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		clicked = true;

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouseB = arg0.getButton();

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mouseB = -1;
		released = true;

	}
	public boolean getClicked() {
		return clicked;
	}
	public boolean getReleased() {
		return released;
	}
	public void reset() {
		clicked = false;
		released = false;
		if (mouseB!=1)
		drag = false;
		
	}
	public boolean getDrag() {
		return drag;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		drag = true;
		trueXpixels = arg0.getX();
		trueYpixels = arg0.getY();
		trueX = trueXpixels / 3;
		trueY = trueYpixels / 3;
		mouseX = trueX + game.xScroll;
		mouseY = trueY + game.yScroll;
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		trueXpixels = arg0.getX();
		trueYpixels = arg0.getY();
		trueX = trueXpixels / 3;
		trueY = trueYpixels / 3;
		mouseX = trueX + game.xScroll;
		mouseY = trueY + game.yScroll;
		mouseTileX = mouseX >> 4;
		mouseTileY = mouseY >> 4;

	}

	public int getTrueX() {
		return trueX;
	}

	public int getTrueY() {
		return trueY;
	}

	public int getTrueXPixels() {
		return trueXpixels;
	}

	public int getTrueYPixels() {
		return trueYpixels;
	}

}
