package ch.hearc.gcapp.garbages.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.Garbage;
import ch.hearc.gcapp.garbages.GarbageAdapter;

// TODO Move this activity and "GarbageDetailsActivity" into fragments

// TODO Content of this activity and "GarbageDetailsActivity" must be shown side-by-side while the phone is held horizontally (landscape)

/**
 * Show the garbage list.
 *
 * Within this activity, we can:
 *
 *   - see all garbages in our application;
 *   - filter the garbages with a search view;
 *   - start an intent to create a garbage;
 *   - start an intent to see a garbage details.
 */
public class GarbageListActivity extends Fragment {

    private Button addGarbageButton;

    private ListView garbageListView;

    private SearchView garbageSearchView;

    private GarbageAdapter garbageAdapter;


    interface OnGarbageListActivityInteraction {
        void onGarbageClicked(Garbage garbage);
    }

    private OnGarbageListActivityInteraction listener;

    public OnGarbageListActivityInteraction getListener() {
        return listener;
    }

    public void setListener(OnGarbageListActivityInteraction listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.garbage_list_activity, container, false);
        retrieveViews(v);
        setUpViews();

        return v;
    }

    /**
     * Retrieve all views inside res/layout/garbage_list_activity.xml.
     */
    private void retrieveViews(View v) {
        addGarbageButton = (Button) v.findViewById(R.id.addGarbageButton);
        garbageListView = (ListView) v.findViewById(R.id.garbageListView);
        garbageSearchView = (SearchView) v.findViewById(R.id.garbageSearchView);
    }

    /**
     * Construct our logic. What we wants is the following:
     *
     *   - being able to filter the garbage list;
     *   - being able to see a garbage details by clicking an item in the list;
     *   - being able to start the creation of a new garbage by clicking the "Add" button.
     */
    private void setUpViews() {
        garbageAdapter = new GarbageAdapter(getContext());

        // Tell by which adapter we will handle our list
        garbageListView.setAdapter(garbageAdapter);

        // See a garbage details when clicking on it
        garbageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Garbage garbage = (Garbage) garbageListView.getItemAtPosition(position);

                if(listener != null){
                    listener.onGarbageClicked(garbage);
                }
            }
        });

        // Miscellaneous configuration for our search view
        garbageSearchView.setSubmitButtonEnabled(true);
        garbageSearchView.setQueryHint("Garbage name...");

        // The core for the search view: what to do when the text change!
        garbageSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do nothing when clicking the submit button (displayed ">") -> return false

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // When the text change, filter our list of garbages

                Filter filter = garbageAdapter.getFilter();

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

        // Start the activity to add a garbage when clicking the "Add" button
        /*addGarbageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GarbageListActivity.this, AddGarbageActivity.class);

                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        // Important! Refresh our list when we return to this activity (from another one)
        garbageAdapter.notifyDataSetChanged();
    }
}
