package ru.job4j.cars.servlet;

import org.apache.log4j.Logger;
import ru.job4j.cars.model.Adv;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AdvServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AdvServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("ads", new ArrayList<>(AdRepository.instOf().findAllAds()));
        HttpSession session = req.getSession();
        req.setAttribute("user", session.getAttribute("user"));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("advertisements.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        if ("delete".equals(req.getParameter("action"))) {

        } else if ("update".equals(req.getParameter("action"))) {

            int id = Integer.parseInt(req.getParameter("id"));

            CarBrand carBrand = AdRepository.instOf().findCarBrandById(
                    Integer.valueOf(req.getParameter("carBrandId")));

            if (id == 0) {

                AdRepository.instOf().saveAdv(
                        new Adv(
                                req.getParameter("name"),
                                req.getParameter("description"),
                                "new",
                                carBrand,
                                req.getParameter("carBodyTypeId"),
                                Integer.parseInt(req.getParameter("price"))
                        )
                );
            } else {
                AdRepository.instOf().updateAdv(
                        new Adv(
                                req.getParameter("name"),
                                req.getParameter("description"),
                                req.getParameter("status"),
                                carBrand,
                                req.getParameter("carBodyTypeId"),
                                Integer.parseInt(req.getParameter("price"))
                        )
                );
            }
        }
        resp.sendRedirect(req.getContextPath() + "/adv.do");
    }
}
