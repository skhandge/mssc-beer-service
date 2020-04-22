package guru.springframework.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.domain.Beer;

@Component
public class BeerLoader implements CommandLineRunner{

	public static final String BEER_1_UPC = "0631234200036";
	public static final String BEER_2_UPC = "0631234300019";
	public static final String BEER_3_UPC = "0083783375213";
	private final BeerRepository beerRepository;
	
	public BeerLoader(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		loadBeerObjects();
	}

	private void loadBeerObjects() {
		if(beerRepository == null || beerRepository.count() <= 0 ) {
			beerRepository.save(Beer.builder()
					.beerName("Bira")
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(BEER_1_UPC)
					.price(new BigDecimal("12.95"))
					.build());
			
			beerRepository.save(Beer.builder()
					.beerName("KingFisher")
					.beerStyle("ALE")
					.quantityToBrew(500)
					.minOnHand(50)
					.upc(BEER_2_UPC)
					.price(new BigDecimal("11.95"))
					.build());

			beerRepository.save(Beer.builder()
					.beerName("Stud")
					.beerStyle("ALE")
					.quantityToBrew(500)
					.minOnHand(50)
					.upc(BEER_3_UPC)
					.price(new BigDecimal("11.95"))
					.build());
		}
		
	}
}
