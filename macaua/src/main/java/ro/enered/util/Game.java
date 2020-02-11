package ro.enered.util;

import static org.mockito.Matchers.intThat;

import java.util.ArrayList;
import java.util.Random;

public class Game {

	private ArrayList<Player> jucatori = new ArrayList<Player>();
	private Card carteaDeJos;
	private ArrayList<Card> pachet = new ArrayList<Card>();
	private boolean inceput = false;
	private Player jucatorCurent;
	private int pozJucatorCurent = 0;

	public boolean isInceput() {
		return inceput;
	}

	public void setInceput(boolean inceput) {
		this.inceput = inceput;
	}

	public Player getJucatorCurent() {
		return jucatorCurent;
	}

	public void setJucatorCurent(Player jucatorCurent) {
		this.jucatorCurent = jucatorCurent;
	}

	public ArrayList<Player> getJucatori() {
		return jucatori;
	}

	public void setJucatori(ArrayList<Player> jucatori) {
		this.jucatori = jucatori;
	}

	public Card getCarteaDeJos() {
		return carteaDeJos;
	}

	public void setCarteaDeJos(Card carteaDeJos) {
		this.carteaDeJos = carteaDeJos;
	}

	public ArrayList<Card> getPachet() {
		return pachet;
	}

	public void setPachet(ArrayList<Card> pachet) {
		this.pachet = pachet;
	}

	// LOGICA MEA

	public void adaugaJucatorLaJoc(Player p) {
		jucatori.add(p);

	}

	public void startJoc() {

		for (int i = 2; i <= 14; i++) {
			for (int j = 0; j < 4; j++) {
				Card c = new Card();
				if (i == 11)
					c.setSymbol(Constants.juvete);
				if (i == 12)
					c.setSymbol(Constants.dama);
				if (i == 13)
					c.setSymbol(Constants.popa);
				if (i == 14)
					c.setSymbol(Constants.as);
				if (i < 11)
					c.setSymbol(i + "");

				if (j == 0)
					c.setColor(Constants.inima);
				if (j == 1)
					c.setColor(Constants.romb);
				if (j == 2)
					c.setColor(Constants.frunza);
				if (j == 3)
					c.setColor(Constants.trefla);

				System.out.println("Generat cartea : " + c.getSymbol() + " de " + c.getColor());
				pachet.add(c);

			}

		}

		Random r = new Random();
		int cartiPachetNr = 51;
		for (Player p : jucatori) {
			for (int i = 0; i < 5; i++) {
				int pozCarte = r.nextInt(cartiPachetNr--);

				p.getCards().add(pachet.get(pozCarte));
				System.out.println("I-am dat cartea " + pachet.get(pozCarte).getSymbol() + " de "
						+ pachet.get(pozCarte).getColor() + " lui " + p.getUsername());

				pachet.remove(pozCarte);

			}

		}

		int pozCarteMijloc = r.nextInt(51 - jucatori.size() * 5);
		carteaDeJos = pachet.get(pozCarteMijloc);
		pachet.remove(pozCarteMijloc);
		System.out.println("Cartea din mijloc este : " + carteaDeJos.getSymbol() + " de " + carteaDeJos.getColor());

		inceput = true;

		jucatorCurent = jucatori.get(0);
	}

	public int puneCarte(int idJucator, Card c) {
		for (Player p : jucatori) {
			if (idJucator == p.getId()) {
				// culoarea sau simbolul la fel
				if (carteaDeJos.getColor().equals(c.getColor()) || carteaDeJos.getSymbol().equals(c.getSymbol())
						|| c.getSymbol().equals(Constants.as)

				) {
					
					// miscarea e okay
					carteaDeJos = c;
					p.getCards().remove(c);
					
					if (p.getCards().size() == 0) {
						inceput = false;
						return Constants.CASTIGATOR;

					} else {
						if (pozJucatorCurent < (jucatori.size() - 1))
							jucatorCurent = jucatori.get(++pozJucatorCurent);
						else{
							pozJucatorCurent = 0;
							jucatorCurent = jucatori.get(pozJucatorCurent);
						}
						return Constants.MUTARE;

					}

				} else {

					return Constants.MUTARE_NEPERMISA;

				}

			}

		}
		return Constants.MUTARE_NEPERMISA;

	}
	
	public void trageCarte(){
		
		Random r = new Random();
		if(pachet.size()==0){
			if (pozJucatorCurent < (jucatori.size() - 1))
				jucatorCurent = jucatori.get(++pozJucatorCurent);
			else{
				pozJucatorCurent = 0;
				jucatorCurent = jucatori.get(pozJucatorCurent);
			}
			return;
			
		}
		int pozCarteTrasa = r.nextInt(pachet.size()-1);
		
		jucatorCurent.getCards().add(pachet.get(pozCarteTrasa));
		pachet.remove(pozCarteTrasa);
		if (pozJucatorCurent < (jucatori.size() - 1))
			jucatorCurent = jucatori.get(++pozJucatorCurent);
		else{
			pozJucatorCurent = 0;
			jucatorCurent = jucatori.get(pozJucatorCurent);
		}
		
		
	}

}
