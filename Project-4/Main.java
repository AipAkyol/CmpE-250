import java.nio.Buffer;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
 
    public static void main(String[] args) throws FileNotFoundException, IOException{ 

        double startTime = System.nanoTime();
        
        HashMap<Integer, Double> weatherMultipliers = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            weatherMultipliers.put(i, Calculators.weatherMultiplier(i));
        }

        String weatherFileName = "C:\\BOUN\\CmpE250\\CmpE-250\\Project-4\\cases/weather.csv";
        File weatherFile = new File(weatherFileName);
        BufferedReader weatherReader = new BufferedReader(new FileReader(weatherFile));

        HashMap<String, HashMap<String, Double>> weatherInfo = new HashMap<>();

        weatherReader.readLine(); // Skip the first line
        while (weatherReader.ready()) {
            String line = weatherReader.readLine();
            String[] lineArray = line.split(",");
            if (!weatherInfo.containsKey(lineArray[0])) {
                weatherInfo.put(lineArray[0], new HashMap<>());
            } 
            weatherInfo.get(lineArray[0]).put(lineArray[1], weatherMultipliers.get(Integer.parseInt(lineArray[2])));
        }

        weatherReader.close();

        String airportsFileName = "C:\\BOUN\\CmpE250\\CmpE-250\\Project-4\\cases\\airports/INTER-2.csv";
        File airportsFile = new File(airportsFileName);
        BufferedReader airportsReader = new BufferedReader(new FileReader(airportsFile));

        HashMap<String, Airport> airports = new HashMap<>();

        airportsReader.readLine(); // Skip the first line
        while (airportsReader.ready()) {
            String line = airportsReader.readLine();
            String[] lineArray = line.split(",");
            airports.put(lineArray[0], new Airport(lineArray[0], lineArray[1], Double.parseDouble(lineArray[2]), Double.parseDouble(lineArray[3]), Double.parseDouble(lineArray[4])));
        }

        airportsReader.close();

        String directionsFileName = "C:\\BOUN\\CmpE250\\CmpE-250\\Project-4\\cases\\directions/INTER-2.csv";
        File directionsFile = new File(directionsFileName);
        BufferedReader directionsReader = new BufferedReader(new FileReader(directionsFile));

        HashMap<String, ArrayList<String>> directions = new HashMap<>();

        directionsReader.readLine(); // Skip the first line
        while (directionsReader.ready()) {
            String line = directionsReader.readLine();
            String[] lineArray = line.split(",");
            if (!directions.containsKey(lineArray[0])) {
                directions.put(lineArray[0], new ArrayList<>());
            }
            directions.get(lineArray[0]).add(lineArray[1]);
        }

        directionsReader.close();

        String missionFile = "C:\\BOUN\\CmpE250\\CmpE-250\\Project-4\\cases\\missions/INTER-2.in";
        File mission = new File(missionFile);
        BufferedReader missionReader = new BufferedReader(new FileReader(mission));

        String plane = missionReader.readLine();
        

        while(missionReader.ready()) {
            String line = missionReader.readLine();
            String[] lineArray = line.split(" ");
            String startAirport = lineArray[0];
            String endAirport = lineArray[1];
            String timeOrigin = lineArray[2];
            String deadline = lineArray[3];
            //GraphDijkstra dj = new GraphDijkstra();
            //dj.dijkstra(directions, weatherInfo, airports, startAirport, endAirport, Integer.parseInt(timeOrigin));
            AdvancedDijkstra dj = new AdvancedDijkstra();
            dj.dijkstra(directions, weatherInfo, airports, startAirport, endAirport, Integer.parseInt(timeOrigin), Integer.parseInt(deadline), plane);
        }

        double endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1000000000;
        System.out.println(duration);
    }
 }