package com.varsha.lowcost.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.varsha.lowcost.R;
import com.varsha.lowcost.model.GridEntry;
import com.varsha.lowcost.Utils.UtilHelper;
import com.varsha.lowcost.model.GridEntrySearch;
import com.varsha.lowcost.model.LowPath;

import java.util.List;


public class LowestCostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowest_cost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button goButton = (Button) findViewById(R.id.run_button);
        goButton.setOnClickListener(new GoOnClickListener());

        EditText customGridContents = (EditText) findViewById(R.id.custom_grid_contents);
        customGridContents.addTextChangedListener(new GridTextWatcher());
    }

    private String formatPath(LowPath path) {
        StringBuilder builder = new StringBuilder();
        List<Integer> rows = path.getRowsTraversed();

        for (int i = 0; i < rows.size(); i++) {
            builder.append(rows.get(i));
            if (i < rows.size() - 1) {
                builder.append("\t");
            }
        }

        return builder.toString();
    }

    private boolean IsGridContentValid(int[][] contents) {
        if (contents.length < 1 || contents.length > 10 || contents[0].length < 5 || contents[0].length > 100) {
            return false;
        } else {
            return true;
        }
    }

    private void loadGrid(int[][] contents) {
        GridEntry validGrid = new GridEntry(contents);
        GridEntrySearch visitor = new GridEntrySearch(validGrid);
        LowPath bestPath = visitor.getBestPathForGrid();

        if (bestPath.isSuccessful()) {
            ((TextView) findViewById(R.id.results_success)).setText("Yes");
        } else {
            ((TextView) findViewById(R.id.results_success)).setText("No");
        }
        ((TextView) findViewById(R.id.results_total_cost)).setText(Integer.toString(bestPath.getTotalCost()));
        ((TextView) findViewById(R.id.results_path_taken)).setText(formatPath(bestPath));
        ((TextView) findViewById(R.id.grid_contents)).setText(validGrid.asDelimitedString("\t"));
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Invalid GridEntry")
                .setMessage(R.string.invalid_grid_message)
                .setPositiveButton("OK", null)
                .show();
    }

    class GridTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Button goButton = (Button) findViewById(R.id.run_button);
            goButton.setEnabled(!s.toString().isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }

    class GoOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String gridString = ((EditText) findViewById(R.id.custom_grid_contents)).getText().toString();
            int[][] potentialGridContents = UtilHelper.convertFromStringToGridArray(gridString);
            if (IsGridContentValid(potentialGridContents)) {
                loadGrid(potentialGridContents);
            } else {
                showAlert();
            }
        }
    }


}
