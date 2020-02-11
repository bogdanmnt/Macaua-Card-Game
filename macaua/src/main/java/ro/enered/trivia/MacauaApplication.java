package ro.enered.trivia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.enered.util.Card;
import ro.enered.util.Constants;
import ro.enered.util.Player;

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })

@SpringBootApplication
@CrossOrigin(origins = "*")
@RestController
public class MacauaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacauaApplication.class, args);
	

		
	}

	@RequestMapping(value = "join-game", method = RequestMethod.POST)
	public ResponseEntity<?> joinGame(@RequestParam(value = "id-jucator") int idJucator,
			@RequestParam(value = "username") String numeJucator) {

		Player p = new Player();
		p.setId(idJucator);
		p.setUsername(numeJucator);
		Constants.joc.adaugaJucatorLaJoc(p);
		System.out.println("------------------------ JUCATOR ADAUGAT  " + numeJucator + " ---------------");
		return ResponseEntity.ok("ok");

	}

	@RequestMapping(value = "pune-carte", method = RequestMethod.POST)
	public ResponseEntity<?> puneCarte(@RequestParam(value = "id-jucator") int idJucator,
			@RequestParam(value = "carte") String carte) {
		
		// simbol_culoare

		Card c = new Card();
		c.setSymbol(carte.split("_")[0]);
		c.setColor(carte.split("_")[1]);

		for (Card c2 : Constants.joc.getJucatorCurent().getCards()) {

			if (c2.getColor().equals(c.getColor()) && c2.getSymbol().equals(c.getSymbol())) {
				System.out.println("----------------- JUCATORUL CU IDUL " + idJucator + " A PUS O CARTE ------------------ ");
				if (Constants.joc.puneCarte(idJucator, c2) == Constants.MUTARE)
					return ResponseEntity.ok("ok");
				if (Constants.joc.puneCarte(idJucator, c2) == Constants.MUTARE_NEPERMISA)
					return ResponseEntity.ok("nok");
				if (Constants.joc.puneCarte(idJucator, c2) == Constants.CASTIGATOR)
					return ResponseEntity.ok("win");

			}

		}
		
		return ResponseEntity.ok("nok");

	}
	
	@RequestMapping(value = "info-joc", method = RequestMethod.POST)
	public ResponseEntity<?> getInfoJoc() {
		return ResponseEntity.ok(Constants.joc);
		
	}
	
	@RequestMapping(value = "start-joc", method = RequestMethod.POST)
	public ResponseEntity<?> startJoc() {
		Constants.joc.startJoc();
		System.out.println("------------------------ START JOC ---------------");
		return ResponseEntity.ok("started");
		
	}
	
	@RequestMapping(value = "trage-carte", method = RequestMethod.POST)
	public ResponseEntity<?> trageCarte() {
		Constants.joc.trageCarte();
		System.out.println("------------------------ CARTE TRASA ---------------");
		return ResponseEntity.ok("carteTrasa");
		
	}
	

}
