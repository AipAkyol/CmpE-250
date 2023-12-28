import java.util.*;

public class GraphDijkstra {

    private Set<String> settled;
    private PriorityQueue<Node> pq;

    public GraphDijkstra()
    {
        settled = new HashSet<>();
        Comparator<Node> comparator = Comparator.comparingDouble(Node::getCost);
        pq = new PriorityQueue<>(comparator);
    }

    public void dijkstra(HashMap<String, ArrayList<String>> directions, HashMap<String, HashMap<String, Double>> weatherInfo, HashMap<String, Airport> airports,
                        String source, String destination, int time) {
        
        Node sourceNode = new Node(source, time, 0, null);
        pq.add(sourceNode);

        while (pq.size() != 0) {
            Node currentNode = pq.remove();

            if (currentNode.getAirportCode().equals(destination)) {
                printPath(currentNode);
                return;
            }

            if (settled.contains(currentNode.getAirportCode()))
                continue;

            settled.add(currentNode.getAirportCode());
            Airport currentAirport = airports.get(currentNode.getAirportCode());
            String timeString = String.valueOf(time);
            double departingWeatherMultiplier = weatherInfo.get(currentAirport.getAirfield()).get(timeString);
 
            ArrayList<String> neighbours = directions.get(currentNode.getAirportCode());
            for (String neighbour : neighbours) {
                if (!settled.contains(neighbour)) {
                    Airport neighbourAirport = airports.get(neighbour);
                    double distance = Calculators.distance(currentAirport.getLatitude(), currentAirport.getLongitude(), neighbourAirport.getLatitude(), neighbourAirport.getLongitude());
                    double neighbourWeatherMultiplier = weatherInfo.get(neighbourAirport.getAirfield()).get(timeString);
                    double cost = currentNode.getCost() + Calculators.flightCost(distance, departingWeatherMultiplier, neighbourWeatherMultiplier);
                    Node neighbourNode = new Node(neighbour, time, cost, currentNode);
                    pq.add(neighbourNode);
                }
            }
        }
    }
    public void printPath(Node targetNode) {
        StringBuilder path = new StringBuilder();
        Node currentNode = targetNode;
        while (currentNode.getParent() != null) {
            path.insert(0, currentNode.getAirportCode() + " ");
            currentNode = currentNode.getParent();
        }
        path.insert(0, currentNode.getAirportCode() + " ");
        String cost = String.format("%.5f", targetNode.getCost());
        path.append(cost);
        System.out.println(path);
    }
}

