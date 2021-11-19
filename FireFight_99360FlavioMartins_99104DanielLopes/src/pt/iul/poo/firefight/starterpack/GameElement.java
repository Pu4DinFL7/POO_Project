package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private String name;
	private Point2D position;
	private int layer;

	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name = name;
		this.layer = layer;

	}

	public void changePosition(Direction dir) {
		position = position.plus(dir.asVector());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

}
