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
        Store store = AdRepository.instOf();
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String status = req.getParameter("advStatusSelectorId");
        int price = Integer.parseInt(req.getParameter("price"));
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
                logger.warn("Read default ad image error", e);
            }

            store.saveAdv(
                    new Adv(
                            id,
                            name,
                            description,
                            "Продается",
                            bFile,
                            carBrand,
                            carBodyType,
                            user,
                            price
                    )
            );
        } else {
            Adv adv = store.findAdvById(id);
            adv.setName(name);
            adv.setDescription(description);
            adv.setStatus(status);
            adv.setCarBrand(carBrand);
            adv.setCarBodyType(carBodyType);
            adv.setUser(user);
            adv.setPrice(price);
            store.updateAdv(adv);
        }

        resp.sendRedirect(req.getContextPath() + "/adv.do");
    }
}
