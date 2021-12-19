package ru.job4j.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.model.Advertisement;
import ru.job4j.model.User;
import ru.job4j.service.AdsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(urlPatterns = "/ad/userads.do")
public class UserAdsServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("userid");
        List<Advertisement> ads = AdsService.instOf().findAds(Integer.parseInt(userId));
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(ads);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");
        String idStr = req.getParameter("id");
        String description = req.getParameter("description");
        String carBrand = req.getParameter("carbrand");
        String bodyType = req.getParameter("bodytype");
        String fileName = "";
        AdsService.instOf().saveAd(user, idStr, description, carBrand, bodyType, fileName);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
