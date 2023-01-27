package app;

import app.insurance.InsuranceType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class InsuranceCompanyControllerTest {
    static InsuranceCompanyApp insuranceCompanyApp = null;

    @BeforeAll
    public static void initAllTestData() {
        insuranceCompanyApp = new InsuranceCompanyController("InsuranceCompanyTest.json");
    }

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

    @AfterAll
    public static void deleteAllTestData() {
    }

    @Test
    public void addInsurantTest() {
        assertEquals("Страхователь test успешно зарегестрирован",
                insuranceCompanyApp.addInsurant("test", "test"),
                "wrong output");
    }

    @Test
    public void deleteInsurantTest() {
        assertEquals("Страхователь Test1 успешно удален",
                insuranceCompanyApp.deleteInsurant("Test1", "Test1"),
                "wrong output");
    }

    @Test
    public void updateInsurantTest() {
        assertEquals("Страхователь Test1 успешно изменен",
                insuranceCompanyApp.updateInsurant("Test1", "Test1", "Test11"),
                "wrong output");
    }

    @Test
    public void getInsuranceTest() {
        assertEquals("Test2 оформил MEDICAL страховку на 20 месяц(а/ов)",
                insuranceCompanyApp.getInsurance("Test2", InsuranceType.MEDICAL, 20),
                "wrong output");
    }

    @Test
    public void testLoginTest() {
        assertFalse(insuranceCompanyApp.testLogin("add", "qwe456"), "incorrect method");
        assertTrue(insuranceCompanyApp.testLogin("add", "qwe"), "incorrect method");
        assertTrue(insuranceCompanyApp.testLogin("verification", "Test1"), "incorrect method");
    }

    @Test
    public void testPasswordTest() {
        assertFalse(insuranceCompanyApp.testPassword("add", "qwe", "123$"),
                "incorrect method");
        assertTrue(insuranceCompanyApp.testPassword("add", "qwe", "123"),
                "incorrect method");
        assertTrue(insuranceCompanyApp.testPassword("verification", "Test1", "Test1"),
                "incorrect method");
    }

    @Test
    public void pushDataTest() {
        assertTrue(insuranceCompanyApp.pushData(), "incorrect method");
    }
}
