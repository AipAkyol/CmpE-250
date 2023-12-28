public class Node {
    private String airportCode;
    private Node parent;
    private int time;
    private double cost;

    public Node(String airportCode, int time, double cost, Node parent) {
        this.airportCode = airportCode;
        this.parent = parent;
        this.time = time;
        this.cost = cost;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public Node getParent() {
        return parent;
    }

    public int getTime() {
        return time;
    }

    public double getCost() {
        return cost;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
