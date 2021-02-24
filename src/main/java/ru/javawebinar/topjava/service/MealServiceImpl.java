package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import ru.javawebinar.topjava.exception.NotFoundException;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServiceImpl implements MealService {

    private static final int CALORIES_PER_DAY = 2000;
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealServiceImpl INSTANCE = new MealServiceImpl();
    private final MealRepository repository = MealRepositoryImpl.getInstance();

    private MealServiceImpl() {}

    public static MealService getInstance() {
        return INSTANCE;
    }

    @Override
    public MealTo get(long mealId) {
        return Optional.ofNullable(repository.get(mealId))
                .map(meal -> new MealTo(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        false,
                        meal.getId())
                )
                .orElseThrow(() -> {
                    log.error(String.format("Not found by id: %s", mealId));
                    throw new NotFoundException(mealId);
                });
    }

    @Override
    public long save(MealTo meal) {
        return Optional.ofNullable(meal)
                .map(m -> new Meal(
                        m.getDateTime(),
                        m.getDescription(),
                        m.getCalories(),
                        m.getId()
                ))
                .map(repository::save)
                .orElseThrow(() -> {
                    log.error("Meal can`t is null.");
                    throw new IllegalArgumentException("Meal can`t is null");
                });
    }

    @Override
    public boolean delete(long id) {
        return repository.delete(id);
    }

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByStreams(
                repository.getAll(),
                LocalTime.of(0, 0, 1),
                LocalTime.of(23, 59, 59),
                CALORIES_PER_DAY
        );
    }

}
