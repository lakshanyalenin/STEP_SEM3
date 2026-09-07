public class Practice3_BusRoute {

    static class BusRoute {

        private String routeCode;
        private String routeName;
        private int priority;

        public BusRoute(String routeCode, String routeName, int priority) {
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        // Constructor chaining with a default priority of 5
        public BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, 5);
        }

        public int compareTo(BusRoute other) {

            // Higher priority comes first
            if (this.priority != other.priority) {
                return other.priority - this.priority;
            }

            // If priority is same, compare route codes
            int codeComparison = this.routeCode.compareToIgnoreCase(other.routeCode);

            if (codeComparison != 0) {
                return codeComparison;
            }

            // If route code is also same, compare route names
            return this.routeName.compareToIgnoreCase(other.routeName);
        }
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {

        // Bubble sort - stable sorting
        for (int i = 0; i < routes.length - 1; i++) {

            for (int j = 0; j < routes.length - 1 - i; j++) {

                if (routes[j].compareTo(routes[j + 1]) > 0) {

                    BusRoute temp = routes[j];
                    routes[j] = routes[j + 1];
                    routes[j + 1] = temp;
                }
            }
        }

        return routes;
    }

    public static void main(String[] args) {

        BusRoute[] routes = {
                new BusRoute("RT205L", "Airport Express", 3),
                new BusRoute("rt201j", "City Central", 4),
                new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] rankedRoutes = rankRoutes(routes);

        System.out.println("Ranked routes:");

        for (BusRoute route : rankedRoutes) {
            System.out.println(route.routeCode);
        }
    }
}
