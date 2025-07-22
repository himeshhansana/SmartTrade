package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.City;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Quality;
import hibernate.Status;
import hibernate.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;

@WebServlet(name = "LoadProductData", urlPatterns = {"/LoadProductData"})
public class LoadProductData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);
        Session s = HibernateUtil.getSessionFactory().openSession();

        //get brands
        Criteria c1 = s.createCriteria(Brand.class);
        List<City> brandList = c1.list();

        //get models
        Criteria c2 = s.createCriteria(Model.class);
        List<Model> modelList = c2.list();

        //get color
        Criteria c3 = s.createCriteria(Color.class);
        List<Color> colorList = c3.list();

        //get quality
        Criteria c4 = s.createCriteria(Quality.class);
        List<Quality> qualityList = c4.list();

        //get storage
        Criteria c5 = s.createCriteria(Storage.class);
        List<Storage> storageList = c5.list();

        Gson gson = new Gson();

        responseObject.addProperty("status", true);

        responseObject.add("brandList", gson.toJsonTree(brandList));
        responseObject.add("modelList", gson.toJsonTree(modelList));
        responseObject.add("colorList", gson.toJsonTree(colorList));
        responseObject.add("qualityList", gson.toJsonTree(qualityList));
        responseObject.add("storageList", gson.toJsonTree(storageList));

        response.setContentType("application/java");
        response.getWriter().write(gson.toJson(responseObject));

    }

}
