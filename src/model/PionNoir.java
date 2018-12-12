package model;

public class PionNoir extends Pion{
	public PionNoir(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	
	public boolean deplacementDiagoIsOK(int xFinal, int yFinal) {
		boolean ret = false;
		if ((yFinal == this.getY()+1 && xFinal == this.getX()+1) 
				|| (yFinal == this.getY()+1 && xFinal == this.getX()-1)) {
			ret = true;
		}
		return ret;
	}

	public boolean deplacementVerticalIsOK(int yFinal) {
		boolean ret = false;
		if (yFinal - this.getY() > 0) {
			ret = true;
		}
		return ret;
	}
}
