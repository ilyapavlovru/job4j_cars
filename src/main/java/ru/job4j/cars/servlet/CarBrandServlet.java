package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CarBrandServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<CarBrand> cities = AdRepository.instOf().findAllCarBrands();
        String json = new Gson().toJson(cities);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
