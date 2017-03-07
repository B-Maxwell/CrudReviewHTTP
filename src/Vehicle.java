/**
 * Created by MacbookStudioPro on 2/27/17.
 */
public class Vehicle {
    String make;
    String model;
    String color;
    String year;
    String serviceType;
    int requestId;


    public Vehicle(String make, String model, String color, String year, String serviceType, int requestId) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.serviceType = serviceType;
        this.requestId = requestId;
    }
}
