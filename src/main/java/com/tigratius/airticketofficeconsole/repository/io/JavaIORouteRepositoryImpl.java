package main.java.com.tigratius.airticketofficeconsole.repository.io;

import main.java.com.tigratius.airticketofficeconsole.model.*;
import main.java.com.tigratius.airticketofficeconsole.repository.CityRepository;
import main.java.com.tigratius.airticketofficeconsole.repository.RouteRepository;
import main.java.com.tigratius.airticketofficeconsole.util.IOUtil;

import java.text.SimpleDateFormat;
import java.util.*;

public class JavaIORouteRepositoryImpl implements RouteRepository {

    private final static String FILE_NAME = "route.csv";
    private CityRepository cityRepository;

    public JavaIORouteRepositoryImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Route getById(Long id) throws Exception {
        List<Route> routes = stringToData( IOUtil.read(FILE_NAME));

        Route current = null;
        for (Route route: routes
        ) {
            if (route.getId().equals(id)) {
                current = route;
                break;
            }
        }

        if (current != null) {
            return current;
        }

        throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
    }

    @Override
    public List<Route> getAll() throws Exception {
        return stringToData(IOUtil.read(FILE_NAME));
    }

    @Override
    public List<Route> stringToData(List<String> items) throws Exception {
        List<Route> routes = new ArrayList<>();

        for (String str : items
        ) {
            String[] parts = str.split(";");
            Route route = new Route();
            route.setId(Long.parseLong(parts[0]));

            Long cityDepartureId = Long.parseLong(parts[1]);
            Date departureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(parts[2]);
            Long cityArrivalId = Long.parseLong(parts[3]);
            Date arrivalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(parts[4]);

            route.setDeparture(cityRepository.getById(cityDepartureId));
            route.setDepartureDate(departureDate);
            route.setArrival(cityRepository.getById(cityArrivalId));
            route.setArrivalDate(arrivalDate);

            routes.add(route);
        }

        return routes;
    }
}
