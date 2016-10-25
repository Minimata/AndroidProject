package ch.hearc.gcapp.garbages.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.hearc.gcapp.garbages.Garbage;
import ch.hearc.gcapp.garbages.GarbageStore;
import ch.hearc.gcapp.garbages.categories.GarbageCategory;
import ch.hearc.gcapp.garbages.categories.activities.ChooseGarbageCategoryActivity;
import ch.hearc.gcapp.R;

/**
 * Show the form for a new garbage.
 *
 * Within this activity, we can:
 *
 *   - create a new garbage;
 *   - start an intent to choose a garbage category.
 */
public class AddGarbageActivity extends AppCompatActivity {

    private static final String TAG = AddGarbageActivity.class.getSimpleName();

    /**
     * Activity result code for "ChooseGarbageCategoryActivity".
     */
    private static final int CHOOSE_CATEGORY_REQUEST_CODE = 0;

    private Button addGarbageButton;
    private Button chooseCategoryButton;

    private EditText nameEditText;

    private TextView categoryNameTextView;

    private GarbageCategory currentCategory;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_garbage_activity);

        retrieveViews();
        setUpViews();
    }

    /**
     * Retrieve all views inside res/layout/add_garbage_activity.xml.
     */
    private void retrieveViews() {
        addGarbageButton = (Button) findViewById(R.id.addGarbageButton);
        chooseCategoryButton = (Button) findViewById(R.id.chooseCategoryButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        categoryNameTextView = (TextView) findViewById(R.id.categoryNameTextView);
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     *   - being able to choose a garbage category by clicking the "Choose category" button;
     *   - being able to create a new garbage by clicking the "Add garbage" button.
     */
    private void setUpViews() {
        // Create our garbage when clicking the "Add garbage" button
        addGarbageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEditText.getText()) || currentCategory == null) {
                    // Do not continue if one field is missing data
                    Log.w(TAG, "Unable to create a new garbage: one or more fields are empty.");

                    return;
                }

                Garbage garbage = new Garbage(
                        nameEditText.getText().toString(),
                        currentCategory
                );

                GarbageStore.GARBAGES.add(garbage);

                finish();
            }
        });

        // Start the activity to choose a category when clicking the "Choose category" button
        chooseCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGarbageActivity.this, ChooseGarbageCategoryActivity.class);

                startActivityForResult(intent, CHOOSE_CATEGORY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Here we retrieve our activity results
        if (requestCode == CHOOSE_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            // The user choose a garbage category and everything is ok

            String garbageCategoryName = data.getStringExtra("garbageCategoryName");

            if (garbageCategoryName != null) {
                currentCategory = GarbageStore.findGarbageCategoryByName(garbageCategoryName);

                categoryNameTextView.setText(currentCategory.getName());
            }
        }
    }
}
