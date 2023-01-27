package app;

import app.insurance.InsuranceType;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InsuranceCompanyView {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("insurance");

    private static void loadProperties(String language) {
        Locale locale = new Locale(language);
        resourceBundle = ResourceBundle.getBundle("insurance", locale);
    }

    private static void init(Scanner scanner, InsuranceCompanyApp insuranceCompanyApp) {
        insuranceCompanyApp.pullData();
        System.out.println("Choose language: EN or RU | Выберите язык: EN или RU");
        String language = scanner.next().toLowerCase();
        if (language.equals("en")) {
            loadProperties("en");
        } else if (language.equals("ru")) {
            loadProperties("ru");
        } else {
            loadProperties("ru");
        }
        System.out.println(resourceBundle.getString("insurance.info"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InsuranceCompanyApp insuranceCompanyApp = new InsuranceCompanyController(resourceBundle.getString("dbfilepath"));
        init(scanner, insuranceCompanyApp);
        while (true) {
            String selectedAction = scanner.next();
            switch (selectedAction) {
                case "info" -> System.out.println(resourceBundle.getString("insurance.info"));
                case "add" -> {
                    System.out.println(resourceBundle.getString("loginRequest"));
                    String addLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("add", addLogin)) {
                        System.out.println(resourceBundle.getString("passwordRequest"));
                        String addPassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("add", addLogin, addPassword)) {
                            System.out.println(insuranceCompanyApp.addInsurant(addLogin, addPassword));
                        }
                    }
                }
                case "delete" -> {
                    System.out.println(resourceBundle.getString("loginRequest"));
                    String deleteLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", deleteLogin)) {
                        System.out.println(resourceBundle.getString("passwordRequest"));
                        String deletePassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("verification", deleteLogin, deletePassword)) {
                            System.out.println(insuranceCompanyApp.deleteInsurant(deleteLogin, deletePassword));
                        }
                    }
                }
                case "update" -> {
                    System.out.println(resourceBundle.getString("loginRequest"));
                    String updateLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", updateLogin)) {
                        System.out.println(resourceBundle.getString("passwordOldRequest"));
                        String updateOldPassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("verification", updateLogin, updateOldPassword)) {
                            System.out.println(resourceBundle.getString("passwordNewRequest"));
                            String updateNewPassword = scanner.next();
                            if (insuranceCompanyApp.testPassword("add", updateLogin, updateNewPassword)) {
                                System.out.println(insuranceCompanyApp.updateInsurant(updateLogin, updateOldPassword,
                                        updateNewPassword));
                            }
                        }
                    }
                }
                case "getinsurance" -> {
                    System.out.println(resourceBundle.getString("loginRequest"));
                    String getInsuranceLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", getInsuranceLogin)) {
                        System.out.println(resourceBundle.getString("passwordRequest"));
                        String getInsurancePassword = scanner.next();
                        if (insuranceCompanyApp.
                                testPassword("verification", getInsuranceLogin, getInsurancePassword)) {
                            System.out.println(resourceBundle.getString("insuranceTypeSelection"));
                            System.out.println(resourceBundle.getString("insuranceType.info"));
                            String selectedInsurance = scanner.next().toUpperCase();
                            if (!selectedInsurance.equals("MEDICAL") && !selectedInsurance.equals("LIFE") &&
                                    !selectedInsurance.equals("AUTO") && !selectedInsurance.equals("TRAVEL") &&
                                    !selectedInsurance.equals("HOME")) {
                                System.out.println(resourceBundle.getString("wrongInsurance"));
                            } else {
                                System.out.println(resourceBundle.getString("insuranceDuration"));
                                int duration = scanner.nextInt();
                                System.out.println(insuranceCompanyApp.getInsurance(getInsuranceLogin,
                                        InsuranceType.valueOf(selectedInsurance), duration));
                            }
                        }
                    }
                }
                case "print" -> insuranceCompanyApp.printInfo();
                case "changelanguage" -> {
                    System.out.println("Choose language: EN or RU | Выберите язык: EN или RU");
                    String language = scanner.next().toLowerCase();
                    if (language.equals("en")) {
                        loadProperties("en");
                    } else if (language.equals("ru")) {
                        loadProperties("ru");
                    } else {
                        loadProperties("ru");
                    }
                    System.out.println(resourceBundle.getString("insurance.info"));
                }
                default -> System.out.println(resourceBundle.getString("wrongCommand"));
            }
        }
    }
}