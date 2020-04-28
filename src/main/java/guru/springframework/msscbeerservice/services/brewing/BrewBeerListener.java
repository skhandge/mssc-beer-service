package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent){
        BeerDto beerDto = brewBeerEvent.getBeerDto();
        System.out.println("Beer ID from BeerDto " + beerDto.getId());

        Beer beer = beerRepository.getOne(beerDto.getId());
        System.out.println("Beer object retrieved " + beer.toString());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        System.out.println("BeerDTO object after setting QOH " + beerDto.toString());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
        System.out.println("New Inventory Event " + newInventoryEvent.toString());

        log.debug("Brewed beer " + beer.getMinOnHand() + "  :  QOH: " + beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
