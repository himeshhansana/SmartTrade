package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Color;
import hibernate.HibernateUtil;
import hibernate.Model;
import hibernate.Product;
import hibernate.Quality;
import hibernate.Status;
import hibernate.Storage;
import hibernate.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Anne
 */
@MultipartConfig
@WebServlet(name = "SaveProduct", urlPatterns = {"/SaveProduct"})
public class SaveProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String brandId = request.getParameter("brandId");
        String modelId = request.getParameter("modelId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String storageId = request.getParameter("storageId");
        String colorId = request.getParameter("colorId");
        String conditionId = request.getParameter("conditionId");
        String price = request.getParameter("price");
        String qty = request.getParameter("qty");


        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session s = sf.openSession();

        //validation
        if (request.getSession().getAttribute("user") == null) {
            responseObject.addProperty("message", "Please sign in!");
        } else if (!Util.isInteger(modelId)) {
            responseObject.addProperty("message", "Invalid model!");
        } else if (Integer.parseInt(modelId) == 0) {
            responseObject.addProperty("message", "Please select a model!");
        } else {
            Model model = (Model) s.get(Model.class, Integer.parseInt(modelId));
            if (model == null) {
                responseObject.addProperty("message", "Invalid model!");
            } else {
                if (!String.valueOf(model.getBrand().getId()).equals(brandId)) {
                    responseObject.addProperty("message", "Invalid brand!");
                } else {
                }
            }
        }
        //validation

//
//        Model model = (Model) s.load(Model.class, Integer.parseInt(modelId));
//        Storage storage = (Storage) s.load(Storage.class, Integer.parseInt(storageId));
//        Color color = (Color) s.load(Color.class, Integer.parseInt(colorId));
//        Quality quality = (Quality) s.load(Quality.class, Integer.parseInt(conditionId));
//        Status status = (Status) s.load(Status.class, 1);//Pending
//        User user = (User) request.getSession().getAttribute("user");
//
//        Product p = new Product();
//        p.setModel(model);
//        p.setTitle(title);
//        p.setDescription(description);
//        p.setStorage(storage);
//        p.setColor(color);
//        p.setQuality(quality);
//        p.setPrice(Double.parseDouble(price));
//        p.setQty(Integer.parseInt(qty));
//        p.setStatus(status);
//        p.setUser(user);
//        p.setCreated_at(new Date());
//
//        // methanin karanne save karaddi save vena id eka api varible ekakata alla gann eka 
//        int id = (int) s.save(p);
//        s.beginTransaction().commit();
//        s.close();
//
//        //imge uploading     
//        Part part1 = request.getPart("image1");
//        Part part2 = request.getPart("image2");
//        Part part3 = request.getPart("image3");
//
//        //build/web eka athule hdenne
//        //full path of the web pages folder
//        String appPath = getServletContext().getRealPath("");
//
//        // methanin karanne normal web eka athule hadana eka
//        String newPath = appPath.replace("build\\web", "web\\product-images");
//        // onima ekkat gela pennm // dekk danna
//
//        File productFolder = new File(newPath, String.valueOf(id));
//        productFolder.mkdir();
//
//        File file1 = new File(productFolder, "image1.png");
//        Files.copy(part1.getInputStream(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        File file2 = new File(productFolder, "image2.png");
//        Files.copy(part2.getInputStream(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        File file3 = new File(productFolder, "image3.png");
//        Files.copy(part3.getInputStream(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
        //imge uploading
        //send response
        Gson gson = new Gson();
        response.setContentType("application/java");
        response.getWriter().write(gson.toJson(responseObject));
        //send response

    }

}
