package ru.yandex.job.galleryyandexstajer.activity;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.EnumSet;

import ru.yandex.job.galleryyandexstajer.utils.Settings;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FullImageActivityTest {

    @Rule
    public ActivityTestRule<FullImageActivity> mActivityRule =
            new ActivityTestRule<>(FullImageActivity.class);

    @Before
    public void setup() {
        mActivityRule.getActivity();
        Intents.init();
    }

    @After
    public void finish() {
        Intents.release();
    }

    @Test
    public void test() {

        Intents.intended(IntentMatchers.hasExtraWithKey(Settings.FIELD_IMAGE_CUR));
        Intents.intended(IntentMatchers.hasExtraWithKey(Settings.FIELD_IMAGE_URLS));
    }

}