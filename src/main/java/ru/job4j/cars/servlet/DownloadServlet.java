package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Adv;
import ru.job4j.cars.store.HbmStore;
import ru.job4j.cars.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int advId = Integer.parseInt(req.getParameter("id"));
        Store store = new HbmStore();
        Adv adv = store.findAdvById(advId);
        byte[] imageBytes = adv.getImage();
        resp.setContentType("image/png");
        resp.setContentLength(imageBytes.length);
        resp.getOutputStream().write(imageBytes);
    }
}
