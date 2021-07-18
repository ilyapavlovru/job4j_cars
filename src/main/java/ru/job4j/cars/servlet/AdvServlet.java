package ru.job4j.cars.servlet;

import org.apache.log4j.Logger;
import ru.job4j.cars.model.Adv;
import ru.job4j.cars.model.CarBodyType;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;
import ru.job4j.cars.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AdvServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(AdvServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        Store store = AdRepository.instOf();
        User user = store.findUserByEmail(sessionUser.getEmail());
        req.setAttribute("ads", new ArrayList<>(store.findAllAdsByUserId(user.getId())));
        req.setAttribute("user", sessionUser);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("advertisements.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        if ("delete".equals(req.getParameter("action"))) {

        } else if ("update".equals(req.getParameter("action"))) {

            Store store = AdRepository.instOf();

            int id = Integer.parseInt(req.getParameter("id"));

            CarBrand carBrand = store.findCarBrandById(
                    Integer.valueOf(req.getParameter("carBrandId")));

            CarBodyType carBodyType = store.findCarBodyTypeById(
                    Integer.valueOf(req.getParameter("carBodyTypeId")));

            HttpSession session = req.getSession();
            User sessionUser = (User) session.getAttribute("user");
            User user = store.findUserByEmail(sessionUser.getEmail());

            if (id == 0) {

                File file = new File("c:\\car-images\\no-image.png");
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AdRepository.instOf().saveAdv(
                        new Adv(
                                id,
                                req.getParameter("name"),
                                req.getParameter("description"),
                                "Продается",
                                bFile,
                                carBrand,
                                carBodyType,
                                user,
                                Integer.parseInt(req.getParameter("price"))
                        )
                );
            } else {
                Adv adv = AdRepository.instOf().findAdvById(id);
                adv.setName(req.getParameter("name"));
                adv.setDescription(req.getParameter("description"));
                adv.setStatus(req.getParameter("advStatusSelectorId"));
                adv.setCarBrand(carBrand);
                adv.setCarBodyType(carBodyType);
                adv.setUser(user);
                adv.setPrice(Integer.parseInt(req.getParameter("price")));
                AdRepository.instOf().updateAdv(adv);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/adv.do");
    }
}
