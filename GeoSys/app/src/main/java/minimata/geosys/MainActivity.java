package minimata.geosys;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import minimata.geosys.dummy.DummyContent;

import static android.R.drawable.ic_delete;
import static android.R.drawable.ic_input_add;

public class MainActivity extends AppCompatActivity implements
        GoogleMapFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener,
        TypeFragment.OnListFragmentInteractionListener,
        AlarmFragment.OnListFragmentInteractionListener {

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(INITIAL_PERMS, 1);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        //New alarm button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Alarm selection bottom sheet
        View bottomSheet = findViewById(R.id.bottom_sheet);
        //Bottom sheet behaviour
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        bottomSheet.getLayoutParams().height = height / 2;
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //reset settings
                        fab.setImageResource(ic_input_add);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fab.setImageResource(ic_delete);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.setImageResource(ic_delete);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        //Action button behaviour
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (behavior.getState()) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //delete settings
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        //show new settings
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //delete settings
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;

                    default:
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem dummyItem) {
//        View view = findViewById(R.id.activity_main);
//        Snackbar.make(view, dummyItem.content, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }
}
