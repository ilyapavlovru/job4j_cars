package ru.job4j.cars.servlet;

import org.apache.log4j.Logger;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;
import ru.job4j.cars.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AllAdvServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AllAdvServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        Store store = AdRepository.instOf();
        req.setAttribute("ads", new ArrayList<>(store.findAllActiveAds()));
        req.setAttribute("user", sessionUser);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("allAdvertisements.jsp");
        requestDispatcher.forward(req, resp);
    }
}
