package guru.springframework.msscbeerservice.services.inventory;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;
    @Test
    void getOnhandInventory() {
        //Integer qoh = beerInventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);
        //System.out.println("Beer Quantity in hand " + qoh);
    }
}