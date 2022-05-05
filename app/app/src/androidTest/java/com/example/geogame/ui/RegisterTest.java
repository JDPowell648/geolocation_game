package com.example.geogame.ui;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.geogame.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityTestRule<Register> mActivityTestRule = new ActivityTestRule<>(Register.class);

    @Test
    public void editFieldEmailExists() {
        testStatements.testFieldExist(R.id.inputEmailAddress);
    }

    @Test
    public void editFieldUsernameExists() {
        testStatements.testFieldExist(R.id.inputUsername);
    }

    @Test
    public void editFieldPasswordExists() {
        testStatements.testFieldExist(R.id.inputPassword);
    }

    @Test
    public void createAccountButtonExists() {
        testStatements.testFieldExist(R.id.create_account);
    }

    //Email Tests
    @Test
    public void allCharTypeEmailCATest() {
        testStatements.testRegister("aA0._-@a.a", "example123",
                "password123", R.string.creating_account);
    }

    @Test
    public void invalid1EmailCATest() {
        testStatements.testRegister("@email.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void invalid2EmailCATest() {
        testStatements.testRegister("example@.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void invalid3EmailCATest() {
        testStatements.testRegister("example@email.", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void space1EmailCATest() {
        testStatements.testRegister("exa mple@email.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void space2EmailCATest() {
        testStatements.testRegister("example@em ail.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void space3EmailCATest() {
        testStatements.testRegister("example@email.co m", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void special1EmailCATest() {
        testStatements.testRegister("exa!mple@email.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void special2EmailCATest() {
        testStatements.testRegister("example@em!ail.com", "example123",
                "password123", R.string.invalid_email);
    }

    @Test
    public void special3EmailCATest() {
        testStatements.testRegister("example@email.co!m", "example123",
                "password123", R.string.invalid_email);
    }

    //No input tests
    @Test
    public void allInputTest() {
        testStatements.testRegister("example@email.com", "example123",
                "password123", R.string.creating_account);
    }

    @Test
    public void noInputCA1Test() {
        testStatements.testRegister("", "",
                "", R.string.enter_email);
    }

    @Test
    public void noInputCA2Test() {
        testStatements.testRegister("example@email.com", "",
                "", R.string.enter_username);
    }

    @Test
    public void noInputCA3Test() {
        testStatements.testRegister("example@email.com", "example123",
                "", R.string.enter_password);
    }

    @Test
    public void noInputCA4Test() {
        testStatements.testRegister("", "example123",
                "password123", R.string.enter_email);
    }

    @Test
    public void noInputCA5Test() {
        testStatements.testRegister("", "example123",
                "", R.string.enter_email);
    }

    @Test
    public void noInputCA6Test() {
        testStatements.testRegister("", "",
                "password123", R.string.enter_email);
    }

    @Test
    public void noInputCA7Test() {
        testStatements.testRegister("example@email.com", "",
                "password123", R.string.enter_username);
    }

    //Password tests
    @Test
    public void spacePasswordCATest() {
        testStatements.testRegister("example@email.com", "example123",
                "pass word123", R.string.invalid_password);
    }

    @Test
    public void specialPasswordCATest() {
        testStatements.testRegister("example@email.com", "example123",
                "pass!word123", R.string.invalid_password);
    }

    @Test
    public void numberPasswordCATest() {
        testStatements.testRegister("example@email.com", "example123",
                "12345678", R.string.creating_account);
    }

    @Test
    public void letterPasswordCATest() {
        testStatements.testRegister("example@email.com", "example123",
                "Password", R.string.creating_account);
    }

    @Test
    public void shortPasswordCATest() {
        testStatements.testRegister("example@email.com", "example123",
                "passwor", R.string.invalid_password);

    }

    //Username tests
    @Test
    public void shortUsernameCATest() {
        testStatements.testRegister("example@email.com", "ex1",
                "password123", R.string.invalid_username);
    }

    @Test
    public void letterUsernameTest() {
        testStatements.testRegister("example@email.com", "Example123",
                "password123", R.string.creating_account);
    }

    @Test
    public void spaceUsernameCATest() {
        testStatements.testRegister("example@email.com", "example 123",
                "password123", R.string.invalid_username);
    }

    @Test
    public void specialUsernameCATest() {
        testStatements.testRegister("example@email.com", "example!123",
                "password123", R.string.invalid_username);
    }

    @Test
    public void numberUsernameCATest() {
        testStatements.testRegister("example@email.com", "1234",
                "password123", R.string.creating_account);
    }
}
