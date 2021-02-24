package ru.javawebinar.topjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.web.MealServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private final MealService service = MealServiceImpl.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseId(req.getParameter("id"));
        String action = req.getParameter("action");
        if (Objects.nonNull(action) && action.equals("delete") && Objects.nonNull(id)) {
            service.delete(id);
            List<MealTo> mealToList = service.getAll();
            req.setAttribute("meals", mealToList);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        if (Objects.nonNull(id)) {
            req.setAttribute("meal", service.get(id));
            req.setAttribute("isNew", false);
        } else {
            req.setAttribute("isNew", true);
        }
        req.setAttribute("title", "Meal");
        req.getRequestDispatcher("meal.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service.save(new MealTo(
                LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
                        .parse(req.getParameter("dateTime"))),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")),
                false,
                parseId(req.getParameter("id"))
        ));
        List<MealTo> mealToList = service.getAll();
        req.setAttribute("meals", mealToList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    private Long parseId(String id) {
        return Optional.ofNullable(id)
                .map(i -> {
                    try {
                        return Long.parseLong(i);
                    } catch (NumberFormatException e) {
                        log.error("Не число!");
                        return null;
                    }
                })
                .orElse(null);
    }

}
