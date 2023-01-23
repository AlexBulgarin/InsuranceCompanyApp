package app;

import app.insurance.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class InsuranceCompanyController implements InsuranceCompanyApp {
    public static InsuranceCompany insuranceCompany = new InsuranceCompany();
    ObjectMapper objectMapper = new ObjectMapper();
    List<InsuranceInfo> autoInsuranceList = new ArrayList<>();
    List<InsuranceInfo> homeInsuranceList = new ArrayList<>();
    List<InsuranceInfo> lifeInsuranceList = new ArrayList<>();
    List<InsuranceInfo> medicalInsuranceList = new ArrayList<>();
    List<InsuranceInfo> travelInsuranceList = new ArrayList<>();

    @Override
    public void addInsurant(String login, String password) {
        insuranceCompany.getInsurantMap().put(login, new Insurant(login, password));
        if (pushData()) {
            System.out.println("Страхователь|Insurant " + login + " успешно зарегестрирован|successfully created");
        } else {
            insuranceCompany.getInsurantMap().remove(login);
        }
    }

    @Override
    public void deleteInsurant(String login, String password) {
        insuranceCompany.getInsurantMap().remove(login);
        if (pushData()) {
            System.out.println("Страхователь|Insurant " + login + " успешно удален|successfully deleted");
        } else {
            insuranceCompany.getInsurantMap().put(login, new Insurant(login, password));
        }
    }

    @Override
    public void updateInsurant(String login, String oldPassword, String newPassword) {
        insuranceCompany.getInsurantMap().put(login, new Insurant(login, newPassword));
        if (pushData()) {
            System.out.println("Пароль страхователя|Insurant password " + login +
                    " успешно изменен|successfully changed");
        } else {
            insuranceCompany.getInsurantMap().put(login, new Insurant(login, oldPassword));
        }
    }

    @Override
    public void getInsurance(String login, InsuranceType insuranceType, int duration) {
        switch (insuranceType) {
            case MEDICAL -> {
                medicalInsuranceList.add(new InsuranceInfo(new MedicalInsurance(duration),
                        insuranceCompany.getInsurantMap().get(login)));
                insuranceCompany.getInsuranceMap().put(insuranceType, medicalInsuranceList);
            }
            case TRAVEL -> {
                travelInsuranceList.add(new InsuranceInfo(new TravelInsurance(duration),
                        insuranceCompany.getInsurantMap().get(login)));
                insuranceCompany.getInsuranceMap().put(insuranceType, travelInsuranceList);
            }
            case LIFE -> {
                lifeInsuranceList.add(new InsuranceInfo(new LifeInsurance(duration),
                        insuranceCompany.getInsurantMap().get(login)));
                insuranceCompany.getInsuranceMap().put(insuranceType, lifeInsuranceList);
            }
            case HOME -> {
                homeInsuranceList.add(new InsuranceInfo(new HomeInsurance(duration),
                        insuranceCompany.getInsurantMap().get(login)));
                insuranceCompany.getInsuranceMap().put(insuranceType, homeInsuranceList);
            }
            case AUTO -> {
                autoInsuranceList.add(new InsuranceInfo(new AutoInsurance(duration),
                        insuranceCompany.getInsurantMap().get(login)));
                insuranceCompany.getInsuranceMap().put(insuranceType, autoInsuranceList);
            }
        }
        if (pushData()) {
            System.out.println(login + " оформил|got " + insuranceType + " страховку на|insurance for " + duration +
                    " месяц(а/ов)|month");
        }
    }

    @Override
    public void printInfo() {
        insuranceCompany.getInsurantMap().entrySet().forEach(System.out::println);
        insuranceCompany.getInsuranceMap().entrySet().forEach(System.out::println);
    }

    @Override
    public boolean pushData() {
        try {
            objectMapper.writeValue(new File("InsuranceCompany.json"), insuranceCompany);
        } catch (IOException e) {
            System.out.println("Ошибка доступа, попробуйте позднее|Connection error, try again later");
            return false;
        }
        return true;
    }

    @Override
    public void pullData() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("InsuranceCompany.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            insuranceCompany = objectMapper.readValue(jsonData, InsuranceCompany.class);
            autoInsuranceList = insuranceCompany.getInsuranceMap().entrySet().stream().
                    filter(insuranceTypeListEntry -> insuranceTypeListEntry.getKey().equals(InsuranceType.AUTO)).
                    map(Map.Entry::getValue).findAny().get();
            medicalInsuranceList = insuranceCompany.getInsuranceMap().entrySet().stream().
                    filter(insuranceTypeListEntry -> insuranceTypeListEntry.getKey().equals(InsuranceType.MEDICAL)).
                    map(Map.Entry::getValue).findAny().get();
            homeInsuranceList = insuranceCompany.getInsuranceMap().entrySet().stream().
                    filter(insuranceTypeListEntry -> insuranceTypeListEntry.getKey().equals(InsuranceType.HOME)).
                    map(Map.Entry::getValue).findAny().get();
            travelInsuranceList = insuranceCompany.getInsuranceMap().entrySet().stream().
                    filter(insuranceTypeListEntry -> insuranceTypeListEntry.getKey().equals(InsuranceType.TRAVEL)).
                    map(Map.Entry::getValue).findAny().get();
            lifeInsuranceList = insuranceCompany.getInsuranceMap().entrySet().stream().
                    filter(insuranceTypeListEntry -> insuranceTypeListEntry.getKey().equals(InsuranceType.LIFE)).
                    map(Map.Entry::getValue).findAny().get();
        } catch (IOException e) {
            System.out.println("Ошибка доступа, попробуйте позднее|Connection error, try again later");
            e.printStackTrace();
        }
    }

    @Override
    public boolean testLogin(String option, String login) {
        switch (option) {
            case "add":
                if (Pattern.compile("[^A-Za-z]").matcher(login).find()) {
                    System.out.println("Введен некорректный логин. Логин должен состоять только из букв|" +
                            "Incorrect login. Login should contain only letters");
                    return false;
                } else if (insuranceCompany.getInsurantMap().containsKey(login)) {
                    System.out.println("Пользователь под таким логином уже зарегестрирован, попробуйте другой|" +
                            "Login is already used, try another one");
                    return false;
                }
                break;
            case "verification":
                if (!insuranceCompany.getInsurantMap().containsKey(login)) {
                    System.out.println("Пользователь с таким логином не зарегестрирован| " +
                            "User with such login is not registered");
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean testPassword(String option, String login, String password) {
        switch (option) {
            case "add":
                if (Pattern.compile("[^A-Za-z0-9]").matcher(password).find()) {
                    System.out.println("Введен некорректный пароль. Пароль должен состоять только из букв и цифр|" +
                            "Incorrect password. Password should contain only letters and digits");
                    return false;
                }
                break;
            case "verification":
                if (!insuranceCompany.getInsurantMap().get(login).getPassword().equals(password)) {
                    System.out.println("Введен неверный пароль| Wrong password");
                    return false;
                }
                break;
        }
        return true;
    }
}
