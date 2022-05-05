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
public class LoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void editFieldUsernameExists() {
        testStatements.testFieldExist(R.id.username);
    }

    @Test
    public void editFieldPasswordExists() {
        testStatements.testFieldExist(R.id.password);
    }

    @Test
    public void registerButtonExists() {
        testStatements.testFieldExist(R.id.register);
    }

    @Test
    public void loginButtonExists() {
        testStatements.testFieldExist(R.id.login);
    }
}
