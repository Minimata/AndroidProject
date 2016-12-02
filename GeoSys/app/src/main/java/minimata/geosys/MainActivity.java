package minimata.geosys;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;

import java.io.Serializable;

import minimata.geosys.dummy.DummyContent;

import static android.R.drawable.*;

public class MainActivity extends AppCompatActivity implements
        MapsFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener,
        TypeFragment.OnListFragmentInteractionListener,
        AlarmFragment.OnListFragmentInteractionListener {

    private LocationManager locationManager;
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(INITIAL_PERMS,1);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.Maps_fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            //New alarm button
            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            //Alarm selection bottom sheet
            View bottomSheet = findViewById(R.id.bottom_sheet);
            //Bottom sheet behaviour
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);


            // Pushing MapsFragment
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            MapsFragment mapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Maps_fragment_container, mapsFragment).commit();

            //Alarms Fragment
            AlarmFragment alarmFragment = new AlarmFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Alarm_fragment_container, alarmFragment).commit();


            //The next 2 fragments will interchange in the bottomsheet.
            //Type of alarms / settings fragment
            TypeFragment typeFragment = new TypeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Type_fragment_container, typeFragment).commit();

            SettingFragment settingFragment = new SettingFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.Type_fragment_container, typeFragment).commit();

            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;
            bottomSheet.getLayoutParams().height = (height - 100) / 2;
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    switch (newState){
                        case BottomSheetBehavior.STATE_HIDDEN:
                            //reset settings
                            fab.setImageResource(ic_input_add);
                            break;

                        case BottomSheetBehavior.STATE_EXPANDED:
                            //show settings
                            fab.setImageResource(ic_delete);
                            break;

                        case BottomSheetBehavior.STATE_COLLAPSED:
                            //keep settings
                            fab.setImageResource(ic_delete);
                            break;

                        default:
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
                    switch (behavior.getState()){
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
