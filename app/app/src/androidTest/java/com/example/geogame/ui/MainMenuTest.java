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
public class MainMenuTest {

    @Rule
    public ActivityTestRule<MainMenu> mActivityTestRule = new ActivityTestRule<>(MainMenu.class);

    @Test
    public void profileButtonExists() {
        testStatements.testFieldExist(R.id.profileButton);
    }

    @Test
    public void gameHistoryButtonExists() {
        testStatements.testFieldExist(R.id.historyButton);
    }

    @Test
    public void leaderboardsButtonExists() {
        testStatements.testFieldExist(R.id.leaderboardButton);
    }

    @Test
    public void playGameButtonExists() {
        testStatements.testFieldExist(R.id.playGameButton);
    }

    @Test
    public void createChallengeButtonExists() {
        testStatements.testFieldExist(R.id.createChallengeButton);
    }
}
