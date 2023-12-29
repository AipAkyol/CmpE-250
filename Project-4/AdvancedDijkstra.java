import java.io.BufferedWriter;
import java.util.*;
import java.io.IOException;

public class AdvancedDijkstra {

    private PriorityQueue<Node> pq;
    private HashMap<String, HashMap<String, Boolean>> visited;
    private BufferedWriter writer;

    public AdvancedDijkstra(BufferedWriter writer) {
        Comparator<Node> comparator = Comparator.comparingDouble(Node::getD);
        pq = new PriorityQueue<>(comparator);
        this.writer = writer;
    }

    public void dijkstra(HashMap<String, ArrayList<String>> directions, HashMap<String, HashMap<String, Double>> weatherInfo,
     HashMap<String, Airport> airports, String source, String destination, int startTime, int deadline, String plane) throws IOException{

        visited = new HashMap<>();

        for (String airport : airports.keySet()) {
            visited.put(airport, new HashMap<>());
        }
        
        double dLat = airports.get(destination).getLatitude();
        double dLon = airports.get(destination).getLongitude();
        double hsCost = Calculators.distance(airports.get(source).getLatitude(), airports.get(source).getLongitude(), dLat, dLon);
        Node sourceNode = new Node(source, startTime, 0, null, hsCost);
        pq.add(sourceNode);

        while (pq.size() != 0) {
            Node currentNode = pq.remove();
            if (visited.get(currentNode.getAirportCode()).containsKey(String.valueOf(currentNode.getTime()))) {
                continue;
            }

            visited.get(currentNode.getAirportCode()).put(String.valueOf(currentNode.getTime()), true);

            if (currentNode.getAirportCode().equals(destination)) {
                printPath(currentNode);
                return;
            }

            Airport currentAirport = airports.get(currentNode.getAirportCode());
            int currentTime = currentNode.getTime();
            String startTimeString = String.valueOf(currentTime);
            double departingWeatherMultiplier = weatherInfo.get(currentAirport.getAirfield()).get(startTimeString);

            int parkTime = currentTime + 6*3600;
            if (parkTime <= deadline
             && (!visited.get(currentNode.getAirportCode()).containsKey(String.valueOf(parkTime)))) {
                double hpCost = Calculators.distance(currentAirport.getLatitude(), currentAirport.getLongitude(), dLat, dLon);
                double cost = currentNode.getCost() + currentAirport.getParkCost();
                Node parkNode = new Node(currentNode.getAirportCode(), parkTime, cost, currentNode, hpCost);
                pq.add(parkNode); 
            }
 
            ArrayList<String> neighbours = directions.get(currentNode.getAirportCode());
            for (String neighbour : neighbours) {
                Airport neighbourAirport = airports.get(neighbour);
                double distance = Calculators.distance(currentAirport.getLatitude(), currentAirport.getLongitude(), neighbourAirport.getLatitude(), neighbourAirport.getLongitude());
                int flightHours = Calculators.flightDuration(distance, plane);
                int endTime = currentNode.getTime() + flightHours*3600;
                if (endTime <= deadline
                 && (!visited.get(neighbour).containsKey(String.valueOf(endTime)))) {
                    String endTimeString = String.valueOf(endTime);
                    double neighbourWeatherMultiplier = weatherInfo.get(neighbourAirport.getAirfield()).get(endTimeString);
                    double nhCost = Calculators.distance(neighbourAirport.getLatitude(), neighbourAirport.getLongitude(), dLat, dLon);
                    double cost = currentNode.getCost() + Calculators.flightCost(distance, departingWeatherMultiplier, neighbourWeatherMultiplier);
                    Node neighbourNode = new Node(neighbour, endTime, cost, currentNode,nhCost);
                    pq.add(neighbourNode);
                }
            }
        }
        writer.write("No possible solution.");
        writer.newLine();
    }
    
    public void printPath(Node targetNode) throws IOException{
        StringBuilder path = new StringBuilder();
        Node currentNode = targetNode;
        while (currentNode.getParent() != null) {
            if(currentNode.getParent().getAirportCode() == currentNode.getAirportCode()){
                path.insert(0, "PARK ");
            } else {
                path.insert(0, currentNode.getAirportCode() + " ");
            }
            currentNode = currentNode.getParent();
        }
        path.insert(0, currentNode.getAirportCode() + " ");
        String cost = String.format("%.5f", targetNode.getCost());
        path.append(cost);
        writer.write(path.toString());
        writer.newLine();
    }
}