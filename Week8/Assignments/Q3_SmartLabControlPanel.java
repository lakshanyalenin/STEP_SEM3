import java.util.*;

interface Capability {
    void apply(Device device, int value);
}

class PowerCapability implements Capability {
    public void apply(Device device, int value) {
        if (value == 0 || value == 1) {
            device.setValue("Power", value);
        } else {
            System.out.println("Invalid power value");
        }
    }
}

class BrightnessCapability implements Capability {
    public void apply(Device device, int value) {
        if (value >= 0 && value <= 100) {
            device.setValue("Brightness", value);
        } else {
            System.out.println("Invalid brightness: " + value);
        }
    }
}

class TemperatureCapability implements Capability {
    public void apply(Device device, int value) {
        if (value >= 16 && value <= 30) {
            device.setValue("Temperature", value);
        } else {
            System.out.println("Invalid temperature: " + value);
        }
    }
}

class Device {
    String name;
    ArrayList<Capability> capabilities = new ArrayList<>();
    HashMap<String, Integer> values = new HashMap<>();

    Device(String name) {
        this.name = name;
    }

    void addCapability(Capability capability) {
        capabilities.add(capability);
    }

    void setValue(String capability, int value) {
        values.put(capability, value);
        System.out.println(name + " -> " + capability + ": " + value);
    }

    boolean hasCapability(Class<?> type) {
        for (Capability capability : capabilities) {
            if (type.isInstance(capability))
                return true;
        }
        return false;
    }

    void apply(Class<?> type, int value) {
        for (Capability capability : capabilities) {
            if (type.isInstance(capability)) {
                capability.apply(this, value);
            }
        }
    }
}

class SceneStep {
    Class<?> capabilityType;
    int value;

    SceneStep(Class<?> capabilityType, int value) {
        this.capabilityType = capabilityType;
        this.value = value;
    }
}

class Scene {
    String name;
    ArrayList<SceneStep> steps = new ArrayList<>();

    Scene(String name) {
        this.name = name;
    }

    void addStep(Class<?> capabilityType, int value) {
        steps.add(new SceneStep(capabilityType, value));
    }

    void apply(List<Device> devices) {
        System.out.println("\nApplying Scene: " + name);

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.capabilityType)) {
                    device.apply(step.capabilityType, step.value);
                }
            }
        }
    }
}

public class Q3_SmartLabControlPanel {

    public static void main(String[] args) {

        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        ArrayList<Device> devices = new ArrayList<>();
        devices.add(ac);
        devices.add(lights);
        devices.add(projector);

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep(PowerCapability.class, 1);
        lectureMode.addStep(BrightnessCapability.class, 40);
        lectureMode.addStep(TemperatureCapability.class, 24);

        lectureMode.apply(devices);

        System.out.println("\nTesting invalid temperature:");
        ac.apply(TemperatureCapability.class, 12);

        System.out.println("\nAdding Brightness capability to Projector:");
        projector.addCapability(new BrightnessCapability());

        projector.apply(BrightnessCapability.class, 70);
    }
}