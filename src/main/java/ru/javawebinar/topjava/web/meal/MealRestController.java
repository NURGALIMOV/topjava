package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Objects;

@Controller
public class MealRestController {

    private static final String NO_ACCESS = "No access";
    private final MealService service;
    private final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public MealTo get(int mealId) {
        MealTo mealTo = service.get(SecurityUtil.authUserId(), mealId);
        log.info("get {} by {} for {}", mealTo, mealId, SecurityUtil.authUserId());
        return mealTo;
    }

    public void delete(int mealId) {
        service.delete(SecurityUtil.authUserId(), mealId);
        log.info("delete by {} for {}", mealId, SecurityUtil.authUserId());
    }

    public MealTo create(MealTo meal) {
        MealTo result = service.save(SecurityUtil.authUserId(), meal);
        log.info("create {} for {}", result, SecurityUtil.authUserId());
        return result;
    }

    public void update(MealTo meal) {
        if(Objects.nonNull(meal.getId())) {
            MealTo result = service.save(SecurityUtil.authUserId(), meal);
            log.info("save {} for {}", result, SecurityUtil.authUserId());
        }
        throw new IllegalArgumentException("Is new meal");
    }

    public Collection<MealTo> getAll() {
        log.info("get all for {}", SecurityUtil.authUserId());
        return service.getAll(SecurityUtil.authUserId());
    }

}