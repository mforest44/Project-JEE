package jmx;

public class Premier implements PremierMBean{

	
	private static String nom ="Premier";
	private int valeur = 100;
	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return nom;
	}

	@Override
	public int getValeur() {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public void setValeur(int valeur) {
		// TODO Auto-generated method stub
		this.valeur = valeur;
	}

	@Override
	public void rafraichir() {
		// TODO Auto-generated method stub
		System.out.println("Rafraîchir les données");
	}

}
