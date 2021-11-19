package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<ImageTile> tileList; // Lista de imagens
	private Fireman fireman; // Referencia para o bombeiro

	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador
	// e' feito no construtor
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	public GameEngine() {

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		tileList = new ArrayList<>();
	}

	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado
	// (neste caso seria a GUI)
	@Override
	public void update(Observed source) {

//		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada
		// if (key == KeyEvent.VK_ENTER) // se a tecla for ENTER, manda o bombeiro mover
		fireman.move();

		gui.update(); // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}

	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		// createTerrain(); // criar mapa do terreno
		// createMoreStuff(); // criar mais objetos (bombeiro, fogo,...)
		sendImagesToGUI(); // enviar as imagens para a GUI
	}

	// Criacao do terreno - so' pinheiros neste exemplo

	public void readFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		String aux;
		Scanner sc = new Scanner(file);
		int x = 0;
		int y = 0;
		while (sc.hasNextLine()) {
			aux = sc.nextLine();
			// itera sobre uma linha do ficheiro para saber o caracter que esta em cada
			// posicao
			if (x < GRID_WIDTH && y < GRID_HEIGHT) {
				for (int i = 0; i < aux.length(); i++) {
					if (x < GRID_WIDTH) {
						drawTerrain(x, y, aux.charAt(i));
						x++;
					}
					if (x >= GRID_WIDTH) {
						x = 0;
						y++;
					}
				}
			} else {
				String[] str = aux.split(" ");
				String name = str[0];
				int positionX = Integer.parseInt(str[1]);
				int positionY = Integer.parseInt(str[2]);
				drawObject(name, positionX, positionY);
			}
		}
		sc.close();
	}

	// private void createTerrain() {
//
//		for (int y = 0; y < GRID_HEIGHT; y++)
//			for (int x = 0; x < GRID_HEIGHT; x++)
//				tileList.add(new Pine(new Point2D(x, y)));
//	}

	private void drawObject(String s, int x, int y) {
		switch (s) {
		case "Fireman":
			fireman = new Fireman(new Point2D(x, y));
			tileList.add(fireman);
			break;

		case "Bulldozer":
			Bulldozer bulldozer = new Bulldozer(new Point2D(x, y));
			tileList.add(bulldozer);
			

			break;

		case "Fire":
			tileList.add(new Fire(new Point2D(x, y)));
			
			break;
		default:

			break;
		}
	}

	private void drawTerrain(int x, int y, char c) {
//		System.out.println("X -> " + x);
//		System.out.println("Y -> " + y);
		switch (c) {
		case 'p':
			tileList.add(new Pine(new Point2D(x, y)));
			// System.out.println("TEMOS UM PINHEIRO");
			break;
		case 'e': {
			tileList.add(new Eucaliptus(new Point2D(x, y)));
			break;
		}

		case 'm': {
			tileList.add(new Grass(new Point2D(x, y)));
			break;
		}

		case '_': {
			tileList.add(new Ground(new Point2D(x, y)));
			break;
		}

		default:
			// System.out.println("Para de me foder");
			break;
		}
	}
	
	
	
	
	

	// Criacao de mais objetos - neste exemplo e' um bombeiro e dois fogos
	private void createMoreStuff() {

//		Point2D position = new Point2D(100,100);
		fireman = new Fireman(new Point2D(5, 5));

		// VALIDA SE O BOMBEIRO ESTA A SER CIRADO DENTRO DA AREA DE JOGO
//		if(fireman.canMoveTo(position) == false)
//			throw new IllegalArgumentException();
		tileList.add(fireman);

		tileList.add(new Fire(new Point2D(3, 3)));
		tileList.add(new Fire(new Point2D(3, 2)));
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
