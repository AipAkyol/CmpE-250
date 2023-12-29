import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
 
    public static void main(String[] args) throws FileNotFoundException, IOException{ 
        
        HashMap<Integer, Double> weatherMultipliers = new HashMap<>();
        for (int i = 0; i < 32; i++) {
            weatherMultipliers.put(i, Calculators.weatherMultiplier(i));
        }

        String weatherFileName = args[2];
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

        String airportsFileName = args[0];
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

        String directionsFileName = args[1];
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

        String missionFile = args[3];
        File mission = new File(missionFile);
        BufferedReader missionReader = new BufferedReader(new FileReader(mission));

        String plane = missionReader.readLine();
        
        String task1FileName = args[4];
        BufferedWriter task1writer = new BufferedWriter(new java.io.FileWriter(task1FileName));
        
        String task2FileName = args[5];
        BufferedWriter task2writer = new BufferedWriter(new java.io.FileWriter(task2FileName));

        while(missionReader.ready()) {
            String line = missionReader.readLine();
            String[] lineArray = line.split(" ");
            String startAirport = lineArray[0];
            String endAirport = lineArray[1];
            String timeOrigin = lineArray[2];
            String deadline = lineArray[3];
            GraphDijkstra dj = new GraphDijkstra(task1writer);
            dj.dijkstra(directions, weatherInfo, airports, startAirport, endAirport, Integer.parseInt(timeOrigin));
            AdvancedDijkstra adj = new AdvancedDijkstra(task2writer);
            adj.dijkstra(directions, weatherInfo, airports, startAirport, endAirport, Integer.parseInt(timeOrigin), Integer.parseInt(deadline), plane);
        }

        missionReader.close();
        task1writer.close();
        task2writer.close();
    }
 }