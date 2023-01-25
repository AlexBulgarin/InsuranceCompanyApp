package app;

import app.insurance.InsuranceType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class InsuranceCompanyControllerTest {
    static InsuranceCompanyApp insuranceCompanyApp = null;

    @BeforeEach
    public void initTestData() {
        insuranceCompanyApp.addInsurant("Test1", "Test1");
        insuranceCompanyApp.addInsurant("Test2", "Test2");
        insuranceCompanyApp.addInsurant("Test3", "Test3");

    }

    @AfterEach
    public void deleteTestData() {
        insuranceCompanyApp.deleteInsurant("Test1", "Test1");
        insuranceCompanyApp.deleteInsurant("Test2", "Test2");
        insuranceCompanyApp.deleteInsurant("Test3", "Test3");
    }

    @BeforeAll
    public static void initAllTestData() {
        insuranceCompanyApp = new InsuranceCompanyController("InsuranceCompanyTest.json");
    }

    @AfterAll
    public static void deleteAllTestData() {

    }

    @Test
    public void addInsurantTest() {
        try {
            insuranceCompanyApp.addInsurant("test", "test");
        } catch (Exception e) {
            fail("Can't add insurant");
        }
    }

    @Test
    public void testLoginTest() {
        assertFalse(insuranceCompanyApp.testLogin("add", "qwe456"), "incorrect method");
    }

    @Test
    public void getInsuranceTest() {
        try {
            insuranceCompanyApp.getInsurance("test", InsuranceType.MEDICAL, 20);
        } catch (Exception e) {
            fail("Can't get insurance");
        }
    }

    @Test
    public void pushDataTest() {
        assertTrue(insuranceCompanyApp.pushData(), "incorrect method");
    }
}
