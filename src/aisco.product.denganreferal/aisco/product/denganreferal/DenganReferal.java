package aisco.product.denganreferal;

import aisco.program.core.Program;
import aisco.program.ProgramFactory;
import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.FinancialReportFactory;
import aisco.donation.core.Donation;
import aisco.donation.DonationFactory;
import java.util.ArrayList;
import java.util.List;

public class DenganReferal {
    private static final int INDEX_EDUCATION = 0;

    private static FinancialReport income1;

    public static List<Program> addProgram() {
        System.out.println("\n Programs: ");
        List<Program> programs = new ArrayList<>();
        Program education = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", 1, "Education Fund", "Fund for underprivileged students", "200 students", "Local Government", "https://www.denganreferal.id/logo");
        programs.add(INDEX_EDUCATION, education);
        return programs;
    }

    public static List<FinancialReport> addIncome(List<Program> programs) {
        List<FinancialReport> incomes = new ArrayList<>();
        income1 = FinancialReportFactory.createFinancialReport(
            "aisco.financialreport.income.FinancialReportImpl",
            FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", "1", "01-03-2024", 1000000, "Donation Budi", programs.get(INDEX_EDUCATION), "11000"),
            "Transfer");
        incomes.add(income1);
        incomes.add(FinancialReportFactory.createFinancialReport(
            "aisco.financialreport.income.FinancialReportImpl",
            FinancialReportFactory.createFinancialReport("aisco.financialreport.core.FinancialReportImpl", "2", "02-03-2024", 500000, "Donation Sari", programs.get(INDEX_EDUCATION), "11000"),
            "Cash"));
        return incomes;
    }

    public static void addDonation() {
        System.out.println("\n Donation (Dengan Referral): ");
        Donation base = DonationFactory.createDonation("aisco.donation.core.DonationImpl");
        aisco.donation.kodereferral.DonationImpl referralDonation =
            (aisco.donation.kodereferral.DonationImpl) DonationFactory.createDonation("aisco.donation.kodereferral.DonationImpl", base);

        // Register who owns each referral code
        referralDonation.registerReferralCode("REF-IZI-001", "Izi");
        referralDonation.registerReferralCode("REF-HAEKAL-002", "Haekal");

        // Haekal and Yodha donate using Izi's referral code; Izi donates without one
        referralDonation.addDonation("Haekal", "haekal@jmail.com", "+62828 2345 3091", 500000, "Cash", "REF-IZI-001");
        referralDonation.addDonation("Yodha", "yodha@jmail.com", "+62812 9876 5432", 300000, "Payku", "REF-IZI-001");
        referralDonation.addDonation("Izi", "izi@jmail.com", "+62878 6654 3321", 2500000, "Transfer", null);

        referralDonation.getDonation();
    }

    public static void main(String[] args) {
        System.out.println("Product Dengan Referral");

        List<Program> programs = addProgram();
        System.out.println(programs);

        List<FinancialReport> incomes = addIncome(programs);
        income1.printHeader();
        System.out.println(incomes);
        int totalincome = ((aisco.financialreport.income.FinancialReportImpl) income1).total(incomes);

        addDonation();
        System.out.println("Total Income: " + totalincome);
    }
}
