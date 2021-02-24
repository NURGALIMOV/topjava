package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    Meal get(long mealId);

    long save(Meal meal);

    boolean delete(long id);

    List<Meal> getAll();

}
