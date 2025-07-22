
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hibernate.Address;
import hibernate.City;
import hibernate.HibernateUtil;
import hibernate.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Anne
 */
@WebServlet(name = "MyAccount", urlPatterns = {"/MyAccount"})
public class MyAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession ses = request.getSession();
        if (ses != null && ses.getAttribute("user") != null) {
            User user = (User) ses.getAttribute("user");
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("firstName", user.getFirst_name());
            responseObject.addProperty("lastName", user.getLast_name());
            responseObject.addProperty("password", user.getPassword());

            String since = new SimpleDateFormat("MMM yyyy").format(user.getCreated_at());
            responseObject.addProperty("since", since);
            
            Gson gson = new Gson();
            Session s = HibernateUtil.getSessionFactory().openSession();
          Criteria c=  s.createCriteria(Address.class);
          c.add(Restrictions.eq("user", user));
          
          if(!c.list().isEmpty()){
              List<Address> addressList = c.list();
              responseObject.add("addressList", gson.toJsonTree(addressList));
          }

            
            String toJson = gson.toJson(responseObject);
            response.setContentType("application/json");
            response.getWriter().write(toJson);

        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject userData = gson.fromJson(request.getReader(), JsonObject.class);

        String firstName = userData.get("firstName").getAsString();
        String lastName = userData.get("lastName").getAsString();
        String lineOne = userData.get("lineOne").getAsString();
        String lineTwo = userData.get("lineTwo").getAsString();
        String postalCode = userData.get("postalCode").getAsString();
        int cityId = userData.get("cityId").getAsInt();
        String currentPassword = userData.get("currentPassword").getAsString();
        String newPassword = userData.get("newPassword").getAsString();
        String confirmPassword = userData.get("confirmPassword").getAsString();

        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("status", false);

        if (firstName.isEmpty()) {

            responseObject.addProperty("message", "First Name can not be empty ! ");

        } else if (lastName.isEmpty()) {
            responseObject.addProperty("message", "Last Name can not be empty ! ");
        } else if (lineOne.isEmpty()) {
            responseObject.addProperty("message", "Line One can not be empty!");
        } else if (lineTwo.isEmpty()) {
            responseObject.addProperty("message", "Line two can not be empty!");
        } else if (postalCode.isEmpty()) {
            responseObject.addProperty("message", "Postal Code can not be empty!");
        } else if (!Util.isCodeValid(postalCode)) { //check is number
            responseObject.addProperty("message", "Postal Code must be a (4-5) long digit");
        } else if (cityId == 0) {
            responseObject.addProperty("message", "Select a city");
        } else if (currentPassword.isEmpty()) {
            responseObject.addProperty("message", "Enter your current password");
        } else if (!newPassword.isEmpty() && Util.isPasswordValid(newPassword)) {
            responseObject.addProperty("message", "Password must contain a-A * 123");

        } else if (!newPassword.isEmpty() && newPassword.equals(currentPassword)) {
            responseObject.addProperty("message", "New password can not match current password");

        } else if (!confirmPassword.isEmpty() && Util.isPasswordValid(newPassword)) {
            responseObject.addProperty("message", "Password must contain a-A * 123");

        } else if (!confirmPassword.equals(newPassword)) {
            responseObject.addProperty("message", "Confirm password does not match new password");

        } else {

            HttpSession ses = request.getSession();

            if (ses.getAttribute("user") != null) {
                User u = (User) ses.getAttribute("user");

                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session s = sf.openSession();

                Criteria c = s.createCriteria(User.class);
                c.add(Restrictions.eq("email", u.getEmail()));

                if (!c.list().isEmpty()) {
                    User u1 = (User) c.list().get(0);
                    u1.setFirst_name(firstName);
                    u1.setLast_name(lastName);
                    if (!confirmPassword.isEmpty()) {
                        u1.setPassword(confirmPassword);
                    } else {
                        u1.setPassword(currentPassword);
                    }

                    City city = (City) s.load(City.class, cityId); //primary key search

                    Address address = new Address();
                    address.setLineOne(lineOne);
                    address.setLineTwo(lineTwo);
                    address.setPostalCode(postalCode);
                    address.setCity(city);
                    address.setUser(u1);
                    
                    //session-management
                    ses.setAttribute("user", u1);

                    s.merge(u1);
                    s.save(address);

                    s.beginTransaction().commit();
                    responseObject.addProperty("status", true);
                    responseObject.addProperty("message", "User profile details update successfully");
                    s.close();

                }
            }

        }

        String toJson = gson.toJson(responseObject);
        response.setContentType("application/json");
        response.getWriter().write(toJson);

    }

}
