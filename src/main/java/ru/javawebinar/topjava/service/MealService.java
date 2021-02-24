package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealService {

    MealTo get(long mealId);

    long save(MealTo meal);

    boolean delete(long id);

    List<MealTo> getAll();

}
