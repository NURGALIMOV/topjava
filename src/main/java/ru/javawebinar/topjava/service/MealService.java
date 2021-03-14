package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public MealTo save(int userId, MealTo meal) {
        Meal entity = repository.save(userId, MealsUtil.createMeal(meal));
        return MealsUtil.createTo(entity, false);
    }

    public void delete(int userId, int id) {
        repository.delete(userId, id);
    }

    public MealTo get(int userId, int id) {
        return MealsUtil.createTo(repository.get(userId, id), false);
    }

    public Collection<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAll() {
        return MealsUtil.getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}