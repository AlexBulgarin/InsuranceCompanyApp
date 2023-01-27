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

import static app.InsuranceCompanyView.resourceBundle;

public class InsuranceCompanyController implements InsuranceCompanyApp {
    private final String dbFilePath;
    public static InsuranceCompany insuranceCompany = new InsuranceCompany();
    ObjectMapper objectMapper = new ObjectMapper();
    List<InsuranceInfo> autoInsuranceList = new ArrayList<>();
    List<InsuranceInfo> homeInsuranceList = new ArrayList<>();
    List<InsuranceInfo> lifeInsuranceList = new ArrayList<>();
    List<InsuranceInfo> medicalInsuranceList = new ArrayList<>();
    List<InsuranceInfo> travelInsuranceList = new ArrayList<>();

    public InsuranceCompanyController(String dbFilePath) {
        this.dbFilePath = dbFilePath;
    }

    @Override
    public String addInsurant(String login, String password) {
        insuranceCompany.getInsurantMap().put(login, new Insurant(login, password));
        if (pushData()) {
            return resourceBundle.getString("insurant") + " " + login + " " +
                    resourceBundle.getString("registered");
        } else {
            insuranceCompany.getInsurantMap().remove(login);
            return resourceBundle.getString("accessError");
        }
    }

    @Override
    public String deleteInsurant(String login, String password) {
        insuranceCompany.getInsurantMap().remove(login);
        if (pushData()) {
            return resourceBundle.getString("insurant") + " " + login + " " +
                    resourceBundle.getString("deleted");
        } else {
            insuranceCompany.getInsurantMap().put(login, new Insurant(login, password));
            return resourceBundle.getString("accessError");
        }
    }

    @Override
    public String updateInsurant(String login, String oldPassword, String newPassword) {
        insuranceCompany.getInsurantMap().put(login, new Insurant(login, newPassword));
        if (pushData()) {
            return resourceBundle.getString("insurant") + " " + login + " " +
                    resourceBundle.getString("updated");
        } else {
            insuranceCompany.getInsurantMap().put(login, new Insurant(login, oldPassword));
            return resourceBundle.getString("accessError");
        }
    }

    @Override
    public String getInsurance(String login, InsuranceType insuranceType, int duration) {
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
        return pushData() ? login + " " + resourceBundle.getString("got") + " " + insuranceType + " " +
                resourceBundle.getString("insuranceFor") + " " + duration + " " +
                resourceBundle.getString("month") : resourceBundle.getString("accessError");
    }

    @Override
    public void printInfo() {
        insuranceCompany.getInsurantMap().entrySet().forEach(System.out::println);
        insuranceCompany.getInsuranceMap().entrySet().forEach(System.out::println);
    }

    @Override
    public boolean pushData() {
        try {
            objectMapper.writeValue(new File(dbFilePath), insuranceCompany);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void pullData() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(dbFilePath));
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
            System.out.println(resourceBundle.getString("accessError"));
            e.printStackTrace();
        }
    }

    @Override
    public boolean testLogin(String option, String login) {
        switch (option) {
            case "add":
                if (Pattern.compile("[^A-Za-z]").matcher(login).find()) {
                    System.out.println(resourceBundle.getString("incorrectLogin"));
                    return false;
                } else if (insuranceCompany.getInsurantMap().containsKey(login)) {
                    System.out.println(resourceBundle.getString("alreadyUsed"));
                    return false;
                }
                break;
            case "verification":
                if (!insuranceCompany.getInsurantMap().containsKey(login)) {
                    System.out.println(resourceBundle.getString("notRegistered"));
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
                    System.out.println(resourceBundle.getString("incorrectPassword"));
                    return false;
                }
                break;
            case "verification":
                if (!insuranceCompany.getInsurantMap().get(login).getPassword().equals(password)) {
                    System.out.println(resourceBundle.getString("wrongPassword"));
                    return false;
                }
                break;
        }
        return true;
    }
}
