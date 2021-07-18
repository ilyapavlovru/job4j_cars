package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import ru.job4j.cars.model.CarBodyType;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CarBodyTypeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<CarBodyType> carBodyTypes = AdRepository.instOf().findAllCarBodyTypes();
        String json = new Gson().toJson(carBodyTypes);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
