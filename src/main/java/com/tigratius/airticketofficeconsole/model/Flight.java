package main.java.com.tigratius.airticketofficeconsole.model;

import java.util.HashMap;

public class Flight extends BaseEntity {

    private Aircraft aircraft;
    private Route route;

    private HashMap<SeatType, Integer> seatOccupiedMap = new HashMap<>();
    private HashMap<SeatType, Double> priceMap = new HashMap<>();

    /*public HashMap<SeatType, Double> getPriceMap() {
        return priceMap;
    }*/

    public void setPriceMap(HashMap<SeatType, Double> priceMap) {
        this.priceMap = priceMap;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    /*public HashMap<SeatType, Integer> getSeatOccupiedMap() {
        return seatOccupiedMap;
    }*/

    public void setSeatOccupiedMap(HashMap<SeatType, Integer> seatOccupiedMap) {
        this.seatOccupiedMap = seatOccupiedMap;
    }

    /*Price*/

    public double getPriceBySeatType(SeatType seatType)
    {
        return priceMap.get(seatType);
    }

    /*OccupiedSeats*/

    public int getOccupiedSeatsBySeatType(SeatType seatType)
    {
        return seatOccupiedMap.get(seatType);
    }

    /*public int getTotalOccupiedSeats()
    {
        return seatOccupiedMap.get(SeatType.BUSINESS) + seatOccupiedMap.get(SeatType.ECONOMY);
    }*/

    /*FreeSeats*/

    /*public int getTotalFreeSeats()
    {
        return aircraft.getTotalSeats() - getTotalOccupiedSeats();
    }*/

    public int getFreeSeatsBySeatType(SeatType seatType)
    {
        return aircraft.getNumberSeatsBySeatType(seatType) - getOccupiedSeatsBySeatType(seatType);
    }

    public boolean occupySeat(SeatType seatType)
    {
        int occupiedSeats = getOccupiedSeatsBySeatType(seatType);

        if (occupiedSeats < getFreeSeatsBySeatType(seatType)) {
            seatOccupiedMap.put(seatType, occupiedSeats + 1);
            return true;
        }

        return false;
    }

    public void reassignSeat(SeatType seatType)
    {
        int occupiedSeats = getOccupiedSeatsBySeatType(seatType);
        seatOccupiedMap.put(seatType, occupiedSeats - 1);
    }
}
