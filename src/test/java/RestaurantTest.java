import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //Below test will work unless the running time in between 23:50 to 00:05
        //Mocking will only work once we can mock LocalTime which is again have only static methods
        //and for that need to add different dependency so not sure if that is fine, so skipping that option.
        //Cant mock the restaurant object as that is test class only.
        LocalTime time = LocalTime.now();
        LocalTime openingTime = time.minusMinutes(5);
        LocalTime closingTime = time.plusMinutes(5);
        restaurant = new Restaurant("Cafe1","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime time = LocalTime.now();
        LocalTime openingTime = time.minusHours(2);
        LocalTime closingTime = time.minusHours(1);
        restaurant = new Restaurant("Cafe1","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDERED VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void validate_selected_items_total_price(){

        restaurant.addToMenu("Sizzling brownie",319);

        List<Item> menuItems = restaurant.getMenu();
        List<Item> selectedItems = new ArrayList<Item>();
        selectedItems.add(menuItems.get(0));//Sweet corn soup, price 119
        selectedItems.add(menuItems.get(2));//Sizzling brownie,price 319
        assertEquals(438,restaurant.getOrderValue(selectedItems));

    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDERED VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}