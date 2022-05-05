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
public class CreateChallenge1Test {

    @Rule
    public ActivityTestRule<CreateChallenge> mActivityTestRule = new ActivityTestRule<>(CreateChallenge.class);

    @Test
    public void mapExists() {
        testStatements.testFieldExist(R.id.map);
    }

    @Test
    public void backButtonExists() {
        testStatements.testFieldExist(R.id.BackButton);
    }

    @Test
    public void chooseLocationButtonExists() {
        testStatements.testFieldExist(R.id.ChooseLocation);
    }
}