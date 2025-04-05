public class Vehicle {
    private boolean hasAirConditioning;
    public Vehicle(boolean hasAirConditioning) {
		 System.out.println("This package is delivered in an air-conditioned vehicle.");
        this.hasAirConditioning = hasAirConditioning;
    }
    public boolean hasAirConditioning() {
        return hasAirConditioning;
    }
 
}