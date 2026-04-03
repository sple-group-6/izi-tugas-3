package aisco.donation.kodereferral;

import aisco.donation.core.DonationComponent;
import aisco.donation.core.DonationDecorator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationImpl extends DonationDecorator {
    // delta adds attributes
    private Map<String, String> referralCodeOwners; // code -> owner name
    private List<String[]> referralDonations;       // [name, email, phone, amount, paymentMethod, referralCode]

    // delta adds attributes, modifies the constructor
    public DonationImpl(DonationComponent donation) {
        super(donation);
        referralCodeOwners = new HashMap<>();
        referralDonations = new ArrayList<>();
    }

    // delta adds method: register who owns a referral code
    public void registerReferralCode(String code, String ownerName) {
        referralCodeOwners.put(code, ownerName);
        System.out.println("Referral code registered: " + code + " owned by " + ownerName);
    }

    // delta adds method: add a donation with an optional referral code
    public void addDonation(String name, String email, String phone, int amount, String paymentMethod, String referralCode) {
        referralDonations.add(new String[]{name, email, phone, String.valueOf(amount), paymentMethod, referralCode});
        System.out.println("Donation added: " + name + " (code: " + (referralCode != null ? referralCode : "none") + ")");
    }

    // delta modifies method
    public void getDonation() {
        int withReferral = 0;
        System.out.println("\nDonation List with Referral Info:");
        for (String[] d : referralDonations) {
            String code = d[5];
            String refInfo;
            if (code != null && referralCodeOwners.containsKey(code)) {
                refInfo = " [Referred by: " + referralCodeOwners.get(code) + " (code: " + code + ")]";
                withReferral++;
            } else {
                refInfo = " [No Referral]";
            }
            System.out.println("- Donasi " + d[0] + ": " + d[3] + " Payment Method: " + d[4] + refInfo);
        }
        System.out.println("Total donations with referral: " + withReferral);
    }
}
