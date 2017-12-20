package com.varsha.lowcost;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.varsha.lowcost.view.LowestCostActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.util.ActivityController;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        manifest = "src/main/AndroidManifest.xml", packageName = "com.varsha.lowcost")
public class LowestCostActivityTest {

    private LowestCostActivity activity;
    private ActivityController<LowestCostActivity> controller;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(LowestCostActivity.class).create().start();
        activity = controller.resume().visible().get();
    }

    @Test
    public void goButtonIsDisabledByDefault() {
        Button goButton = (Button) getViewFromActivity(R.id.run_button);
        assertThat(goButton.isEnabled(), is(false));
    }

    @Test
    public void enteringAnyTextIntoTheCustomGridContentsEnablesTheGoButton() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);
        Button goButton = (Button) getViewFromActivity(R.id.run_button);

        customGridContents.setText("a");

        assertThat(goButton.isEnabled(), is(true));
    }

    @Test
    public void removingAllTextFromTheCustomGridContentsDisablesTheGoButton() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);
        Button goButton = (Button) getViewFromActivity(R.id.run_button);
        customGridContents.setText("a");

        customGridContents.setText("");

        assertThat(goButton.isEnabled(), is(false));
    }

    @Test
    public void clickingGoWithLessThanFiveColumnsOfDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4");
        getViewFromActivity(R.id.run_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid GridEntry"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }

    @Test
    public void clickingGoWithMoreThanOneHundredColumnsOfDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 1; i <= 101; i++) {
            inputBuilder.append(i).append(" ");
        }

        customGridContents.setText(inputBuilder.toString());
        getViewFromActivity(R.id.run_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid GridEntry"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }

    @Test
    public void clickingGoWithNonNumericDataDisplaysErrorMessage() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 b");
        getViewFromActivity(R.id.run_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid GridEntry"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));
    }


    @Test
    public void clickingGoWithValidDataDisplaysYesIfPathSuccessful() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5");
        getViewFromActivity(R.id.run_button).performClick();

        TextView resultsView = (TextView) getViewFromActivity(R.id.results_success);
        assertThat(resultsView.getText().toString(), equalTo("Yes"));
    }

    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysNoIfPathNotSuccessful() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("50 2 3 4 5");
        getViewFromActivity(R.id.run_button).performClick();

        TextView resultsView = (TextView) getViewFromActivity(R.id.results_success);
        assertThat(resultsView.getText().toString(), equalTo("No"));
    }

    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysTotalCostOfPathOnSecondLineOfResults() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5");
        getViewFromActivity(R.id.run_button).performClick();

        TextView resultsView = (TextView) getViewFromActivity(R.id.results_total_cost);
        assertThat(resultsView.getText().toString(), equalTo("15"));
    }

    @Test
    public void clickingGoAfterClickingAGridButtonDisplaysPathTakenOnThirdLineOfResults() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);

        customGridContents.setText("1 2 3 4 5 6\n2 1 2 2 2 2\n3 3 1 3 3 3\n4 4 4 1 1 4\n5 5 5 5 5 1\n6 6 6 6 6 6");
        getViewFromActivity(R.id.run_button).performClick();

        TextView resultsView = (TextView) getViewFromActivity(R.id.results_path_taken);
        assertThat(resultsView.getText().toString(), equalTo("1\t2\t3\t4\t4\t5"));
    }

    private View getViewFromActivity(int id) {
        return activity.findViewById(id);
    }
}