public class Airport {
    private String airportCode;
    private String airfield;
    private double latitude;
    private double longitude;
    private double parkCost;

    public Airport(String airportCode, String airfield, double latitude, double longitude, double parkCost) {
        this.airportCode = airportCode;
        this.airfield = airfield;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkCost = parkCost;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public String getAirfield() {
        return airfield;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getParkCost() {
        return parkCost;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public void setAirfield(String airfield) {
        this.airfield = airfield;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setParkCost(double parkCost) {
        this.parkCost = parkCost;
    }

    
}
