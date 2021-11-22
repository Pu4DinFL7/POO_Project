package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement {

	public Terrain(Point2D position, String name, int layer) {
		super(position, name, layer);
	}

	@Override
	public String toString() {
		return "Terrain " + getPosition();
	}

}
