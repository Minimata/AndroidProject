package minimata.geosys;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;

import minimata.geosys.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements
        MapsFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.Maps_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // Pushing MapsFragment
            MapsFragment mapsFragment = new MapsFragment();
            //mapsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Maps_fragment_container, mapsFragment).commit();

            //Settings Fragment
            SettingFragment settingFragment = new SettingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Setting_fragment_container, settingFragment).commit();

            //Alarm selection bottom sheet
            View bottomSheet = findViewById(R.id.bottom_sheet);
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheet.post(new Runnable() {
                @Override
                public void run() {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    // React to state change
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // React to dragging events
                }
            });

            //New alarm button
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (behavior.getState()){
                        case BottomSheetBehavior.STATE_COLLAPSED:
                            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            break;

                        case BottomSheetBehavior.STATE_EXPANDED:
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            break;

                        default:
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem dummyItem) {
        View view = findViewById(R.id.activity_main);
        Snackbar.make(view, dummyItem.content, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
