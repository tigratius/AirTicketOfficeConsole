package main.java.com.tigratius.airticketofficeconsole.repository.io;

import main.java.com.tigratius.airticketofficeconsole.model.City;
import main.java.com.tigratius.airticketofficeconsole.model.Message;
import main.java.com.tigratius.airticketofficeconsole.repository.CityRepository;
import main.java.com.tigratius.airticketofficeconsole.util.IOUtil;

import java.util.ArrayList;
import java.util.List;

public class JavaIOCityRepositoryImpl implements CityRepository
{
    private final static String FILE_NAME = "city.csv";

        @Override
        public City getById(Long id) throws Exception {
            List<City> cities = stringToData( IOUtil.read(FILE_NAME));

            City current = null;
            for (City c : cities
            ) {
                if (c.getId().equals(id)) {
                    current = c;
                    break;
                }
            }

            if (current != null) {
                return current;
            }

            throw new Exception(Message.NOT_FIND_ID.getMessage() + id);
        }

        @Override
        public List<City> getAll(){
            return stringToData(IOUtil.read(FILE_NAME));
        }

        @Override
        public List<City> stringToData(List<String> items){
            List<City> cities = new ArrayList<>();

            for (String str : items
            ) {
                String[] parts = str.split(";");
                City city = new City();
                city.setId(Long.parseLong(parts[0]));
                city.setName(parts[1]);
                cities.add(city);
            }

            return cities;
        }

}
