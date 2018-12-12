package controler;

import model.Coord;

public interface ChessGameControlers {

	
	/**
	 * @param initCoord
	 * @param finalCoord
	 * @return true si le deplacement s'est bien passe
	 */
	public boolean move(Coord initCoord, Coord finalCoord);

	/**
	 * @return message relatif aux deplacement, capture, etc.
	 */
	public String getMessage();
	
	/**
	 * @return true si fin de partie OK (echec et mat, pat, etc.)
	 */
	public boolean isEnd();

	/**
	 * @param initCoord
	 * @return une info dont la vue se servira 
	 * pour empecher tout deplacement sur le damier
	 */
	public boolean isPlayerOK(Coord initCoord);

}
