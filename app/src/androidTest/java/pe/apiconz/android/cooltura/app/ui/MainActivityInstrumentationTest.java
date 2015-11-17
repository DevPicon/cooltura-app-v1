package pe.apiconz.android.cooltura.app.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.activities.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

/**
 * Created by Armando on 11/14/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testActionButtonRefresh() {

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onData(
                hasToString(is(
                        activityTestRule.getActivity().getString(R.string.action_refresh)
                )))
                .inRoot(isPlatformPopup()).perform(click());
    }


    @Test
    public void testActionButtonSettings() {

        // Con esta función abrimos el popup menu
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        // Buscamos el elemento que contenga la cadena que buscamos,
        // verificamos el tipo de componente raíz y pulsamos
        onData(
                hasToString(is(
                        activityTestRule.getActivity().getString(R.string.action_settings)
                )))
                .inRoot(isPlatformPopup()).perform(click());

        onData(hasToString(is(
                activityTestRule.getActivity().getString(R.string.pref_locationDialogTitle)
        ))).perform(click());
    }

}
