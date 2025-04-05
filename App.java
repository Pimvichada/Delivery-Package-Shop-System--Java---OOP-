import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random;
import java.time.format.DateTimeFormatter;
public class App {
    static String ans;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            int choice = getUserChoice(scanner);
            switch (choice) {
                case 1:
                    checkHistoryByDate(scanner);
                    break;
                case 2:
                    sendPackage(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println("Do you want to perform another action? (Y/N)");
            String input = scanner.nextLine();
            if (!input.equalsIgnoreCase("Y")) {
                break;
            }
        } while (true);
        scanner.close();
    }
    private static int getUserChoice(Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Check history by date");
        System.out.println("2. Send a package");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        return choice;
    }
   private static void checkHistoryByDate(Scanner scanner) {
    System.out.println("Enter the date to check (dd-MM-yyyy): ");
    String dateToCheck = scanner.nextLine();
    try {
        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\pimvichada\\Desktop\\ShippingApp.txt"));
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Date: " + dateToCheck)) {
                found = true;
                System.out.println(lines.get(i)); 
                
                System.out.println(lines.get(i - 1)); 
               
                System.out.println(lines.get(i + 1)); 
                System.out.println(lines.get(i + 2)); 
                System.out.println(lines.get(i + 3)); 
                System.out.println(lines.get(i + 4)); 
                System.out.println(lines.get(i + 5)+"\n"); 
            }
        }
        if (!found) {
            System.out.println("No shipping records found for the date: " + dateToCheck);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

  private static void sendPackage(Scanner scanner) {
    do {
        int shippingID = getLastShippingID();
        Random random = new Random();
        int randomDigits = random.nextInt(10000);
        String randomText = "TH" + String.format("%04d", randomDigits);
        ans = randomText;
        System.out.println("Welcome to the Shipping System. Shipping ID: " + ans);
        System.out.println("Enter sender's information:");
        Person sender = getPersonInfo(scanner);
        System.out.println("\nEnter receiver's information:");
        Person receiver = getPersonInfo(scanner);
        System.out.println("\nEnter package information:");
        double weight = getValidDoubleInput(scanner, "Enter package weight (kg):");
        int packageType = getPackageType(scanner);
        Shipping shipping = new Shipping();
        shipping.addPackage2(sender, receiver, weight, packageType == 2);
        
        if (packageType == 2) {
            boolean hasAirConditioning = true; // สร้างรถทันทีเมื่อเป็น packageType 2
            Vehicle vehicle = new Vehicle(hasAirConditioning);
        }
 
        if (!shipping.getPackages().isEmpty()) {
            double shippingCost = shipping.getShippingCostAtIndex(0);
            System.out.println("The shipping cost is: " + shippingCost + " Bath");
            saveShippingData(shipping, ans); // Save shipping data to the file
        } else {
            System.out.println("No package available.");
        }
 
        System.out.println("Do you want to enter another package? (Y/N)");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            break;
        }
    } while (true);
    scanner.close();
}
    private static int getLastShippingID() {
        try {
            if (!Files.exists(Paths.get("C:\\Users\\pimvichada\\Desktop\\ShippingApp.txt"))) {
                return 1;
            }
            List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\pimvichada\\Desktop\\ShippingApp.txt"));
            if (!lines.isEmpty()) {
                String lastLine = lines.get(lines.size() - 1);
                String[] parts = lastLine.split(":");
                if (parts.length > 1) {
                    return Integer.parseInt(parts[1].trim()) + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
private static void saveShippingData(Shipping shipping, String shippingID) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\pimvichada\\Desktop\\ShippingApp.txt", true))) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            bufferedWriter.write("Shipping ID: " + shippingID + "\n");
            bufferedWriter.write("Date: " + LocalDate.now().format(dateFormatter) + "\n");
            for (Package2 pack : shipping.getPackages()) {
                bufferedWriter.write("Sender: " + pack.getSender().getName() + "\n");
                bufferedWriter.write("Receiver: " + pack.getReceiver().getName() + "\n");
                bufferedWriter.write("Weight: " + pack.getWeight() + "\n");
                bufferedWriter.write("Shipping Cost: " + pack.getShippingCost() + "\n");
                bufferedWriter.write("Status: " + pack.getStatus() + "\n");
                bufferedWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	private static Person getPersonInfo(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        return new Person(name, address, phoneNumber);
    }
private static double getValidDoubleInput(Scanner scanner, String prompt) {
        double value = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " ");
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // consume the invalid input
            }
        }
        return value;
    }
    private static int getPackageType(Scanner scanner) {
        int packageType = 1; // Default is Dry
        System.out.print("Enter package type (1 for Dry / 2 for Fresh): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        if (choice == 2) {
            packageType = 2; // Fresh
        }
        return packageType;
    }
}
 