package model;

public class PionBlanc extends Pion {
	public PionBlanc(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	public boolean deplacementDiagoIsOK(int xFinal, int yFinal) {
		if ((yFinal == this.getY()-1 && xFinal == this.getX()+1) 
				|| (yFinal == this.getY()-1 && xFinal == this.getX()-1)) {
			return true;
		}
		return false;
	}

	public boolean deplacementVerticalIsOK(int yFinal) {
		boolean ret = false;
		if (yFinal - this.getY() < 0) {
			ret = true;
		}
		return ret;
	}
}
