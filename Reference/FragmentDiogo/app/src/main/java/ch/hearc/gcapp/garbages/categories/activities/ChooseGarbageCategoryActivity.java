package ch.hearc.gcapp.garbages.categories.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.categories.GarbageCategory;
import ch.hearc.gcapp.garbages.categories.GarbageCategoryAdapter;

/**
 * Show the garbage category list.
 *
 * Within this activity, we can:
 *
 *   - see all garbage categories in our application;
 *   - filter the garbage categories with a search view;
 *   - choose a category (return the result to the garbage list);
 *   - start an intent to create a new garbage category.
 */
public class ChooseGarbageCategoryActivity extends AppCompatActivity {

    private Button addGarbageCategoryButton;

    private ListView garbageCategoryListView;

    private SearchView garbageCategorySearchView;

    private GarbageCategoryAdapter garbageCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_garbage_category_activity);

        retrieveViews();
        setUpViews();
    }

    /**
     * Retrieve all views inside res/layout/choose_garbage_category_activity.xml.
     */
    private void retrieveViews() {
        addGarbageCategoryButton = (Button) findViewById(R.id.addGarbageCategoryButton);
        garbageCategoryListView = (ListView) findViewById(R.id.garbageCategoryListView);
        garbageCategorySearchView = (SearchView) findViewById(R.id.garbageCategorySearchView);
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     *   - being able to filter the garbage category list;
     *   - being able to choose a garbage category to continue the creation of our new garbage;
     *   - being able to add a new garbage category by clicking the "Add" button.
     */
    private void setUpViews() {
        garbageCategoryAdapter = new GarbageCategoryAdapter(this);

        // Tell by which adapter we will handle our list
        garbageCategoryListView.setAdapter(garbageCategoryAdapter);

        garbageCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GarbageCategory garbageCategory = (GarbageCategory) garbageCategoryListView.getItemAtPosition(position);

                Intent intent = getIntent();

                intent.putExtra("garbageCategoryName", garbageCategory.getName());

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        // Miscellaneous configuration for our search view
        garbageCategorySearchView.setSubmitButtonEnabled(true);
        garbageCategorySearchView.setQueryHint("Category name...");

        // The core for the search view: what to do when the text change!
        garbageCategorySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do nothing when clicking the submit button (displayed ">") -> return false

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // When the text change, filter our list of garbage categories

                Filter filter = garbageCategoryAdapter.getFilter();

                if (TextUtils.isEmpty(newText)) {
                    // Empty search field = no filtering
                    filter.filter(null);
                } else {
                    filter.filter(newText);
                }

                // Something was done -> return true instead of false
                return true;
            }
        });

        // Start the activity to add a garbage category when clicking the "Add" button
        addGarbageCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseGarbageCategoryActivity.this, AddGarbageCategoryActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        // Important! Refresh our list when we return to this activity (from another one)
        garbageCategoryAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
