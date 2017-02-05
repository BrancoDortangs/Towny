package entity.mob;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Resource;
import entity.item.Item;
import entity.pathfinding.Path;
import entity.pathfinding.PathFinder;
import entity.pathfinding.Point;
import graphics.HairSprite;
import graphics.Screen;
import graphics.Sprite;
import map.Map;

public class Villager extends Mob {
	public Path movement;
	private int counter;
	private boolean arrived = false;
	public List<Item> wearing;
	public boolean male;
	public Item holding;
	private Sprite hair;
	private int hairnr;
	private boolean selected;
	private List<Job> jobs;
	private int idletimer = getIdleTimer();

	public Villager(int x, int y, Map level) {
		super(level);
		this.sprite = Sprite.getPerson();
		wearing = new ArrayList<Item>();
		jobs = new ArrayList<Job>();
		initHair(true);
		this.x = x;
		this.y = y;

	}
	private int getIdleTimer() {
		return random.nextInt(5)*60;
	}

	private boolean idleTime() {
		if (idletimer <= 0) {
			idletimer = getIdleTimer();
			return true;
		} else {
			idletimer = idletimer - 1;
			return false;
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void idle() {
		while (movement == null) {
			movement = getPath(x >> 4, y >> 4, (x >> 4) + random.nextInt(4) - 2, (y >> 4) + random.nextInt(4) - 2);
		}

	}

	public boolean isSelected() {
		return selected;
	}

	public Villager(int x, int y, Map level, int hairnr, List<Item> wearing, Item holding) {
		this(x, y, level);
		this.hairnr = hairnr;
		initHair(false);
		this.wearing = wearing;
		this.holding = holding;
	}

	private void initHair(boolean generate) {
		if (generate)
			hairnr = random.nextInt(HairSprite.maleHair.length);
		hair = HairSprite.maleHair[hairnr];

	}

	private Item getNearestItemOfType(String name) {
		Item closest = null;
		Path path = null;
		for (int i = 0; i < level.getItemList().size(); i++) {
			if (level.getItem(i).getName().equals(name)) {
				closest = level.getItem(i);
				path = getPath(x >> 4, y >> 4, closest.x >> 4, closest.y >> 4);
				if (closest.x >> 4 == x >> 4 && y >> 4 == closest.y >> 4) {
					return closest;
				}
			}
		}
		if (closest == null && path == null) {
			return null;
		}
		for (Item i : level.getItemList()) {
			if (i.getName().equals(name)) {
				if (path.getLength() > getPath(x >> 4, y >> 4, i.x >> 4, i.y >> 4).getLength()) {
					closest = i;
					path = getPath(x >> 4, y >> 4, i.x >> 4, i.y >> 4);
				}
			}
		}
		return closest;
	}

	public void work() {
		if (jobs.get(0) != null && !jobs.get(0).completed) {
			jobs.get(0).execute();
		} else {
			if (jobs.get(0).completed) {
				jobs.remove(0);
			}
		}

	}

	public boolean aroundSpot(int startx, int starty, int endx, int endy) {
		return aroundTile(startx >> 4, starty >> 4, endx >> 4, endy >> 4);

	}

	public boolean aroundTile(int startx, int starty, int endx, int endy) {
		return ((startx <= endx + 1 && startx >= endx - 1) && (starty >= endy - 1 && starty <= endy + 1));

	}

	public boolean pickUp(Item e) {
		if (!e.isReservedVil(this)) {
			return false;
		}
		movement = getShortest(e);
		if (aroundSpot(x, y, e.x, e.y)) {
			e.setReservedVil(this);
			level.removeItem(e);
			holding = e;
			return true;
		}
		if (!(movement == null)) {
			if (!movement.isArrived()) {
				e.setReservedVil(this);
				move();
				return false;
			} else {
				level.removeItem(e);
				holding = e;
				return true;
			}

		}
		return false;

	}

	public void drop() {
		if (holding != null) {
			holding.setReservedVil(null);
			level.addItem(holding);
		}
		holding = null;
	}

	public void addJob(Resource e) {
		if (e != null) {
			addJob(new Job(e, this));
		}
	}

	public void addBuildJob(int x, int y) {
		addJob(new Job(x, y, getNearestItemOfType("Logs"), this, level));
	}

	private void addJob(Job e) {
		jobs.add(e);
	}

	public Path getShortest(Entity e) {
		if (e != null)
			return getShortest(e.x >> 4, e.y >> 4);
		return null;
	}

	public Path getShortest(int x, int y) {
		return PathFinder.getShortest(
				new Path[] { getPath(this.x >> 4, this.y >> 4, x - 1, y), getPath(this.x >> 4, this.y >> 4, x + 1, y),
						getPath(this.x >> 4, this.y >> 4, x, y - 1), getPath(this.x >> 4, this.y >> 4, x, y + 1) });
	}

	public void update() {
		if (jobs.size() != 0) {
			work();
		} else {
			// IDLE
			if (idleTime()) {
				idle();
			}
			move();

		}

	}
	public int getJobSize() {
		return jobs.size();
	}

	public void addClothing(Item item) {
		wearing.add(item);
	}

	public void move() {
		if (movement == null)
			return;
		if (arrived) {
			counter++;
			arrived = false;
		}
		if (movement.getLength() == counter) {
			counter = 0;
			movement = null;
			arrived = false;
			return;
		} else {
			if (!arrived) {
				Point step = movement.getStep(counter);
				if (!level.isClearTile(step.x, step.y)) {
					int destx = movement.getXdest();
					int desty = movement.getYdest();
					movement = getShortest(destx, desty);
					return;
				}
				moveTo(step.x << 4, step.y << 4);
				if (x == step.x << 4 && y == step.y << 4) {
					arrived = true;
				}

			}
		}

	}

	public void resetMove() {
		if (holding != null) {
			drop();
		}
		// jobsLeeg(); //WIP
		counter = 0;
		arrived = false;
		movement = null;
	}

	private void jobsLeeg() {
		for (Job i : jobs) {
			jobs.remove(i);

		}
	}

	private void moveTo(int x, int y) { // DO NOT USE. SET DESTINATION ON
										// MOVEMENT AND USE move()!! DO NOT
										// USE!!!
		int xmov, ymov;
		if (this.x > x) {
			xmov = -1;
		} else {
			if (this.x == x) {
				xmov = 0;
			} else {
				xmov = 1;
			}
		}
		if (this.y > y) {
			ymov = -1;
		} else {
			if (this.y == y) {
				ymov = 0;
			} else {
				ymov = 1;
			}
		}
		move(xmov, ymov);
		if (!(holding == null)) {
			holding.x = x;
			holding.y = y;
		}

	}

	public void render(Screen screen) {
		screen.renderVillager(x, y, this);
		if (wearing != null) {
			for (int i = 0; i < wearing.size(); i++) {
				if (wearing.get(i) != null) {
					screen.renderEntity(x, y, wearing.get(i).sprite);
				}
			}
		}
		screen.renderEntity(x, y, hair);
		if (holding != null) {
			screen.renderEntity(x, y, holding.sprite);
		}
		screen.renderSelection(x, y, this);

	}

}
