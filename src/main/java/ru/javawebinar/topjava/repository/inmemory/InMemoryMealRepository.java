package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> storage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        init();
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> currentUserMeals = storage.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            currentUserMeals.put(meal.getId(), meal);
            storage.put(userId, currentUserMeals);
            return meal;
        }
        Meal result = currentUserMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        storage.put(userId, currentUserMeals);
        return result;
    }

    @Override
    public boolean delete(int userId, int id) {
        return storage.getOrDefault(userId, Collections.emptyMap())
                .remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return Optional.ofNullable(storage.getOrDefault(userId, Collections.emptyMap())
                .get(id))
                .orElseThrow(() -> new NotFoundException("Not found meal"));
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return storage.getOrDefault(userId, Collections.emptyMap())
                .values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll() {
        return storage.values()
                .stream()
                .flatMap(map -> map.values().stream())
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private void init() {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

}

