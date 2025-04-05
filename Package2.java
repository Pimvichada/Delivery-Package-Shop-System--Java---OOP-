public class Package2 {
    private Person sender;
    private Person receiver;
    private double weight;
    private boolean isFresh;
    private double shippingCost;
    private String status;
    public Package2(Person sender, Person receiver, double weight, boolean isFresh) {
        this.sender = sender;
        this.receiver = receiver;
        this.weight = weight;
        this.isFresh = isFresh;
        this.status = "In transit";
        calculateShippingCost();
    }
   private void calculateShippingCost() {
        double firstKiloCost = 40;
        double additionalKiloCost = 15;
        this.shippingCost = firstKiloCost + ((weight - 1) * additionalKiloCost);
        if (isFresh) {
            this.shippingCost += weight * 10;
        }
    }
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
    public String getStatus() {
        return status;
    }
    public Person getSender() {
        return sender;
    }
    public Person getReceiver() {
        return receiver;
    }
    public double getWeight() {
        return weight;
    }
    public boolean isFresh() {
        return isFresh;
    }
    public double getShippingCost() {
        return shippingCost;
    }
}