package vue;

import model.Coord;
import model.PieceIHMs;
import tools.Observer;

import java.util.List;

import controler.ChessGameControler;



/**
 * @author francoise.perrin
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD *
 * 
 * Vue console d'un jeu d'echec
 * Cette classe est un observateur et le damier est mis à jour à chaque changement dans la classe metier
 */
public class ChessGameCmdLine implements Observer {

	ChessGameControler chessGameControler;

	public   ChessGameCmdLine(ChessGameControler chessGameControler) {
		this.chessGameControler = chessGameControler;
	}
	
	@Override
	public void update(Object o) {
		List<PieceIHMs> listPieceIhm = (List<PieceIHMs>) o;
		System.out.println(listPieceIhm);
	}


	public void go() {

		System.out.print("\n Deplacement de 3,6 vers 3,4 = ");
		chessGameControler.move(new Coord(3,6), new Coord(3, 4));	// true
		System.out.println(chessGameControler.getMessage() + "\n");	
		System.out.println(chessGameControler);
		
		System.out.print("\n Deplacement de 3,4 vers 3,6 = ");		
		chessGameControler.move(new Coord(3,4), new Coord(3, 6));	// false 
		System.out.println(chessGameControler.getMessage() + "\n");	
		System.out.println(chessGameControler);
		
		System.out.print("\n Deplacement de 4,1 vers 4,3 = ");
		chessGameControler.move(new Coord(4, 1), new Coord(4, 3));	// true
		System.out.println(chessGameControler.getMessage() + "\n");	
		System.out.println(chessGameControler);
		
		System.out.print("\n Deplacement de 3,4 vers 3,4 = ");
		chessGameControler.move(new Coord(3, 4), new Coord(3, 4));	// false
		System.out.println(chessGameControler.getMessage() + "\n");	
		System.out.println(chessGameControler);
		
		System.out.print("\n Deplacement de 3,4 vers 4,3 = ");
		chessGameControler.move(new Coord(3, 4), new Coord(4, 3));	// true		
		System.out.println(chessGameControler.getMessage() + "\n");	
		System.out.println(chessGameControler);
	}


	

}
