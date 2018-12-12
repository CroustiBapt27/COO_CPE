package model;

import java.util.LinkedList;
import java.util.List;


/**
 * @author francoise.perrin - 
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD
 * 
 * <p>La clase Echiquier sert de facade aux jeux et pieces
 * qui ne sont pas accessibles de l'exterieur
 * C'est elle qui gere toute la logique metier des deplacements </p>
 *
 */
/**
 * @author francoise.perrin
 *
 */

public class Echiquier implements BoardGames {

	private Game jeuBlanc;
	private Game jeuNoir;
	private Game jeuCourant, jeuOppose;
	private String message;
	 
	private boolean isMoveOk ;
	private boolean isCatchOk;
	private boolean isPieceToMoveOk;

	private boolean isForMove;
	private boolean isCastlingPossible; 


	public Echiquier() {
		super();
		this.jeuBlanc = new Jeu(Couleur.BLANC);
		this.jeuNoir = new Jeu(Couleur.NOIR);
		this.jeuCourant = this.jeuBlanc;
		this.jeuOppose = this.jeuNoir;
		this.setMessage("Les blancs doivent commencer");
		this.isCatchOk = false;
		this.isMoveOk = false;
		this.isPieceToMoveOk = false;
		this.isCastlingPossible = false;
	}


	public List<PieceIHMs> getPiecesIHM() {
		List<PieceIHMs> list = new LinkedList<PieceIHMs>();
		
		if(this.jeuBlanc != null) {
			list.addAll(this.jeuBlanc.getPiecesIHM());
		}
		
		else if(this.jeuNoir != null) {
			list.addAll(this.jeuNoir.getPiecesIHM());
		}
		
		return list;
	}
	/**
	 * Permet de changer le joueur courant.
	 */
	public void switchJoueur() {
		if (this.jeuCourant == this.jeuBlanc) {
			this.jeuCourant = this.jeuNoir;
			this.jeuOppose = this.jeuBlanc;
		} else {
			this.jeuCourant = this.jeuBlanc;
			this.jeuOppose = this.jeuNoir;
		}
	}


	/**
	 * Permet de verifier si une piece peut être deplacee.
	 * <p>L'algo est le suivant : <br>
	 * 		s'il n'existe pas de piece du jeu courant aux coordonnees initiales --> false, <br>
	 * 		si les coordonnees finales ne sont pas valides ou egales aux initiales --> false, <br>
	 * 		si position finale ne correspond pas à algo de deplacement piece --> false, <br> 
	 * 		s'il existe une piece intermediaire sur la trajectoire --> false (sauf cavalier), <br> 
	 * 		s'il existe une piece positionnees aux coordonnees finales :<br>
	 * 			si elle est de la meme couleur --> false ou tentative roque du roi, <br>
	 * 			sinon  :	prendre la piece intermediaire (vigilance pour le cas du pion)
	 * 			 			et deplacer la piece -->true,<br>
	 * 		sinon deplacer la piece -->true</p>
	 * @param xInit position initiale
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return true si le deplacement est effectue, false sinon
	 * 
	 */
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {

		this.isMoveOk = false;
		this.isForMove = true;	

		//	s'il n'existe pas de piece du jeu courant aux coordonnees initiales --> false
		this.isPieceToMoveOk = jeuCourant.isPieceHere(xInit, yInit)	;
		if (!this.isPieceToMoveOk) {
			this.isMoveOk = false;
			this.setMessage("KO : c'est au tour de l'autre joueur");
		}
		else {
			this.isMoveOk = isMoveLegal(xInit,  yInit,  xFinal,  yFinal);
			
			if(!this.isMoveOk){ 
				this.setMessage("KO : la position finale ne correspond pas à "
						+ "algo de deplacement legal de la piece ");

			}
		}
		
		

		return this.isMoveOk;
	}

	private boolean isMoveLegal(int xInit, int yInit, int xFinal, int yFinal) {

		boolean isPieceOnPath = false;
		Coord coordPieceInter = null;

		this.isCatchOk = false;
		this.isMoveOk = false;
		this.isCastlingPossible = false;

		// si coordonnees finales == coordonnees initiales --> false
		if (!(xInit == xFinal &&  yInit == yFinal)){		

			//  s'il existe une pièce intermediaire sur la trajectoire --> false (sauf cavalier)
			coordPieceInter = this.pieceOnTraject(xInit, yInit, xFinal, yFinal);
			if (coordPieceInter != null) {

				// s'il existe une piece positionnees aux coordonnees finales :
				if (coordPieceInter.x == xFinal && coordPieceInter.y == yFinal){

					//	si elle est de la meme couleur --> false ou potentiel "roque du roi"
					if (this.jeuCourant.isPieceHere(xFinal, yFinal) ){
						this.isCastlingPossible = true;
						this.jeuCourant.setCastling();
						isPieceOnPath = true;
					}
					// si elle n'est pas de la meme couleur, elle pourrait être prise 
					else {
						this.isCatchOk = true;
						if (this.isForMove == true){
							this.jeuCourant.setPossibleCapture();
						}
					}			
				}
				// pièce intermediaire sur la trajectoire qui empêche deplacement
				else {
					isPieceOnPath = true;
				}
			}

			//	si position finale ne correspond pas à algo de deplacement piece --> false		 
			boolean isMoveJeuOk = jeuCourant.isMoveOk(xInit, yInit, xFinal, yFinal, this.isCatchOk, this.isCastlingPossible);

			this.isMoveOk = this.isPieceToMoveOk && !isPieceOnPath && isMoveJeuOk;	
		}
		
		return this.isMoveOk;
	}

	/**
	 * Permet de deplacer une piece connaissant ses coordonnees initiales vers ses
	 * coordonnees finales.
	 * l'algo verifie que le deplacement est legal, 
	 * effectue ce deplacement avec l'eventuelle capture,
	 * rembobine si le deplacement et la capture ont mis le roi en echec
	 * @param xInit position initiale
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return true si le deplacement est effectue, false sinon
	 * 
	 */
	public boolean move (int xInit, int yInit, int xFinal, int yFinal){

		boolean ret = false;

		// effectuer le deplacement
		ret = this.jeuCourant.move(xInit, yInit, xFinal, yFinal);
		this.setMessage("OK : deplacement sans capture "); // message sera annule si capture

		// effectuer l'eventuelle capture
		if (ret && this.isCatchOk) {
			this.jeuOppose.capture(xFinal, yFinal);
			this.setMessage("OK : deplacement  + capture  ");			
		}


		

		this.isCatchOk = false;
		this.isMoveOk = false;

		return ret;
	}


	/**
	 * Permet de verifier s'il existe une piece sur la trajectoire
	 * et retourne ses coordonnees si oui.
	 * @param xInit
	 * @param yInit
	 * @param xFinal
	 * @param yFinal
	 * @return coordonnees de la 1ere piece intermediaire rencontree
	 * apres scan dans toutes les directions
	 */
	private Coord pieceOnTraject(int xInit, int yInit, int xFinal, int yFinal) {
		Coord pieceOnTrajectCoord = null;

		// Mouvement rectiligne le long de l'axe Y
		if (xInit == xFinal) {

			// si vers Y croissants
			if (yFinal > yInit) {
				for (int i = yInit + 1; i <= yFinal; i++) {
					if (jeuBlanc.isPieceHere(xInit, i) || jeuNoir.isPieceHere(xInit, i)) {
						pieceOnTrajectCoord = new Coord(xInit,i);
						break;
					}
				}
			}
			// si vers Y decroissants
			if (yFinal < yInit) {				
				for (int i = yInit - 1; i >= yFinal; i--) {
					if (jeuBlanc.isPieceHere(xInit, i) || jeuNoir.isPieceHere(xInit, i)) {
						pieceOnTrajectCoord = new Coord(xInit,i);
						break;
					}
				}
			}
		} 
		else {
			// Mouvement rectiligne le long de l'axe X
			if (yInit == yFinal) { 
				// si vers X croissants
				if (xFinal > xInit) {
					for (int i = xInit + 1; i <= xFinal; i++) {
						if (jeuBlanc.isPieceHere(i, yInit) || jeuNoir.isPieceHere(i, yInit)) {
							pieceOnTrajectCoord = new Coord(i,yInit);
							break;
						}
					}
				}
				// si vers X decroissants
				if (xFinal < xInit) {
					for (int i = xInit - 1; i >= xFinal; i--) {
						if (jeuBlanc.isPieceHere(i, yInit) || jeuNoir.isPieceHere(i, yInit)) {
							pieceOnTrajectCoord = new Coord(i,yInit);
							break;
						}
					}
				}
			} 
			else {
				// Mouvement en diagonale 
				if (Math.abs(yInit - yFinal) == Math.abs(xInit - xFinal)) {
					int ecart = Math.abs(yInit - yFinal);

					// X croissant, Y croissant
					if ((xFinal - xInit > 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							if (jeuBlanc.isPieceHere(xInit + i, yInit + i) || jeuNoir.isPieceHere(xInit + i, yInit + i)) {
								pieceOnTrajectCoord = new Coord(xInit + i, yInit + i);
								break;
							}						
						}
					}
					// X croissant, Y decroissant
					if ((xFinal - xInit > 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							if (jeuBlanc.isPieceHere(xInit + i, yInit - i) || jeuNoir.isPieceHere(xInit + i, yInit - i)) {
								pieceOnTrajectCoord = new Coord(xInit + i, yInit - i);
								break;
							}						
						}
					}
					// X decroissant, Y decroissant
					if ((xFinal - xInit < 0) && (yFinal - yInit < 0)) {
						for (int i = 1; i <= ecart; i++) {
							if (jeuBlanc.isPieceHere(xInit - i, yInit - i) || jeuNoir.isPieceHere(xInit - i, yInit - i)) {
								pieceOnTrajectCoord = new Coord(xInit - i, yInit - i);
								break;
							}							
						}
					}					

					// X decroissant, Y croissant
					if ((xFinal - xInit < 0) && (yFinal - yInit > 0)) {
						for (int i = 1; i <= ecart; i++) {
							if (jeuBlanc.isPieceHere(xInit - i, yInit + i) || jeuNoir.isPieceHere(xInit - i, yInit + i)) {
								pieceOnTrajectCoord = new Coord(xInit - i, yInit + i);
								break;
							}							
						}
					}
				} 
				else {
					// Dans tous les autres cas de mouvement
					// la piece au coordonnees initiale est un cavalier
					if (jeuBlanc.isPieceHere(xFinal, yFinal) || jeuNoir.isPieceHere(xFinal, yFinal)) {
						pieceOnTrajectCoord = new Coord(xFinal, yFinal);			
					}	
				}
			}
		}
		return pieceOnTrajectCoord;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * invoque la methode toString des 2 jeux
	 */

	public String toString() {		
		String st = "";
		st += "Jeu Blanc " + this.jeuBlanc.toString() + "\n";
		st += "Jeu Noir " + this.jeuNoir.toString() + "\n";
		return st;
	}

	/**
	 * @return couleur du jeu courant
	 */
	public Couleur getColorCurrentPlayer() {
		return this.jeuCourant.getCouleur();
	}

	public Couleur getPieceColor(int x, int y){
		return jeuCourant.getPieceColor(x, y);
	}

	/**
	 * @return message relatif aux deplacement, capture, etc.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message
	 * ne doit pas etre accessible de l'exterieur
	 */
	private void setMessage(String message) {
		this.message = message;
	}



	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}


	public static void main(String[] args) {
		Echiquier e = new Echiquier();
		boolean isMoveOK = false;
		
		System.out.println("Test classe Echiquier\n");
		
		System.out.println(e);
	
//		System.out.print("\n D�placement de 3,6 vers 3,4 = ");
//		if (e.isMoveOk(3, 6, 3, 4))
//			isMoveOK = e.move(3, 6, 3, 4);		// true
//		System.out.println(e.getMessage() + "\n");	
//		System.out.println(e);
//		if (isMoveOK)
//			e.switchJoueur();
//		
//		System.out.print("\n D�placement de 3,4 vers 3,6 = ");		
//		if (e.isMoveOk(3, 4, 3, 6))
//			isMoveOK = e.move(3, 4, 3, 6); 	// false : autre joueur
//		System.out.println(e.getMessage() + "\n");	
//		System.out.println(e);
//		e.switchJoueur();
//		
//		System.out.print("\n D�placement de 3,4 vers 3,6 = ");		
//		if (e.isMoveOk(3, 4, 3, 6))
//			isMoveOK = e.move(3, 4, 3, 6); 	// false : algo KO
//		System.out.println(e.getMessage() + "\n");	
//		System.out.println(e);
//		if (isMoveOK)
//			e.switchJoueur();
		
		System.out.println("\n");	
		System.out.println("Hello");
		System.out.println(e.getPiecesIHM());
		
		
	}
}
