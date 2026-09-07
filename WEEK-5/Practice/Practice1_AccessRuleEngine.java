public class Practice1_AccessRuleEngine {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            return accessorContext.equals("SAME_CLASS")
                    ? "ALLOWED"
                    : "DENIED";
        }

        if (fieldModifier.equals("default")) {
            return (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE"))
                            ? "ALLOWED"
                            : "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            return (accessorContext.equals("SAME_CLASS")
                    || accessorContext.equals("SAME_PACKAGE"))
                            ? "ALLOWED"
                            : "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {

        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {
            if (attempt == null || attempt.length != 2) {
                denied++;
                continue;
            }

            String result = classifyAccess(attempt[0], attempt[1]);

            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    static class PatientRecord {

        private String patientId;
        String wardCode;
        protected double vitalsScore;
        public String facilityName;

        public PatientRecord(
                String patientId,
                String wardCode,
                double vitalsScore,
                String facilityName) {

            if (patientId == null
                    || patientId.trim().isEmpty()
                    || patientId.trim().length() < 4) {
                throw new IllegalArgumentException(
                        "Patient ID must contain at least 4 characters.");
            }

            this.patientId = patientId.trim();
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }
    }

    public static void main(String[] args) {

        System.out.println(
                classifyAccess("private", "SAME_CLASS"));

        System.out.println(
                classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
                { "protected", "SAME_PACKAGE" },
                { "protected", "DIFFERENT_PACKAGE" },
                { "public", "DIFFERENT_PACKAGE" }
        };

        System.out.println(summarizeBatch(attempts));

        try {
            new PatientRecord(
                    "MT9",
                    "W3",
                    98.2,
                    "MediTrack Central");
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        PatientRecord patient = new PatientRecord(
                "MT94",
                "W3",
                98.2,
                "MediTrack Central");

        System.out.println("Valid patient record created.");
    }
}