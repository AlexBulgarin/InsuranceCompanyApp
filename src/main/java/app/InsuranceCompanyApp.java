package app;

import app.insurance.InsuranceType;

public interface InsuranceCompanyApp {
    String addInsurant(String login, String password);

    String deleteInsurant(String login, String password);

    String updateInsurant(String login, String oldPassword, String newPassword);

    String getInsurance(String login, InsuranceType insuranceType, int duration);

    void printInfo();

    boolean pushData();

    void pullData();

    boolean testLogin(String option, String login);

    boolean testPassword(String option, String login, String password);
}
