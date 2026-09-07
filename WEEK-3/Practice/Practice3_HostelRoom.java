public class Practice3_HostelRoom {

    static class HostelRoom {

        String roomNo;
        int beds;
        int occupied;

        HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        void allot(String name) {
            if (occupied < beds) {
                occupied++;
                System.out.println(name + " allotted to " + roomNo);
            }
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, String name) {

        HostelRoom availableRoom = findAvailableRoom(rooms);

        if (availableRoom != null) {
            availableRoom.allot(name);
        } else {
            System.out.println("No rooms available for " + name);
        }
    }

    public static void main(String[] args) {

        HostelRoom[] rooms = {
                new HostelRoom("C-214", 3, 2),
                new HostelRoom("C-507", 2, 2)
        };

        safeAllot(rooms, "Divya");

        // Passing the array does not copy the HostelRoom objects.
        // The array contains references to the original objects.
        // Therefore, changes made to a room affect the original object.

        safeAllot(rooms, "Divya");
    }
}
