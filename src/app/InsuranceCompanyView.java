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
        InsuranceCompanyApp insuranceCompanyApp = new InsuranceCompanyController();
        init(scanner, insuranceCompanyApp);
        while (true) {
            String selectedAction = scanner.next();
            switch (selectedAction) {
                case "info" -> System.out.println(resourceBundle.getString("insurance.info"));
                case "add" -> {
                    System.out.println(resourceBundle.getString("loginrequest.info"));
                    String addLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("add", addLogin)) {
                        System.out.println(resourceBundle.getString("passwordrequest.info"));
                        String addPassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("add", addLogin, addPassword)) {
                            insuranceCompanyApp.addInsurant(addLogin, addPassword);
                        }
                    }
                }
                case "delete" -> {
                    System.out.println(resourceBundle.getString("loginrequest.info"));
                    String deleteLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", deleteLogin)) {
                        System.out.println(resourceBundle.getString("passwordrequest.info"));
                        String deletePassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("verification", deleteLogin, deletePassword)) {
                            insuranceCompanyApp.deleteInsurant(deleteLogin, deletePassword);
                        }
                    }
                }
                case "update" -> {
                    System.out.println(resourceBundle.getString("loginrequest.info"));
                    String updateLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", updateLogin)) {
                        System.out.println(resourceBundle.getString("passwordoldrequest.info"));
                        String updateOldPassword = scanner.next();
                        if (insuranceCompanyApp.testPassword("verification", updateLogin, updateOldPassword)) {
                            System.out.println(resourceBundle.getString("passwordnewrequest.info"));
                            String updateNewPassword = scanner.next();
                            if (insuranceCompanyApp.testPassword("add", updateLogin, updateNewPassword)) {
                                insuranceCompanyApp.updateInsurant(updateLogin, updateOldPassword, updateNewPassword);
                            }
                        }
                    }
                }
                case "getinsurance" -> {
                    System.out.println(resourceBundle.getString("loginrequest.info"));
                    String getInsuranceLogin = scanner.next();
                    if (insuranceCompanyApp.testLogin("verification", getInsuranceLogin)) {
                        System.out.println(resourceBundle.getString("passwordrequest.info"));
                        String getInsurancePassword = scanner.next();
                        if (insuranceCompanyApp.
                                testPassword("verification", getInsuranceLogin, getInsurancePassword)) {
                            System.out.println(resourceBundle.getString("insurancetypeselection.info"));
                            System.out.println(resourceBundle.getString("insurancetype.info"));
                            String selectedInsurance = scanner.next().toUpperCase();
                            if (!selectedInsurance.equals("MEDICAL") && !selectedInsurance.equals("LIFE") &&
                                    !selectedInsurance.equals("AUTO") && !selectedInsurance.equals("TRAVEL") &&
                                    !selectedInsurance.equals("HOME")) {
                                System.out.println(resourceBundle.getString("wronginsurance.info"));
                            } else {
                                System.out.println(resourceBundle.getString("insuranceduration.info"));
                                int duration = scanner.nextInt();
                                insuranceCompanyApp.
                                        getInsurance(getInsuranceLogin, InsuranceType.valueOf(selectedInsurance), duration);
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
                default -> System.out.println(resourceBundle.getString("wrongcommand.info"));
            }
        }
    }
}