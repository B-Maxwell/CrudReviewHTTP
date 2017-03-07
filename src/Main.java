import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Crud Review Web Application...");

        HashMap<String, User> users = new HashMap<>();
        ArrayList<Vehicle> allVehicles = new ArrayList<>();

        Spark.init();

        //<editor-fold desc="GET /">
        Spark.get("/", (request, response) -> {

                    HashMap m = new HashMap();

                    Session session = request.session();
                    String userName = session.attribute("userName");
                    m.put("userName", userName);
                    m.put("vehicles", allVehicles);

                    return new ModelAndView(m, "vehicle-service.html");
                },
                new MustacheTemplateEngine()

        );
        //</editor-fold>

        //<editor-fold desc="POST /create-service">
        Spark.post("/create-service", (request, response) -> {
            String make = request.queryParams("make");
            String model = request.queryParams("model");
            String color = request.queryParams("color");
            String year = request.queryParams("year");
            String serviceType = request.queryParams("serviceType");
            Vehicle vehicle = new Vehicle(make, model, color, year, serviceType, allVehicles.size()+1);
            allVehicles.add(vehicle);
            response.redirect("/");
            return "";
        });
        //</editor-fold>

        //<editor-fold desc="POST /login">
        Spark.post("/login", (request, response) -> {

            String userName = request.queryParams("loginName");
            if (userName == null) {
                response.redirect("/");
            }
            User user = users.get(userName);
            if (user == null) {
                user = new User(userName, users.size() + 1);
                users.put(userName, user);
            }
            Session session = request.session();
            session.attribute("userName", userName);
            response.redirect("/");
            return "";
        });
        //</editor-fold>

        //<editor-fold desc="POST /logout">
        Spark.post("/logout", (request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";

        });
        //</editor-fold>

        //<editor-fold desc="/Edit">
        Spark.get("/edit", (request, response) -> {
                    Integer reqId = Integer.valueOf(request.queryParams("id"));
                    HashMap p = new HashMap();
                    ArrayList<Vehicle> vehicleDetails = new ArrayList<>();
                    for (Vehicle v1 : allVehicles) {
                        if (v1.requestId == reqId) {
                            vehicleDetails.add(v1);
                        }
                    }
                    p.put("details", vehicleDetails);
                    p.put("requestId", reqId);

                    return new ModelAndView(p, "edit-service.html");
                },
                new MustacheTemplateEngine()
        );
        //</editor-fold>

        //<editor-fold desc="/Delete">
        Spark.post("/delete", (request, response) -> {
                    int reqNumber = Integer.parseInt(request.queryParams("requestId"));;
                    allVehicles.remove(reqNumber-1);
                    response.redirect("/");
                    return "";
                }
        );
        //</editor-fold>

        //<editor-fold desc="/Update">
        Spark.post("/update", (request, response) -> {
                    String requestId = request.queryParams("requestId");
                    String make = request.queryParams("make");
                    String model = request.queryParams("model");
                    String color = request.queryParams("color");
                    String year = request.queryParams("year");
                    String serviceType = request.queryParams("serviceType");
                    int reqNumber = Integer.valueOf(requestId);
                    Vehicle vehicle = new Vehicle(make, model, color, year, serviceType, reqNumber);
                    allVehicles.set(reqNumber - 1, vehicle);
                    response.redirect("/");
                    return "";
                }
        );
        //</editor-fold>

    }
}
