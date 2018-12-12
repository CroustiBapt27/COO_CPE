package launcher.localLauncher;

import java.awt.Dimension;
import tools.Observer;

import javax.swing.JFrame;

import controler.ChessGameControler;
import controler.ChessGameControlers;
import model.ChessGame;
import vue.ChessGameGUI;



/**
 * @author francoise.perrin
 * Lance l'ex�cution d'un jeu d'�chec en mode graphique.
 * La vue (ChessGameGUI) observe le mod�le (ChessGame)
 * les �changes passent par le contr�leur (ChessGameControlers)
 * 
 */
public class LauncherGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ChessGame chessGame;	
		ChessGameControlers chessGameControler;
		JFrame frame;	
		Dimension dim;
	
		dim = new Dimension(700, 700);
		
		chessGame = new ChessGame();	
		chessGameControler = new ChessGameControler(chessGame);
		
		frame = new ChessGameGUI("Jeu d'�chec", chessGameControler,  dim);
		chessGame.addObserver((Observer) frame);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(600, 10);
		frame.setPreferredSize(dim);
		frame.pack();
		frame.setVisible(true);
	}
}
