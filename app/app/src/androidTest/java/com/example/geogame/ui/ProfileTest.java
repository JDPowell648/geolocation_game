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
public class ProfileTest {

    @Rule
    public ActivityTestRule<Profile> mActivityTestRule = new ActivityTestRule<>(Profile.class);

    @Test
    public void logoutButtonExists() {
        testStatements.testFieldExist(R.id.logout);
    }

    @Test
    public void returnMainMenuButtonExists() {
        testStatements.testFieldExist(R.id.BackButton);
    }

    @Test
    public void profileImageExists() {
        testStatements.testFieldExist(R.id.imageView);
    }

    @Test
    public void usernameTextExists() {
        testStatements.testFieldExist(R.id.UsernameText);
    }

    @Test
    public void rankedTextExists() {
        testStatements.testFieldExist(R.id.RankText);
    }

    @Test
    public void gamesPlayedTextExists() {
        testStatements.testFieldExist(R.id.GamesPlayedText);
    }

    @Test
    public void averageScoreTextExists() {
        testStatements.testFieldExist(R.id.AvgScoreText);
    }

    @Test
    public void accuracyTextExists() {
        testStatements.testFieldExist(R.id.AccuracyText);
    }
}
