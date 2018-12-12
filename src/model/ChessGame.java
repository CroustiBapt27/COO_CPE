package model;

import java.util.Observable;

import tools.AbstractObservable;

public class ChessGame extends AbstractObservable implements BoardGames{
		private Echiquier echiquier;
		
		public ChessGame() {
			this.echiquier = new Echiquier();
			
		}
		
		@Override
		public String toString() {
			Observable obs = new Observable();
			Object arg = new Object();
			obs.notifyObservers(arg);
			return echiquier.toString() + " : " + getMessage() + " \n";
		}
		
		@Override
		public boolean move (int xInit, int yInit, int xFinal, int yFinal) {
			boolean movePossible = false;
			
			if(echiquier.isMoveOk(xInit, yInit, xFinal, yFinal)) {
				movePossible = echiquier.move(xInit, yInit, xFinal, yFinal);
				if(movePossible) {
					echiquier.switchJoueur();
				}
			}
			this.notifyObservers(echiquier.getPiecesIHM());
			return movePossible;
		}
		
		@Override
		public boolean isEnd() {
			return echiquier.isEnd();
		}
		
		@Override
		public String getMessage() {
			return echiquier.getMessage();
		}
		
		@Override
		public Couleur getColorCurrentPlayer() {
			return echiquier.getColorCurrentPlayer();
		}

		@Override
		public Couleur getPieceColor(int x, int y) {
			return echiquier.getPieceColor(x, y);
		}
}
