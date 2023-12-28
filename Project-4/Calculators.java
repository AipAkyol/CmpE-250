public class Calculators {
    
    // Calculates the weather multiplier for a given weather code
    public static double weatherMultiplier(int code) {
        // Convert the code to a binary string with 5 digits
        String binaryString = Integer.toBinaryString(code);
        binaryString = String.format("%5s", binaryString).replace(' ', '0');
        double w = Double.parseDouble(Character.toString(binaryString.charAt(0)));
        double r = Double.parseDouble(Character.toString(binaryString.charAt(1)));
        double s = Double.parseDouble(Character.toString(binaryString.charAt(2)));
        double h = Double.parseDouble(Character.toString(binaryString.charAt(3)));
        double b = Double.parseDouble(Character.toString(binaryString.charAt(4)));
        return (w * 1.05 + (1 - w))
                * (r * 1.05 + (1 - r))
                * (s * 1.10 + (1 - s))
                * (h * 1.15 + (1 - h))
                * (b * 1.20 + (1 - b));
    }

    // calculates the distance between two airports using the Haversine formula 
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the earth in km
        double R = 6371;

        // Convert the latitude and longitude values from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the difference between the latitudes and longitudes
        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        // Calculate the distance between the two airports
        double a = Math.pow(Math.sin(latDiff / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.pow(Math.sin(lonDiff / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // Calculates the flight duration for a given distance and plane, 6, 12 or 18 hours
    public static int flightDuration(double distance, String plane) {
         switch (plane) {
            case "Carreidas 160":
                if (distance <= 175) {
                    return 6;
                } else if (distance <= 350) {
                    return 12;
                } else {
                    return 18;
                }
            case "Orion III":
                if (distance <= 1500) {
                    return 6;
                } else if (distance <= 3000) {
                    return 12;
                } else {
                    return 18;
                }
            case "Skyfleet S570":
                if (distance <= 500) {
                    return 6;
                } else if (distance <= 1000) {
                    return 12;
                } else {
                    return 18;
                }
            case "T-16 Skyhopper":
                if (distance <= 2500) {
                    return 6;
                } else if (distance <= 5000) {
                    return 12;
                } else {
                    return 18;
                }
            default:
                System.out.println("Invalid plane type");
                return 0;
         }
    }

    // Calculates the flight cost for a given distance and weather conditions
    public static double flightCost(double distance, double departingWeatherMultiplier, double landingWeatherMultiplier) {
        return 300 * departingWeatherMultiplier * landingWeatherMultiplier + distance;
    }
}
