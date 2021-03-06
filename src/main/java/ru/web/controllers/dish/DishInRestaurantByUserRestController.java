package ru.web.controllers.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.repository.DishRepository;
import ru.to.DishTo;
import ru.util.toUtil.DishesUtil;

import java.util.List;

import static ru.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishInRestaurantByUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishInRestaurantByUserRestController {
    static final String REST_URL = "/rest/restaurants/{restaurantId}/lunchMenu";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishRepository dishRepository;

    @Autowired
    public DishInRestaurantByUserRestController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<DishTo> getAll(@PathVariable("restaurantId") int restaurantId) {
        log.info("get all dishes included in lunch menu for restaurant {}", restaurantId);
        return DishesUtil.asToListFilteredByIncluded((dishRepository.getAllIncludedByRestaurantId(restaurantId)));
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable("restaurantId") int restaurantId, @PathVariable int id) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return DishesUtil.asToList(checkNotFoundWithId(dishRepository.get(id, restaurantId), id));
    }
}
