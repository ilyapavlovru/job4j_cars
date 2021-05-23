package ru.job4j.cars.servlet;

import com.google.gson.Gson;
import ru.job4j.cars.model.Adv;
import ru.job4j.cars.store.HbmStore;
import ru.job4j.cars.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class AdvServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = new HbmStore();
        Collection<Adv> ads = store.findAllAds();
        String json = new Gson().toJson(ads);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}
