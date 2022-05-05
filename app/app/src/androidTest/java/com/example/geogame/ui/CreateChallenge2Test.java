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
public class CreateChallenge2Test {

    @Rule
    public ActivityTestRule<CreateChallenge2> mActivityTestRule = new ActivityTestRule<>(CreateChallenge2.class);

    @Test
    public void backButtonExists() {
        testStatements.testFieldExist(R.id.Back_Button);
    }

    @Test
    public void submitButtonExists() {
        testStatements.testFieldExist(R.id.Submit);
    }

    @Test
    public void streetViewExists() {
        testStatements.testFieldExist(R.id.streetviewpanorama);
    }
}
