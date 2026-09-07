import java.util.Arrays;

public class Practice5_DischargeSummary {

    static {
        System.out.println("Discharge ledger initialized.");
    }

    static class DischargeSummary {

        private final String patientId;
        private final String[] medicationCodes;

        public DischargeSummary(
                String patientId,
                String[] medicationCodes) {

            if (patientId == null || patientId.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Patient ID cannot be blank.");
            }

            if (medicationCodes == null) {
                throw new IllegalArgumentException(
                        "Medication codes cannot be null.");
            }

            for (String code : medicationCodes) {
                if (code == null || !code.matches("MED-[A-Z]")) {
                    throw new IllegalArgumentException(
                            "Invalid medication code.");
                }
            }

            this.patientId = patientId;
            this.medicationCodes = Arrays.copyOf(
                    medicationCodes,
                    medicationCodes.length);
        }

        public String[] getMedicationCodes() {
            return Arrays.copyOf(
                    medicationCodes,
                    medicationCodes.length);
        }

        public DischargeSummary withCorrectedMedication(
                int index,
                String newCode) {

            if (index < 0 || index >= medicationCodes.length) {
                throw new IndexOutOfBoundsException(
                        "Invalid medication index.");
            }

            if (newCode == null
                    || !newCode.matches("MED-[A-Z]")) {
                throw new IllegalArgumentException(
                        "Invalid medication code.");
            }

            String[] correctedCodes = getMedicationCodes();
            correctedCodes[index] = newCode;

            return new DischargeSummary(
                    patientId,
                    correctedCodes);
        }
    }

    static final class CriticalCareDischargeSummary
            extends DischargeSummary {

        private final int icuDays;

        public CriticalCareDischargeSummary(
                String patientId,
                String[] medicationCodes,
                int icuDays) {

            super(patientId, medicationCodes);

            if (icuDays < 0) {
                throw new IllegalArgumentException(
                        "ICU days cannot be negative.");
            }

            this.icuDays = icuDays;
        }

        public int getIcuDays() {
            return icuDays;
        }
    }

    static String processNightlyBatch(
            DischargeSummary[] summaries) {

        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries == null) {
            return "0 processed | 0 null skipped | "
                    + "0 critical-care | 0 routine";
        }

        for (DischargeSummary summary : summaries) {

            if (summary == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (summary instanceof CriticalCareDischargeSummary) {
                criticalCare++;
            } else {
                routine++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | "
                + routine + " routine";
    }

    public static void main(String[] args) {

        try {
            new DischargeSummary(
                    "MT2026-0142",
                    new String[] { "MED-A", "bad" });
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        DischargeSummary d = new DischargeSummary(
                "MT2026-0142",
                new String[] { "MED-A", "MED-B" });

        String[] codes = d.getMedicationCodes();
        codes[0] = "TAMPERED";

        System.out.println(
                "Original codes: "
                        + Arrays.toString(d.getMedicationCodes()));

        DischargeSummary corrected = d.withCorrectedMedication(1, "MED-C");

        System.out.println(
                "Corrected codes: "
                        + Arrays.toString(
                                corrected.getMedicationCodes()));

        DischargeSummary[] summaries = {
                new CriticalCareDischargeSummary(
                        "MT001",
                        new String[] { "MED-X" },
                        4),
                null,
                new DischargeSummary(
                        "MT002",
                        new String[] { "MED-Y" })
        };

        System.out.println(
                processNightlyBatch(summaries));
    }
}