public class Assignment3_ParkingSlot {

    static class ParkingSlot {

        String slotNo;
        int capacity;
        int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        void allot(String vehicleNo) {

            if (occupiedCount < capacity) {
                occupiedCount++;
                System.out.println(vehicleNo
                        + " allotted to slot " + slotNo);
            }
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {

        for (ParkingSlot slot : slots) {

            if (slot.occupiedCount < slot.capacity) {
                return slot;
            }
        }

        return null;
    }

    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {

        ParkingSlot availableSlot = findAvailableSlot(slots);

        if (availableSlot != null) {
            availableSlot.allot(vehicleNo);
        } else {
            System.out.println("No slots available for "
                    + vehicleNo);
        }
    }

    public static void main(String[] args) {

        ParkingSlot[] slots = {
                new ParkingSlot("A1", 4, 3),
                new ParkingSlot("A2", 5, 5)
        };

        safeAllot(slots, "TN09AB1234");

        // Passing the array does not copy the ParkingSlot objects.
        // The array contains references to the original objects.
        // Therefore, changes made to a slot affect the original object.

        safeAllot(slots, "TN09AB1234");
    }
}