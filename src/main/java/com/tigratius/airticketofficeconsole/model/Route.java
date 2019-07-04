package main.java.com.tigratius.airticketofficeconsole.model;

import java.util.Date;

public class Route  extends BaseEntity{

    private City departure;
    private Date departureDate;
    private City arrival;
    private Date arrivalDate;

    public City getArrival() {
                return arrival;
    }

    public void setArrival(City arrival) {
        this.arrival = arrival;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public City getDeparture() {
        return departure;
    }

    public void setDeparture(City departure) {
        this.departure = departure;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
