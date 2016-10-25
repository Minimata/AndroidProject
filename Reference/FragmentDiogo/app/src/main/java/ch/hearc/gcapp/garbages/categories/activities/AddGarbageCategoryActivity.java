package ch.hearc.gcapp.garbages.categories.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.GarbageStore;
import ch.hearc.gcapp.garbages.categories.GarbageCategory;

/**
 * Show the form for a new garbage category.
 *
 * Within this activity, we can:
 *
 *  - create a new garbage category.
 */
public class AddGarbageCategoryActivity extends AppCompatActivity {

    private Button addGarbageCategoryButton;

    private EditText nameEditText;
    private EditText trashCanEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_garbage_category_activity);

        retrieveViews();
        setUpViews();
    }

    /**
     * Retrieve all views inside res/layout/add_garbage_category_activity.xml.
     */
    private void retrieveViews() {
        addGarbageCategoryButton = (Button) findViewById(R.id.addGarbageCategoryButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        trashCanEditText = (EditText) findViewById(R.id.trashCanEditText);
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     *   - being able to create a new garbage by clicking the "Add category" button.
     */
    private void setUpViews() {
        // Create our garbage category when clicking the "Add category" button
        addGarbageCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEditText.getText()) || TextUtils.isEmpty(trashCanEditText.getText())) {
                    // Do not continue if one field is missing data

                    return;
                }

                GarbageCategory garbageCategory = new GarbageCategory(
                        nameEditText.getText().toString(),
                        trashCanEditText.getText().toString()
                );

                GarbageStore.GARBAGE_CATEGORIES.add(garbageCategory);

                finish();
            }
        });
    }
}
