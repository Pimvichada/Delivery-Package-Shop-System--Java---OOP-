import java.util.List;
import java.util.ArrayList;
import java.util.Random;
class Shipping {
    private static int shippingCounter = 0;
    private List<Package2> packages;
    private List<String> statusHistory;
    public Shipping() {
        packages = new ArrayList<>();
        statusHistory = new ArrayList<>();
        shippingCounter++;
    }
    public void addPackage2(Person sender, Person receiver, double weight, boolean isFresh) {
        Package2 newPackage2 = new Package2(sender, receiver, weight, isFresh); 
        packages.add(newPackage2);
        statusHistory.add("Package from " + sender.getName() + " to " + receiver.getName() + " is In transit");
    }
    public static String generateShippingID() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(10000); 
        return "TH" + String.format("%04d", randomNumber); 
    }
    public void updateStatus(int packageIndex, String newStatus) {
        if (packageIndex >= 0 && packageIndex < packages.size()) {
            packages.get(packageIndex).updateStatus(newStatus);
            statusHistory.add("Package " + (packageIndex + 1) + " status changed to " + newStatus);
        } else {
            System.out.println("Invalid package index");
        }
    }
    public List<String> getPackageHistory() {
        return statusHistory;
    }
    public static int getShippingCounter() {
        return shippingCounter++;
    }
    public List<Package2> getPackages() {
        return packages;
    }
    public double getShippingCostAtIndex(int index) {
        if (index >= 0 && index < packages.size()) {
            return packages.get(index).getShippingCost();
        } else {
            return -1; 
        }
    }
    public Package2 getPackageAtIndex(int index) {
        if (index >= 0 && index < packages.size()) {
            return packages.get(index);
        } else {
            return null;
        }
    }
}