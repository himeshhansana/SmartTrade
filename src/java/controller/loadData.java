package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Brand;
import hibernate.City;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Quality;
import hibernate.Status;
import hibernate.Storage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "LoadProducts", urlPatterns = {"/loadData"})
public class loadData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

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

        //load-product-data
        Status status = (Status) s.get(Status.class, 2);
        Criteria c6 = s.createCriteria(Product.class);
        c6.addOrder(Order.desc("id"));
        c6.add(Restrictions.eq("status", status));
        responseObject.addProperty("allProductCount", c6.list().size());

        c6.setFirstResult(0);
        c6.setMaxResults(6);

        List<Product> productList = c6.list();
        for (Product product : productList) {
            product.setUser(null);
        }

        //load-product-data
        Gson gson = new Gson();

        responseObject.addProperty("status", true);

        responseObject.add("brandList", gson.toJsonTree(brandList));
        responseObject.add("modelList", gson.toJsonTree(modelList));
        responseObject.add("colorList", gson.toJsonTree(colorList));
        responseObject.add("qualityList", gson.toJsonTree(qualityList));
        responseObject.add("storageList", gson.toJsonTree(storageList));

        responseObject.add("productList", gson.toJsonTree(productList));
        responseObject.addProperty("status", true);

        response.setContentType("application/java");
        response.getWriter().write(gson.toJson(responseObject));
        s.close();

    }

}
