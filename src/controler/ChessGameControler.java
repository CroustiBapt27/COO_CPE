package controler;

import model.ChessGame;
import model.Coord;
import model.Couleur;

public class ChessGameControler implements ChessGameControlers {
	private ChessGame chessGame;

	public ChessGameControler(ChessGame chessGame) {
		this.chessGame = chessGame;
	}
	@Override
	public boolean move(Coord initCoord, Coord finalCoord) {
		return chessGame.move(initCoord.x, initCoord.y, finalCoord.x, finalCoord.y);
	}

	@Override
	public String getMessage() {
		return chessGame.getMessage();
	}

	@Override
	public boolean isEnd() {
		return chessGame.isEnd();
	}

	@Override
	public boolean isPlayerOK(Coord initCoord) {
		boolean ret = false;
		if(initCoord != null) {
			Couleur colorPiece = this.chessGame.getPieceColor(initCoord.x, initCoord.y);
			Couleur colorCurrentPlayer = this.chessGame.getColorCurrentPlayer();
			if(colorPiece.equals(colorCurrentPlayer)) {
				ret = true;
			}
		}
		return ret;
	}

}
