package com.example.geogame.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;

import com.example.geogame.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class testStatements {
    public static void testFieldExist(int id) {
        onView(allOf(withId(id))).check(matches(isDisplayed()));
    }

    public static void testRegister(String email, String username, String password, int expected) {
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.inputEmailAddress),
                childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1),
                isDisplayed()));
        appCompatEditText.perform(replaceText(email), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.inputUsername),
                childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2),
                isDisplayed()));
        appCompatEditText2.perform(replaceText(username), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.inputPassword),
                childAtPosition(childAtPosition(withId(android.R.id.content), 0), 3),
                isDisplayed()));
        appCompatEditText3.perform(replaceText(password), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.create_account), withText("Create Account"),
                childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0),
                isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(allOf(withId(R.id.messageBox), withText(expected),
                withParent(withParent(withId(android.R.id.content))), isDisplayed()));
        textView.check(matches(withText(expected)));
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
