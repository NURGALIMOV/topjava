package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class MealRepositoryImpl implements MealRepository {

    private static final MealRepositoryImpl INSTANCE = new MealRepositoryImpl();
    private final Map<Long, Meal> mealStorage = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(8);

    {
        mealStorage.put(1L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1L));
        mealStorage.put(2L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 2L));
        mealStorage.put(3L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 3L));
        mealStorage.put(4L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение",
                100, 4L));
        mealStorage.put(5L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 5L));
        mealStorage.put(6L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 6L));
        mealStorage.put(7L, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 7L));
    }

    private MealRepositoryImpl(){}

    public static MealRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Meal get(long mealId) {
        return mealStorage.get(mealId);
    }

    @Override
    public long save(Meal meal) {
        if (Objects.isNull(meal.getId())) {
            meal.setId(currentId.getAndIncrement());
        }
        mealStorage.put(meal.getId(), meal);
        return meal.getId();
    }

    @Override
    public boolean delete(long id) {
        return Objects.nonNull(mealStorage.remove(id));
    }

    @Override
    public List<Meal> getAll() {
        return mealStorage.values()
                .stream()
                .collect(Collectors.toList());
    }

}
