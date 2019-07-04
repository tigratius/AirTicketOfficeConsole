package main.java.com.tigratius.airticketofficeconsole.model;

import java.util.HashMap;

public class Aircraft extends BaseEntity{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    /*public HashMap<SeatType, Integer> getSeatNumberMap() {
        return seatNumberMap;
    }*/

    public void setSeatNumberMap(HashMap<SeatType, Integer> seatNumberMap) {
        this.seatNumberMap = seatNumberMap;
    }

    private HashMap<SeatType, Integer> seatNumberMap = new HashMap<>();

    int getNumberSeatsBySeatType(SeatType seatType)
    {

        return seatNumberMap.get(seatType);
    }

    /*public int getTotalSeats()
    {
        return seatNumberMap.get(SeatType.BUSINESS) + seatNumberMap.get(SeatType.ECONOMY);
    }*/
}
