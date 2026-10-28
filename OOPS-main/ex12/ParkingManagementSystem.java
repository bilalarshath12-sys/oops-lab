import java.util.*;

class Vehicle {
    private String vehicleNumber;
    private int slotNumber;
    private long entryTime;

    public Vehicle(String number, int slot) {
        vehicleNumber = number;
        slotNumber = slot;
        entryTime = System.currentTimeMillis();
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public long getEntryTime() {
        return entryTime;
    }
}

public class ParkingManagementSystem {
    static Map<String, Vehicle> parking = new HashMap<>();
    static int nextSlot = 1;
    static final int MAX_SLOTS = 5;
    static final int RATE = 20;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("1. Vehicle Entry");
            System.out.println("2. Vehicle Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter vehicle number: ");
            String number = sc.nextLine().trim().toUpperCase();

            if (number.isEmpty()) {
                throw new IllegalArgumentException(
                    "Vehicle number cannot be empty.");
            }

            if (choice == 1) {
                if (parking.containsKey(number))
                    throw new IllegalArgumentException(
                        "Vehicle is already parked.");

                if (parking.size() >= MAX_SLOTS)
                    throw new IllegalStateException(
                        "Parking is full.");

                Vehicle v = new Vehicle(number, nextSlot++);
                parking.put(number, v);

                System.out.println("Vehicle Entry Successful!");
                System.out.println("Vehicle Number: "
                    + v.getVehicleNumber());
                System.out.println("Slot Number: "
                    + v.getSlotNumber());

            } else if (choice == 2) {
                Vehicle v = parking.get(number);

                if (v == null)
                    throw new IllegalArgumentException(
                        "Vehicle not found.");

                long duration = Math.max(1,
                    (System.currentTimeMillis()
                    - v.getEntryTime()) / 60000);

                long fee = ((duration + 59) / 60) * RATE;

                System.out.println("Vehicle Number: "
                    + v.getVehicleNumber());
                System.out.println("Slot Number: "
                    + v.getSlotNumber());
                System.out.println("Duration: "
                    + duration + " minutes");
                System.out.println("Parking Fee: Rs. " + fee);

                parking.remove(number);

            } else {
                throw new IllegalArgumentException(
                    "Invalid choice.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Error: Enter a valid choice.");
        } catch (IllegalArgumentException |
                 IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
