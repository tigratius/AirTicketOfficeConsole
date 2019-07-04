package main.java.com.tigratius.airticketofficeconsole.repository.io;

import main.java.com.tigratius.airticketofficeconsole.model.Aircraft;
import main.java.com.tigratius.airticketofficeconsole.model.Message;
import main.java.com.tigratius.airticketofficeconsole.model.SeatType;
import main.java.com.tigratius.airticketofficeconsole.repository.AircraftRepository;
import main.java.com.tigratius.airticketofficeconsole.util.IOUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaIOAirCraftRepositoryImpl implements AircraftRepository
{
    private final static String FILE_NAME = "aircraft.csv";

    @Override
    public Aircraft getById(Long id) throws Exception {
        List<Aircraft> airCrafts = stringToData( IOUtil.read(FILE_NAME));

        Aircraft current = null;
        for (Aircraft aircraft : airCrafts
        ) {
            if (aircraft.getId().equals(id)) {
                current = aircraft;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public List<Aircraft> getAll(){
        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public List<Aircraft> stringToData(List<String> items) {
        List<Aircraft> airCrafts = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(";");
            Aircraft aircraft = new Aircraft();
            aircraft.setId(Long.parseLong(parts[0]));
            aircraft.setName(parts[1]);

            HashMap<SeatType, Integer> seatNumberMap = new HashMap<>();
            seatNumberMap.put(SeatType.valueOf(parts[2]), Integer.parseInt(parts[3]));
            seatNumberMap.put(SeatType.valueOf(parts[4]), Integer.parseInt(parts[5]));
            aircraft.setSeatNumberMap(seatNumberMap);

            airCrafts.add(aircraft);
        }

        return airCrafts;
    }
}
