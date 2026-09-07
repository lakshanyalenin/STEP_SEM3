public class Assignment4_LibraryMember {

    static class LibraryMember {

        private String membershipId;
        private String memberName;
        private String email;
        private boolean premiumMember;

        private String securityAnswerHash;

        // No-argument constructor
        public LibraryMember() {
            this("UNKNOWN", "Unknown");
        }

        // Name-only constructor
        public LibraryMember(String memberName) {
            this("UNKNOWN", memberName);
        }

        // ID + name constructor
        public LibraryMember(String membershipId, String memberName) {
            this.membershipId = membershipId;
            this.memberName = memberName;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String membershipId) {

            if (this.membershipId == null
                    || this.membershipId.equals("UNKNOWN")) {
                this.membershipId = membershipId;
            }
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premiumMember) {
            this.premiumMember = premiumMember;
        }

        // Security answer is settable but never retrievable
        public void setSecurityAnswer(String securityAnswer) {

            if (securityAnswer == null) {
                return;
            }

            long hash = 7;

            for (int i = 0; i < securityAnswer.length(); i++) {
                hash = hash * 31 + securityAnswer.charAt(i);
            }

            securityAnswerHash = Long.toHexString(hash);
        }
    }

    public static void main(String[] args) {

        LibraryMember member = new LibraryMember("LB94", "Priya");

        member.setEmail("priya@example.com");
        member.setPremiumMember(true);

        member.setSecurityAnswer("blue");

        System.out.println("Member: "
                + member.getMemberName());

        System.out.println("ID: "
                + member.getMembershipId());

        System.out.println("Premium: "
                + member.isPremiumMember());

        member.setMembershipId("LB99");

        System.out.println("ID after second update: "
                + member.getMembershipId());

        System.out.println("Security answer cannot be retrieved.");
    }
}