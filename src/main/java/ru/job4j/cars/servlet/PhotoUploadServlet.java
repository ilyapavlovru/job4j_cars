package ru.job4j.cars.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ru.job4j.cars.model.Adv;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PhotoUploadServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(PhotoUploadServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("add".equals(req.getParameter("action"))) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("id", id);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/photoUpload.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("c:\\car-images\\");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + id + ".jpg");

                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }

                    byte[] bFile = new byte[(int) file.length()];
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        fileInputStream.read(bFile);
                        fileInputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Adv adv = AdRepository.instOf().findAdvById(id);
                    adv.setImage(bFile);
                    AdRepository.instOf().updateAdv(adv);
                }
            }
        } catch (FileUploadException e) {
            logger.warn("Upload photo error", e);
        }

        req.setCharacterEncoding("UTF-8");
        resp.sendRedirect(req.getContextPath() + "/adv.do");
    }
}
