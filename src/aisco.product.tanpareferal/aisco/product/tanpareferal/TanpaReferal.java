package aisco.product.tanpareferal;

import aisco.program.core.Program;
import aisco.program.ProgramFactory;
import aisco.financialreport.core.FinancialReport;
import aisco.financialreport.FinancialReportFactory;
import aisco.donation.core.Donation;
import aisco.donation.DonationFactory;
import java.util.ArrayList;
import java.util.List;

public class TanpaReferal {
    private static final int INDEX_EDUCATION = 0;

    private static FinancialReport income1;

    public static List<Program> addProgram() {
        System.out.println("\n Programs: ");
        List<Program> programs = new ArrayList<>();
        Program education = ProgramFactory.createProgram("aisco.program.activity.ProgramImpl", 1, "Education Fund", "Fund for underprivileged students", "200 students", "Local Government", "https://www.tanpareferal.id/logo");
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
        System.out.println("\n Donation (Tanpa Referral): ");
        Donation donate = DonationFactory.createDonation("aisco.donation.core.DonationImpl");
        donate.addDonation();
        donate.getDonation();
    }

    public static void main(String[] args) {
        System.out.println("Product Tanpa Referral");

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
