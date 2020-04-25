package guru.springframework.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collectors;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService{

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	@Override
	@Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
	public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
		System.out.println(" Called Beerby id ");

		if(showInventoryOnHand){
			return beerMapper.beerToBeerDtoWithInventory(
					beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
			);
		}else{
			return beerMapper.beerToBeerDto(
					beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
			);
		}
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle().name());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());

		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	@Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
		BeerPagedList beerPagedList;
		Page<Beer> beerPage;
		System.out.println(" Called beerlist ");
		if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			//search both
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			//search beer_service name
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			//search beer_service style
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			beerPage = beerRepository.findAll(pageRequest);
		}

		if(showInventoryOnHand){
			beerPagedList = new BeerPagedList(beerPage
					.getContent()
					.stream()
					.map(beerMapper::beerToBeerDtoWithInventory)
					.collect(Collectors.toList()),
					PageRequest
							.of(beerPage.getPageable().getPageNumber(),
									beerPage.getPageable().getPageSize()),
					beerPage.getTotalElements());
		}else{
			beerPagedList = new BeerPagedList(beerPage
					.getContent()
					.stream()
					.map(beerMapper::beerToBeerDto)
					.collect(Collectors.toList()),
					PageRequest
							.of(beerPage.getPageable().getPageNumber(),
									beerPage.getPageable().getPageSize()),
					beerPage.getTotalElements());
		}


		return beerPagedList;
	}

	@Override
	@Cacheable(cacheNames = "beerUpcCache", condition = "#showInventoryOnHand == false ")
	public BeerDto getByUpc(String upc, Boolean showInventoryOnHand) {
		System.out.println(" Called Beer by upc ");

		if(showInventoryOnHand){
			return beerMapper.beerToBeerDtoWithInventory(
					beerRepository.findByUpc(upc));
		}else{
			return beerMapper.beerToBeerDto(
					beerRepository.findByUpc(upc));
		}	}

}
