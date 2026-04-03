package aisco.donation.core;

public abstract class DonationDecorator extends DonationComponent {
    public DonationComponent donation;

    public DonationDecorator() {
    }

    public DonationDecorator(DonationComponent donation) {
        this.donation = donation;
    }

    public void addDonation() {
        donation.addDonation();
    }

    public void getDonation() {
        donation.getDonation();
    }
}
