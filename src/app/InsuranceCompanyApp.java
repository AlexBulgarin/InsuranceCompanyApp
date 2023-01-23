package app;

import app.insurance.InsuranceType;

public interface InsuranceCompanyApp {
    void addInsurant(String login, String password);

    void deleteInsurant(String login, String password);

    void updateInsurant(String login, String oldPassword, String newPassword);

    void getInsurance(String login, InsuranceType insuranceType, int duration);

    void printInfo();

    boolean pushData();

    void pullData();

    boolean testLogin(String option, String login);

    boolean testPassword(String option, String login, String password);
}
