package com.varsha.lowcost;


import android.view.View;
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
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        manifest = "src/main/AndroidManifest.xml", packageName = "com.varsha.lowcost")

public class CostTest {
    private LowestCostActivity activity;
    private ActivityController<LowestCostActivity> controller;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(LowestCostActivity.class).create().start();
        activity = controller.resume().visible().get();
    }

    private View getViewFromActivity(int id) {
        return activity.findViewById(id);
    }

    @Test
    public void clickingGoForVariousInputs() {
        EditText customGridContents = (EditText) getViewFromActivity(R.id.custom_grid_contents);
        TextView costView = (TextView) getViewFromActivity(R.id.results_total_cost);
        TextView succesView = (TextView) getViewFromActivity(R.id.results_success);
        TextView pathView = (TextView) getViewFromActivity(R.id.results_path_taken);

        customGridContents.setText("3 4 1 2 8 6 \n" +
                "6 1 8 2 7 4 \n" + "5 9 3 9 9 5 \n" + "8 4 1 3 2 6 \n" + "3 7 2 8 6 4");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("16"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("1\t2\t3\t4\t4\t5"));

        customGridContents.setText("3 4 1 2 8 6 \n" +
                "6 1 8 2 7 4 \n" + "5 9 3 9 9 5 \n" + "8 4 1 3 2 6 \n" + "3 7 2 1 2 3");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("11"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("1\t2\t1\t5\t4\t5"));

        customGridContents.setText("19 10 19 10 19 \n" +
                "21 23 20 19 12 \n" + "20 12 20 11 10");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("48"));
        assertThat(succesView.getText().toString(), equalTo("No"));
        assertThat(pathView.getText().toString(), equalTo("1\t1\t1"));

        customGridContents.setText("5 8 5 3 5");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("26"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("1\t1\t1\t1\t1"));

        customGridContents.setText("5 4 H \n" +
                "8 M 7 \n" + "5 7 5");
        getViewFromActivity(R.id.run_button).performClick();

        ShadowAlertDialog alertDialog = Shadows.shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertThat(alertDialog.getTitle().toString(), equalTo("Invalid GridEntry"));
        assertThat(alertDialog.getMessage().toString(), equalTo(activity.getResources().getString(R.string.invalid_grid_message)));

        customGridContents.setText("69 10 19 10 19 \n" +
                "51 23 20 19 12 \n" + "60 12 20 11 10");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("0"));
        assertThat(succesView.getText().toString(), equalTo("No"));
        assertThat(pathView.getText().toString(), equalTo(""));

        customGridContents.setText("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 \n" + "2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("20"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1\t1"));

        customGridContents.setText("5\n" +
                "8\n" + "5\n" + "3\n" + "5");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("3"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("4"));

        customGridContents.setText("60 3 3 6 \n" +
                "6 3 7 9 \n" + "5 6 8 3");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("14"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("3\t2\t1\t3"));

        customGridContents.setText("6,3,-5,9 \n" +
                "-5,2,4,10 \n" + "3,-2,6,10 \n" + "6,-1,-2,10");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("0"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("2\t3\t4\t1"));

        customGridContents.setText("51,51 \n" +
                "0,51 \n" + "51,51 \n" + "5,5");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("10"));
        assertThat(succesView.getText().toString(), equalTo("Yes"));
        assertThat(pathView.getText().toString(), equalTo("4\t4"));

        customGridContents.setText("51 51 51 \n" + "0 51 51 \n" + "51 51 51 \n" + "5 5 51");
        getViewFromActivity(R.id.run_button).performClick();

        assertThat(costView.getText().toString(), equalTo("10"));
        assertThat(succesView.getText().toString(), equalTo("No"));
        assertThat(pathView.getText().toString(), equalTo("4\t4"));


    }


}
