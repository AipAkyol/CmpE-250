import java.util.*;

public class AdvancedDijkstra {

    private PriorityQueue<Node> pq;
    private HashMap<String, HashMap<String, Boolean>> visited;

    public AdvancedDijkstra()
    {
        Comparator<Node> comparator = Comparator.comparingDouble(Node::getCost);
        pq = new PriorityQueue<>(comparator);
    }

    public void dijkstra(HashMap<String, ArrayList<String>> directions, HashMap<String, HashMap<String, Double>> weatherInfo, HashMap<String, Airport> airports,
                        String source, String destination, int startTime, int deadline, String plane) {

        visited = new HashMap<>();

        for (String airport : airports.keySet()) {
            visited.put(airport, new HashMap<>());
        }
        
        Node sourceNode = new Node(source, startTime, 0, null);
        pq.add(sourceNode);

        while (pq.size() != 0) {
            Node currentNode = pq.remove();
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
            if (parkTime <= deadline) {
                double cost = currentNode.getCost() + currentAirport.getParkCost();
                Node parkNode = new Node(currentNode.getAirportCode(), parkTime, cost, currentNode);
                if (!visited.get(currentNode.getAirportCode()).containsKey(String.valueOf(parkTime))) {
                    pq.add(parkNode);
                }
                
            }
 
            ArrayList<String> neighbours = directions.get(currentNode.getAirportCode());
            for (String neighbour : neighbours) {
                Airport neighbourAirport = airports.get(neighbour);
                double distance = Calculators.distance(currentAirport.getLatitude(), currentAirport.getLongitude(), neighbourAirport.getLatitude(), neighbourAirport.getLongitude());
                int flightHours = Calculators.flightDuration(distance, plane);
                int endTime = currentNode.getTime() + flightHours*3600;
                if (endTime <= deadline) {
                    String endTimeString = String.valueOf(endTime);
                    double neighbourWeatherMultiplier = weatherInfo.get(neighbourAirport.getAirfield()).get(endTimeString);
                    double cost = currentNode.getCost() + Calculators.flightCost(distance, departingWeatherMultiplier, neighbourWeatherMultiplier);
                    Node neighbourNode = new Node(neighbour, endTime, cost, currentNode);
                    if (!visited.get(currentNode.getAirportCode()).containsKey(String.valueOf(endTime))) {
                        pq.add(neighbourNode);
                    }
                }
            }
        }
        System.out.println("No possible solution.");
    }
    public void printPath(Node targetNode) {
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
        System.out.println(path);
    }
}