abstract class ServiceableVehicle {

    private double mileage;

    public ServiceableVehicle() {
        mileage = 0.0;
    }

    public abstract String performMaintenance();

    public double getMileage() {
        return mileage;
    }

    public void addMileage(double km) {
        if (km < 0) {
            throw new IllegalArgumentException("Mileage cannot be negative");
        }

        mileage += km;
    }
}

interface Insurable {
    String getInsuranceInfo();
}

class Forklift extends ServiceableVehicle implements Insurable {

    protected String assetTag;

    public Forklift(String assetTag) {
        if (assetTag == null || assetTag.isBlank()) {
            throw new IllegalArgumentException("Asset tag cannot be blank");
        }

        this.assetTag = assetTag;
    }

    @Override
    public String performMaintenance() {
        return "Forklift " + assetTag
                + ": hydraulic and fork inspection complete";
    }

    @Override
    public String getInsuranceInfo() {
        return "Insured under fleet policy - Asset " + assetTag;
    }
}

class HeavyDutyForklift extends Forklift {

    public HeavyDutyForklift(String assetTag) {
        super(assetTag);
    }

    @Override
    public String performMaintenance() {
        return super.performMaintenance()
                + " | high-pressure hydraulic check complete";
    }
}

public class Q3_FleetMaintenanceTracker {

    public static String getInsuranceIfApplicable(ServiceableVehicle v) {

        if (v instanceof Insurable) {
            Insurable insurableVehicle = (Insurable) v;
            return insurableVehicle.getInsuranceInfo();
        }

        return "No insurance record exists";
    }

    public static void main(String[] args) {

        Forklift f = new Forklift("FL-22");

        f.addMileage(120);

        System.out.println("Mileage: " + f.getMileage());
        System.out.println(f.performMaintenance());

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");

        System.out.println(hd.performMaintenance());

        System.out.println(getInsuranceIfApplicable(f));
        System.out.println(getInsuranceIfApplicable(hd));
    }
}