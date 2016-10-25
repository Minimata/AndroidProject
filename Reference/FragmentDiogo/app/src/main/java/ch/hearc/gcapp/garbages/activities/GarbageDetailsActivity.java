package ch.hearc.gcapp.garbages.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.Garbage;
import ch.hearc.gcapp.garbages.GarbageStore;

// TODO Move this activity and "GarbageListActivity" into fragments

// TODO Content of this activity and "GarbageListActivity" must be shown side-by-side while the phone is held horizontally (landscape)

/**
 * Show the details for a garbage.
 *
 * Withing this activity, we can... do nothing, except reading.
 */
public class GarbageDetailsActivity extends Fragment {

    private static final String TAG = GarbageDetailsActivity.class.getSimpleName();

    private TextView garbageDetailsTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.garbage_details_activity, container, false);
        retrieveViews(v);
        setUpViews();

        return v;
    }

    /**
     * Retrieve all views inside res/layout/garbage_details_activity.xml.
     */
    private void retrieveViews(View v) {
        garbageDetailsTextView = (TextView) v.findViewById(R.id.garbageDetailsTextView);
    }

    /**
     * Construct our logic.
     *
     * No listener to create, only populate fields.
     */
    private void setUpViews() {
        String garbageName = getArguments().getString("garbageName");

        Garbage garbage = GarbageStore.findGarbageByName(garbageName);

        garbageDetailsTextView.setText(
                String.format(
                        getResources().getString(R.string.garbage_details),
                        garbage.getName(),
                        garbage.getCategory().getName(),
                        garbage.getCategory().getGarbageCanName()
                )
        );
    }
}
