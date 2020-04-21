package guru.springframework.msscbeerservice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	@Override
	public BeerDto getById(UUID beerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeerDto saveNewBeer(BeerDto newBeer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto newBeer) {
		// TODO Auto-generated method stub
		return null;
	}

}
