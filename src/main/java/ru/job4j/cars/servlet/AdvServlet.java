package ru.job4j.cars.servlet;

import ru.job4j.cars.store.HbmStore;
import ru.job4j.cars.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AdvServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Store store = new HbmStore();
        req.setAttribute("ads", new ArrayList<>(store.findAllAds()));
        HttpSession session = req.getSession();
        req.setAttribute("user", session.getAttribute("user"));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("advertisements.jsp");
        requestDispatcher.forward(req, resp);


//        Collection<Adv> ads = AdRepository.instOf().findTodayAds();
//        Collection<Adv> ads = AdRepository.instOf().findAdsWithPhoto();
//        Collection<Adv> ads = AdRepository.instOf().findAdsByCarBrandId(10);
//        String json = new Gson().toJson(ads);
//        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType("application/json");
//        resp.getWriter().write(json);
    }
}
