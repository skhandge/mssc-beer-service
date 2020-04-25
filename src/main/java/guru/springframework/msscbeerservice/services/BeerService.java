package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;


public interface BeerService {

	BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

	BeerDto saveNewBeer(BeerDto newBeer);

	BeerDto updateBeer(UUID beerId, BeerDto newBeer);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto getByUpc(String upc, Boolean showInventoryOnHand);
}
