package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement {

	private static List<Fire> listFires;
	private static ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

	public Fire(Point2D position) {
		super(position, "fire", 1);
		listFires = new ArrayList<>();
	}

	public static void getFires() {
		for (GameElement ge : listGameElements()) {
			if (ge instanceof Fire) {
				listFires.add((Fire) ge);
			}
		}
	}

	public static void propagate() {
		List<Terrain> list = new ArrayList<>();
		List<Point2D> points = new ArrayList<>();
		getFires();
		for (Fire f : listFires) {
//			System.out.println("________________________________________________________");
//			System.out.println("Fire: " + f);
			points.addAll(f.getPosition().getNeighbourhoodPoints());
//			System.out.println("_________________________________________________________");
//			System.out.println("Points " + points);
		}
		for (GameElement ge : listGameElements()) {
			if (ge instanceof Terrain) {
				list.add((Terrain) ge);
//				System.out.println("Terrain " + ge);
			}
		}
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
//				System.out.println(points.get(i) + "==" + list.get(j).getPosition());
				if (points.get(i).equals(list.get(j).getPosition())) {
//					System.out.println("Entrei aqui");
					// atualiza os objetos na tileList
					GameEngine.addImage(new Fire(list.get(j).getPosition()));
//					listGameElements().add(new Fire(list.get(j).getPosition()));
					listFires.add(new Fire(list.get(j).getPosition()));
					gui.addImage(new Fire(list.get(j).getPosition()));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Fire " + getPosition();
	}
}
