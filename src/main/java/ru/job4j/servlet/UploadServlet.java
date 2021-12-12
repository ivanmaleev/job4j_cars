package ru.job4j.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.model.Advertisement;
import ru.job4j.store.AdRepostiroty;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/ad/uploadphoto.do")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        Advertisement ad = AdRepostiroty.instOf().findAdById(id);
        String fileName = ad.getCar().getPhotoPath();
        File downloadFile = null;
        if (!"".equals(fileName) && fileName != null) {
            for (File file : new File(AdRepostiroty.getStorePath()).listFiles()) {
                if (fileName.equals(file.getName())) {
                    downloadFile = file;
                    break;
                }
            }
            if (downloadFile != null) {
                req.setAttribute("images", List.of(downloadFile.getName()));
            }
        }
        req.setAttribute("ads", List.of(ad));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/ad/uploadphoto.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File(AdRepostiroty.getStorePath());
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String extension = "";
                    int i = item.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = item.getName().substring(i + 1);
                    }
                    File file = new File(folder + File.separator + id + "." + extension);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        Advertisement ad = AdRepostiroty.instOf().findAdById(id);
                        ad.getCar().setPhotoPath(file.getName());
                        AdRepostiroty.instOf().saveAd(ad);
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        doGet(req, resp);
    }
}
